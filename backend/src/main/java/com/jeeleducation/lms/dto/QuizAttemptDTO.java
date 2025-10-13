package com.jeeleducation.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for QuizAttempt entity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizAttemptDTO {

    private Long id;
    private Long studentId;
    private String studentName;
    private Long quizId;
    private String quizTitle;
    private Integer attemptNumber;
    private Integer totalQuestions;
    private Integer correctAnswers;
    private Integer incorrectAnswers;
    private Integer score;
    private Double percentage;
    private Boolean passed;
    private Integer timeSpentSeconds;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private LocalDateTime createdAt;
}

