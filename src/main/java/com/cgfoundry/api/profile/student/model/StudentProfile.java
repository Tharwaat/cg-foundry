package com.cgfoundry.api.profile.student.model;


import com.cgfoundry.api.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Builder
@ToString
@Entity
@Table(name="student_profile", schema = "users")
@AllArgsConstructor
@NoArgsConstructor
public class StudentProfile {
    @Id
    @Column(name = "user_id")
    private UUID id;
    @Column
    private int age;
    @Column
    private String profession;
    @Column
    private String education;
    @Column
    private String interest;
    @Column
    private String objective;
    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
}
