package com.jeeleducation.lms.mapper;

import com.jeeleducation.lms.dto.CreateUserRequest;
import com.jeeleducation.lms.dto.UserDTO;
import com.jeeleducation.lms.entity.User;
import org.springframework.stereotype.Component;

/**
 * Mapper class for converting between User entity and DTOs.
 */
@Component
public class UserMapper {

    /**
     * Convert User entity to UserDTO.
     * 
     * @param user User entity
     * @return UserDTO
     */
    public UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .mobile(user.getMobile())
                .role(user.getRole())
                .isActive(user.getIsActive())
                .isEmailVerified(user.getIsEmailVerified())
                .isAccountNonLocked(user.getIsAccountNonLocked())
                .profilePictureUrl(user.getProfilePictureUrl())
                .bio(user.getBio())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .lastLoginAt(user.getLastLoginAt())
                .build();
    }

    /**
     * Convert CreateUserRequest to User entity.
     * Note: Password should be encoded before using this method.
     * 
     * @param request CreateUserRequest
     * @return User entity
     */
    public User toEntity(CreateUserRequest request) {
        if (request == null) {
            return null;
        }

        return User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .mobile(request.getMobile())
                .role(request.getRole())
                .bio(request.getBio())
                .isActive(true)
                .isEmailVerified(false)
                .isAccountNonLocked(true)
                .build();
    }
}

