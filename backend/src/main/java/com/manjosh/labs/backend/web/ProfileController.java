package com.manjosh.labs.backend.web;

import com.manjosh.labs.backend.domain.Profile;
import com.manjosh.labs.backend.domain.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/register")
    ResponseEntity<Profile> registerProfile(@RequestBody final Profile profile) {
        final Profile savedProfile = profileService.registerProfile(profile);
        return ResponseEntity.ok(savedProfile);
    }
}
