package com.example.voterapp.controller;

import com.example.voterapp.entity.Agent;
import com.example.voterapp.service.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AgentService agentSvc;

    public AdminController(AgentService agentSvc) {
        this.agentSvc = agentSvc;
    }

    @GetMapping("/agents")
    public List<Agent> listAgents() {
        return agentSvc.agentRepo.findAll();
    }

    @GetMapping("/agent/{id}")
    public Agent getAgent(@PathVariable Long id) {
        return agentSvc.agentRepo.findById(id)
                 .orElseThrow(() -> new IllegalArgumentException("Agent not found"));
    }

    @PostMapping("/agent")
    public Agent createAgent(@RequestBody RegisterAgentRequest req) {
        return agentSvc.register(req);
    }

    @PutMapping("/agent/{id}")
    public Agent updateAgent(@PathVariable Long id, @RequestBody RegisterAgentRequest req) {
        Agent existing = agentSvc.agentRepo.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Agent not found"));
        existing.setUsername(req.username());
        existing.setPassword(agentSvc.encoder.encode(req.password()));
        existing.setEmail(req.email());
        return agentSvc.agentRepo.save(existing);
    }

    @DeleteMapping("/agent/{id}")
    public void deleteAgent(@PathVariable Long id) {
        agentSvc.agentRepo.deleteById(id);
    }
}
