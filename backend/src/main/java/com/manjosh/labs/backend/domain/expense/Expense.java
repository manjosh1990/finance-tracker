package com.manjosh.labs.backend.domain.expense;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Expense(
        Long id,
        String name,
        String icon,
        String description,
        Long categoryId,
        BigDecimal amount,
        String transactionDate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {}
