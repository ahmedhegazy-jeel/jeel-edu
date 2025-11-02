package com.jeeleducation.lms.controller.minimax.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * LessonContent entity for multi-format lesson content (text, video, image, audio)
 */
/*@Entity
@Table(name = "lesson_content", indexes = {
    @Index(name = "idx_content_lesson", columnList = "lesson_id"),
    @Index(name = "idx_content_order", columnList = "content_order"),
    @Index(name = "idx_content_type", columnList = "content_type")
})
@EntityListeners(AuditingEntityListener.class)*/
public class LessonContent {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Lesson is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;
    
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Content type is required")
    @Column(name = "content_type", nullable = false, length = 20)
    private ContentType contentType;
    
    @NotNull(message = "Content order is required")
    @Column(name = "content_order", nullable = false)
    private Integer contentOrder;
    
    @Size(max = 200, message = "Title must not exceed 200 characters")
    @Column(length = 200)
    private String title;
    
    @Column(name = "text_content", columnDefinition = "TEXT")
    private String textContent;
    
    @Size(max = 500, message = "Media URL must not exceed 500 characters")
    @Column(name = "media_url", length = 500)
    private String mediaUrl;
    
    @Size(max = 500, message = "Thumbnail URL must not exceed 500 characters")
    @Column(name = "thumbnail_url", length = 500)
    private String thumbnailUrl;
    
    @Column(name = "media_duration")
    private Integer mediaDuration; // Duration in seconds for video/audio
    
    @Size(max = 500, message = "Caption must not exceed 500 characters")
    @Column(length = 500)
    private String caption;
    
    @Size(max = 200, message = "Overlay text must not exceed 200 characters")
    @Column(name = "overlay_text", length = 200)
    private String overlayText;
    
    @Size(max = 100, message = "Aspect ratio must not exceed 100 characters")
    @Column(name = "aspect_ratio", length = 100)
    private String aspectRatio = "16:9";
    
    @Column(name = "is_required", nullable = false)
    private Boolean isRequired = true;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Enums
    public enum ContentType {
        TEXT("Text Content", "📝", "Reading material with formatted text"),
        IMAGE("Image", "🖼️", "Visual content with optional caption"),
        IMAGE_OVERLAY("Image with Overlay", "🎨", "Hero image with text overlay"),
        VIDEO("Video", "📹", "Video content with player controls"),
        AUDIO("Audio", "🎵", "Audio content with player controls"),
        INTERACTIVE("Interactive Activity", "🎮", "Interactive learning element");
        
        private final String displayName;
        private final String emoji;
        private final String description;
        
        ContentType(String displayName, String emoji, String description) {
            this.displayName = displayName;
            this.emoji = emoji;
            this.description = description;
        }
        
        public String getDisplayName() { return displayName; }
        public String getEmoji() { return emoji; }
        public String getDescription() { return description; }
        
        public boolean requiresMedia() {
            return this == IMAGE || this == IMAGE_OVERLAY || this == VIDEO || this == AUDIO;
        }
        
        public boolean supportsOverlay() {
            return this == IMAGE_OVERLAY;
        }
        
        public boolean hasPlayer() {
            return this == VIDEO || this == AUDIO;
        }
    }
    
    // Constructors
    public LessonContent() {}
    
    public LessonContent(Lesson lesson, ContentType contentType, Integer contentOrder) {
        this.lesson = lesson;
        this.contentType = contentType;
        this.contentOrder = contentOrder;
    }
    
    // Helper methods
    public String getContentTypeDisplay() {
        return contentType != null ? contentType.getDisplayName() : "";
    }
    
    public String getContentTypeEmoji() {
        return contentType != null ? contentType.getEmoji() : "📄";
    }
    
    public boolean hasMedia() {
        return mediaUrl != null && !mediaUrl.trim().isEmpty();
    }
    
    public boolean hasThumbnail() {
        return thumbnailUrl != null && !thumbnailUrl.trim().isEmpty();
    }
    
    public boolean hasOverlay() {
        return overlayText != null && !overlayText.trim().isEmpty() && 
               contentType != null && contentType.supportsOverlay();
    }
    
    public String getMediaDurationText() {
        if (mediaDuration == null || mediaDuration <= 0) return null;
        
        int minutes = mediaDuration / 60;
        int seconds = mediaDuration % 60;
        
        if (minutes > 0) {
            return String.format("%d:%02d", minutes, seconds);
        } else {
            return seconds + "s";
        }
    }
    
    public String getDisplayContent() {
        if (textContent != null && !textContent.trim().isEmpty()) {
            return textContent;
        }
        if (title != null && !title.trim().isEmpty()) {
            return title;
        }
        return getContentTypeDisplay();
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Lesson getLesson() { return lesson; }
    public void setLesson(Lesson lesson) { this.lesson = lesson; }
    
    public ContentType getContentType() { return contentType; }
    public void setContentType(ContentType contentType) { this.contentType = contentType; }
    
    public Integer getContentOrder() { return contentOrder; }
    public void setContentOrder(Integer contentOrder) { this.contentOrder = contentOrder; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getTextContent() { return textContent; }
    public void setTextContent(String textContent) { this.textContent = textContent; }
    
    public String getMediaUrl() { return mediaUrl; }
    public void setMediaUrl(String mediaUrl) { this.mediaUrl = mediaUrl; }
    
    public String getThumbnailUrl() { return thumbnailUrl; }
    public void setThumbnailUrl(String thumbnailUrl) { this.thumbnailUrl = thumbnailUrl; }
    
    public Integer getMediaDuration() { return mediaDuration; }
    public void setMediaDuration(Integer mediaDuration) { this.mediaDuration = mediaDuration; }
    
    public String getCaption() { return caption; }
    public void setCaption(String caption) { this.caption = caption; }
    
    public String getOverlayText() { return overlayText; }
    public void setOverlayText(String overlayText) { this.overlayText = overlayText; }
    
    public String getAspectRatio() { return aspectRatio; }
    public void setAspectRatio(String aspectRatio) { this.aspectRatio = aspectRatio; }
    
    public Boolean getIsRequired() { return isRequired; }
    public void setIsRequired(Boolean isRequired) { this.isRequired = isRequired; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}