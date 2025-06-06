package com.dev.codecon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Evaluation {

    private String endpoint;
    private int status;
    private boolean valid_json;
    private long time_ms;
}
