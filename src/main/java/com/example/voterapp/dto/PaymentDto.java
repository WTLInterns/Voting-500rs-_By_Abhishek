package com.example.voterapp.dto;

public record PaymentDto(
    Long voterId,
    double latitude,
    double longitude
) {}
