package com.cgfoundry.api.role;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleService {

    @PersistenceContext
    private final EntityManager entityManager;

    public Role getRole(Roles role) {
        return entityManager.getReference(Role.class, role.getId());
    }
}
