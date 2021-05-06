package com.lib.exception;

public class NoSuchElementException extends CustomUserException {
    public NoSuchElementException() {
    }

    public NoSuchElementException(String message) {
        super(message);
    }
}
