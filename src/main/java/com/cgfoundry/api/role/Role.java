package com.cgfoundry.api.role;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@ToString
@Entity
@Table(name="role", schema = "users")
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;

    public Role(Roles role) {
        this.id = role.getId();
        this.name = role.name();
    }
}
