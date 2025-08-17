package com.manjosh.labs.backend.domain;

public class AuthenticationFailedException extends RuntimeException {
    public AuthenticationFailedException(String message) {
        super(message);
    }

    public AuthenticationFailedException(Throwable cause) {
        super(cause);
    }

    public AuthenticationFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
