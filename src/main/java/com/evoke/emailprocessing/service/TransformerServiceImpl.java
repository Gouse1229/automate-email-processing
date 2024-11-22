package com.evoke.emailprocessing.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of TransformerService for categorizing emails via a Transformer model.
 */
@Service
public class TransformerServiceImpl implements TransformerService {

    private final String apiUrl = "https://api-inference.huggingface.co/models/your-model";
    private final String apiKey = "YOUR_HUGGINGFACE_API_KEY";

    @Override
    public String categorizeContent(String content) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> payload = new HashMap<>();
        payload.put("inputs", content);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(payload, headers);
        String response = restTemplate.postForObject(apiUrl, request, String.class);

        return parseCategory(response);
    }

    private String parseCategory(String response) {
        return response.contains("insurance") ? "Insurance" : "General";
    }
}
