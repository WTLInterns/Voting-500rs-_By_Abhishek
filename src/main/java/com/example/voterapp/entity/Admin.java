package com.example.voterapp.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Admin {
  @Id @GeneratedValue Long id;
  String username, password, email, emailServerPassword;
  @ManyToOne MasterAdmin masterAdmin;
  @OneToMany(mappedBy="admin") List<Agent> agents;
  // getters/setters
}
