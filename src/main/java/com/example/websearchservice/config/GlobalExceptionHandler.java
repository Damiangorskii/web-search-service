package com.example.websearchservice.config;

import com.example.websearchservice.error.ExternalServiceUnavailableException;
import com.example.websearchservice.error.InvalidExternalResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<String> handleRestClientException(RestClientException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    @ExceptionHandler(ExternalServiceUnavailableException.class)
    public ResponseEntity<String> handleServiceUnavailable(ExternalServiceUnavailableException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidExternalResponseException.class)
    public ResponseEntity<String> handleInvalidResponse(InvalidExternalResponseException ex) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(ex.getMessage());
    }
}
