package com.jeeleducation.lms.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Email service for sending emails.
 * Currently uses console logging for development.
 * Can be configured with SMTP for production.
 */
@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Value("${app.frontend-url:http://localhost:3000}")
    private String frontendUrl;

    @Value("${spring.mail.enabled:false}")
    private boolean emailEnabled;

    /**
     * Send password reset email.
     *
     * @param toEmail Recipient email address
     * @param resetToken Password reset token
     * @param username Username
     */
    public void sendPasswordResetEmail(String toEmail, String resetToken, String username) {
        String resetLink = frontendUrl + "/reset-password?token=" + resetToken;
        
        String subject = "Password Reset Request - JeelEducation LMS";
        String body = buildPasswordResetEmailBody(username, resetLink, resetToken);

        if (emailEnabled) {
            // TODO: Implement actual SMTP email sending
            logger.info("Email would be sent to: {}", toEmail);
            logger.info("Subject: {}", subject);
            logger.info("Reset Link: {}", resetLink);
        } else {
            // Development mode - log to console
            logger.info("\n" + "=".repeat(80));
            logger.info("📧 PASSWORD RESET EMAIL (Development Mode)");
            logger.info("=".repeat(80));
            logger.info("To: {}", toEmail);
            logger.info("Subject: {}", subject);
            logger.info("\n{}", body);
            logger.info("=".repeat(80) + "\n");
        }
    }

    /**
     * Send welcome email to new user.
     *
     * @param toEmail Recipient email address
     * @param username Username
     * @param firstName First name
     */
    public void sendWelcomeEmail(String toEmail, String username, String firstName) {
        String subject = "Welcome to JeelEducation LMS!";
        String body = buildWelcomeEmailBody(firstName, username);

        if (emailEnabled) {
            // TODO: Implement actual SMTP email sending
            logger.info("Welcome email would be sent to: {}", toEmail);
        } else {
            // Development mode - log to console
            logger.info("\n" + "=".repeat(80));
            logger.info("📧 WELCOME EMAIL (Development Mode)");
            logger.info("=".repeat(80));
            logger.info("To: {}", toEmail);
            logger.info("Subject: {}", subject);
            logger.info("\n{}", body);
            logger.info("=".repeat(80) + "\n");
        }
    }

    /**
     * Send email verification email.
     *
     * @param toEmail Recipient email address
     * @param verificationToken Verification token
     * @param username Username
     */
    public void sendEmailVerification(String toEmail, String verificationToken, String username) {
        String verificationLink = frontendUrl + "/verify-email?token=" + verificationToken;
        
        String subject = "Verify Your Email - JeelEducation LMS";
        String body = buildEmailVerificationBody(username, verificationLink);

        if (emailEnabled) {
            // TODO: Implement actual SMTP email sending
            logger.info("Verification email would be sent to: {}", toEmail);
        } else {
            // Development mode - log to console
            logger.info("\n" + "=".repeat(80));
            logger.info("📧 EMAIL VERIFICATION (Development Mode)");
            logger.info("=".repeat(80));
            logger.info("To: {}", toEmail);
            logger.info("Subject: {}", subject);
            logger.info("\n{}", body);
            logger.info("Verification Link: {}", verificationLink);
            logger.info("=".repeat(80) + "\n");
        }
    }

    private String buildPasswordResetEmailBody(String username, String resetLink, String token) {
        return """
                Hello %s,
                
                We received a request to reset your password for your JeelEducation LMS account.
                
                To reset your password, click the link below:
                %s
                
                Or use this token: %s
                
                This link will expire in 1 hour.
                
                If you didn't request a password reset, please ignore this email.
                Your password will remain unchanged.
                
                Best regards,
                JeelEducation LMS Team
                """.formatted(username, resetLink, token);
    }

    private String buildWelcomeEmailBody(String firstName, String username) {
        return """
                Hello %s,
                
                Welcome to JeelEducation LMS!
                
                Your account has been successfully created.
                Username: %s
                
                You can now log in and start your learning journey.
                
                Best regards,
                JeelEducation LMS Team
                """.formatted(firstName, username);
    }

    private String buildEmailVerificationBody(String username, String verificationLink) {
        return """
                Hello %s,
                
                Thank you for registering with JeelEducation LMS!
                
                Please verify your email address by clicking the link below:
                %s
                
                This link will expire in 24 hours.
                
                Best regards,
                JeelEducation LMS Team
                """.formatted(username, verificationLink);
    }
}

