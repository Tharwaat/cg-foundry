package com.cgfoundry.api.auth.controller;

import com.cgfoundry.api.auth.config.JwtService;
import com.cgfoundry.api.user.UserDto;
import com.cgfoundry.api.user.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;
    private final UserServiceImpl userService;

    public AuthController(JwtService jwtService, UserServiceImpl userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/login")
    ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        UserDto user = userService.getUser(request.email());
        String token = jwtService.generateToken(user.getEmail());
        return ResponseEntity.ok(new LoginResponse(user.getEmail(), token));
    }
}
