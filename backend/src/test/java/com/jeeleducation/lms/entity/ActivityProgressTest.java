package com.jeeleducation.lms.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for ActivityProgress entity.
 */
class ActivityProgressTest {

    @Test
    void testCalculateScorePercentage() {
        ActivityProgress progress = ActivityProgress.builder()
                .maxScore(100)
                .currentScore(75)
                .build();

        progress.calculateScorePercentage();

        assertEquals(75.0, progress.getScorePercentage());
    }

    @Test
    void testCalculateScorePercentageWithZeroMax() {
        ActivityProgress progress = ActivityProgress.builder()
                .maxScore(0)
                .currentScore(0)
                .build();

        progress.calculateScorePercentage();

        assertEquals(0.0, progress.getScorePercentage());
    }

    @Test
    void testMarkCompleted() {
        ActivityProgress progress = ActivityProgress.builder()
                .isCompleted(false)
                .isPassed(false)
                .build();

        progress.markCompleted(true);

        assertTrue(progress.getIsCompleted());
        assertTrue(progress.getIsPassed());
        assertNotNull(progress.getCompletedAt());
    }
}

