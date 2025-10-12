package com.jeeleducation.lms.dto;

import com.jeeleducation.lms.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for Lesson entity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonDTO {

    private Long id;
    private String name;
    private String description;
    private String audioName;
    private String icon;
    private Status status;
    private Integer displayOrder;
    private Long unitId;
    private String unitName;
    private Integer activityCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

