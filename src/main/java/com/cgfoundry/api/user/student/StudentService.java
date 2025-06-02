package com.cgfoundry.api.user.student;

import com.cgfoundry.api.profile.student.StudentProfileRepository;
import com.cgfoundry.api.profile.student.model.StudentProfile;
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
    private final PasswordEncoder passwordEncoder;

    public Optional<UserDto> findByEmail(String email) {
        Optional<User> student = userRepository.findByEmail(email);
        return student.map(User::toUserDto);
    }

    public boolean verifyPassword(UserDto user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }

    public UserDto save(StudentDto newUser) {
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        User userToSave = newUser.toNewStudentEntity();
        User savedUser = userRepository.save(userToSave);
        StudentProfile studentProfileToSave = newUser.toNewStudentProfile(savedUser);
        studentProfileRepository.save(studentProfileToSave);

        return savedUser.toUserDto();
    }
}
