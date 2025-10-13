package com.jeeleducation.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * DTO for system-wide statistics.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemStatsDTO {

    // User statistics
    private Long totalUsers;
    private Long activeUsers;
    private Long totalStudents;
    private Long totalTeachers;
    private Long totalParents;
    private Long totalSchoolAdmins;
    private Long totalSuperAdmins;

    // School statistics
    private Long totalSchools;
    private Long activeSchools;
    private Long totalStudentsEnrolled;

    // Curriculum statistics
    private Long totalCurriculums;
    private Long publishedCurriculums;
    private Long draftCurriculums;
    private Long totalUnits;
    private Long totalLessons;
    private Long totalActivities;

    // Activity type breakdown
    private Map<String, Long> activitiesByType;

    // Progress statistics
    private Long totalEnrollments;
    private Long completedCurriculums;
    private Double averageCompletionRate;
    private Long totalQuizzesTaken;
    private Double averageQuizScore;

    // System health
    private String systemStatus;
    private Long databaseSize;
}

