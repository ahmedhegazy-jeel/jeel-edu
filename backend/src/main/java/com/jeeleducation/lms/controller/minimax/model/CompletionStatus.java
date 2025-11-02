package com.jeeleducation.lms.controller.minimax.model;

/**
 * Status of lesson completion for tracking student progress
 */
public enum CompletionStatus {
    NOT_STARTED("Not Started", "⭕", "#b2bec3", 0),
    IN_PROGRESS("In Progress", "🔄", "#fdcb6e", 50),
    COMPLETED("Completed", "✅", "#00b894", 100);

    private final String displayName;
    private final String icon;
    private final String colorCode;
    private final Integer progressPercentage;

    CompletionStatus(String displayName, String icon, String colorCode, Integer progressPercentage) {
        this.displayName = displayName;
        this.icon = icon;
        this.colorCode = colorCode;
        this.progressPercentage = progressPercentage;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getIcon() {
        return icon;
    }

    public String getColorCode() {
        return colorCode;
    }

    public Integer getProgressPercentage() {
        return progressPercentage;
    }

    public Boolean isCompleted() {
        return this == COMPLETED;
    }

    public Boolean isInProgress() {
        return this == IN_PROGRESS;
    }

    public Boolean isNotStarted() {
        return this == NOT_STARTED;
    }
}