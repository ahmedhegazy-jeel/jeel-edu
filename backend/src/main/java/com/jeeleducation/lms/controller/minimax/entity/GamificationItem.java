package com.jeeleducation.lms.controller.minimax.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * GamificationItem entity representing badges, achievements, and collectible items
 */
/*@Entity
@Table(name = "gamification_items", indexes = {
    @Index(name = "idx_gamification_type", columnList = "item_type"),
    @Index(name = "idx_gamification_category", columnList = "category"),
    @Index(name = "idx_gamification_active", columnList = "is_active")
})
@EntityListeners(AuditingEntityListener.class)*/
public class GamificationItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Item name is required")
    @Size(max = 100, message = "Item name must not exceed 100 characters")
    @Column(nullable = false, length = 100)
    private String name;
    
    @Size(max = 500, message = "Description must not exceed 500 characters")
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "item_type", nullable = false, length = 20)
    private ItemType itemType;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Category category;
    
    @Size(max = 200, message = "Icon URL must not exceed 200 characters")
    @Column(name = "icon_url", length = 200)
    private String iconUrl;
    
    @Column(name = "unlock_condition", length = 500)
    private String unlockCondition;
    
    @Column(name = "points_required")
    private Integer pointsRequired;
    
    @Column(name = "rarity_level", nullable = false)
    private Integer rarityLevel = 1; // 1=Common, 2=Rare, 3=Epic, 4=Legendary
    
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Enums
    public enum ItemType {
        BADGE("Achievement Badge", "🏆"),
        TROPHY("Trophy", "🏆"),
        COLLECTIBLE("Collectible Item", "💎"),
        AVATAR("Avatar Item", "👤"),
        THEME("Theme Unlock", "🎨");
        
        private final String displayName;
        private final String emoji;
        
        ItemType(String displayName, String emoji) {
            this.displayName = displayName;
            this.emoji = emoji;
        }
        
        public String getDisplayName() { return displayName; }
        public String getEmoji() { return emoji; }
    }
    
    public enum Category {
        // Achievement Categories
        FIRST_STEPS("First Steps", "Getting started achievements"),
        CONSISTENCY("Consistency", "Streak and daily learning"),
        MASTERY("Subject Mastery", "Subject-specific achievements"),
        EXPLORATION("Explorer", "Discovery and exploration"),
        PERFECTION("Perfectionist", "Perfect scores and accuracy"),
        SOCIAL("Social Learning", "Sharing and collaboration"),
        SPEED("Speed Demon", "Fast completion achievements"),
        DEDICATION("Dedication", "Time and effort milestones"),
        
        // Collectible Categories  
        GEMS("Knowledge Gems", "Collectible crystals"),
        TREASURES("Treasure Collection", "Adventure treasures"),
        CHARACTERS("Character Collection", "Unlockable characters"),
        DECORATIONS("Decorations", "Profile decorations");
        
        private final String displayName;
        private final String description;
        
        Category(String displayName, String description) {
            this.displayName = displayName;
            this.description = description;
        }
        
        public String getDisplayName() { return displayName; }
        public String getDescription() { return description; }
    }
    
    // Constructors
    public GamificationItem() {}
    
    public GamificationItem(String name, String description, ItemType itemType, 
                           Category category, String iconUrl) {
        this.name = name;
        this.description = description;
        this.itemType = itemType;
        this.category = category;
        this.iconUrl = iconUrl;
    }
    
    // Helper methods
    public String getRarityText() {
        switch (rarityLevel) {
            case 1: return "Common";
            case 2: return "Rare";
            case 3: return "Epic";
            case 4: return "Legendary";
            default: return "Unknown";
        }
    }
    
    public String getRarityColor() {
        switch (rarityLevel) {
            case 1: return "#9ca3af"; // Gray - Common
            case 2: return "#3b82f6"; // Blue - Rare
            case 3: return "#8b5cf6"; // Purple - Epic
            case 4: return "#f59e0b"; // Gold - Legendary
            default: return "#6b7280";
        }
    }
    
    public boolean isUnlockedBy(Student student) {
        // Basic implementation - can be enhanced with complex unlock logic
        if (pointsRequired != null) {
            return student.getTotalPoints() >= pointsRequired;
        }
        return true;
    }
    
    public String getItemTypeDisplay() {
        return itemType != null ? itemType.getDisplayName() : "";
    }
    
    public String getItemTypeEmoji() {
        return itemType != null ? itemType.getEmoji() : "🎁";
    }
    
    public String getCategoryDisplay() {
        return category != null ? category.getDisplayName() : "";
    }
    
    public boolean isBadge() {
        return itemType == ItemType.BADGE || itemType == ItemType.TROPHY;
    }
    
    public boolean isCollectible() {
        return itemType == ItemType.COLLECTIBLE;
    }
    
    public boolean isAvatar() {
        return itemType == ItemType.AVATAR;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public ItemType getItemType() { return itemType; }
    public void setItemType(ItemType itemType) { this.itemType = itemType; }
    
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
    
    public String getIconUrl() { return iconUrl; }
    public void setIconUrl(String iconUrl) { this.iconUrl = iconUrl; }
    
    public String getUnlockCondition() { return unlockCondition; }
    public void setUnlockCondition(String unlockCondition) { this.unlockCondition = unlockCondition; }
    
    public Integer getPointsRequired() { return pointsRequired; }
    public void setPointsRequired(Integer pointsRequired) { this.pointsRequired = pointsRequired; }
    
    public Integer getRarityLevel() { return rarityLevel; }
    public void setRarityLevel(Integer rarityLevel) { this.rarityLevel = rarityLevel; }
    
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}