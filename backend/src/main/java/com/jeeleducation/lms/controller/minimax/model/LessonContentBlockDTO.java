package com.jeeleducation.lms.controller.minimax.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonContentBlockDTO {
    private Integer displayOrder;
    private ContentType type;
    private String textContent;
    private String mediaUrl;
    private String caption;
    private String overlayText;
}
