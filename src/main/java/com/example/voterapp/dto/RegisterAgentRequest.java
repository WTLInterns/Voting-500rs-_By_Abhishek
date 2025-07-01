package com.example.voterapp.dto;

public record RegisterAgentRequest(
    String username,
    String password,
    String email,
    Long adminId
) {}
