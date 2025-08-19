package com.manjosh.labs.backend.web.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(org.springframework.http.HttpStatus.BAD_REQUEST)
public class CategoryException extends RuntimeException {
    public CategoryException(String message) {
        super(message);
    }
}
