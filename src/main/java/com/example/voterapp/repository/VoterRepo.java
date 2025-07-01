package com.example.voterapp.repository;

import com.example.voterapp.entity.Voter;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VoterRepo extends JpaRepository<Voter, Long> {
    List<Voter> findByNameContainingIgnoreCase(String name);
}
