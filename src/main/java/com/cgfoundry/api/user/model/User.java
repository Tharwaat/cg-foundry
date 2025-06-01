package com.cgfoundry.api.user.model;


import com.cgfoundry.api.profile.student.model.StudentProfile;
import com.cgfoundry.api.user.student.StudentDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Setter
@Getter
@Builder
@Entity
@Table(name="user", schema = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column
    private String email;
    @Column
    private String password;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "country")
    private String country;
    @Column(name = "is_active")
    private boolean isActive;
    @Column(name = "is_verified")
    private boolean isVerified;
    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, optional = false)
    private StudentProfile studentProfile;

    public StudentDto toStudentDto() {
        return StudentDto.builder()
                .email(this.getEmail())
                .phoneNumber(this.getPhoneNumber())
                .country(this.getCountry())
                .password(this.getPassword())
                .age(this.studentProfile.getAge())
                .profession(this.studentProfile.getProfession())
                .education(this.studentProfile.getEducation())
                .interest(this.studentProfile.getInterest())
                .objective(this.studentProfile.getObjective())
                .isActive(this.isActive)
                .isVerified(this.isVerified)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }
}