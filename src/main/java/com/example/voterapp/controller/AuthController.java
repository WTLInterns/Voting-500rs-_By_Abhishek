package com.example.voterapp.controller;

import com.example.voterapp.dto.*;
import com.example.voterapp.entity.*;
import com.example.voterapp.service.*;
import com.example.voterapp.security.JwtUtil;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authMgr;
    private final JwtUtil jwtUtil;
    private final MasterAdminService masterSvc;
    private final AdminService adminSvc;
    private final AgentService agentSvc;
    private final CustomUserDetailsService userDetailsSvc;

    public AuthController(AuthenticationManager authMgr,
                          JwtUtil jwtUtil,
                          MasterAdminService masterSvc,
                          AdminService adminSvc,
                          AgentService agentSvc,
                          CustomUserDetailsService userDetailsSvc) {
        this.authMgr = authMgr;
        this.jwtUtil = jwtUtil;
        this.masterSvc = masterSvc;
        this.adminSvc = adminSvc;
        this.agentSvc = agentSvc;
        this.userDetailsSvc = userDetailsSvc;
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest req) {
        authMgr.authenticate(
          new UsernamePasswordAuthenticationToken(req.username(), req.password())
        );
        UserDetails ud = userDetailsSvc.loadUserByUsername(req.username());
        return jwtUtil.generateToken(ud);
    }

    @PostMapping("/register/master")
    public MasterAdmin registerMaster(@RequestBody RegisterMasterRequest req) {
        return masterSvc.register(req);
    }

    @PostMapping("/register/admin")
    public Admin registerAdmin(@RequestBody RegisterAdminRequest req) {
        return adminSvc.register(req);
    }

    @PostMapping("/register/agent")
    public Agent registerAgent(@RequestBody RegisterAgentRequest req) {
        return agentSvc.register(req);
    }
}
