package com.dev.codecon.dto.response;

import java.time.Instant;
import java.util.List;

import com.dev.codecon.dto.Evaluation;

import lombok.Data;

@Data

public class EvaluationResponse {

    private Instant timestamp;
    private long execution_time_ms;
    private List<Evaluation> results;
}
