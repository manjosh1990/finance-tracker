package com.manjosh.labs.backend.web;

import com.manjosh.labs.backend.domain.Auth;
import com.manjosh.labs.backend.domain.AuthService;
import com.manjosh.labs.backend.domain.Profile;
import com.manjosh.labs.backend.domain.ProfileService;
import java.util.Map;
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
    private final AuthService authService;

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

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> getPublicProfile(@RequestBody final Auth auth) {
        try {
            if (!profileService.isProfileActive(auth.email())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "Profile not activated"));
            }
            Map<String, Object> response = authService.authenticateAndGenerateToken(auth);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/test")
    public String test() {
        return "test success";
    }
}
