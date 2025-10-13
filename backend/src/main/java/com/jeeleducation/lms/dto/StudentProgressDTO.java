package com.jeeleducation.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for StudentProgress entity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentProgressDTO {

    private Long id;
    private Long studentId;
    private String studentName;
    private Long curriculumId;
    private String curriculumName;
    private Integer totalUnits;
    private Integer completedUnits;
    private Integer totalLessons;
    private Integer completedLessons;
    private Integer totalActivities;
    private Integer completedActivities;
    private Integer totalPoints;
    private Integer earnedPoints;
    private Double completionPercentage;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isCompleted;
}

