package com.jeeleducation.lms.controller.minimax.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * StudentProgress entity for tracking lesson completion and progress
 */
/*@Entity
@Table(name = "student_progress", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "lesson_id"}),
       indexes = {
           @Index(name = "idx_progress_student", columnList = "student_id"),
           @Index(name = "idx_progress_lesson", columnList = "lesson_id"),
           @Index(name = "idx_progress_status", columnList = "completion_status")
       })
@EntityListeners(AuditingEntityListener.class)*/
public class StudentProgress {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Student is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    
    @NotNull(message = "Lesson is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;
    
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Completion status is required")
    @Column(name = "completion_status", nullable = false, length = 20)
    private CompletionStatus completionStatus = CompletionStatus.NOT_STARTED;
    
    @Column(name = "completion_percentage", nullable = false)
    private Integer completionPercentage = 0;
    
    @Column(name = "stars_earned")
    private Integer starsEarned;
    
    @Column(name = "time_spent")
    private Integer timeSpent; // in seconds
    
    @Column(name = "attempts_count", nullable = false)
    private Integer attemptsCount = 0;
    
    @Column(name = "first_attempt_date")
    private LocalDateTime firstAttemptDate;
    
    @Column(name = "last_attempt_date")
    private LocalDateTime lastAttemptDate;
    
    @Column(name = "completion_date")
    private LocalDateTime completionDate;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Enums
    public enum CompletionStatus {
        NOT_STARTED("Not Started", "locked", "#9ca3af"),
        IN_PROGRESS("In Progress", "active", "#4ecdc4"),
        COMPLETED("Completed", "completed", "#51cf66"),
        PERFECT("Perfect Score", "perfect", "#ffd93d");
        
        private final String displayName;
        private final String cssClass;
        private final String color;
        
        CompletionStatus(String displayName, String cssClass, String color) {
            this.displayName = displayName;
            this.cssClass = cssClass;
            this.color = color;
        }
        
        public String getDisplayName() { return displayName; }
        public String getCssClass() { return cssClass; }
        public String getColor() { return color; }
    }
    
    // Constructors
    public StudentProgress() {}
    
    public StudentProgress(Student student, Lesson lesson) {
        this.student = student;
        this.lesson = lesson;
        this.firstAttemptDate = LocalDateTime.now();
        this.lastAttemptDate = LocalDateTime.now();
        this.attemptsCount = 1;
    }
    
    // Helper methods
    public boolean isCompleted() {
        return completionStatus == CompletionStatus.COMPLETED || 
               completionStatus == CompletionStatus.PERFECT;
    }
    
    public boolean isPerfect() {
        return completionStatus == CompletionStatus.PERFECT;
    }
    
    public boolean isStarted() {
        return completionStatus != CompletionStatus.NOT_STARTED;
    }
    
    public String getTimeSpentText() {
        if (timeSpent == null || timeSpent <= 0) return "0m";
        
        int minutes = timeSpent / 60;
        int seconds = timeSpent % 60;
        
        if (minutes > 0) {
            return minutes + "m " + seconds + "s";
        } else {
            return seconds + "s";
        }
    }
    
    public void markAsCompleted(Integer stars) {
        this.completionStatus = (stars != null && stars >= 3) ? 
            CompletionStatus.PERFECT : CompletionStatus.COMPLETED;
        this.completionPercentage = 100;
        this.starsEarned = stars;
        this.completionDate = LocalDateTime.now();
        this.lastAttemptDate = LocalDateTime.now();
    }
    
    public void updateProgress(Integer percentage) {
        this.completionPercentage = Math.max(0, Math.min(100, percentage));
        this.completionStatus = (percentage >= 100) ? 
            CompletionStatus.COMPLETED : CompletionStatus.IN_PROGRESS;
        this.lastAttemptDate = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    
    public Lesson getLesson() { return lesson; }
    public void setLesson(Lesson lesson) { this.lesson = lesson; }
    
    public CompletionStatus getCompletionStatus() { return completionStatus; }
    public void setCompletionStatus(CompletionStatus completionStatus) { this.completionStatus = completionStatus; }
    
    public Integer getCompletionPercentage() { return completionPercentage; }
    public void setCompletionPercentage(Integer completionPercentage) { this.completionPercentage = completionPercentage; }
    
    public Integer getStarsEarned() { return starsEarned; }
    public void setStarsEarned(Integer starsEarned) { this.starsEarned = starsEarned; }
    
    public Integer getTimeSpent() { return timeSpent; }
    public void setTimeSpent(Integer timeSpent) { this.timeSpent = timeSpent; }
    
    public Integer getAttemptsCount() { return attemptsCount; }
    public void setAttemptsCount(Integer attemptsCount) { this.attemptsCount = attemptsCount; }
    
    public LocalDateTime getFirstAttemptDate() { return firstAttemptDate; }
    public void setFirstAttemptDate(LocalDateTime firstAttemptDate) { this.firstAttemptDate = firstAttemptDate; }
    
    public LocalDateTime getLastAttemptDate() { return lastAttemptDate; }
    public void setLastAttemptDate(LocalDateTime lastAttemptDate) { this.lastAttemptDate = lastAttemptDate; }
    
    public LocalDateTime getCompletionDate() { return completionDate; }
    public void setCompletionDate(LocalDateTime completionDate) { this.completionDate = completionDate; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}