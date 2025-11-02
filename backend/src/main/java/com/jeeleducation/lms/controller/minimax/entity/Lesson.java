package com.jeeleducation.lms.controller.minimax.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Lesson entity representing individual learning sessions within units
 */
/*
@Entity
@Table(name = "lessons", indexes = {
    @Index(name = "idx_lesson_unit", columnList = "unit_id"),
    @Index(name = "idx_lesson_number", columnList = "lesson_number"),
    @Index(name = "idx_lesson_type", columnList = "lesson_type")
})
@EntityListeners(AuditingEntityListener.class)
*/
public class Lesson {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Unit is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", nullable = false)
    private Unit unit;
    
    @NotNull(message = "Lesson number is required")
    @Column(name = "lesson_number", nullable = false)
    private Integer lessonNumber;
    
    @NotBlank(message = "Lesson title is required")
    @Size(max = 100, message = "Lesson title must not exceed 100 characters")
    @Column(nullable = false, length = 100)
    private String title;
    
    @Size(max = 500, message = "Description must not exceed 500 characters")
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Lesson type is required")
    @Column(name = "lesson_type", nullable = false, length = 20)
    private LessonType lessonType;
    
    @Column(name = "estimated_duration")
    private Integer estimatedDuration; // in minutes
    
    @Column(name = "points_reward", nullable = false)
    private Integer pointsReward = 10;
    
    @Column(name = "unlock_requirement")
    private String unlockRequirement;
    
    @Column(name = "is_boss_lesson", nullable = false)
    private Boolean isBossLesson = false; // Special milestone lessons
    
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // One-to-many relationship with lesson content
    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("content_order ASC")
    private List<LessonContent> contents;
    
    // Enums
    public enum LessonType {
        VIDEO("Video Lesson", "📹", "#ff6b6b"),
        TEXT("Reading Lesson", "📖", "#44a2fc"),
        INTERACTIVE("Interactive Activity", "🎮", "#4ecdc4"),
        MIXED("Mixed Content", "📚", "#ffe66d"),
        QUIZ("Knowledge Check", "❓", "#51cf66"),
        BOSS("Boss Challenge", "👑", "#ff9f43");
        
        private final String displayName;
        private final String emoji;
        private final String color;
        
        LessonType(String displayName, String emoji, String color) {
            this.displayName = displayName;
            this.emoji = emoji;
            this.color = color;
        }
        
        public String getDisplayName() { return displayName; }
        public String getEmoji() { return emoji; }
        public String getColor() { return color; }
    }
    
    // Constructors
    public Lesson() {}
    
    public Lesson(Unit unit, Integer lessonNumber, String title, 
                  String description, LessonType lessonType) {
        this.unit = unit;
        this.lessonNumber = lessonNumber;
        this.title = title;
        this.description = description;
        this.lessonType = lessonType;
    }
    
    // Helper methods
    public String getDisplayTitle() {
        return "Lesson " + lessonNumber + ": " + title;
    }
    
    public String getLessonTypeDisplay() {
        return lessonType != null ? lessonType.getDisplayName() : "";
    }
    
    public String getLessonTypeEmoji() {
        return lessonType != null ? lessonType.getEmoji() : "📝";
    }
    
    public String getLessonTypeColor() {
        return lessonType != null ? lessonType.getColor() : "#gray";
    }
    
    public boolean isFirstLesson() {
        return lessonNumber != null && lessonNumber == 1;
    }
    
    public String getAdventureSpotStyle() {
        if (isBossLesson) {
            return "boss"; // Special styling for boss lessons
        }
        return "regular";
    }
    
    public String getEstimatedDurationText() {
        if (estimatedDuration == null) return "~5 min";
        if (estimatedDuration == 1) return "1 min";
        return estimatedDuration + " mins";
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Unit getUnit() { return unit; }
    public void setUnit(Unit unit) { this.unit = unit; }
    
    public Integer getLessonNumber() { return lessonNumber; }
    public void setLessonNumber(Integer lessonNumber) { this.lessonNumber = lessonNumber; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public LessonType getLessonType() { return lessonType; }
    public void setLessonType(LessonType lessonType) { this.lessonType = lessonType; }
    
    public Integer getEstimatedDuration() { return estimatedDuration; }
    public void setEstimatedDuration(Integer estimatedDuration) { this.estimatedDuration = estimatedDuration; }
    
    public Integer getPointsReward() { return pointsReward; }
    public void setPointsReward(Integer pointsReward) { this.pointsReward = pointsReward; }
    
    public String getUnlockRequirement() { return unlockRequirement; }
    public void setUnlockRequirement(String unlockRequirement) { this.unlockRequirement = unlockRequirement; }
    
    public Boolean getIsBossLesson() { return isBossLesson; }
    public void setIsBossLesson(Boolean isBossLesson) { this.isBossLesson = isBossLesson; }
    
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public List<LessonContent> getContents() { return contents; }
    public void setContents(List<LessonContent> contents) { this.contents = contents; }
}