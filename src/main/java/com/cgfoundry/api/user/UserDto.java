package com.cgfoundry.api.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserDto {
    private UUID id;
    private String role;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String country;
    private boolean isActive;
    private boolean isVerified;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
