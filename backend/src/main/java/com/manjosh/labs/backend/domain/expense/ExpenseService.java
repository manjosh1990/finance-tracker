package com.manjosh.labs.backend.domain.expense;

import com.manjosh.labs.backend.domain.categories.CategoryEntity;
import com.manjosh.labs.backend.domain.categories.CategoryService;
import com.manjosh.labs.backend.domain.profile.ProfileEntity;
import com.manjosh.labs.backend.domain.profile.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final ProfileService profileService;
    private final CategoryService categoryService;

    @Transactional
    public Expense addExpense(Expense expense) {
        final ProfileEntity currentProfile = profileService.getCurrentProfile();
        log.info("Adding expense: {}", expense);
        log.info("Current profile: {}", currentProfile);
        final CategoryEntity categoryEntity = categoryService.getCategoryEntityById(expense.categoryId());
        log.info("Category entity: {}", categoryEntity);
        final ExpenseEntity expenseEntity = ExpenseMapper.toEntity(expense, currentProfile, categoryEntity);
        expenseRepository.saveAndFlush(expenseEntity);
        return ExpenseMapper.toExpense(expenseEntity);
    }
}
