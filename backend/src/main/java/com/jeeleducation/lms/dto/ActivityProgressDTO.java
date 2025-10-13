package com.jeeleducation.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for ActivityProgress entity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityProgressDTO {

    private Long id;
    private Long studentId;
    private String studentName;
    private Long activityId;
    private String activityTitle;
    private String activityType;
    private Boolean isCompleted;
    private Boolean isPassed;
    private Integer earnedPoints;
    private Integer attemptCount;
    private Integer maxScore;
    private Integer currentScore;
    private Double scorePercentage;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private LocalDateTime lastAccessedAt;
    private Integer timeSpentSeconds;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

