package com.jeeleducation.lms.controller;

import com.jeeleducation.lms.dto.CurriculumAnalyticsDTO;
import com.jeeleducation.lms.dto.StudentPerformanceDTO;
import com.jeeleducation.lms.dto.SystemStatsDTO;
import com.jeeleducation.lms.service.AdminPanelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for admin panel operations.
 * Provides analytics, reports, and system statistics.
 */
@RestController
@RequestMapping("/admin-panel")
@PreAuthorize("hasAnyRole('SUPER_ADMIN', 'SCHOOL_ADMIN')")
public class AdminPanelController {

    @Autowired
    private AdminPanelService adminPanelService;

    /**
     * Get comprehensive system statistics.
     *
     * @return System stats DTO
     */
    @GetMapping("/stats/system")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<SystemStatsDTO> getSystemStats() {
        SystemStatsDTO stats = adminPanelService.getSystemStats();
        return ResponseEntity.ok(stats);
    }

    /**
     * Get curriculum analytics.
     *
     * @param curriculumId Curriculum ID
     * @return Curriculum analytics DTO
     */
    @GetMapping("/analytics/curriculum/{curriculumId}")
    public ResponseEntity<CurriculumAnalyticsDTO> getCurriculumAnalytics(@PathVariable Long curriculumId) {
        CurriculumAnalyticsDTO analytics = adminPanelService.getCurriculumAnalytics(curriculumId);
        return ResponseEntity.ok(analytics);
    }

    /**
     * Get top performing students.
     *
     * @param limit Number of students (default 10)
     * @return List of top student performance DTOs
     */
    @GetMapping("/students/top-performers")
    public ResponseEntity<List<StudentPerformanceDTO>> getTopPerformers(
            @RequestParam(defaultValue = "10") int limit) {
        List<StudentPerformanceDTO> topStudents = adminPanelService.getTopPerformingStudents(limit);
        return ResponseEntity.ok(topStudents);
    }

    /**
     * Get all students performance.
     *
     * @return List of all student performance DTOs
     */
    @GetMapping("/students/performance")
    public ResponseEntity<List<StudentPerformanceDTO>> getAllStudentsPerformance() {
        List<StudentPerformanceDTO> performance = adminPanelService.getAllStudentsPerformance();
        return ResponseEntity.ok(performance);
    }

    /**
     * Get student performance details.
     *
     * @param studentId Student ID
     * @return Student performance DTO
     */
    @GetMapping("/students/{studentId}/performance")
    public ResponseEntity<StudentPerformanceDTO> getStudentPerformance(@PathVariable Long studentId) {
        StudentPerformanceDTO performance = adminPanelService.getStudentPerformance(studentId);
        return ResponseEntity.ok(performance);
    }

    /**
     * Get curriculum leaderboard.
     *
     * @param curriculumId Curriculum ID
     * @param limit Number of students (default 10)
     * @return List of top students in curriculum
     */
    @GetMapping("/curriculum/{curriculumId}/leaderboard")
    public ResponseEntity<List<StudentPerformanceDTO>> getCurriculumLeaderboard(
            @PathVariable Long curriculumId,
            @RequestParam(defaultValue = "10") int limit) {
        List<StudentPerformanceDTO> leaderboard = adminPanelService.getCurriculumLeaderboard(curriculumId, limit);
        return ResponseEntity.ok(leaderboard);
    }
}

