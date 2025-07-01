package com.example.voterapp.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class PaymentRecord {
  @Id @GeneratedValue Long id;
  @ManyToOne Agent agent;
  @ManyToOne Voter voter;
  LocalDateTime timestamp;
  double latitude, longitude;
  // getters/setters
}
