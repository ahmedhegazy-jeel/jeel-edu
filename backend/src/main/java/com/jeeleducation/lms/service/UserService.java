package com.jeeleducation.lms.service;

import com.jeeleducation.lms.dto.CreateUserRequest;
import com.jeeleducation.lms.dto.UpdateUserRequest;
import com.jeeleducation.lms.dto.UserDTO;
import com.jeeleducation.lms.entity.Role;
import com.jeeleducation.lms.entity.User;
import com.jeeleducation.lms.mapper.UserMapper;
import com.jeeleducation.lms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for user management operations.
 */
@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Create a new user.
     *
     * @param request User creation request
     * @return Created user DTO
     * @throws RuntimeException if username or email already exists
     */
    public UserDTO createUser(CreateUserRequest request) {
        // Check if username exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username is already taken");
        }

        // Check if email exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email is already in use");
        }

        // Convert DTO to entity
        User user = userMapper.toEntity(request);

        // Encode password
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Save user
        User savedUser = userRepository.save(user);

        return userMapper.toDTO(savedUser);
    }

    /**
     * Get user by ID.
     *
     * @param id User ID
     * @return User DTO
     * @throws RuntimeException if user not found
     */
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return userMapper.toDTO(user);
    }

    /**
     * Get user by username.
     *
     * @param username Username
     * @return User DTO
     * @throws RuntimeException if user not found
     */
    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
        return userMapper.toDTO(user);
    }

    /**
     * Get all users.
     *
     * @return List of user DTOs
     */
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get all active users.
     *
     * @return List of active user DTOs
     */
    public List<UserDTO> getActiveUsers() {
        return userRepository.findByIsActiveTrue().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get users by role.
     *
     * @param role User role
     * @return List of user DTOs with specified role
     */
    public List<UserDTO> getUsersByRole(Role role) {
        return userRepository.findByRole(role).stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Search users by name or email.
     *
     * @param searchTerm Search term
     * @return List of matching user DTOs
     */
    public List<UserDTO> searchUsers(String searchTerm) {
        return userRepository.searchUsers(searchTerm).stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update user information.
     *
     * @param id User ID
     * @param request Update request
     * @return Updated user DTO
     * @throws RuntimeException if user not found or email already exists
     */
    public UserDTO updateUser(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        // Check if email is being changed and if it's already in use
        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new RuntimeException("Email is already in use");
            }
            user.setEmail(request.getEmail());
            user.setIsEmailVerified(false); // Require re-verification
        }

        // Update fields if provided
        if (request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }
        if (request.getMobile() != null) {
            user.setMobile(request.getMobile());
        }
        if (request.getBio() != null) {
            user.setBio(request.getBio());
        }
        if (request.getProfilePictureUrl() != null) {
            user.setProfilePictureUrl(request.getProfilePictureUrl());
        }

        User updatedUser = userRepository.save(user);
        return userMapper.toDTO(updatedUser);
    }

    /**
     * Change user password.
     *
     * @param id User ID
     * @param newPassword New password
     * @throws RuntimeException if user not found
     */
    public void changePassword(Long id, String newPassword) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    /**
     * Activate user account.
     *
     * @param id User ID
     * @throws RuntimeException if user not found
     */
    public void activateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        user.setIsActive(true);
        userRepository.save(user);
    }

    /**
     * Deactivate user account.
     *
     * @param id User ID
     * @throws RuntimeException if user not found
     */
    public void deactivateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        user.setIsActive(false);
        userRepository.save(user);
    }

    /**
     * Lock user account.
     *
     * @param id User ID
     * @throws RuntimeException if user not found
     */
    public void lockUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        user.setIsAccountNonLocked(false);
        userRepository.save(user);
    }

    /**
     * Unlock user account.
     *
     * @param id User ID
     * @throws RuntimeException if user not found
     */
    public void unlockUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        user.setIsAccountNonLocked(true);
        userRepository.save(user);
    }

    /**
     * Delete user.
     *
     * @param id User ID
     * @throws RuntimeException if user not found
     */
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    /**
     * Count users by role.
     *
     * @param role User role
     * @return Number of users with specified role
     */
    public long countUsersByRole(Role role) {
        return userRepository.countByRole(role);
    }

    /**
     * Get total user count.
     *
     * @return Total number of users
     */
    public long getTotalUserCount() {
        return userRepository.count();
    }
}

