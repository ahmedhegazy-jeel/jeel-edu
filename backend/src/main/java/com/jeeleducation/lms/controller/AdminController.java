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
 * REST controller for admin operations.
 * Accessible by SUPER_ADMIN and SCHOOL_ADMIN roles.
 */
@RestController
@RequestMapping("/admin")
@PreAuthorize("hasAnyRole('SUPER_ADMIN', 'SCHOOL_ADMIN')")
public class AdminController {

    @Autowired
    private UserService userService;

    /**
     * Get admin dashboard data.
     *
     * @return Dashboard statistics
     */
    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboard() {
        Map<String, Object> dashboard = Map.of(
                "totalUsers", userService.getTotalUserCount(),
                "totalStudents", userService.countUsersByRole(Role.STUDENT),
                "totalTeachers", userService.countUsersByRole(Role.TEACHER),
                "totalParents", userService.countUsersByRole(Role.PARENT),
                "totalSchoolAdmins", userService.countUsersByRole(Role.SCHOOL_ADMIN),
                "activeUsers", userService.getActiveUsers().size()
        );
        return ResponseEntity.ok(dashboard);
    }

    /**
     * Get all teachers.
     *
     * @return List of teachers
     */
    @GetMapping("/teachers")
    public ResponseEntity<List<UserDTO>> getAllTeachers() {
        List<UserDTO> teachers = userService.getUsersByRole(Role.TEACHER);
        return ResponseEntity.ok(teachers);
    }

    /**
     * Get all students.
     *
     * @return List of students
     */
    @GetMapping("/students")
    public ResponseEntity<List<UserDTO>> getAllStudents() {
        List<UserDTO> students = userService.getUsersByRole(Role.STUDENT);
        return ResponseEntity.ok(students);
    }

    /**
     * Get all parents.
     *
     * @return List of parents
     */
    @GetMapping("/parents")
    public ResponseEntity<List<UserDTO>> getAllParents() {
        List<UserDTO> parents = userService.getUsersByRole(Role.PARENT);
        return ResponseEntity.ok(parents);
    }
}

