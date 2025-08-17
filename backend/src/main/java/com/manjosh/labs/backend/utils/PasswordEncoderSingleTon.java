package com.manjosh.labs.backend.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public enum PasswordEncoderSingleTon {
    INSTANCE;

    private PasswordEncoderSingleTon() {}

    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    public PasswordEncoder getEncoder() {
        return encoder;
    }

    public String encode(final String password) {
        return encoder.encode(password);
    }
}
