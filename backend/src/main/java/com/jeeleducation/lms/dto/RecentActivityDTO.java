package com.jeeleducation.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for recent system activities.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecentActivityDTO {

    private String activityType; // USER_REGISTERED, CURRICULUM_CREATED, QUIZ_COMPLETED, etc.
    private String description;
    private String userName;
    private String resourceName;
    private LocalDateTime timestamp;
}

