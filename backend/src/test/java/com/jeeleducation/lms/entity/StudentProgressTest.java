package com.jeeleducation.lms.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for StudentProgress entity.
 */
class StudentProgressTest {

    @Test
    void testIsCompleted() {
        StudentProgress completed = StudentProgress.builder()
                .completedAt(LocalDateTime.now())
                .build();

        StudentProgress inProgress = StudentProgress.builder()
                .completedAt(null)
                .build();

        assertTrue(completed.isCompleted());
        assertFalse(inProgress.isCompleted());
    }

    @Test
    void testUpdateCompletionPercentage() {
        StudentProgress progress = StudentProgress.builder()
                .totalActivities(100)
                .completedActivities(75)
                .build();

        progress.updateCompletionPercentage();

        assertEquals(75.0, progress.getCompletionPercentage());
    }

    @Test
    void testUpdateCompletionPercentageWithZeroTotal() {
        StudentProgress progress = StudentProgress.builder()
                .totalActivities(0)
                .completedActivities(0)
                .build();

        progress.updateCompletionPercentage();

        assertEquals(0.0, progress.getCompletionPercentage());
    }

    @Test
    void testFullCompletion() {
        StudentProgress progress = StudentProgress.builder()
                .totalActivities(50)
                .completedActivities(50)
                .build();

        progress.updateCompletionPercentage();

        assertEquals(100.0, progress.getCompletionPercentage());
    }
}

