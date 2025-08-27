package com.manjosh.labs.backend.domain.expense;

import com.manjosh.labs.backend.domain.categories.CategoryEntity;
import com.manjosh.labs.backend.domain.categories.CategoryService;
import com.manjosh.labs.backend.domain.profile.ProfileEntity;
import com.manjosh.labs.backend.domain.profile.ProfileService;
import com.manjosh.labs.backend.web.exception.ServiceException;
import java.time.LocalDate;
import java.util.List;
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

    @Transactional(readOnly = true)
    public List<Expense> getExpensesCurrentMonthExpenseForCurrentProfile() {
        final ProfileEntity currentProfile = profileService.getCurrentProfile();
        final LocalDate now = LocalDate.now();
        final LocalDate startOfMonth = now.withDayOfMonth(1);
        final LocalDate endDate = now.withDayOfMonth(now.lengthOfMonth());
        final List<ExpenseEntity> expenseEntities = expenseRepository.findByProfileIdAndTransactionDateBetween(
                currentProfile.getId(), startOfMonth, endDate);
        return expenseEntities.stream().map(ExpenseMapper::toExpense).toList();
    }

    @Transactional
    public void deleteExpense(final Long id) {
        final ProfileEntity currentProfile = profileService.getCurrentProfile();
        final ExpenseEntity expenseEntity =
                expenseRepository.findById(id).orElseThrow(() -> new ServiceException("Income not found"));
        if (!expenseEntity.getProfile().getId().equals(currentProfile.getId())) {
            throw new ServiceException("Unauthorized, Not allowed to delete expense");
        }
        expenseRepository.deleteById(id);
    }
}
