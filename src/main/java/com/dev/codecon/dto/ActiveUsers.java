package com.dev.codecon.dto;

import java.time.Instant;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ActiveUsers{ 

    private Instant execution_time_ms;
    private List<Logins> logins;

}