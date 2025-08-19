package com.manjosh.labs.backend.domain.categories;

import java.time.LocalDateTime;

public record Category(
        Long id,
        String name,
        String icon,
        String type,
        Long profileId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {}
