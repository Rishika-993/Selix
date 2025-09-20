package com.example.ecommercebackend.exception;

public class APIException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public APIException() {
    }

    public APIException(String message) {
        super(message);
    }

    public APIException(String message, Integer status) {
        super(String.format(message, status));
    }
}
