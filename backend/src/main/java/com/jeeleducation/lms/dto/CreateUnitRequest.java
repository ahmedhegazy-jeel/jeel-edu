package com.jeeleducation.lms.dto;

import com.jeeleducation.lms.entity.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for creating a new unit.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUnitRequest {

    @NotBlank(message = "Unit name is required")
    @Size(max = 100, message = "Unit name must not exceed 100 characters")
    private String name;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    private String audioName;

    private String icon;

    @Builder.Default
    private Status status = Status.DRAFT;

    private Integer displayOrder;

    @NotNull(message = "Curriculum ID is required")
    private Long curriculumId;
}

