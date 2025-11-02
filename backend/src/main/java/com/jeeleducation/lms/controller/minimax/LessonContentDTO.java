package com.jeeleducation.lms.controller.minimax;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.jeeleducation.lms.controller.minimax.model.ContentType;
import com.jeeleducation.lms.controller.minimax.model.LessonContentBlockDTO;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonContentDTO {
    private Long lessonId;
    private String title;
    private String type; // VIDEO, TEXT, INTERACTIVE, BOSS
    private Integer estimatedMinutes;
    private Integer maxStars;
    private List<LessonContentBlockDTO> contentBlocks;
    private List<String> assessmentQuestions;
    private String videoUrl;
    private String interactiveUrl;
    private String notes;
}
