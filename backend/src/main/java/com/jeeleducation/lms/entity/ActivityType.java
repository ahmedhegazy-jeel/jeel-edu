package com.jeeleducation.lms.entity;

/**
 * Enum representing different types of activities.
 */
public enum ActivityType {
    TEXT("Text", "Text-based learning content"),
    PDF("PDF", "PDF document with audio"),
    AUDIO("Audio", "Audio learning content"),
    BOOK("Book", "Interactive book with pages"),
    VIDEO("Video", "Video learning content"),
    INTERACTIVE("Interactive", "External interactive activity"),
    QUIZ("Quiz", "Assessment quiz"),
    HOMEWORK("Homework", "Homework assignment");

    private final String displayName;
    private final String description;

    ActivityType(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }
}

