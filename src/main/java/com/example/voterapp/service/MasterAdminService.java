package com.example.voterapp.service;

import com.example.voterapp.entity.MasterAdmin;
import com.example.voterapp.dto.RegisterMasterRequest;
import com.example.voterapp.repository.MasterAdminRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MasterAdminService {
    private final MasterAdminRepo repo;
    private final PasswordEncoder encoder;

    public MasterAdminService(MasterAdminRepo repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    public MasterAdmin register(RegisterMasterRequest req) {
        var m = new MasterAdmin();
        m.setUsername(req.username());
        m.setPassword(encoder.encode(req.password()));
        m.setEmail(req.email());
        m.setEmailServerPassword(req.emailServerPassword());
        return repo.save(m);
    }

    public List<MasterAdmin> listAll() {
        return repo.findAll();
    }

    public Optional<MasterAdmin> getById(Long id) {
        return repo.findById(id);
    }

    public Optional<MasterAdmin> update(Long id, RegisterMasterRequest req) {
        return repo.findById(id).map(m -> {
            m.setUsername(req.username());
            m.setPassword(encoder.encode(req.password()));
            m.setEmail(req.email());
            m.setEmailServerPassword(req.emailServerPassword());
            return repo.save(m);
        });
    }

    public boolean delete(Long id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }
}
