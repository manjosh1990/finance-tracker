package com.manjosh.labs.backend.domain.income;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Income(
        Long id,
        String name,
        String icon,
        String description,
        Long categoryId,
        Long profileId,
        BigDecimal amount,
        String transactionDate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {}
