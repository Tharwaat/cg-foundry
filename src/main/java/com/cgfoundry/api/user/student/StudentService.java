package com.cgfoundry.api.user.student;

import com.cgfoundry.api.auth.config.SecurityService;
import com.cgfoundry.api.profile.student.StudentProfileRepository;
import com.cgfoundry.api.profile.student.model.StudentProfile;
import com.cgfoundry.api.role.RoleService;
import com.cgfoundry.api.role.Roles;
import com.cgfoundry.api.user.UserDto;
import com.cgfoundry.api.user.UserRepository;
import com.cgfoundry.api.user.model.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class StudentService  {

    private final UserRepository userRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final SecurityService securityService;
    private final RoleService roleService;

    public Optional<UserDto> findByEmail(String email) {
        Optional<User> student = userRepository.findByEmail(email);
        return student.map(User::toUserDto);
    }

    public UserDto save(StudentDto newUser) {
        User userToSave = newUser.toNewStudentEntity();
        userToSave.setPassword(securityService.encrypt(newUser.getPassword()));
        userToSave.setRole(roleService.getRole(Roles.STUDENT));
        log.info("[save()] Saving user: {}", userToSave);
        User savedUser = userRepository.save(userToSave);

        log.info("[save()] Saving profile: {}", userToSave.getId());
        StudentProfile studentProfileToSave = newUser.toNewStudentProfile(savedUser);
        studentProfileRepository.save(studentProfileToSave);

        return savedUser.toUserDto();
    }
}
