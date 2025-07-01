package com.example.voterapp.service;

import com.example.voterapp.entity.*;
import com.example.voterapp.repository.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final MasterAdminRepo mRepo;
    private final AdminRepo aRepo;
    private final AgentRepo agRepo;

    public CustomUserDetailsService(MasterAdminRepo mRepo,
                                    AdminRepo aRepo,
                                    AgentRepo agRepo) {
        this.mRepo = mRepo;
        this.aRepo = aRepo;
        this.agRepo = agRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // try master
        if (mRepo.findByUsername(username).isPresent()) {
            var m = mRepo.findByUsername(username).get();
            return new org.springframework.security.core.userdetails.User(
                m.getUsername(), m.getPassword(),
                Stream.of(new SimpleGrantedAuthority("ROLE_MASTER")).toList()
            );
        }
        // try admin
        if (aRepo.findByUsername(username).isPresent()) {
            var a = aRepo.findByUsername(username).get();
            return new org.springframework.security.core.userdetails.User(
                a.getUsername(), a.getPassword(),
                Stream.of(new SimpleGrantedAuthority("ROLE_ADMIN")).toList()
            );
        }
        // try agent
        var agOpt = agRepo.findByUsername(username);
        if (agOpt.isPresent()) {
            var ag = agOpt.get();
            if (ag.isBlocked()) {
                throw new DisabledException("Agent is blocked");
            }
            return new org.springframework.security.core.userdetails.User(
                ag.getUsername(), ag.getPassword(),
                Stream.of(new SimpleGrantedAuthority("ROLE_AGENT")).toList()
            );
        }
        throw new UsernameNotFoundException("User not found");
    }
}
