package com.example.voterapp.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class MasterAdmin {
  @Id @GeneratedValue Long id;
  String username, password, email, emailServerPassword;
  @OneToMany(mappedBy="masterAdmin") List<Admin> admins;
  // getters/setters
}
