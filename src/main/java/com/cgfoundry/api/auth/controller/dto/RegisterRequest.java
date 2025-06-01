package com.cgfoundry.api.auth.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @Email
    @NotNull
    @NotBlank
    private String email;
    @NotNull
    @NotBlank
    private String password;
    @NotNull
    @NotBlank
    @JsonProperty(value = "first_name")
    private String firstName;
    @NotNull
    @NotBlank
    @JsonProperty(value = "last_name")
    private String lastName;
    @NotNull
    @NotBlank
    private String country;
    @NotNull
    @NotBlank
    private String phoneNumber;
}
