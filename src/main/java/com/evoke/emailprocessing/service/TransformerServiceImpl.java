package com.evoke.emailprocessing.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

/**
 * Implementation of TransformerService for categorizing emails via a Transformer model.
 */
@Service
public class TransformerServiceImpl implements TransformerService {

    @Override
    public String categorizeContent(String content) {
        RestTemplate restTemplate = new RestTemplate();
        String FAST_API_URL = "http://127.0.0.1:8000";
        String url = UriComponentsBuilder.fromHttpUrl(FAST_API_URL)
                .path("/categorize")  // This is the endpoint in your FastAPI application
                .toUriString();

        // You may send the email content as JSON payload
        String jsonBody = "{ \"content\": \""+content+"\"}";

        // Set HTTP headers for JSON
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

        // Make the POST request
        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

        // Extract the category field from the response JSON
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            Map<String, Object> responseBody = response.getBody();
            return responseBody.get("category").toString();
        } else {
            throw new RuntimeException("Failed to categorize email content. Status: " + response.getStatusCode());
        }
    }

//    private final String apiUrl = "https://api-inference.huggingface.co/models/your-model";
//    private final String apiKey = "YOUR_HUGGINGFACE_API_KEY";
//
//    @Override
//    public String categorizeContent(String content) {
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + apiKey);
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        Map<String, String> payload = new HashMap<>();
//        payload.put("inputs", content);
//
//        HttpEntity<Map<String, String>> request = new HttpEntity<>(payload, headers);
//        String response = restTemplate.postForObject(apiUrl, request, String.class);
//
//        return parseCategory(response);
//    }
//
//    private String parseCategory(String response) {
//        return response.contains("insurance") ? "Insurance" : "General";
//    }




}
