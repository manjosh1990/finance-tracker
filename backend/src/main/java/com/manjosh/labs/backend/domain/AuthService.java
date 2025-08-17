package com.manjosh.labs.backend.domain;

import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;

    public Map<String, Object> authenticateAndGenerateToken(final Auth auth) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(auth.email(), auth.password()));
            // generate token
            return Map.of("token", UUID.randomUUID().toString());
        } catch (Exception e) {
            throw new RuntimeException("Error authenticating user", e);
        }
    }
}
