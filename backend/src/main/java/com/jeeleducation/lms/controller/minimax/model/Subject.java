package com.jeeleducation.lms.controller.minimax.model;

/**
 * Academic subjects available in the curriculum
 */
public enum Subject {
    MATH("Mathematics", "🔢", "#0984e3"),
    ENGLISH("English Language", "🌟", "#00b894"),
    ARABIC("Arabic Language", "📖", "#e17055");

    private final String displayName;
    private final String icon;
    private final String colorCode;

    Subject(String displayName, String icon, String colorCode) {
        this.displayName = displayName;
        this.icon = icon;
        this.colorCode = colorCode;
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
}