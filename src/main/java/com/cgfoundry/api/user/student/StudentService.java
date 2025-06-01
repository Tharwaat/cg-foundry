package com.cgfoundry.api.user.student;

import com.cgfoundry.api.profile.student.StudentProfileRepository;
import com.cgfoundry.api.profile.student.model.StudentProfile;
import com.cgfoundry.api.user.UserDto;
import com.cgfoundry.api.user.UserRepository;
import com.cgfoundry.api.user.model.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class StudentService  {

    private final UserRepository userRepository;
    private final StudentProfileRepository studentProfileRepository;

    public Optional<StudentDto> findByEmail(String email) {
        Optional<User> student = userRepository.findByEmail(email);
        return student.map(User::toStudentDto);
    }

    public StudentDto save(StudentDto newUser) {
        User userToSave = newUser.toNewStudentEntity();
        User savedUser = userRepository.save(userToSave);

        StudentProfile studentProfileToSave = newUser.toNewStudentProfile();
        studentProfileToSave.setUser(savedUser);
        StudentProfile savedStudentProfile = studentProfileRepository.save(studentProfileToSave);
        savedUser.setStudentProfile(savedStudentProfile);

        return savedUser.toStudentDto();
    }
}
