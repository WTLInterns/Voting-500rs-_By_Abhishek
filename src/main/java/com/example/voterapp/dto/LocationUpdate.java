package com.example.voterapp.dto;

import java.time.LocalDateTime;
public record LocationUpdate(
  Long agentId,
  double lat,
  double lon,
  LocalDateTime at
){}
