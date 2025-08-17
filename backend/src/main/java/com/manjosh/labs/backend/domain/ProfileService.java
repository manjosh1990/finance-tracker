package com.manjosh.labs.backend.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final EmailService emailService;

    @Transactional(readOnly = true)
    public List<Profile> getAllProfiles() {
        return profileRepository.findAll().stream()
                .map(ProfileMapper::toProfile)
                .toList();
    }

    @Transactional
    public Profile registerProfile(final Profile profile, final boolean triggerActivationEmail) {
        final ProfileEntity newProfile = ProfileMapper.toProfileEntity(profile);
        newProfile.setActivationToken(UUID.randomUUID().toString());
        final ProfileEntity saved = profileRepository.saveAndFlush(newProfile);
        if (triggerActivationEmail) {
            triggerActivationEmail(saved);
        }
        return ProfileMapper.toProfile(saved);
    }

    private void triggerActivationEmail(final ProfileEntity profile) {
        final String activationLink = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/activate")
                .queryParam("token", profile.getActivationToken())
                .toUriString();
        final String subject = "FinanceTracker- Account Activation";
        String body = "Please click on the link below to activate your account: " + activationLink;
        emailService.sendEmail(profile.getEmail(), subject, body);
    }

    public boolean activateProfile(final String token) {
        return profileRepository
                .findByActivationToken(token)
                .map(profileEntity -> {
                    profileEntity.setActive(true);
                    profileRepository.save(profileEntity);
                    return true;
                })
                .orElse(false);
    }

    public void deleteAllProfiles() {
        profileRepository.deleteAll();
    }
}
