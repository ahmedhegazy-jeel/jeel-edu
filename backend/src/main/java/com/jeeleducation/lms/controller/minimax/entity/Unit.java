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
 * Unit entity representing chapters/modules within a curriculum
 */
/*@Entity
@Table(name = "units", indexes = {
    @Index(name = "idx_unit_curriculum", columnList = "curriculum_id"),
    @Index(name = "idx_unit_number", columnList = "unit_number")
})
@EntityListeners(AuditingEntityListener.class)*/
public class Unit {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Curriculum is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculum_id", nullable = false)
    private Curriculum curriculum;
    
    @NotNull(message = "Unit number is required")
    @Column(name = "unit_number", nullable = false)
    private Integer unitNumber;
    
    @NotBlank(message = "Unit title is required")
    @Size(max = 100, message = "Unit title must not exceed 100 characters")
    @Column(nullable = false, length = 100)
    private String title;
    
    @Size(max = 500, message = "Description must not exceed 500 characters")
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "total_lessons", nullable = false)
    private Integer totalLessons = 0;
    
    @Column(name = "unlock_requirement")
    private String unlockRequirement;
    
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // One-to-many relationship with lessons
    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Lesson> lessons;
    
    // Constructors
    public Unit() {}
    
    public Unit(Curriculum curriculum, Integer unitNumber, String title, String description) {
        this.curriculum = curriculum;
        this.unitNumber = unitNumber;
        this.title = title;
        this.description = description;
    }
    
    // Helper methods
    public String getDisplayTitle() {
        return "Unit " + unitNumber + ": " + title;
    }
    
    public boolean isFirstUnit() {
        return unitNumber != null && unitNumber == 1;
    }
    
    public String getChapterTheme() {
        // Return adventure theme based on unit number
        String[] themes = {
            "The Beginning Adventure", "Treasure Island", "Magical Forest", 
            "Crystal Caves", "Sky Castle", "Dragon's Lair", "Golden Kingdom",
            "Mystic Mountains", "Ocean Depths", "Final Quest"
        };
        
        if (unitNumber != null && unitNumber > 0 && unitNumber <= themes.length) {
            return themes[unitNumber - 1];
        }
        return "Chapter " + unitNumber;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Curriculum getCurriculum() { return curriculum; }
    public void setCurriculum(Curriculum curriculum) { this.curriculum = curriculum; }
    
    public Integer getUnitNumber() { return unitNumber; }
    public void setUnitNumber(Integer unitNumber) { this.unitNumber = unitNumber; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Integer getTotalLessons() { return totalLessons; }
    public void setTotalLessons(Integer totalLessons) { this.totalLessons = totalLessons; }
    
    public String getUnlockRequirement() { return unlockRequirement; }
    public void setUnlockRequirement(String unlockRequirement) { this.unlockRequirement = unlockRequirement; }
    
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public List<Lesson> getLessons() { return lessons; }
    public void setLessons(List<Lesson> lessons) { this.lessons = lessons; }
}