package com.manjosh.labs.backend.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Converter(autoApply = false)
public class EncodePasswordConverter implements AttributeConverter<String, String> {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public String convertToDatabaseColumn(final String s) {
        return passwordEncoder.encode(s);
    }

    @Override
    public String convertToEntityAttribute(final String s) {
        return s;
    }
}
