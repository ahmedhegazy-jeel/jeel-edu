package com.jeeleducation.lms.controller.minimax.model;

/**
 * Types of content that can be displayed in lessons
 */
public enum ContentType {
    TEXT("Text", "📝", "Simple text content"),
    IMAGE("Image", "🖼️", "Static image content"),
    IMAGE_OVERLAY("Image Overlay", "📱", "Image with text overlay"),
    VIDEO("Video", "📹", "Video content"),
    AUDIO("Audio", "🎵", "Audio content"),
    INTERACTIVE("Interactive", "🎮", "Interactive activity");

    private final String displayName;
    private final String icon;
    private final String description;

    ContentType(String displayName, String icon, String description) {
        this.displayName = displayName;
        this.icon = icon;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getIcon() {
        return icon;
    }

    public String getDescription() {
        return description;
    }
}