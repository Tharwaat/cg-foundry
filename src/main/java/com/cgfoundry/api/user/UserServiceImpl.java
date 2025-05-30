package com.cgfoundry.api.user;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public UserDto getUser(String email) {
        return new UserDto("ahmed@gmail.com", "Password");
    }
}
