package com.jeeleducation.lms.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for JwtUtils.
 */
@SpringBootTest
class JwtUtilsTest {

    @Autowired
    private JwtUtils jwtUtils;

    @Test
    void testGenerateTokenFromUsername() {
        String token = jwtUtils.generateTokenFromUsername("testuser");

        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    void testGetUsernameFromJwtToken() {
        String token = jwtUtils.generateTokenFromUsername("testuser");

        String username = jwtUtils.getUsernameFromJwtToken(token);

        assertEquals("testuser", username);
    }

    @Test
    void testValidateJwtToken() {
        String token = jwtUtils.generateTokenFromUsername("testuser");

        boolean isValid = jwtUtils.validateJwtToken(token);

        assertTrue(isValid);
    }

    @Test
    void testValidateInvalidToken() {
        String invalidToken = "invalid.token.here";

        boolean isValid = jwtUtils.validateJwtToken(invalidToken);

        assertFalse(isValid);
    }

    @Test
    void testGenerateRefreshToken() {
        String refreshToken = jwtUtils.generateRefreshToken("testuser");

        assertNotNull(refreshToken);
        assertTrue(refreshToken.length() > 0);
        assertTrue(jwtUtils.validateJwtToken(refreshToken));
    }

    @Test
    void testIsTokenExpired() {
        String token = jwtUtils.generateTokenFromUsername("testuser");

        boolean isExpired = jwtUtils.isTokenExpired(token);

        assertFalse(isExpired);
    }
}

