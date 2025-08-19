package com.manjosh.labs.backend.domain.profile;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {
    Optional<ProfileEntity> findByEmail(String email);

    Optional<ProfileEntity> findByActivationToken(String activationToken);
}
