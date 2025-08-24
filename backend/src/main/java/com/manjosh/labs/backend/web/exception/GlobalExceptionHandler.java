package com.manjosh.labs.backend.web.exception;

import java.time.Instant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String ERROR_CATEGORY = "error_category";
    public static final String TIMESTAMP = "timestamp";

    @ExceptionHandler(Exception.class)
    ProblemDetail handleException(Exception e) {
        log.error("In generic exception handler, Exception occurred", e);
        ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        problemDetail.setTitle("Internal server error");
        problemDetail.setProperty(ERROR_CATEGORY, "Generic");
        problemDetail.setProperty(TIMESTAMP, Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(BadCredentialsException.class)
    ProblemDetail handleBadCredentialsException(BadCredentialsException e) {
        log.error("bad credentials exception", e);
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage());
        problemDetail.setTitle("Bad credentials");
        problemDetail.setProperty(ERROR_CATEGORY, "Authentication");
        problemDetail.setProperty(TIMESTAMP, Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    ProblemDetail handleAuthenticationException(AuthenticationFailedException e) {
        log.error("in auth exception", e);
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, e.getMessage());
        problemDetail.setTitle("Invalid credentials");
        problemDetail.setProperty(ERROR_CATEGORY, "Authentication");
        problemDetail.setProperty(TIMESTAMP, Instant.now());
        return problemDetail;
    }
}
