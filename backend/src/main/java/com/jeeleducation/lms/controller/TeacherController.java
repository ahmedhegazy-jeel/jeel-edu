package com.jeeleducation.lms.controller;

import com.jeeleducation.lms.dto.UserDTO;
import com.jeeleducation.lms.entity.Role;
import com.jeeleducation.lms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * REST controller for teacher-specific operations.
 * Accessible by TEACHER role.
 */
@RestController
@RequestMapping("/teacher")
@PreAuthorize("hasRole('TEACHER')")
public class TeacherController {

    @Autowired
    private UserService userService;

    /**
     * Get teacher dashboard data.
     *
     * @return Dashboard data for teachers
     */
    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getTeacherDashboard() {
        Map<String, Object> dashboard = Map.of(
                "totalStudents", userService.countUsersByRole(Role.STUDENT),
                "message", "Welcome to Teacher Dashboard"
        );
        return ResponseEntity.ok(dashboard);
    }

    /**
     * Get all students (teacher view).
     *
     * @return List of students
     */
    @GetMapping("/students")
    public ResponseEntity<List<UserDTO>> getStudents() {
        List<UserDTO> students = userService.getUsersByRole(Role.STUDENT);
        return ResponseEntity.ok(students);
    }
}

