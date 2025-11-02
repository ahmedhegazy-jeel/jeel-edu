package com.jeeleducation.lms.controller.minimax;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

import com.jeeleducation.lms.controller.minimax.model.ItemType;
import com.jeeleducation.lms.controller.minimax.model.Rarity;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AchievementDTO {
    private Long id;
    private String name;
    private ItemType type;
    private String description;
    private String iconUrl;
    private Rarity rarity;
    private Integer pointsRequired;
    private String unlockCondition;
    private Boolean isEarned;
    private LocalDateTime earnedAt;
    private String displayName;
    private String achievementColor;
    private Boolean isNew; // For highlighting new achievements
}