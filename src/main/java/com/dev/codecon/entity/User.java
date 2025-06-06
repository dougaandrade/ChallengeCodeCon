package com.dev.codecon.entity;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class User {

  private UUID id;
  private String name;
  private int age;
  private int score;
  private boolean active;
  private String country;
  private Team team;
  private List<Logs> logs;
}
