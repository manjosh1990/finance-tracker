package com.manjosh.labs.backend.domain.categories;

import com.manjosh.labs.backend.domain.profile.ProfileEntity;

public class CategoryMapper {
    private CategoryMapper() {}

    public static CategoryEntity toEntity(final Category category, final ProfileEntity profile) {
        return CategoryEntity.builder()
                .id(category.id())
                .name(category.name())
                .icon(category.icon())
                .profile(profile)
                .type(category.type())
                .build();
    }

    public static Category toCategory(final CategoryEntity categoryEntity) {
        return new Category(
                categoryEntity.getId(),
                categoryEntity.getName(),
                categoryEntity.getIcon(),
                categoryEntity.getType(),
                categoryEntity.getProfile() == null
                        ? null
                        : categoryEntity.getProfile().getId(),
                categoryEntity.getCreatedAt(),
                categoryEntity.getUpdatedAt());
    }
}
