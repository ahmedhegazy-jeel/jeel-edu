# Password Reset API Documentation

## Base URL
```
http://localhost:8080/api/auth
```

## Overview
The Password Reset API provides secure password recovery functionality with email-based token verification. Supports both password reset (forgotten password) and password change (authenticated user).

---

## Endpoints

### 1. Request Password Reset

**Endpoint:** `POST /auth/password-reset/request`

**Description:** Initiates password reset process. Sends an email with a reset token to the user.

**Request Headers:**
```
Content-Type: application/json
```

**Request Body:**
```json
{
  "email": "user@example.com"
}
```

**Success Response (200 OK):**
```json
{
  "message": "Password reset email sent. Please check your email."
}
```

**Note:** For security reasons, the endpoint returns a success message even if the email doesn't exist. This prevents email enumeration attacks.

**Error Response (400 Bad Request):**
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 400,
  "error": "Validation Failed",
  "errors": {
    "email": "Email should be valid"
  }
}
```

---

### 2. Validate Reset Token

**Endpoint:** `GET /auth/password-reset/validate?token={token}`

**Description:** Validates if a password reset token is valid and not expired.

**Query Parameters:**
- `token` (required): The password reset token

**Success Response (200 OK):**
```json
{
  "message": "Token is valid"
}
```

**Error Response (400 Bad Request):**
```json
{
  "message": "Token is invalid or expired"
}
```

---

### 3. Confirm Password Reset

**Endpoint:** `POST /auth/password-reset/confirm`

**Description:** Resets the password using a valid reset token.

**Request Headers:**
```
Content-Type: application/json
```

**Request Body:**
```json
{
  "token": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
  "newPassword": "newSecurePassword123"
}
```

**Success Response (200 OK):**
```json
{
  "message": "Password has been reset successfully. You can now login with your new password."
}
```

**Error Responses:**

**400 Bad Request** - Invalid or expired token:
```json
{
  "message": "Invalid reset token"
}
```

**400 Bad Request** - Token expired:
```json
{
  "message": "Reset token has expired"
}
```

**400 Bad Request** - Validation error:
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 400,
  "error": "Validation Failed",
  "errors": {
    "newPassword": "Password must be at least 8 characters"
  }
}
```

---

### 4. Change Password (Authenticated)

**Endpoint:** `POST /auth/password-change`

**Description:** Changes password for an authenticated user. Requires current password verification.

**Request Headers:**
```
Content-Type: application/json
Authorization: Bearer <access_token>
```

**Request Body:**
```json
{
  "currentPassword": "oldPassword123",
  "newPassword": "newSecurePassword123"
}
```

**Success Response (200 OK):**
```json
{
  "message": "Password changed successfully"
}
```

**Error Response (400 Bad Request):**
```json
{
  "message": "Current password is incorrect"
}
```

---

## Password Reset Flow

### Complete Flow Diagram

```
1. User forgets password
   ↓
2. User clicks "Forgot Password" on login page
   ↓
3. Frontend: POST /auth/password-reset/request with email
   ↓
4. Backend: Generates reset token, stores with 1-hour expiry
   ↓
5. Backend: Sends email with reset link
   ↓
6. User receives email, clicks reset link
   ↓
7. Frontend: GET /auth/password-reset/validate?token={token}
   ↓
8. If valid: Frontend shows "Set New Password" form
   ↓
9. User enters new password
   ↓
10. Frontend: POST /auth/password-reset/confirm
    ↓
11. Backend: Validates token, updates password, clears token
    ↓
12. Frontend: Redirects to login page
    ↓
13. User logs in with new password
```

---

## Email Configuration

### Development Mode (Default)
Emails are logged to the console. No SMTP configuration required.

**Console Output Example:**
```
================================================================================
📧 PASSWORD RESET EMAIL (Development Mode)
================================================================================
To: user@example.com
Subject: Password Reset Request - JeelEducation LMS

Hello username,

We received a request to reset your password for your JeelEducation LMS account.

To reset your password, click the link below:
http://localhost:3000/reset-password?token=a1b2c3d4-e5f6-7890-abcd-ef1234567890

Or use this token: a1b2c3d4-e5f6-7890-abcd-ef1234567890

This link will expire in 1 hour.

If you didn't request a password reset, please ignore this email.
Your password will remain unchanged.

Best regards,
JeelEducation LMS Team
================================================================================
```

### Production Mode
Configure SMTP in `application.yml`:

```yaml
spring.mail:
  enabled: true
  host: smtp.gmail.com
  port: 587
  username: your-email@gmail.com
  password: your-app-password
  properties:
    mail:
      smtp:
        auth: true
        starttls:
          enable: true
```

---

## Security Features

### 1. Token Security
- **Random UUID**: Cryptographically secure token generation
- **One-time use**: Token is cleared after successful reset
- **Expiration**: Token expires after 1 hour
- **Database storage**: Token stored securely in database

### 2. Email Enumeration Prevention
Password reset request always returns success message, even if email doesn't exist.

### 3. Password Requirements
- Minimum 8 characters
- BCrypt encryption
- Validated on both client and server

### 4. Rate Limiting (Recommended)
Implement rate limiting on password reset requests to prevent abuse:
- Max 3 requests per email per hour
- Max 10 requests per IP per hour

---

## Testing Examples

### 1. Request Password Reset
```bash
curl -X POST http://localhost:8080/api/auth/password-reset/request \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com"
  }'
```

### 2. Validate Token
```bash
curl -X GET "http://localhost:8080/api/auth/password-reset/validate?token=YOUR_TOKEN"
```

### 3. Confirm Password Reset
```bash
curl -X POST http://localhost:8080/api/auth/password-reset/confirm \
  -H "Content-Type: application/json" \
  -d '{
    "token": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
    "newPassword": "newSecurePassword123"
  }'
```

### 4. Change Password (Authenticated)
```bash
curl -X POST http://localhost:8080/api/auth/password-change \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN" \
  -d '{
    "currentPassword": "oldPassword123",
    "newPassword": "newSecurePassword123"
  }'
```

---

## Frontend Integration Example

### React/Next.js Password Reset Flow

#### Step 1: Request Reset
```javascript
async function requestPasswordReset(email) {
  const response = await fetch('http://localhost:8080/api/auth/password-reset/request', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ email }),
  });

  const data = await response.json();
  alert(data.message);
}
```

#### Step 2: Validate Token (on reset page load)
```javascript
async function validateToken(token) {
  const response = await fetch(
    `http://localhost:8080/api/auth/password-reset/validate?token=${token}`
  );

  if (response.ok) {
    return true; // Show password reset form
  } else {
    alert('Invalid or expired reset link');
    return false;
  }
}
```

#### Step 3: Confirm Reset
```javascript
async function confirmPasswordReset(token, newPassword) {
  const response = await fetch('http://localhost:8080/api/auth/password-reset/confirm', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      token,
      newPassword,
    }),
  });

  if (response.ok) {
    const data = await response.json();
    alert(data.message);
    // Redirect to login
    window.location.href = '/login';
  } else {
    const error = await response.json();
    alert(error.message);
  }
}
```

---

## Database Schema

### User Table Updates
The password reset functionality uses existing fields in the `users` table:

| Column | Type | Description |
|--------|------|-------------|
| `reset_password_token` | TEXT | UUID token for password reset |
| `reset_password_token_expiry` | DATETIME | Token expiration timestamp (1 hour) |

---

## Error Handling

| Error | Status | Message | Action |
|-------|--------|---------|--------|
| Invalid email format | 400 | "Email should be valid" | Check email format |
| Token not found | 400 | "Invalid reset token" | Request new reset |
| Token expired | 400 | "Reset token has expired" | Request new reset |
| Password too short | 400 | "Password must be at least 8 characters" | Use longer password |
| Wrong current password | 400 | "Current password is incorrect" | Verify current password |

---

## Best Practices

### For Developers
1. **Never expose** if an email exists in the system
2. **Always validate** tokens on both client and server
3. **Implement rate limiting** to prevent abuse
4. **Use HTTPS** in production
5. **Log reset attempts** for security monitoring

### For Users
1. Check email spam folder if email not received
2. Use strong, unique passwords
3. Don't share reset links
4. Complete reset promptly (within 1 hour)

---

## Production Checklist

- [ ] Configure real SMTP server
- [ ] Set `spring.mail.enabled=true`
- [ ] Use secure email credentials (environment variables)
- [ ] Implement rate limiting
- [ ] Enable HTTPS
- [ ] Set up email monitoring
- [ ] Configure proper frontend URL
- [ ] Test email delivery
- [ ] Monitor failed reset attempts
- [ ] Set up alerts for suspicious activity

---

## Related Documentation

- [Authentication API](./authentication-api.md)
- [User Management API](./user-management-api.md)
- [Database Schema](../database/user-schema.md)

