package com.example.voterapp.service;

import com.example.voterapp.entity.Admin;
import com.example.voterapp.entity.MasterAdmin;
import com.example.voterapp.dto.RegisterAdminRequest;
import com.example.voterapp.repository.AdminRepo;
import com.example.voterapp.repository.MasterAdminRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private final AdminRepo repo;
    private final MasterAdminRepo masterRepo;
    private final PasswordEncoder encoder;
    private final EmailService emailService;

    public AdminService(AdminRepo repo,
                        MasterAdminRepo masterRepo,
                        PasswordEncoder encoder,
                        EmailService emailService) {
        this.repo = repo;
        this.masterRepo = masterRepo;
        this.encoder = encoder;
        this.emailService = emailService;
    }

    public Admin register(RegisterAdminRequest req) {
        MasterAdmin m = masterRepo.findById(req.masterAdminId())
                     .orElseThrow(() -> new IllegalArgumentException("MasterAdmin not found"));
        var a = new Admin();
        a.setUsername(req.username());
        a.setPassword(encoder.encode(req.password()));
        a.setEmail(req.email());
        a.setEmailServerPassword(req.emailServerPassword());
        a.setMasterAdmin(m);
        var saved = repo.save(a);

        // notify MasterAdmin
        String subject = "New Admin Registered";
        String body = String.format("Admin %s has been created under you.", saved.getUsername());
        emailService.sendHtml(m, subject, body, m.getEmail());
        return saved;
    }
}
