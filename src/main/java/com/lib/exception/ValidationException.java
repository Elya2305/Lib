package com.lib.exception;

public class ValidationException extends CustomUserException {
    public ValidationException() {
    }

    public ValidationException(String message) {
        super(message);
    }
}
