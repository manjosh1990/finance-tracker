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
    private final EmailService emailService;

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
        final ProfileEntity saved = profileRepository.saveAndFlush(newProfile);
        triggerActivationEmail(saved);
        return ProfileMapper.toProfile(saved);
    }

    private void triggerActivationEmail(final ProfileEntity profile) {
        final String activationLink = "http://localhost:18080/api/v1.0/activate?token=" + profile.getActivationToken();
        final String subject = "FinanceTracker- Account Activation";
        String body = "Please click on the link below to activate your account: " + activationLink;
        emailService.sendEmail(profile.getEmail(), subject, body);
    }
}
