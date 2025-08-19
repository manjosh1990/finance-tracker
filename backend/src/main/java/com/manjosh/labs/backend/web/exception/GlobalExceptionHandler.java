package com.manjosh.labs.backend.web.exception;

import com.manjosh.labs.backend.domain.AuthenticationFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

@RestControllerAdvice
class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String ERROR_CATEGORY = "error_category";
    public static final String TIMESTAMP = "timestamp";

    @ExceptionHandler(Exception.class)
    ProblemDetail handleException(Exception e) {
        ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        problemDetail.setTitle("Internal server error");
        problemDetail.setProperty(ERROR_CATEGORY, "Generic");
        problemDetail.setProperty(TIMESTAMP, Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(BadCredentialsException.class)
    ProblemDetail handleBadCredentialsException(BadCredentialsException e) {
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage());
        problemDetail.setTitle("Bad credentials");
        problemDetail.setProperty(ERROR_CATEGORY, "Authentication");
        problemDetail.setProperty(TIMESTAMP, Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    ProblemDetail handleAuthenticationException(AuthenticationFailedException e) {
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, e.getMessage());
        problemDetail.setTitle("Invalid credentials");
        problemDetail.setProperty(ERROR_CATEGORY, "Authentication");
        problemDetail.setProperty(TIMESTAMP, Instant.now());
        return problemDetail;
    }
}
