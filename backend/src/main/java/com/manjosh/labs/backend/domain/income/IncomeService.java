package com.manjosh.labs.backend.domain.income;

import com.manjosh.labs.backend.domain.categories.CategoryEntity;
import com.manjosh.labs.backend.domain.categories.CategoryService;
import com.manjosh.labs.backend.domain.profile.ProfileEntity;
import com.manjosh.labs.backend.domain.profile.ProfileService;
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
}
