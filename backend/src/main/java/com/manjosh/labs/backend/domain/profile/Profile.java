package com.manjosh.labs.backend.domain.profile;

import java.time.LocalDateTime;

public record Profile(
        Long id,
        String fullName,
        String email,
        String password,
        String profileImageUrl,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String activationCode) {}
