package com.example.voterapp.controller;

import com.example.voterapp.dto.PaymentDto;
import com.example.voterapp.entity.PaymentRecord;
import com.example.voterapp.entity.Voter;
import com.example.voterapp.service.AgentService;
import com.example.voterapp.service.VoterService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agent")
public class AgentController {

    private final VoterService voterSvc;
    private final AgentService agentSvc;
    private final SimpMessagingTemplate wsTemplate;

    public AgentController(VoterService voterSvc,
                           AgentService agentSvc,
                           SimpMessagingTemplate wsTemplate) {
        this.voterSvc = voterSvc;
        this.agentSvc = agentSvc;
        this.wsTemplate = wsTemplate;
    }

    /** Search voters by last name */
    @GetMapping("/voters/search")
    public List<Voter> searchVoters(@RequestParam String lastName) {
        return voterSvc.searchByLastName(lastName);
    }

    /** Confirm payment (captures time, GPS) */
    @PostMapping("/payments/confirm")
    public PaymentRecord confirmPayment(
            @AuthenticationPrincipal UserDetails ud,
            @RequestBody PaymentDto dto) {

        PaymentRecord rec = agentSvc.confirmPayment(ud.getUsername(), dto);

        // broadcast new location to WebSocket subscribers
        wsTemplate.convertAndSend(
            "/topic/locations",
            new com.example.voterapp.dto.LocationUpdate(
                rec.getAgent().getId(),
                rec.getLatitude(),
                rec.getLongitude(),
                rec.getTimestamp()
            )
        );

        return rec;
    }
}
