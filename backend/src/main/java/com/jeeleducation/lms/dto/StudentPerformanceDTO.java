package com.jeeleducation.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for student performance summary.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentPerformanceDTO {

    private Long studentId;
    private String studentName;
    private String studentEmail;
    private Integer enrolledCurriculums;
    private Integer completedCurriculums;
    private Integer totalActivitiesCompleted;
    private Integer totalPointsEarned;
    private Double averageCompletionRate;
    private Double averageQuizScore;
    private Integer totalQuizzesTaken;
    private Integer totalQuizzesPassed;
    private LocalDateTime lastActiveAt;
    private String performanceLevel; // Excellent, Good, Average, Needs Improvement
}

