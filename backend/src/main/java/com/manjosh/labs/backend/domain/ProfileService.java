package com.manjosh.labs.backend.domain;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;

    @Transactional(readOnly = true)
    public List<Profile> getAllProfiles() {
        return profileRepository.findAll().stream()
                .map(ProfileMapper::toProfile)
                .toList();
    }

    @Transactional
    public Profile registerProfile(Profile profile) {
        final ProfileEntity newProfile = ProfileMapper.toProfileEntity(profile);
        newProfile.setActivationToken(UUID.randomUUID().toString());
        ProfileEntity saved = profileRepository.save(newProfile);
        return ProfileMapper.toProfile(saved);
    }
}
