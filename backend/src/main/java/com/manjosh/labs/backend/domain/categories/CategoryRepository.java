package com.manjosh.labs.backend.domain.categories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    List<CategoryEntity> findByProfileId(final Long profileId);

    Optional<CategoryEntity> findByIdAndProfileId(final Long id, final Long profileId);

    List<CategoryEntity> findByTypeAndProfileId(final String type, final Long profileId);

    boolean existsByNameAndProfileId(String name, Long profileId);
}
