package com.jeeleducation.lms.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for School entity.
 */
class SchoolTest {

    @Test
    void testHasCapacity() {
        School schoolWithCapacity = School.builder()
                .studentCapacity(100)
                .currentStudentCount(50)
                .build();

        School schoolAtCapacity = School.builder()
                .studentCapacity(100)
                .currentStudentCount(100)
                .build();

        School schoolOverCapacity = School.builder()
                .studentCapacity(100)
                .currentStudentCount(110)
                .build();

        assertTrue(schoolWithCapacity.hasCapacity());
        assertFalse(schoolAtCapacity.hasCapacity());
        assertFalse(schoolOverCapacity.hasCapacity());
    }

    @Test
    void testGetAvailableCapacity() {
        School school = School.builder()
                .studentCapacity(200)
                .currentStudentCount(150)
                .build();

        assertEquals(50, school.getAvailableCapacity());
    }

    @Test
    void testGetAvailableCapacityWithNullValues() {
        School school = School.builder()
                .studentCapacity(null)
                .currentStudentCount(null)
                .build();

        assertNull(school.getAvailableCapacity());
    }

    @Test
    void testGetCapacityUtilization() {
        School school = School.builder()
                .studentCapacity(200)
                .currentStudentCount(150)
                .build();

        assertEquals(75.0, school.getCapacityUtilization());
    }

    @Test
    void testGetCapacityUtilizationWithZeroCapacity() {
        School school = School.builder()
                .studentCapacity(0)
                .currentStudentCount(0)
                .build();

        assertEquals(0.0, school.getCapacityUtilization());
    }

    @Test
    void testGetCapacityUtilizationAtFullCapacity() {
        School school = School.builder()
                .studentCapacity(100)
                .currentStudentCount(100)
                .build();

        assertEquals(100.0, school.getCapacityUtilization());
    }
}

