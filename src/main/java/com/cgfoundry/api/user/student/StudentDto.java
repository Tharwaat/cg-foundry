package com.cgfoundry.api.user.student;

import com.cgfoundry.api.profile.student.model.StudentProfile;
import com.cgfoundry.api.role.Role;
import com.cgfoundry.api.role.Roles;
import com.cgfoundry.api.user.UserDto;
import com.cgfoundry.api.user.model.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class StudentDto extends UserDto {
    private int age;
    private String profession;
    private String education;
    private String interest;
    private String objective;

    public User toNewStudentEntity() {
        return User.builder()
                .email(this.getEmail())
                .firstName(this.getFirstName())
                .lastName(this.getLastName())
                .phoneNumber(this.getPhoneNumber())
                .country(this.getCountry())
                .password(this.getPassword())
                .isActive(false)
                .isVerified(false)
                .build();
    }

    public StudentProfile toNewStudentProfile(User user) {
        return StudentProfile.builder()
                .user(user)
                .age(this.age)
                .profession(this.profession)
                .education(this.education)
                .interest(this.interest)
                .objective(this.objective)
                .build();
    }
}
