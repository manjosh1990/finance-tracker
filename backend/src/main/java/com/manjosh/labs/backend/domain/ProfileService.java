package com.manjosh.labs.backend.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
class ProfileService {
    private final ProfileRepository profileRepository;

    @Transactional(readOnly = true)
    public List<ProfileEntity> getAllProfiles() {
        return profileRepository.findAll();
    }
}
