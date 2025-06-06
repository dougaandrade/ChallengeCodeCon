package com.dev.codecon.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.codecon.dto.response.EvaluationResponse;
import com.dev.codecon.services.EvaluationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/evaluation")
@RequiredArgsConstructor
public class EvaluationController {

    private final EvaluationService evaluationService;

    @GetMapping
    public ResponseEntity<EvaluationResponse> evaluate() {
        return ResponseEntity.ok(evaluationService.evaluateAll());
    }

}
