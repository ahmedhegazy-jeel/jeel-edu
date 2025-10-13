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
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * ActivityProgress entity tracking student progress on individual activities.
 */
@Entity
@Table(name = "activity_progress")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ActivityProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id", nullable = false)
    private Activity activity;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isCompleted = false;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isPassed = false;

    @Column
    private Integer earnedPoints;

    @Column
    private Integer attemptCount;

    @Column
    private Integer maxScore;

    @Column
    private Integer currentScore;

    @Column
    private Double scorePercentage;

    @Column
    private LocalDateTime startedAt;

    @Column
    private LocalDateTime completedAt;

    @Column
    private LocalDateTime lastAccessedAt;

    @Column
    private Integer timeSpentSeconds;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    /**
     * Calculate score percentage.
     */
    public void calculateScorePercentage() {
        if (maxScore != null && maxScore > 0 && currentScore != null) {
            this.scorePercentage = (currentScore.doubleValue() / maxScore.doubleValue()) * 100;
        } else {
            this.scorePercentage = 0.0;
        }
    }

    /**
     * Mark activity as completed.
     *
     * @param passed Whether student passed
     */
    public void markCompleted(boolean passed) {
        this.isCompleted = true;
        this.isPassed = passed;
        this.completedAt = LocalDateTime.now();
    }
}

