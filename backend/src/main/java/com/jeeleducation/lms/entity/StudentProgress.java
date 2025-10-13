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
 * StudentProgress entity tracking overall curriculum progress for a student.
 */
@Entity
@Table(name = "student_progress")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class StudentProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculum_id", nullable = false)
    private Curriculum curriculum;

    @Column(nullable = false)
    @Builder.Default
    private Integer totalUnits = 0;

    @Column(nullable = false)
    @Builder.Default
    private Integer completedUnits = 0;

    @Column(nullable = false)
    @Builder.Default
    private Integer totalLessons = 0;

    @Column(nullable = false)
    @Builder.Default
    private Integer completedLessons = 0;

    @Column(nullable = false)
    @Builder.Default
    private Integer totalActivities = 0;

    @Column(nullable = false)
    @Builder.Default
    private Integer completedActivities = 0;

    @Column(nullable = false)
    @Builder.Default
    private Integer totalPoints = 0;

    @Column(nullable = false)
    @Builder.Default
    private Integer earnedPoints = 0;

    @Column(nullable = false)
    @Builder.Default
    private Double completionPercentage = 0.0;

    @Column
    private LocalDateTime startedAt;

    @Column
    private LocalDateTime completedAt;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    /**
     * Check if curriculum is completed.
     *
     * @return true if completed, false otherwise
     */
    public boolean isCompleted() {
        return completedAt != null;
    }

    /**
     * Calculate and update completion percentage.
     */
    public void updateCompletionPercentage() {
        if (totalActivities > 0) {
            this.completionPercentage = (completedActivities.doubleValue() / totalActivities.doubleValue()) * 100;
        } else {
            this.completionPercentage = 0.0;
        }
    }
}

