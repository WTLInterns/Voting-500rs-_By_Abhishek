package com.example.voterapp.dto;

public record RegisterAdminRequest(
    String username,
    String password,
    String email,
    String emailServerPassword,
    Long masterAdminId
) {}
