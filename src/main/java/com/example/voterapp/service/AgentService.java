package com.example.voterapp.service;

import com.example.voterapp.entity.Agent;
import com.example.voterapp.entity.Admin;
import com.example.voterapp.entity.Voter;
import com.example.voterapp.entity.PaymentRecord;
import com.example.voterapp.dto.RegisterAgentRequest;
import com.example.voterapp.dto.PaymentDto;
import com.example.voterapp.repository.AgentRepo;
import com.example.voterapp.repository.AdminRepo;
import com.example.voterapp.repository.VoterRepo;
import com.example.voterapp.repository.PaymentRecordRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class AgentService {
    public final AgentRepo agentRepo;
    public final AdminRepo adminRepo;
    public final VoterRepo voterRepo;
    public final PaymentRecordRepo payRepo;
    public final PasswordEncoder encoder;
    public final EmailService emailService;

    public AgentService(AgentRepo agentRepo,
                        AdminRepo adminRepo,
                        VoterRepo voterRepo,
                        PaymentRecordRepo payRepo,
                        PasswordEncoder encoder,
                        EmailService emailService) {
        this.agentRepo = agentRepo;
        this.adminRepo = adminRepo;
        this.voterRepo = voterRepo;
        this.payRepo = payRepo;
        this.encoder = encoder;
        this.emailService = emailService;
    }

    public Agent register(RegisterAgentRequest req) {
        Admin admin = adminRepo.findById(req.adminId())
                      .orElseThrow(() -> new IllegalArgumentException("Admin not found"));
        var ag = new Agent();
        ag.setUsername(req.username());
        ag.setPassword(encoder.encode(req.password()));
        ag.setEmail(req.email());
        ag.setAdmin(admin);
        var saved = agentRepo.save(ag);

        // notify Admin
        String subject = "New Agent Registered";
        String body = String.format("Agent %s has been created under you.", saved.getUsername());
        emailService.sendHtml(admin, subject, body, admin.getEmail());
        return saved;
    }

    public PaymentRecord confirmPayment(String username, PaymentDto dto) {
        Agent ag = agentRepo.findByUsername(username)
                   .orElseThrow(() -> new IllegalArgumentException("Agent not found"));
        Voter v = voterRepo.findById(dto.voterId())
                  .orElseThrow(() -> new IllegalArgumentException("Voter not found"));
        var rec = new PaymentRecord();
        rec.setAgent(ag);
        rec.setVoter(v);
        rec.setTimestamp(LocalDateTime.now());
        rec.setLatitude(dto.latitude());
        rec.setLongitude(dto.longitude());
        return payRepo.save(rec);
    }
}
