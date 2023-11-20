package com.example.websearchservice.client;

import com.example.websearchservice.dto.ProductDTO;
import com.example.websearchservice.error.ExternalServiceUnavailableException;
import com.example.websearchservice.error.InvalidExternalResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.Arrays;
import java.util.List;

@Service
public class ShopMockServiceClient {

    private final RestTemplate restTemplate;
    private final ShopMockServiceClientConfig config;

    @Autowired
    public ShopMockServiceClient(RestTemplate restTemplate, ShopMockServiceClientConfig config) {
        this.restTemplate = restTemplate;
        this.config = config;
    }

    public List<ProductDTO> getAllProducts() {
        try {
            ResponseEntity<ProductDTO[]> response = restTemplate.getForEntity(config.getUrl(), ProductDTO[].class);
            return Arrays.asList(response.getBody());
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode().is5xxServerError()) {
                throw new ExternalServiceUnavailableException("Shop-mock-service is currently unavailable");
            } else if (e.getStatusCode().is4xxClientError()) {
                throw new InvalidExternalResponseException("Invalid response from shop-mock-service.");
            }
            throw e;
        }
    }
}
