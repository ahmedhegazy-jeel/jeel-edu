package com.jeeleducation.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for comprehensive progress summary.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgressSummaryDTO {

    private Long studentId;
    private String studentName;
    private Integer totalCurriculums;
    private Integer completedCurriculums;
    private Integer inProgressCurriculums;
    private Integer totalActivitiesCompleted;
    private Integer totalPointsEarned;
    private Double averageScore;
    private Integer totalQuizzesTaken;
    private Integer totalQuizzesPassed;
    private List<StudentProgressDTO> curriculumProgress;
}

