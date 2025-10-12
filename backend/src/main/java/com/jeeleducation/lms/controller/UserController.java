package com.jeeleducation.lms.controller;

import com.jeeleducation.lms.dto.CreateUserRequest;
import com.jeeleducation.lms.dto.MessageResponse;
import com.jeeleducation.lms.dto.UpdateUserRequest;
import com.jeeleducation.lms.dto.UserDTO;
import com.jeeleducation.lms.entity.Role;
import com.jeeleducation.lms.security.UserDetailsImpl;
import com.jeeleducation.lms.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * REST controller for user management operations.
 * Provides endpoints with role-based access control.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Get current user's profile.
     *
     * @param authentication Authentication object
     * @return Current user DTO
     */
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserDTO user = userService.getUserById(userDetails.getId());
        return ResponseEntity.ok(user);
    }

    /**
     * Update current user's profile.
     *
     * @param authentication Authentication object
     * @param request Update request
     * @return Updated user DTO
     */
    @PutMapping("/me")
    public ResponseEntity<UserDTO> updateCurrentUser(
            Authentication authentication,
            @Valid @RequestBody UpdateUserRequest request) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserDTO updatedUser = userService.updateUser(userDetails.getId(), request);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Get all users (Admin only).
     *
     * @return List of all users
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'SCHOOL_ADMIN')")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Get user by ID (Admin only).
     *
     * @param id User ID
     * @return User DTO
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'SCHOOL_ADMIN')")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * Search users (Admin only).
     *
     * @param searchTerm Search term
     * @return List of matching users
     */
    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'SCHOOL_ADMIN')")
    public ResponseEntity<List<UserDTO>> searchUsers(@RequestParam String searchTerm) {
        List<UserDTO> users = userService.searchUsers(searchTerm);
        return ResponseEntity.ok(users);
    }

    /**
     * Get users by role (Admin only).
     *
     * @param role User role
     * @return List of users with specified role
     */
    @GetMapping("/role/{role}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'SCHOOL_ADMIN')")
    public ResponseEntity<List<UserDTO>> getUsersByRole(@PathVariable Role role) {
        List<UserDTO> users = userService.getUsersByRole(role);
        return ResponseEntity.ok(users);
    }

    /**
     * Create new user (Admin only).
     *
     * @param request User creation request
     * @return Created user DTO
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'SCHOOL_ADMIN')")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserDTO createdUser = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    /**
     * Update user by ID (Admin only).
     *
     * @param id User ID
     * @param request Update request
     * @return Updated user DTO
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'SCHOOL_ADMIN')")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request) {
        UserDTO updatedUser = userService.updateUser(id, request);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Activate user (Admin only).
     *
     * @param id User ID
     * @return Success message
     */
    @PatchMapping("/{id}/activate")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'SCHOOL_ADMIN')")
    public ResponseEntity<MessageResponse> activateUser(@PathVariable Long id) {
        userService.activateUser(id);
        return ResponseEntity.ok(new MessageResponse("User activated successfully"));
    }

    /**
     * Deactivate user (Admin only).
     *
     * @param id User ID
     * @return Success message
     */
    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'SCHOOL_ADMIN')")
    public ResponseEntity<MessageResponse> deactivateUser(@PathVariable Long id) {
        userService.deactivateUser(id);
        return ResponseEntity.ok(new MessageResponse("User deactivated successfully"));
    }

    /**
     * Lock user account (Admin only).
     *
     * @param id User ID
     * @return Success message
     */
    @PatchMapping("/{id}/lock")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'SCHOOL_ADMIN')")
    public ResponseEntity<MessageResponse> lockUser(@PathVariable Long id) {
        userService.lockUser(id);
        return ResponseEntity.ok(new MessageResponse("User account locked successfully"));
    }

    /**
     * Unlock user account (Admin only).
     *
     * @param id User ID
     * @return Success message
     */
    @PatchMapping("/{id}/unlock")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'SCHOOL_ADMIN')")
    public ResponseEntity<MessageResponse> unlockUser(@PathVariable Long id) {
        userService.unlockUser(id);
        return ResponseEntity.ok(new MessageResponse("User account unlocked successfully"));
    }

    /**
     * Delete user (Super Admin only).
     *
     * @param id User ID
     * @return Success message
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<MessageResponse> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(new MessageResponse("User deleted successfully"));
    }

    /**
     * Get user statistics (Admin only).
     *
     * @return User statistics
     */
    @GetMapping("/stats")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'SCHOOL_ADMIN')")
    public ResponseEntity<Map<String, Object>> getUserStats() {
        Map<String, Object> stats = Map.of(
                "total", userService.getTotalUserCount(),
                "superAdmins", userService.countUsersByRole(Role.SUPER_ADMIN),
                "schoolAdmins", userService.countUsersByRole(Role.SCHOOL_ADMIN),
                "teachers", userService.countUsersByRole(Role.TEACHER),
                "parents", userService.countUsersByRole(Role.PARENT),
                "students", userService.countUsersByRole(Role.STUDENT)
        );
        return ResponseEntity.ok(stats);
    }
}

