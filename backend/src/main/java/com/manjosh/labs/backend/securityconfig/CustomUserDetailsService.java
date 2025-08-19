package com.manjosh.labs.backend.securityconfig;

import com.manjosh.labs.backend.domain.profile.Profile;
import com.manjosh.labs.backend.domain.profile.ProfileService;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final ProfileService profileService;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        Profile profile = profileService.findProfileByEmail(email);
        if (profile == null) throw new UsernameNotFoundException("User not found");
        return User.builder()
                .username(profile.email())
                .password(profile.password())
                .authorities(Collections.emptyList())
                .build();
    }
}
