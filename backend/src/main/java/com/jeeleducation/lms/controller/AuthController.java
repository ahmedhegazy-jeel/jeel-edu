package com.jeeleducation.lms.controller;

import com.jeeleducation.lms.dto.JwtResponse;
import com.jeeleducation.lms.dto.LoginRequest;
import com.jeeleducation.lms.dto.MessageResponse;
import com.jeeleducation.lms.dto.PasswordChangeRequest;
import com.jeeleducation.lms.dto.PasswordResetConfirm;
import com.jeeleducation.lms.dto.PasswordResetRequest;
import com.jeeleducation.lms.dto.RefreshTokenRequest;
import com.jeeleducation.lms.entity.User;
import com.jeeleducation.lms.repository.UserRepository;
import com.jeeleducation.lms.security.JwtUtils;
import com.jeeleducation.lms.security.UserDetailsImpl;
import com.jeeleducation.lms.service.PasswordResetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * REST controller for authentication endpoints.
 * Handles login, token refresh, and logout operations.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordResetService passwordResetService;

    /**
     * Authenticate user and return JWT tokens.
     *
     * @param loginRequest Login credentials
     * @return JWT response with tokens and user info
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        String refreshToken = jwtUtils.generateRefreshToken(
                ((UserDetailsImpl) authentication.getPrincipal()).getUsername());

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Update last login timestamp
        userRepository.findById(userDetails.getId()).ifPresent(user -> {
            user.setLastLoginAt(LocalDateTime.now());
            userRepository.save(user);
        });

        JwtResponse response = new JwtResponse(
                jwt,
                refreshToken,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getFirstName(),
                userDetails.getLastName(),
                userDetails.getAuthorities().iterator().next().getAuthority()
        );

        return ResponseEntity.ok(response);
    }

    /**
     * Refresh access token using refresh token.
     *
     * @param request Refresh token request
     * @return New JWT response with refreshed tokens
     */
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();

        if (jwtUtils.validateJwtToken(refreshToken)) {
            String username = jwtUtils.getUsernameFromJwtToken(refreshToken);
            User user = userRepository.findByUsernameOrEmail(username, username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            String newAccessToken = jwtUtils.generateTokenFromUsername(username);
            String newRefreshToken = jwtUtils.generateRefreshToken(username);

            JwtResponse response = new JwtResponse(
                    newAccessToken,
                    newRefreshToken,
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getRole().getAuthority()
            );

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Invalid or expired refresh token"));
        }
    }

    /**
     * Logout user (client-side token removal).
     *
     * @return Success message
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok(new MessageResponse("User logged out successfully"));
    }

    /**
     * Request password reset - sends email with reset token.
     *
     * @param request Password reset request with email
     * @return Success message
     */
    @PostMapping("/password-reset/request")
    public ResponseEntity<?> requestPasswordReset(@Valid @RequestBody PasswordResetRequest request) {
        try {
            passwordResetService.requestPasswordReset(request.getEmail());
            return ResponseEntity.ok(new MessageResponse(
                    "Password reset email sent. Please check your email."));
        } catch (RuntimeException e) {
            // Don't reveal if email exists or not (security best practice)
            return ResponseEntity.ok(new MessageResponse(
                    "If the email exists, a password reset link has been sent."));
        }
    }

    /**
     * Validate password reset token.
     *
     * @param token Reset token
     * @return Token validity status
     */
    @GetMapping("/password-reset/validate")
    public ResponseEntity<?> validateResetToken(@RequestParam String token) {
        boolean isValid = passwordResetService.validateResetToken(token);
        if (isValid) {
            return ResponseEntity.ok(new MessageResponse("Token is valid"));
        } else {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Token is invalid or expired"));
        }
    }

    /**
     * Reset password using token.
     *
     * @param request Password reset confirmation with token and new password
     * @return Success message
     */
    @PostMapping("/password-reset/confirm")
    public ResponseEntity<?> confirmPasswordReset(@Valid @RequestBody PasswordResetConfirm request) {
        try {
            passwordResetService.resetPassword(request.getToken(), request.getNewPassword());
            return ResponseEntity.ok(new MessageResponse(
                    "Password has been reset successfully. You can now login with your new password."));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    /**
     * Change password for authenticated user.
     *
     * @param request Password change request
     * @param authentication Authentication object
     * @return Success message
     */
    @PostMapping("/password-change")
    public ResponseEntity<?> changePassword(
            @Valid @RequestBody PasswordChangeRequest request,
            Authentication authentication) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            passwordResetService.changePassword(
                    userDetails.getId(),
                    request.getCurrentPassword(),
                    request.getNewPassword());
            return ResponseEntity.ok(new MessageResponse("Password changed successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }
}

