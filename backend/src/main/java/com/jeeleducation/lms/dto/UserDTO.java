package com.jeeleducation.lms.dto;

import com.jeeleducation.lms.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for User entity.
 * Used to transfer user data without exposing sensitive information.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String mobile;
    private Role role;
    private Boolean isActive;
    private Boolean isEmailVerified;
    private Boolean isAccountNonLocked;
    private String profilePictureUrl;
    private String bio;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLoginAt;

    /**
     * Get user's full name.
     * 
     * @return Full name (firstName + lastName)
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    /**
     * Get role display name.
     * 
     * @return Role display name
     */
    public String getRoleDisplayName() {
        return role != null ? role.getDisplayName() : null;
    }
}

