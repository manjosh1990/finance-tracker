package com.manjosh.labs.backend.domain.categories;

import com.manjosh.labs.backend.domain.profile.ProfileEntity;
import com.manjosh.labs.backend.domain.profile.ProfileService;
import com.manjosh.labs.backend.web.exception.CategoryException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProfileService profileService;

    @Transactional()
    public Category saveCategory(Category category) {
        final ProfileEntity currentProfile = profileService.getCurrentProfile();
        if (categoryRepository.existsByNameAndProfileId(category.name(), currentProfile.getId())) {
            throw new CategoryException("Category already exists");
        }
        CategoryEntity categoryEntity = CategoryMapper.toEntity(category, currentProfile);
        categoryRepository.saveAndFlush(categoryEntity);
        return CategoryMapper.toCategory(categoryEntity);
    }

    @Transactional(readOnly = true)
    public List<Category> getCategoriesForCurrentProfile() {
        final ProfileEntity currentProfile = profileService.getCurrentProfile();
        return categoryRepository.findByProfileId(currentProfile.getId()).stream()
                .map(CategoryMapper::toCategory)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<Category> getCategoriesByTypeForCurrentProfile(final String type) {
        List<CategoryEntity> byTypeAndProfileId = categoryRepository.findByTypeAndProfileId(
                type, profileService.getCurrentProfile().getId());
        return byTypeAndProfileId.stream().map(CategoryMapper::toCategory).toList();
    }

    @Transactional
    public Category updateCategory(Category category) {
        ProfileEntity currentProfile = profileService.getCurrentProfile();
        categoryRepository
                .findByIdAndProfileId(category.id(), currentProfile.getId())
                .orElseThrow(() -> new CategoryException("Category not found"));
        CategoryEntity categoryEntity = CategoryMapper.toEntity(category, currentProfile);
        categoryRepository.saveAndFlush(categoryEntity);
        return CategoryMapper.toCategory(categoryEntity);
    }

    @Transactional(readOnly = true)
    public CategoryEntity getCategoryEntityById(final Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryException("Category not found"));
    }
}
