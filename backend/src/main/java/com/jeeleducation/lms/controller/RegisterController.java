package com.jeeleducation.lms.controller;

import com.jeeleducation.lms.dto.CreateUserRequest;
import com.jeeleducation.lms.dto.MessageResponse;
import com.jeeleducation.lms.dto.UserDTO;
import com.jeeleducation.lms.entity.Role;
import com.jeeleducation.lms.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * REST controller for user registration.
 * Public endpoint for user self-registration.
 */
@RestController
@RequestMapping("/auth")
public class RegisterController {

    @Autowired
    private UserService userService;

    /**
     * Register new student account.
     * Public endpoint - no authentication required.
     *
     * @param request User registration request
     * @return Created user DTO
     */
    @PostMapping("/register/student")
    public ResponseEntity<?> registerStudent(@Valid @RequestBody CreateUserRequest request) {
        // Force role to STUDENT for self-registration
        request.setRole(Role.STUDENT);

        try {
            UserDTO createdUser = userService.createUser(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    /**
     * Register new parent account.
     * Public endpoint - no authentication required.
     *
     * @param request User registration request
     * @return Created user DTO
     */
    @PostMapping("/register/parent")
    public ResponseEntity<?> registerParent(@Valid @RequestBody CreateUserRequest request) {
        // Force role to PARENT for self-registration
        request.setRole(Role.PARENT);

        try {
            UserDTO createdUser = userService.createUser(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    /**
     * Register new teacher account.
     * Public endpoint - requires approval by admin.
     *
     * @param request User registration request
     * @return Success message
     */
    @PostMapping("/register/teacher")
    public ResponseEntity<?> registerTeacher(@Valid @RequestBody CreateUserRequest request) {
        // Force role to TEACHER for self-registration
        request.setRole(Role.TEACHER);

        try {
            UserDTO createdUser = userService.createUser(request);
            // In production, account should be inactive until admin approves
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "message", "Teacher registration successful. Account pending approval.",
                    "user", createdUser
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }
}

