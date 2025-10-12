package com.jeeleducation.lms.dto;

import com.jeeleducation.lms.entity.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for creating a new curriculum.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCurriculumRequest {

    @NotBlank(message = "Curriculum name is required")
    @Size(max = 100, message = "Curriculum name must not exceed 100 characters")
    private String name;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    private String icon;

    @Builder.Default
    private Status status = Status.DRAFT;

    private Integer displayOrder;
}

