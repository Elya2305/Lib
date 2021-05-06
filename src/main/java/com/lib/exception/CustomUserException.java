package com.lib.exception;

public class CustomUserException extends RuntimeException {
    public CustomUserException() {
    }

    public CustomUserException(String message) {
        super(message);
    }
}
