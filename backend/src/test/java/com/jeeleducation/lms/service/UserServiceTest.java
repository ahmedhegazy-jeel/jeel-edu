package com.jeeleducation.lms.service;

import com.jeeleducation.lms.dto.CreateUserRequest;
import com.jeeleducation.lms.dto.UserDTO;
import com.jeeleducation.lms.entity.Role;
import com.jeeleducation.lms.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for UserService.
 */
@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testCreateUser() {
        CreateUserRequest request = CreateUserRequest.builder()
                .username("newuser")
                .email("newuser@example.com")
                .password("password123")
                .firstName("New")
                .lastName("User")
                .role(Role.STUDENT)
                .build();

        UserDTO created = userService.createUser(request);

        assertNotNull(created);
        assertNotNull(created.getId());
        assertEquals("newuser", created.getUsername());
        assertEquals("newuser@example.com", created.getEmail());
        assertEquals(Role.STUDENT, created.getRole());
        assertTrue(created.getIsActive());
    }

    @Test
    void testCreateUserWithDuplicateUsername() {
        CreateUserRequest request1 = CreateUserRequest.builder()
                .username("duplicate")
                .email("user1@example.com")
                .password("password123")
                .firstName("User")
                .lastName("One")
                .role(Role.STUDENT)
                .build();
        userService.createUser(request1);

        CreateUserRequest request2 = CreateUserRequest.builder()
                .username("duplicate")
                .email("user2@example.com")
                .password("password123")
                .firstName("User")
                .lastName("Two")
                .role(Role.STUDENT)
                .build();

        assertThrows(RuntimeException.class, () -> userService.createUser(request2));
    }

    @Test
    void testCreateUserWithDuplicateEmail() {
        CreateUserRequest request1 = CreateUserRequest.builder()
                .username("user1")
                .email("duplicate@example.com")
                .password("password123")
                .firstName("User")
                .lastName("One")
                .role(Role.STUDENT)
                .build();
        userService.createUser(request1);

        CreateUserRequest request2 = CreateUserRequest.builder()
                .username("user2")
                .email("duplicate@example.com")
                .password("password123")
                .firstName("User")
                .lastName("Two")
                .role(Role.STUDENT)
                .build();

        assertThrows(RuntimeException.class, () -> userService.createUser(request2));
    }

    @Test
    void testGetUsersByRole() {
        CreateUserRequest studentRequest = CreateUserRequest.builder()
                .username("student1")
                .email("student1@example.com")
                .password("password123")
                .firstName("Student")
                .lastName("One")
                .role(Role.STUDENT)
                .build();
        userService.createUser(studentRequest);

        CreateUserRequest teacherRequest = CreateUserRequest.builder()
                .username("teacher1")
                .email("teacher1@example.com")
                .password("password123")
                .firstName("Teacher")
                .lastName("One")
                .role(Role.TEACHER)
                .build();
        userService.createUser(teacherRequest);

        var students = userService.getUsersByRole(Role.STUDENT);
        var teachers = userService.getUsersByRole(Role.TEACHER);

        assertTrue(students.size() >= 1);
        assertTrue(teachers.size() >= 1);
    }
}

