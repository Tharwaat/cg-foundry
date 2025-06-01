package com.cgfoundry.api.auth.controller.dto;

import com.cgfoundry.api.user.student.StudentDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentRegisterRequest extends RegisterRequest {
    @NotNull
    @Min(18)
    private int age;
    @NotNull
    @NotBlank
    private String profession;
    @NotNull
    @NotBlank
    private String education;
    @NotNull
    @NotBlank
    private String interest;
    @NotNull
    @NotBlank
    private String objective;

    public StudentDto toStudentUserDto() {
        return StudentDto.builder()
                .email(this.getEmail())
                .firstName(this.getFirstName())
                .lastName(this.getLastName())
                .phoneNumber(this.getPhoneNumber())
                .country(this.getCountry())
                .password(this.getPassword())
                .age(this.age)
                .profession(this.profession)
                .education(this.education)
                .interest(this.interest)
                .objective(this.objective)
                .isActive(false)
                .isVerified(false)
                .build();
    }
}
