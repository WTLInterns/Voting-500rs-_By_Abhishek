package com.example.voterapp.repository;

import com.example.voterapp.entity.MasterAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MasterAdminRepo extends JpaRepository<MasterAdmin, Long> {
    Optional<MasterAdmin> findByUsername(String username);
}
