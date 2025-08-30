package com.manjosh.labs.backend.domain;

import java.time.LocalDate;

public record FilteredInput(
        String type, LocalDate startDate, LocalDate endDate, String keyword, String sortField, String sortOrder) {}
