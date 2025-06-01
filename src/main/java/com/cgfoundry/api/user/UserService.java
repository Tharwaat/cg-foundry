package com.cgfoundry.api.user;

import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserDto findByEmail(String email);
    UserDto save(UserDto newUser);
}
