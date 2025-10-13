package com.jeeleducation.lms.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for QuizAttempt entity.
 */
class QuizAttemptTest {

    @Test
    void testCalculateScore() {
        QuizAttempt attempt = QuizAttempt.builder()
                .totalQuestions(10)
                .correctAnswers(8)
                .incorrectAnswers(2)
                .build();

        attempt.calculateScore(100);

        assertEquals(80, attempt.getScore());
        assertEquals(80.0, attempt.getPercentage());
    }

    @Test
    void testCheckPassedWithDefaultPercentage() {
        QuizAttempt attempt = QuizAttempt.builder()
                .totalQuestions(10)
                .correctAnswers(7)
                .incorrectAnswers(3)
                .percentage(70.0)
                .build();

        boolean passed = attempt.checkPassed(null); // Uses default 60%

        assertTrue(passed);
        assertTrue(attempt.getPassed());
    }

    @Test
    void testCheckPassedWithCustomPercentage() {
        QuizAttempt attempt = QuizAttempt.builder()
                .totalQuestions(10)
                .correctAnswers(7)
                .incorrectAnswers(3)
                .percentage(70.0)
                .build();

        boolean passed = attempt.checkPassed(80); // Requires 80%

        assertFalse(passed);
        assertFalse(attempt.getPassed());
    }

    @Test
    void testCheckPassedWithHighScore() {
        QuizAttempt attempt = QuizAttempt.builder()
                .totalQuestions(10)
                .correctAnswers(9)
                .incorrectAnswers(1)
                .percentage(90.0)
                .build();

        boolean passed = attempt.checkPassed(80);

        assertTrue(passed);
        assertTrue(attempt.getPassed());
    }
}

