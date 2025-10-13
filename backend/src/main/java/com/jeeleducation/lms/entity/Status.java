package com.jeeleducation.lms.entity;

/**
 * Enum representing status for curriculum entities.
 * Used for Curriculum, Unit, Lesson, and Activity.
 */
public enum Status {
    DRAFT("Draft", "Under development"),
    ACTIVE("ACTIVE", "Available to students"),
    ARCHIVED("Archived", "No longer active");

    private final String displayName;
    private final String description;

    Status(String displayName, String description) {
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

