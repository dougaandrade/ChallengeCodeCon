package com.dev.codecon.entity;

import java.time.LocalDate;

import com.dev.codecon.enums.Action;

import lombok.Data;

@Data
public class Logs {

  private LocalDate date;
  private Action action;

}
