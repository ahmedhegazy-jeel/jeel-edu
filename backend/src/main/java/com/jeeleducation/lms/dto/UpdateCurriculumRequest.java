package com.jeeleducation.lms.dto;

import com.jeeleducation.lms.entity.Status;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for updating a curriculum.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCurriculumRequest {

    @Size(max = 100, message = "Curriculum name must not exceed 100 characters")
    private String name;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    private String icon;

    private Status status;

    private Integer displayOrder;
}

