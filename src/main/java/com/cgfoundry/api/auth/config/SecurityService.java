package com.cgfoundry.api.auth.config;


import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SecurityService {

    private final PasswordEncoder passwordEncoder;

    public boolean verifyPassword(String encodedPassword, String password) {
        return passwordEncoder.matches(password, encodedPassword);
    }

    public String encrypt(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}
