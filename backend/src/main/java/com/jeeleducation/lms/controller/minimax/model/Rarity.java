package com.jeeleducation.lms.controller.minimax.model;

/**
 * Rarity levels for gamification items (badges, trophies, etc.)
 */
public enum Rarity {
    COMMON("Common", "#b2bec3"),
    RARE("Rare", "#0984e3"),
    EPIC("Epic", "#6c5ce7"),
    LEGENDARY("Legendary", "#fdcb6e");

    private final String displayName;
    private final String colorCode;

    Rarity(String displayName, String colorCode) {
        this.displayName = displayName;
        this.colorCode = colorCode;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getColorCode() {
        return colorCode;
    }
}