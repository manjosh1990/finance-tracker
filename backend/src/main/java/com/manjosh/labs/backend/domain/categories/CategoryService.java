package com.manjosh.labs.backend.domain.categories;

import com.manjosh.labs.backend.domain.profile.ProfileEntity;
import com.manjosh.labs.backend.domain.profile.ProfileService;
import com.manjosh.labs.backend.web.exception.CategoryException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProfileService profileService;

    @Transactional(readOnly = true)
    public Category saveCategory(Category category) {
        final ProfileEntity currentProfile = profileService.getCurrentProfile();
        if (categoryRepository.existsByNameAndProfileId(category.name(), currentProfile.getId())) {
            throw new CategoryException("Category already exists");
        }
        CategoryEntity categoryEntity = CategoryMapper.toEntity(category, currentProfile);
        categoryRepository.saveAndFlush(categoryEntity);
        return CategoryMapper.toCategory(categoryEntity);
    }
}
