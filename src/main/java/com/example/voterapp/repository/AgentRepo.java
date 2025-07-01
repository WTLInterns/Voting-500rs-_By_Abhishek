package com.example.voterapp.repository;

import com.example.voterapp.entity.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AgentRepo extends JpaRepository<Agent, Long> {
    Optional<Agent> findByUsername(String username);
}
