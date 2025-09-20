package com.manjosh.labs.backend.domain.income;

import com.manjosh.labs.backend.domain.categories.CategoryEntity;
import com.manjosh.labs.backend.domain.profile.ProfileEntity;
import java.time.LocalDate;

public class IncomeMapper {
    private IncomeMapper() {}

    public static IncomeEntity toEntity(
            final Income income, final ProfileEntity profile, final CategoryEntity category) {
        return IncomeEntity.builder()
                .name(income.name())
                .icon(income.icon())
                .id(income.id())
                .profile(profile)
                .category(category)
                .amount(income.amount())
                .transactionDate(LocalDate.parse(income.transactionDate()))
                .description(income.description() == null ? "NA" : income.description())
                .createdAt(income.createdAt())
                .updatedAt(income.updatedAt())
                .build();
    }

    public static Income toIncome(IncomeEntity incomeEntity) {
        return new Income(
                incomeEntity.getId(),
                incomeEntity.getName(),
                incomeEntity.getIcon(),
                incomeEntity.getDescription(),
                incomeEntity.getCategory() != null ? incomeEntity.getCategory().getId() : null,
                incomeEntity.getCategory() != null ? incomeEntity.getCategory().getName() : "NA",
                incomeEntity.getProfile() != null ? incomeEntity.getProfile().getId() : null,
                incomeEntity.getAmount(),
                incomeEntity.getTransactionDate().toString(),
                incomeEntity.getCreatedAt(),
                incomeEntity.getUpdatedAt());
    }
}
