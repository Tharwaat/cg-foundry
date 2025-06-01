package com.cgfoundry.api.user;

import com.cgfoundry.api.user.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public UserDto findByEmail(String email) {
        return new UserDto("ahmed@gmail.com", "Password");
    }

    @Override
    public UserDto save(UserDto newUser) {
        User userToSave = newUser.toNewUserEntity();
        return UserDto.builder().build();
    }
}
