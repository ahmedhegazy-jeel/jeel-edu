package com.jeeleducation.lms.dto;

import com.jeeleducation.lms.entity.ActivityType;
import com.jeeleducation.lms.entity.Status;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Request DTO for creating a new activity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateActivityRequest {

    @NotBlank(message = "Activity title is required")
    @Size(max = 200, message = "Title must not exceed 200 characters")
    private String titleName;

    private String titleAudioName;

    @Size(max = 2000, message = "Description must not exceed 2000 characters")
    private String description;

    private String icon;

    @NotNull(message = "Activity type is required")
    private ActivityType activityType;

    @Builder.Default
    private Status status = Status.DRAFT;

    @Min(value = 0, message = "Points cannot be negative")
    private Integer points;

    private String tag;

    private String topic;

    private Integer displayOrder;

    @NotNull(message = "Lesson ID is required")
    private Long lessonId;
    
    // Type-specific data as a flexible map
    private Map<String, Object> typeSpecificData;
}

