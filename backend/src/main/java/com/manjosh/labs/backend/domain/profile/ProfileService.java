package com.manjosh.labs.backend.domain.profile;

import com.manjosh.labs.backend.domain.EmailService;
import com.manjosh.labs.backend.utils.PasswordEncoderSingleTon;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

    @Transactional(readOnly = true)
    public Profile findProfileByEmail(final String email) {
        return profileRepository
                .findByEmail(email)
                .map(profile -> ProfileMapper.toProfile(profile, false))
                .orElse(null);
    }

    @Transactional
    public Profile registerProfile(final Profile profile, final boolean triggerActivationEmail) {
        final ProfileEntity newProfile = ProfileMapper.toProfileEntity(profile);
        newProfile.setActivationToken(UUID.randomUUID().toString());
        newProfile.setPassword(PasswordEncoderSingleTon.INSTANCE.encode(newProfile.getPassword()));
        final ProfileEntity saved = profileRepository.saveAndFlush(newProfile);
        if (triggerActivationEmail) {
            triggerActivationEmail(saved);
        }
        return ProfileMapper.toProfile(saved);
    }

    public boolean isProfileActive(String email) {
        return profileRepository.findByEmail(email).map(ProfileEntity::isActive).orElse(false);
    }

    public Profile getCurrentProfile(final boolean hidePassword) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String email = authentication.getName();
        return profileRepository
                .findByEmail(email)
                .map(profile -> ProfileMapper.toProfile(profile, hidePassword))
                .orElseThrow(() -> new UsernameNotFoundException("Profile not found for email: " + email));
    }

    public Profile getPublicProfile(String email) {
        if (email == null) {
            return getCurrentProfile(true);
        }
        return profileRepository
                .findByEmail(email)
                .map(ProfileMapper::toProfile)
                .orElseThrow(() -> new UsernameNotFoundException("Profile not found for email: " + email));
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
