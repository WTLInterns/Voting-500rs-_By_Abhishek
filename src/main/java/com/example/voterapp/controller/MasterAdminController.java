package com.example.voterapp.controller;

import com.example.voterapp.entity.MasterAdmin;
import com.example.voterapp.dto.RegisterMasterRequest;
import com.example.voterapp.service.MasterAdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/master")
public class MasterAdminController {

    private final MasterAdminService masterSvc;

    public MasterAdminController(MasterAdminService masterSvc) {
        this.masterSvc = masterSvc;
    }

    /** List all MasterAdmins */
    @GetMapping
    public List<MasterAdmin> listAll() {
        return masterSvc.listAll();
    }

    /** Get one by ID */
    @GetMapping("/{id}")
    public ResponseEntity<MasterAdmin> getById(@PathVariable Long id) {
        return masterSvc.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** Create new MasterAdmin */
    @PostMapping
    public MasterAdmin create(@RequestBody RegisterMasterRequest req) {
        return masterSvc.register(req);
    }

    /** Update existing MasterAdmin */
    @PutMapping("/{id}")
    public ResponseEntity<MasterAdmin> update(@PathVariable Long id,
                                              @RequestBody RegisterMasterRequest req) {
        return masterSvc.update(id, req)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** Delete by ID */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (masterSvc.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
