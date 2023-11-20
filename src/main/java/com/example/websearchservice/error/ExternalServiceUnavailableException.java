package com.example.websearchservice.error;

public class ExternalServiceUnavailableException extends RuntimeException {

    public ExternalServiceUnavailableException(String message) {
        super(message);
    }
}
