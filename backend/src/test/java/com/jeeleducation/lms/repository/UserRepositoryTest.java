package com.jeeleducation.lms.repository;

import com.jeeleducation.lms.entity.Role;
import com.jeeleducation.lms.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for UserRepository.
 */
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByUsername() {
        User user = User.builder()
                .username("testuser")
                .email("test@example.com")
                .password("password")
                .firstName("Test")
                .lastName("User")
                .role(Role.STUDENT)
                .isActive(true)
                .isEmailVerified(true)
                .isAccountNonLocked(true)
                .build();
        entityManager.persist(user);
        entityManager.flush();

        Optional<User> found = userRepository.findByUsername("testuser");

        assertTrue(found.isPresent());
        assertEquals("testuser", found.get().getUsername());
    }

    @Test
    void testFindByEmail() {
        User user = User.builder()
                .username("testuser")
                .email("test@example.com")
                .password("password")
                .firstName("Test")
                .lastName("User")
                .role(Role.STUDENT)
                .isActive(true)
                .isEmailVerified(true)
                .isAccountNonLocked(true)
                .build();
        entityManager.persist(user);
        entityManager.flush();

        Optional<User> found = userRepository.findByEmail("test@example.com");

        assertTrue(found.isPresent());
        assertEquals("test@example.com", found.get().getEmail());
    }

    @Test
    void testFindByRole() {
        User student = User.builder()
                .username("student1")
                .email("student1@example.com")
                .password("password")
                .firstName("Student")
                .lastName("One")
                .role(Role.STUDENT)
                .isActive(true)
                .isEmailVerified(true)
                .isAccountNonLocked(true)
                .build();
        entityManager.persist(student);

        User teacher = User.builder()
                .username("teacher1")
                .email("teacher1@example.com")
                .password("password")
                .firstName("Teacher")
                .lastName("One")
                .role(Role.TEACHER)
                .isActive(true)
                .isEmailVerified(true)
                .isAccountNonLocked(true)
                .build();
        entityManager.persist(teacher);
        entityManager.flush();

        List<User> students = userRepository.findByRole(Role.STUDENT);
        List<User> teachers = userRepository.findByRole(Role.TEACHER);

        assertEquals(1, students.size());
        assertEquals(1, teachers.size());
        assertEquals(Role.STUDENT, students.get(0).getRole());
        assertEquals(Role.TEACHER, teachers.get(0).getRole());
    }

    @Test
    void testExistsByUsername() {
        User user = User.builder()
                .username("existinguser")
                .email("existing@example.com")
                .password("password")
                .firstName("Existing")
                .lastName("User")
                .role(Role.STUDENT)
                .isActive(true)
                .isEmailVerified(true)
                .isAccountNonLocked(true)
                .build();
        entityManager.persist(user);
        entityManager.flush();

        assertTrue(userRepository.existsByUsername("existinguser"));
        assertFalse(userRepository.existsByUsername("nonexistent"));
    }

    @Test
    void testCountByRole() {
        User student1 = User.builder()
                .username("student1")
                .email("student1@example.com")
                .password("password")
                .firstName("Student")
                .lastName("One")
                .role(Role.STUDENT)
                .isActive(true)
                .isEmailVerified(true)
                .isAccountNonLocked(true)
                .build();
        entityManager.persist(student1);

        User student2 = User.builder()
                .username("student2")
                .email("student2@example.com")
                .password("password")
                .firstName("Student")
                .lastName("Two")
                .role(Role.STUDENT)
                .isActive(true)
                .isEmailVerified(true)
                .isAccountNonLocked(true)
                .build();
        entityManager.persist(student2);
        entityManager.flush();

        long count = userRepository.countByRole(Role.STUDENT);

        assertTrue(count >= 2);
    }
}

