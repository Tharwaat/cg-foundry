package com.cgfoundry.api.user;

import com.cgfoundry.api.user.student.StudentDto;
import com.cgfoundry.api.user.student.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserAuthService {

    private final StudentService studentService;

    public Optional<UserDto> getUserByEmail(String email) {
        return studentService.findByEmail(email);
    }

    public Authentication getAuthenticatedUser(UserDto user) {
        return new UsernamePasswordAuthenticationToken(user, null, List.of());
    }
}
