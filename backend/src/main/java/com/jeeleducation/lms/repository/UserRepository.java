package com.jeeleducation.lms.repository;

import com.jeeleducation.lms.entity.Role;
import com.jeeleducation.lms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for User entity.
 * Provides database operations for user management.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find user by username.
     * 
     * @param username Username to search for
     * @return Optional containing user if found
     */
    Optional<User> findByUsername(String username);

    /**
     * Find user by email.
     * 
     * @param email Email to search for
     * @return Optional containing user if found
     */
    Optional<User> findByEmail(String email);

    /**
     * Find user by username or email.
     * 
     * @param username Username to search for
     * @param email Email to search for
     * @return Optional containing user if found
     */
    Optional<User> findByUsernameOrEmail(String username, String email);

    /**
     * Check if username exists.
     * 
     * @param username Username to check
     * @return true if exists, false otherwise
     */
    boolean existsByUsername(String username);

    /**
     * Check if email exists.
     * 
     * @param email Email to check
     * @return true if exists, false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Find all users by role.
     * 
     * @param role Role to filter by
     * @return List of users with specified role
     */
    List<User> findByRole(Role role);

    /**
     * Find all active users.
     * 
     * @return List of active users
     */
    List<User> findByIsActiveTrue();

    /**
     * Find all users by role and active status.
     * 
     * @param role Role to filter by
     * @param isActive Active status
     * @return List of users matching criteria
     */
    List<User> findByRoleAndIsActive(Role role, Boolean isActive);

    /**
     * Find user by reset password token.
     * 
     * @param token Reset password token
     * @return Optional containing user if found
     */
    Optional<User> findByResetPasswordToken(String token);

    /**
     * Count users by role.
     * 
     * @param role Role to count
     * @return Number of users with specified role
     */
    long countByRole(Role role);

    /**
     * Search users by name or email.
     * 
     * @param searchTerm Search term
     * @return List of matching users
     */
    @Query("SELECT u FROM User u WHERE " +
           "LOWER(u.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(u.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(u.email) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(u.username) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<User> searchUsers(@Param("searchTerm") String searchTerm);
}

