package com.jeeleducation.lms.controller.minimax;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitDTO {
    private Long id;
    private Integer unitNumber;
    private String title;
    private String description;
    private String theme;
    private String themeIcon;
    private Integer totalLessons;
    private Integer completedLessons;
    private Integer totalPoints;
    private Integer pointsEarned;
    private Integer estimatedMinutes;
    private String backgroundImage;
    private String[] unlockConditions;
}