package com.manjosh.labs.backend.domain.authentication;

import com.manjosh.labs.backend.domain.profile.ProfileService;
import com.manjosh.labs.backend.securityconfig.CustomUserDetailsService;
import com.manjosh.labs.backend.utils.JwtUtil;
import com.manjosh.labs.backend.web.exception.AuthenticationFailedException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final ProfileService profileService;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public Map<String, Object> authenticateAndGenerateToken(final Auth auth) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(auth.email(), auth.password()));
        // generate token
        final String token = jwtUtil.generateToken(auth.email());
        String refreshToken = jwtUtil.generateRefreshToken(auth.email());

        return Map.of(
                "token",
                token,
                "user",
                profileService.getPublicProfile(auth.email()),
                "refreshToken",
                refreshToken,
                "message",
                "Login successful");
    }

    public Map<String, Object> refreshToken(final String refreshToken) {
        String username = jwtUtil.extractUsername(refreshToken);
        if (jwtUtil.isTokenExpired(refreshToken)) {
            throw new AuthenticationFailedException("Token expired");
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String newAccessToken = jwtUtil.generateToken(userDetails.getUsername());
        return Map.of("token", newAccessToken, "refreshToken", refreshToken);
    }
}
