package com.cgfoundry.api.auth.controller;

import com.cgfoundry.api.auth.controller.dto.LoginRequest;
import com.cgfoundry.api.auth.controller.dto.LoginResponse;
import com.cgfoundry.api.auth.controller.dto.StudentRegisterRequest;
import com.cgfoundry.api.exception.UserAlreadyRegisteredException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Auth", description = "Authentication APIs for user.")
public interface AuthControllerApi {

    @Operation(summary = "User login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Logged in successfully",
                    content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "User Not Found"),
            @ApiResponse(responseCode = "400", description = "Wrong email or password")
    })
    @PostMapping("/login")
    ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request);

    @Operation(summary = "User Register")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Student register and create an account.",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = StudentRegisterRequest.class))
                    }),
            @ApiResponse(responseCode = "404", description = "User Not Found"),
            @ApiResponse(responseCode = "400", description = "Wrong email or password")
    })
    @PostMapping("/register")
    ResponseEntity<LoginResponse> register(@Valid @RequestBody StudentRegisterRequest request)
            throws UserAlreadyRegisteredException;
}
