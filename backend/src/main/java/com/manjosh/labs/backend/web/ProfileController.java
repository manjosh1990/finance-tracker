package com.manjosh.labs.backend.web;

import com.manjosh.labs.backend.domain.profile.Profile;
import com.manjosh.labs.backend.domain.profile.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/register")
    ResponseEntity<Profile> registerProfile(@RequestBody final Profile profile) {
        final Profile savedProfile = profileService.registerProfile(profile, true);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProfile);
    }

    @GetMapping("/profiles")
    ResponseEntity<Iterable<Profile>> getAllProfiles() {
        return ResponseEntity.ok(profileService.getAllProfiles());
    }

    @GetMapping("/activate")
    ResponseEntity<String> activateProfile(@RequestParam final String token) {
        final boolean activated = profileService.activateProfile(token);
        if (!activated) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid activation token");
        }
        return ResponseEntity.ok("Profile activated successfully");
    }

    @GetMapping("/test")
    public String test() {
        return "test success";
    }

    @GetMapping("/profile")
    public ResponseEntity<Profile> getProfile(){
        Profile publicProfile = profileService.getPublicProfile(null);
        return ResponseEntity.ok(publicProfile);
    }
}
