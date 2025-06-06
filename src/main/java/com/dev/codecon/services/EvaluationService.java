package com.dev.codecon.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dev.codecon.dto.Evaluation;
import com.dev.codecon.dto.response.EvaluationResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EvaluationService {

    private static final String BASE_URL = "http://localhost:8080";

    // Lista dos endpoints principais a serem avaliados
    private final List<String> endpoints = List.of(
            "/api/superusers",
            "/api/topcountry",
            "/api/teaminsights",
            "/api/activeusers");

    public EvaluationResponse evaluateAll() {
        Instant start = Instant.now();

        List<Evaluation> results = new ArrayList<>();
        for (String endpoint : endpoints) {
            results.add(testEndpoint(endpoint));
        }

        EvaluationResponse response = new EvaluationResponse();
        response.setTimestamp(Instant.now());
        response.setExecution_time_ms(Duration.between(start, Instant.now()).toMillis());
        response.setResults(results);

        return response;
    }

    private Evaluation testEndpoint(String path) {
        try {
            long start = System.currentTimeMillis();
            URL url = new URL(BASE_URL + path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int status = connection.getResponseCode();
            boolean validJson;

            try (InputStream is = connection.getInputStream()) {
                String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                new ObjectMapper().readTree(body); // verifica se é JSON válido
                validJson = true;
            } catch (Exception e) {
                validJson = false;
            }

            long elapsed = System.currentTimeMillis() - start;

            return new Evaluation(path, status, validJson, elapsed);

        } catch (IOException e) {
            return new Evaluation(path, -1, false, -1);
        }
    }
}
