package com.example.voterapp.entity;
import jakarta.persistence.*;
@Entity
public class Voter {
  @Id @GeneratedValue Long id;
  String name,address,phone;
  // getters/setters
}
