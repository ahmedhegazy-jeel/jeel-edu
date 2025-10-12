package com.jeeleducation.lms.dto;

import com.jeeleducation.lms.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for Curriculum entity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurriculumDTO {

    private Long id;
    private String name;
    private String description;
    private String icon;
    private Status status;
    private Integer displayOrder;
    private Integer unitCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

