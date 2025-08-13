package com.manjosh.labs.backend.domain;

import org.springframework.data.jpa.repository.JpaRepository;

interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {}
