package com.jeeleducation.lms.controller.minimax;


import lombok.Data;
import lombok.NoArgsConstructor;

import com.jeeleducation.lms.controller.minimax.model.LessonType;

import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonSpotDTO {
    private Long id;
    private Integer lessonNumber;
    private String title;
    private LessonType type;
    private Integer estimatedMinutes;
    private Integer maxStars;
    private Boolean isBossLesson;
    private Boolean isLocked;
    private Boolean isCompleted;
    private Boolean isActive;
    private Integer starsEarned;
    private String iconUrl;
    private String status; // "locked", "active", "completed"
    private String lessonContentPreview;
    private Integer position; // Position on the path curve
}