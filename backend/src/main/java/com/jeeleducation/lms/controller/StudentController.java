package com.jeeleducation.lms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * REST controller for student-specific operations.
 * Accessible by STUDENT role.
 */
@RestController
@RequestMapping("/student")
@PreAuthorize("hasRole('STUDENT')")
public class StudentController {

    /**
     * Get student dashboard data.
     *
     * @return Dashboard data for students
     */
    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getStudentDashboard() {
        Map<String, Object> dashboard = Map.of(
                "message", "Welcome to Student Learning Dashboard",
                "curriculums", 0,  // TODO: Implement curriculum tracking
                "progress", 0      // TODO: Implement progress tracking
        );
        return ResponseEntity.ok(dashboard);
    }

    /**
     * Get student curriculums (placeholder).
     *
     * @return Student curriculums
     */
    @GetMapping("/curriculums")
    public ResponseEntity<Map<String, Object>> getCurriculums() {
        Map<String, Object> curriculums = Map.of(
                "message", "Curriculum learning interface coming soon"
        );
        return ResponseEntity.ok(curriculums);
    }

    /**
     * Get student progress (placeholder).
     *
     * @return Student progress data
     */
    @GetMapping("/progress")
    public ResponseEntity<Map<String, Object>> getProgress() {
        Map<String, Object> progress = Map.of(
                "message", "Progress tracking coming soon"
        );
        return ResponseEntity.ok(progress);
    }
}

