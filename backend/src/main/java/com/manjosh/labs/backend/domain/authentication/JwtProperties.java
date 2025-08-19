package com.manjosh.labs.backend.domain.authentication;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(String secret, long expirationInMs, long refreshExpirationInMs) {}
