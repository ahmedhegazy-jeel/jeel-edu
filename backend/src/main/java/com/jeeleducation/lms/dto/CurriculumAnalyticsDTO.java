package com.jeeleducation.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for curriculum analytics.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurriculumAnalyticsDTO {

    private Long curriculumId;
    private String curriculumName;
    private String status;
    private Integer totalUnits;
    private Integer totalLessons;
    private Integer totalActivities;
    private Integer totalPoints;
    private Long enrolledStudents;
    private Long completedStudents;
    private Double averageCompletionRate;
    private Double averageScore;
    private Integer totalQuizzes;
    private Double quizPassRate;
}

