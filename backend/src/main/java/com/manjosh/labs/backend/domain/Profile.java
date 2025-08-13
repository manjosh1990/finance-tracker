package com.manjosh.labs.backend.domain;

public record Profile(
        Long id,
        String fullName,
        String email,
        String password,
        String profileImageUrl,
        long createdAt,
        long updatedAt) {}
