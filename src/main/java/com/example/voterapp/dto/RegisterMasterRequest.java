package com.example.voterapp.dto;

public record RegisterMasterRequest(
    String username,
    String password,
    String email,
    String emailServerPassword
) {}
