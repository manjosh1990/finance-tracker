package com.manjosh.labs.backend.domain;

import com.manjosh.labs.backend.utils.JwtUtil;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final ProfileService profileService;
    private final JwtUtil jwtUtil;

    public Map<String, Object> authenticateAndGenerateToken(final Auth auth) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(auth.email(), auth.password()));
        // generate token
        final String token = jwtUtil.generateToken(auth.email());
        return Map.of("token", token, "user", profileService.getPublicProfile(auth.email()));
    }
}
