package com.example.voterapp.config;

import com.example.voterapp.security.JwtAuthEntry;
import com.example.voterapp.security.JwtAuthFilter;
import com.example.voterapp.service.CustomUserDetailsService;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final CustomUserDetailsService uds;
    private final JwtAuthEntry entryPoint;
    private final JwtAuthFilter filter;

    public SecurityConfig(CustomUserDetailsService uds,
                          JwtAuthEntry entryPoint,
                          JwtAuthFilter filter) {
        this.uds = uds;
        this.entryPoint = entryPoint;
        this.filter = filter;
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                   .userDetailsService(uds)
                   .passwordEncoder(new BCryptPasswordEncoder())
                   .and().build();
    }

    @Bean
    public SecurityFilterChain chain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .exceptionHandling().authenticationEntryPoint(entryPoint).and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeRequests(auth -> auth
                .antMatchers("/ws/**").permitAll()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/master/**").hasRole("MASTER")
                .antMatchers("/api/admin/**").hasRole("ADMIN")
                .antMatchers("/api/agent/**").hasRole("AGENT")
                .anyRequest().authenticated()
            )
            .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
