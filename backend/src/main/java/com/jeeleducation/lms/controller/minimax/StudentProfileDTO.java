package com.jeeleducation.lms.controller.minimax;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentProfileDTO {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String avatarUrl;
    private String schoolName;
    private Integer totalPoints;
    private Integer currentLevel;
    private Integer currentStreak;
    private LocalDate lastActivityDate;
    private Integer totalLessonsCompleted;
    private Integer totalTimeSpentHours;
    private String currentStreakStatus;
    private List<AchievementDTO> recentAchievements;
    private List<AchievementDTO> availableBadges;
    private String[] ownedAvatars;
    private String levelProgress;
}