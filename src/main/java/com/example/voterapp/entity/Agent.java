package com.example.voterapp.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Agent {
  @Id @GeneratedValue Long id;
  String username, password, email;
  boolean blocked;
  @ManyToOne Admin admin;
  @OneToMany(mappedBy="agent") List<PaymentRecord> payments;
  // getters/setters
}
