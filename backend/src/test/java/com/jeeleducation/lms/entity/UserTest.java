package com.jeeleducation.lms.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for User entity.
 */
class UserTest {

    @Test
    void testGetFullName() {
        User user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .build();

        assertEquals("John Doe", user.getFullName());
    }

    @Test
    void testHasRole() {
        User user = User.builder()
                .role(Role.TEACHER)
                .build();

        assertTrue(user.hasRole(Role.TEACHER));
        assertFalse(user.hasRole(Role.STUDENT));
    }

    @Test
    void testIsAdmin() {
        User superAdmin = User.builder()
                .role(Role.SUPER_ADMIN)
                .build();

        User schoolAdmin = User.builder()
                .role(Role.SCHOOL_ADMIN)
                .build();

        User teacher = User.builder()
                .role(Role.TEACHER)
                .build();

        assertTrue(superAdmin.isAdmin());
        assertTrue(schoolAdmin.isAdmin());
        assertFalse(teacher.isAdmin());
    }

    @Test
    void testIsAccountFullyActive() {
        User activeUser = User.builder()
                .isActive(true)
                .isEmailVerified(true)
                .isAccountNonLocked(true)
                .build();

        User inactiveUser = User.builder()
                .isActive(false)
                .isEmailVerified(true)
                .isAccountNonLocked(true)
                .build();

        User lockedUser = User.builder()
                .isActive(true)
                .isEmailVerified(true)
                .isAccountNonLocked(false)
                .build();

        assertTrue(activeUser.isAccountFullyActive());
        assertFalse(inactiveUser.isAccountFullyActive());
        assertFalse(lockedUser.isAccountFullyActive());
    }
}

