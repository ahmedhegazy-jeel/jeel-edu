package com.jeeleducation.lms.controller.minimax.model;

/**
 * Types of gamification items that students can collect
 */
public enum ItemType {
    BADGE("Badge", "🏆"),
    TROPHY("Trophy", "🥇"),
    AVATAR("Avatar", "🎭"),
    COLLECTIBLE("Collectible", "💎");

    private final String displayName;
    private final String icon;

    ItemType(String displayName, String icon) {
        this.displayName = displayName;
        this.icon = icon;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getIcon() {
        return icon;
    }
}