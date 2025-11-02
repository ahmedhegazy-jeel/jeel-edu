package com.jeeleducation.lms.controller.minimax;


import lombok.Data;
import lombok.NoArgsConstructor;

import com.jeeleducation.lms.controller.minimax.model.Subject;

import lombok.AllArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurriculumDTO {
    private Long id;
    private String name;
    private Subject subject;
    private String gradeLevel;
    private Integer totalUnits;
    private String schoolName;
    private String description;
    private String subjectIcon;
    private Integer progressPercentage;
    private Integer totalPointsEarned;
}