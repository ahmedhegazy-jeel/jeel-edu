package com.jeeleducation.lms.controller.minimax;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonPathDTO {
    private String pathType; // "curved", "zigzag", "mixed"
    private List<LessonSpotDTO> lessons;
    private String pathColor;
    private String startIcon;
    private String endIcon;
    private String backgroundTheme;
    private Integer totalPathLength;
    private String[] pathSegments; // For complex paths with different segments
    private String svgPath; // SVG path for rendering
    private Integer[] lessonPositions; // X,Y coordinates for each lesson
}