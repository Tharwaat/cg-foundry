package com.cgfoundry.api.user;

import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserDto getUser(String email);
}
