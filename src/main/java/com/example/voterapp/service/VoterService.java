package com.example.voterapp.service;

import com.example.voterapp.entity.Voter;
import com.example.voterapp.repository.VoterRepo;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VoterService {
    private final VoterRepo voterRepo;
    public VoterService(VoterRepo voterRepo) {
        this.voterRepo = voterRepo;
    }
    public List<Voter> searchByLastName(String lastName) {
        return voterRepo.findByNameContainingIgnoreCase(lastName);
    }
}
