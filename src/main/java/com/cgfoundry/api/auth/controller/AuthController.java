package com.cgfoundry.api.auth.controller;

import com.cgfoundry.api.auth.config.JwtService;
import com.cgfoundry.api.auth.config.SecurityService;
import com.cgfoundry.api.auth.controller.dto.LoginRequest;
import com.cgfoundry.api.auth.controller.dto.LoginResponse;
import com.cgfoundry.api.auth.controller.dto.StudentRegisterRequest;
import com.cgfoundry.api.exception.UserAlreadyRegisteredException;
import com.cgfoundry.api.user.UserDto;
import com.cgfoundry.api.user.student.StudentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final JwtService jwtService;
    private final StudentService studentService;
    private final SecurityService securityService;

    @PostMapping("/login")
    ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        Optional<UserDto> user = studentService.findByEmail(request.email());
        if (user.isPresent()) {
            UserDto foundUser = user.get();
            if (securityService.verifyPassword(foundUser.getPassword(), request.password())) {
                String token = jwtService.generateToken(user.get().getEmail());
                return ResponseEntity.ok(new LoginResponse(user.get().getEmail(), token));
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/register")
    ResponseEntity<LoginResponse> register(@Valid @RequestBody StudentRegisterRequest request)
            throws UserAlreadyRegisteredException {
        log.info("[register()] Check if email is registered.");
        if (studentService.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyRegisteredException("This email is already registered.");
        }
        log.info("[register()] Email is available.");
        UserDto user = studentService.save(request.toStudentUserDto());
        String token = jwtService.generateToken(user.getEmail());
        return ResponseEntity.ok(new LoginResponse(user.getEmail(), token));
    }
}
