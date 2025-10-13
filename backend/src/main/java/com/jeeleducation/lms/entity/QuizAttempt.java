package com.jeeleducation.lms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * QuizAttempt entity tracking individual quiz attempts by students.
 */
@Entity
@Table(name = "quiz_attempts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class QuizAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private QuizActivity quiz;

    @Column(nullable = false)
    private Integer attemptNumber;

    @Column(nullable = false)
    @Builder.Default
    private Integer totalQuestions = 0;

    @Column(nullable = false)
    @Builder.Default
    private Integer correctAnswers = 0;

    @Column(nullable = false)
    @Builder.Default
    private Integer incorrectAnswers = 0;

    @Column(nullable = false)
    @Builder.Default
    private Integer score = 0;

    @Column(nullable = false)
    @Builder.Default
    private Double percentage = 0.0;

    @Column(nullable = false)
    @Builder.Default
    private Boolean passed = false;

    @Column
    private Integer timeSpentSeconds;

    @Column
    private LocalDateTime startedAt;

    @Column
    private LocalDateTime completedAt;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Calculate score and percentage.
     *
     * @param totalPoints Total possible points
     */
    public void calculateScore(Integer totalPoints) {
        if (totalQuestions > 0) {
            this.percentage = (correctAnswers.doubleValue() / totalQuestions.doubleValue()) * 100;
        }
        if (totalPoints != null && totalPoints > 0) {
            this.score = (int) ((correctAnswers.doubleValue() / totalQuestions.doubleValue()) * totalPoints);
        }
    }

    /**
     * Check if student passed based on required percentage.
     *
     * @param requiredPercentage Required percentage to pass
     * @return true if passed, false otherwise
     */
    public boolean checkPassed(Integer requiredPercentage) {
        if (requiredPercentage == null) {
            requiredPercentage = 60; // Default 60%
        }
        this.passed = this.percentage >= requiredPercentage;
        return this.passed;
    }
}

