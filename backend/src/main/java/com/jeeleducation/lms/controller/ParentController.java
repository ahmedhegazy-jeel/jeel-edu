package com.jeeleducation.lms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * REST controller for parent-specific operations.
 * Accessible by PARENT role.
 */
@RestController
@RequestMapping("/api/parent")
@PreAuthorize("hasRole('PARENT')")
public class ParentController {

    /**
     * Get parent dashboard data.
     *
     * @return Dashboard data for parents
     */
    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getParentDashboard() {
        Map<String, Object> dashboard = Map.of(
                "message", "Welcome to Parent Dashboard",
                "children", 0  // TODO: Implement children tracking
        );
        return ResponseEntity.ok(dashboard);
    }

    /**
     * Get children progress (placeholder).
     *
     * @return Children progress data
     */
    @GetMapping("/children/progress")
    public ResponseEntity<Map<String, Object>> getChildrenProgress() {
        Map<String, Object> progress = Map.of(
                "message", "Children progress tracking coming soon"
        );
        return ResponseEntity.ok(progress);
    }
}

