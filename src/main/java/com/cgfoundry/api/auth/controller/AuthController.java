package com.cgfoundry.api.auth.controller;

import com.cgfoundry.api.auth.config.JwtService;
import com.cgfoundry.api.auth.controller.dto.LoginRequest;
import com.cgfoundry.api.auth.controller.dto.LoginResponse;
import com.cgfoundry.api.auth.controller.dto.StudentRegisterRequest;
import com.cgfoundry.api.user.UserDto;
import com.cgfoundry.api.user.student.StudentDto;
import com.cgfoundry.api.user.student.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;
    private final StudentService userService;

    public AuthController(JwtService jwtService, StudentService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/login")
    ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        Optional<StudentDto> user = userService.findByEmail(request.email());
        if (user.isPresent()) {
            String token = jwtService.generateToken(user.get().getEmail());
            return ResponseEntity.ok(new LoginResponse(user.get().getEmail(), token));
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/register")
    ResponseEntity<LoginResponse> register(@Valid @RequestBody StudentRegisterRequest request) {
        StudentDto newUser = request.toStudentUserDto();
        UserDto user = userService.save(newUser);
        String token = jwtService.generateToken(user.getEmail());
        return ResponseEntity.ok(new LoginResponse(user.getEmail(), token));
    }
}
