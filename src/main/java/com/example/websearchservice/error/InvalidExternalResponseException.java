package com.example.websearchservice.error;

public class InvalidExternalResponseException extends RuntimeException {

    public InvalidExternalResponseException(String message) {
        super(message);
    }
}
