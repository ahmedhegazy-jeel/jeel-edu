package com.jeeleducation.lms.controller.minimax.model;

/**
 * Types of lessons based on content and difficulty
 */
public enum LessonType {
    VIDEO("Video Lesson", "📹", 15, "Watch and learn through video content"),
    TEXT("Text Lesson", "📝", 10, "Read and understand text-based content"),
    INTERACTIVE("Interactive Lesson", "🎮", 20, "Hands-on activities and exercises"),
    BOSS("Boss Challenge", "👑", 30, "Comprehensive assessment and challenge");

    private final String displayName;
    private final String icon;
    private final Integer estimatedMinutes;
    private final String description;

    LessonType(String displayName, String icon, Integer estimatedMinutes, String description) {
        this.displayName = displayName;
        this.icon = icon;
        this.estimatedMinutes = estimatedMinutes;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getIcon() {
        return icon;
    }

    public Integer getEstimatedMinutes() {
        return estimatedMinutes;
    }

    public String getDescription() {
        return description;
    }

    public Boolean isBossLesson() {
        return this == BOSS;
    }

    public Boolean isInteractive() {
        return this == INTERACTIVE;
    }
}