package com.manjosh.labs.backend.domain.expense;

import com.manjosh.labs.backend.domain.categories.CategoryEntity;
import com.manjosh.labs.backend.domain.profile.ProfileEntity;
import java.time.LocalDate;

public class ExpenseMapper {
    private ExpenseMapper() {}

    public static ExpenseEntity toEntity(
            final Expense expense, final ProfileEntity profile, final CategoryEntity category) {
        return ExpenseEntity.builder()
                .name(expense.name())
                .icon(expense.icon())
                .id(expense.id())
                .profile(profile)
                .category(category)
                .amount(expense.amount())
                .transactionDate(LocalDate.parse(expense.transactionDate()))
                .description(expense.description())
                .createdAt(expense.createdAt())
                .updatedAt(expense.updatedAt())
                .build();
    }

    public static Expense toExpense(ExpenseEntity expenseEntity) {
        return new Expense(
                expenseEntity.getId(),
                expenseEntity.getName(),
                expenseEntity.getIcon(),
                expenseEntity.getDescription(),
                expenseEntity.getCategory() != null
                        ? expenseEntity.getCategory().getId()
                        : null,
                expenseEntity.getProfile() != null ? expenseEntity.getProfile().getId() : null,
                expenseEntity.getAmount(),
                expenseEntity.getTransactionDate().toString(),
                expenseEntity.getCreatedAt(),
                expenseEntity.getUpdatedAt());
    }
}
