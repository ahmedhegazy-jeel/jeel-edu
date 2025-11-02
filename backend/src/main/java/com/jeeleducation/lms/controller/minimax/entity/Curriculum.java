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
 * Curriculum entity representing subject-based learning programs
 */
/*
@Entity
@Table(name = "curriculums", indexes = {
    @Index(name = "idx_curriculum_school", columnList = "school_id"),
    @Index(name = "idx_curriculum_subject", columnList = "subject")
})
@EntityListeners(AuditingEntityListener.class)*/
public class Curriculum {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Curriculum name is required")
    @Size(max = 100, message = "Curriculum name must not exceed 100 characters")
    @Column(nullable = false, length = 100)
    private String name;
    
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Subject is required")
    @Column(nullable = false, length = 20)
    private Subject subject;
    
    @Size(max = 500, message = "Description must not exceed 500 characters")
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @NotNull(message = "School is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;
    
    @Size(max = 20, message = "Grade level must not exceed 20 characters")
    @Column(name = "grade_level", length = 20)
    private String gradeLevel;
    
    @Column(name = "total_units", nullable = false)
    private Integer totalUnits = 0;
    
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // One-to-many relationship with units
    @OneToMany(mappedBy = "curriculum", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Unit> units;
    
    // Enums
    public enum Subject {
        MATH("Mathematics", "#ff6b6b", "subject-math.png"),
        ENGLISH("English Language", "#44a2fc", "subject-english.png"),
        ARABIC("Arabic Language", "#4ecdc4", "subject-arabic.png");
        
        private final String displayName;
        private final String color;
        private final String iconPath;
        
        Subject(String displayName, String color, String iconPath) {
            this.displayName = displayName;
            this.color = color;
            this.iconPath = iconPath;
        }
        
        public String getDisplayName() { return displayName; }
        public String getColor() { return color; }
        public String getIconPath() { return iconPath; }
    }
    
    // Constructors
    public Curriculum() {}
    
    public Curriculum(String name, Subject subject, String description, 
                      School school, String gradeLevel) {
        this.name = name;
        this.subject = subject;
        this.description = description;
        this.school = school;
        this.gradeLevel = gradeLevel;
    }
    
    // Helper methods
    public String getSubjectDisplayName() {
        return subject != null ? subject.getDisplayName() : "";
    }
    
    public String getSubjectColor() {
        return subject != null ? subject.getColor() : "#gray";
    }
    
    public String getSubjectIconPath() {
        return subject != null ? subject.getIconPath() : "default-icon.png";
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public Subject getSubject() { return subject; }
    public void setSubject(Subject subject) { this.subject = subject; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public School getSchool() { return school; }
    public void setSchool(School school) { this.school = school; }
    
    public String getGradeLevel() { return gradeLevel; }
    public void setGradeLevel(String gradeLevel) { this.gradeLevel = gradeLevel; }
    
    public Integer getTotalUnits() { return totalUnits; }
    public void setTotalUnits(Integer totalUnits) { this.totalUnits = totalUnits; }
    
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public List<Unit> getUnits() { return units; }
    public void setUnits(List<Unit> units) { this.units = units; }
}