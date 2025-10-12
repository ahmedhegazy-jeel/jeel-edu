package com.jeeleducation.lms.service;

import com.jeeleducation.lms.entity.User;
import com.jeeleducation.lms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Service for password reset operations.
 */
@Service
@Transactional
public class PasswordResetService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final int TOKEN_EXPIRY_HOURS = 1;

    /**
     * Request password reset - generates token and sends email.
     *
     * @param email User email
     * @throws RuntimeException if user not found
     */
    public void requestPasswordReset(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        // Generate reset token
        String resetToken = UUID.randomUUID().toString();
        
        // Set token and expiry
        user.setResetPasswordToken(resetToken);
        user.setResetPasswordTokenExpiry(LocalDateTime.now().plusHours(TOKEN_EXPIRY_HOURS));
        
        userRepository.save(user);

        // Send reset email
        emailService.sendPasswordResetEmail(user.getEmail(), resetToken, user.getUsername());
    }

    /**
     * Validate reset token.
     *
     * @param token Reset token
     * @return true if valid, false otherwise
     */
    public boolean validateResetToken(String token) {
        return userRepository.findByResetPasswordToken(token)
                .map(user -> {
                    if (user.getResetPasswordTokenExpiry() == null) {
                        return false;
                    }
                    return LocalDateTime.now().isBefore(user.getResetPasswordTokenExpiry());
                })
                .orElse(false);
    }

    /**
     * Reset password using token.
     *
     * @param token Reset token
     * @param newPassword New password
     * @throws RuntimeException if token invalid or expired
     */
    public void resetPassword(String token, String newPassword) {
        User user = userRepository.findByResetPasswordToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid reset token"));

        // Check if token is expired
        if (user.getResetPasswordTokenExpiry() == null ||
                LocalDateTime.now().isAfter(user.getResetPasswordTokenExpiry())) {
            throw new RuntimeException("Reset token has expired");
        }

        // Update password
        user.setPassword(passwordEncoder.encode(newPassword));
        
        // Clear reset token
        user.setResetPasswordToken(null);
        user.setResetPasswordTokenExpiry(null);
        
        userRepository.save(user);
    }

    /**
     * Change password for authenticated user.
     *
     * @param userId User ID
     * @param currentPassword Current password
     * @param newPassword New password
     * @throws RuntimeException if current password is incorrect
     */
    public void changePassword(Long userId, String currentPassword, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Verify current password
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }

        // Update password
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}

