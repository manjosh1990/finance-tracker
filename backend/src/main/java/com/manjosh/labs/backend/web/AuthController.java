package com.manjosh.labs.backend.web;

import com.manjosh.labs.backend.domain.Auth;
import com.manjosh.labs.backend.domain.AuthService;
import com.manjosh.labs.backend.domain.ProfileService;
import com.manjosh.labs.backend.domain.RefreshToken;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthController {
    private final ProfileService profileService;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody final Auth auth) {
        if (!profileService.isProfileActive(auth.email())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "Profile not activated"));
        }
        Map<String, Object> response = authService.authenticateAndGenerateToken(auth);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, Object>> refreshToken(@RequestBody final RefreshToken refreshToken) {
        Map<String, Object> response = authService.refreshToken(refreshToken.refreshToken());
        return ResponseEntity.ok(response);
    }
}
