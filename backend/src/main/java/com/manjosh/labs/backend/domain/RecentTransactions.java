package com.manjosh.labs.backend.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record RecentTransactions(
        Long id,
        Long profileId,
        String icon,
        String name,
        BigDecimal amount,
        LocalDate transactionDate,
        String description,
        String type,
        LocalDateTime createdTime,
        LocalDateTime updatedTime) {}
