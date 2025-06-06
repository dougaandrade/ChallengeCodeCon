package com.dev.codecon.entity;

import java.util.List;

import lombok.Data;

@Data
public class Team {

  private String name;
  private boolean leader;
  private List<Projects> projects;

}
