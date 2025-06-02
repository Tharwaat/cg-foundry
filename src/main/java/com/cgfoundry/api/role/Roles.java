package com.cgfoundry.api.role;

import lombok.Getter;

@Getter
public enum Roles {
    ADMIN(1),
    STUDENT(2),
    INSTRUCTOR(3);

    private final int id;

    Roles(int id) {
        this.id = id;
    }
}
