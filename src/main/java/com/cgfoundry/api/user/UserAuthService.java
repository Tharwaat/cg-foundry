package com.cgfoundry.api.user;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserAuthService {
    public Optional<UserDto> getUserByEmail(String email) {
        return Optional.of(new UserDto("ahmed@gmail.com", "Password"));
    }

    public Authentication getAuthenticatedUser(UserDto user) {
        return new UsernamePasswordAuthenticationToken(user, null, List.of());
    }
}
