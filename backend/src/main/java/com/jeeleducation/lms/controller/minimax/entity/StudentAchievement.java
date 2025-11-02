package com.jeeleducation.lms.controller.minimax.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * StudentAchievement entity linking students to their earned gamification items
 */
/*@Entity
@Table(name = "student_achievements",
       uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "gamification_item_id"}),
       indexes = {
           @Index(name = "idx_achievement_student", columnList = "student_id"),
           @Index(name = "idx_achievement_item", columnList = "gamification_item_id"),
           @Index(name = "idx_achievement_date", columnList = "earned_date")
       })
@EntityListeners(AuditingEntityListener.class)*/
public class StudentAchievement {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Student is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    
    @NotNull(message = "Gamification item is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gamification_item_id", nullable = false)
    private GamificationItem gamificationItem;
    
    @NotNull(message = "Earned date is required")
    @Column(name = "earned_date", nullable = false)
    private LocalDateTime earnedDate;
    
    @Column(name = "points_earned")
    private Integer pointsEarned;
    
    @Column(name = "notification_sent", nullable = false)
    private Boolean notificationSent = false;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    // Constructors
    public StudentAchievement() {}
    
    public StudentAchievement(Student student, GamificationItem gamificationItem) {
        this.student = student;
        this.gamificationItem = gamificationItem;
        this.earnedDate = LocalDateTime.now();
    }
    
    public StudentAchievement(Student student, GamificationItem gamificationItem, Integer pointsEarned) {
        this(student, gamificationItem);
        this.pointsEarned = pointsEarned;
    }
    
    // Helper methods
    public boolean isNewlyEarned() {
        return earnedDate != null && earnedDate.isAfter(LocalDateTime.now().minusMinutes(5));
    }
    
    public String getTimeAgoText() {
        if (earnedDate == null) return "Unknown";
        
        LocalDateTime now = LocalDateTime.now();
        long daysBetween = java.time.Duration.between(earnedDate, now).toDays();
        
        if (daysBetween == 0) return "Today";
        if (daysBetween == 1) return "Yesterday";
        if (daysBetween < 7) return daysBetween + " days ago";
        if (daysBetween < 30) return (daysBetween / 7) + " weeks ago";
        
        return (daysBetween / 30) + " months ago";
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    
    public GamificationItem getGamificationItem() { return gamificationItem; }
    public void setGamificationItem(GamificationItem gamificationItem) { this.gamificationItem = gamificationItem; }
    
    public LocalDateTime getEarnedDate() { return earnedDate; }
    public void setEarnedDate(LocalDateTime earnedDate) { this.earnedDate = earnedDate; }
    
    public Integer getPointsEarned() { return pointsEarned; }
    public void setPointsEarned(Integer pointsEarned) { this.pointsEarned = pointsEarned; }
    
    public Boolean getNotificationSent() { return notificationSent; }
    public void setNotificationSent(Boolean notificationSent) { this.notificationSent = notificationSent; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}


/**
 * StudentInventory entity for tracking student's collectible items
 */
/*@Entity
@Table(name = "student_inventory",
       uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "gamification_item_id"}),
       indexes = {
           @Index(name = "idx_inventory_student", columnList = "student_id"),
           @Index(name = "idx_inventory_item", columnList = "gamification_item_id"),
           @Index(name = "idx_inventory_equipped", columnList = "is_equipped")
       })
@EntityListeners(AuditingEntityListener.class)*/
class StudentInventory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Student is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    
    @NotNull(message = "Gamification item is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gamification_item_id", nullable = false)
    private GamificationItem gamificationItem;
    
    @Column(nullable = false)
    private Integer quantity = 1;
    
    @Column(name = "is_equipped", nullable = false)
    private Boolean isEquipped = false;
    
    @Column(name = "acquired_date", nullable = false)
    private LocalDateTime acquiredDate;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    // Constructors
    public StudentInventory() {}
    
    public StudentInventory(Student student, GamificationItem gamificationItem) {
        this.student = student;
        this.gamificationItem = gamificationItem;
        this.acquiredDate = LocalDateTime.now();
    }
    
    // Helper methods
    public void equip() {
        this.isEquipped = true;
    }
    
    public void unequip() {
        this.isEquipped = false;
    }
    
    public void addQuantity(int amount) {
        this.quantity += amount;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    
    public GamificationItem getGamificationItem() { return gamificationItem; }
    public void setGamificationItem(GamificationItem gamificationItem) { this.gamificationItem = gamificationItem; }
    
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    
    public Boolean getIsEquipped() { return isEquipped; }
    public void setIsEquipped(Boolean isEquipped) { this.isEquipped = isEquipped; }
    
    public LocalDateTime getAcquiredDate() { return acquiredDate; }
    public void setAcquiredDate(LocalDateTime acquiredDate) { this.acquiredDate = acquiredDate; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}