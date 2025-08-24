package com.manjosh.labs.backend.domain.income;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record Income(
        Long id,
        String name,
        String icon,
        String description,
        Long categoryId,
        BigDecimal amount,
        LocalDate transactionDate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {}
