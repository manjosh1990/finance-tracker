package com.manjosh.labs.backend.domain.income;

import com.manjosh.labs.backend.domain.categories.CategoryEntity;
import com.manjosh.labs.backend.domain.categories.CategoryService;
import com.manjosh.labs.backend.domain.profile.ProfileEntity;
import com.manjosh.labs.backend.domain.profile.ProfileService;
import com.manjosh.labs.backend.web.exception.ServiceException;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class IncomeService {
    private final IncomeRepository incomeRepository;
    private final ProfileService profileService;
    private final CategoryService categoryService;

    @Transactional
    public Income addIncome(Income income) {
        final ProfileEntity currentProfile = profileService.getCurrentProfile();
        final CategoryEntity categoryEntity = categoryService.getCategoryEntityById(income.categoryId());
        final IncomeEntity incomeEntity = IncomeMapper.toEntity(income, currentProfile, categoryEntity);
        incomeRepository.saveAndFlush(incomeEntity);
        return IncomeMapper.toIncome(incomeEntity);
    }

    @Transactional(readOnly = true)
    public List<Income> getExpensesCurrentMonthExpenseForCurrentProfile() {
        final ProfileEntity currentProfile = profileService.getCurrentProfile();
        final LocalDate now = LocalDate.now();
        final LocalDate startOfMonth = now.withDayOfMonth(1);
        final LocalDate endDate = now.withDayOfMonth(now.lengthOfMonth());
        final List<IncomeEntity> incomeEntities = incomeRepository.findByProfileIdAndTransactionDateBetween(
                currentProfile.getId(), startOfMonth, endDate);
        return incomeEntities.stream().map(IncomeMapper::toIncome).toList();
    }

    @Transactional
    public void deleteIncome(final Long id) {
        final ProfileEntity currentProfile = profileService.getCurrentProfile();
        final IncomeEntity incomeEntity =
                incomeRepository.findById(id).orElseThrow(() -> new ServiceException("Income not found"));
        if (!incomeEntity.getProfile().getId().equals(currentProfile.getId())) {
            throw new ServiceException("Unauthorized, Not allowed to delete income");
        }
        incomeRepository.deleteById(id);
    }
}
