package com.jeeleducation.lms.dto;

import com.jeeleducation.lms.entity.ActivityType;
import com.jeeleducation.lms.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Data Transfer Object for Activity entity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityDTO {

    private Long id;
    private String titleName;
    private String titleAudioName;
    private String description;
    private String icon;
    private ActivityType activityType;
    private Status status;
    private Integer points;
    private String tag;
    private String topic;
    private Integer displayOrder;
    private Long lessonId;
    private String lessonName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Dynamic fields for specific activity types
    private Map<String, Object> typeSpecificData;
}

