# Authentication Implementation Guide

## Overview
This document describes the JWT authentication system implemented for JeelEducation LMS.

## Architecture

### Authentication Flow
```
1. User Login Request
   ↓
2. AuthController receives credentials
   ↓
3. AuthenticationManager validates credentials
   ↓
4. UserDetailsServiceImpl loads user from database
   ↓
5. Password verified (BCrypt)
   ↓
6. JwtUtils generates access & refresh tokens
   ↓
7. Tokens returned to client
   ↓
8. Client stores tokens
   ↓
9. Client includes token in Authorization header for subsequent requests
   ↓
10. JwtAuthenticationFilter validates token on each request
    ↓
11. SecurityContextHolder sets authentication
    ↓
12. Request proceeds to controller
```

## Components

### 1. Security Package

#### JwtUtils
**Purpose:** JWT token generation and validation

**Key Methods:**
- `generateJwtToken(Authentication)` - Generate access token from authentication
- `generateTokenFromUsername(String)` - Generate access token from username
- `generateRefreshToken(String)` - Generate refresh token
- `getUsernameFromJwtToken(String)` - Extract username from token
- `validateJwtToken(String)` - Validate token signature and expiration
- `isTokenExpired(String)` - Check if token is expired

**Configuration:**
- Secret key: Base64-encoded (configured in application.yml)
- Access token expiration: 24 hours
- Refresh token expiration: 7 days

#### UserDetailsImpl
**Purpose:** Wrap User entity for Spring Security

**Implements:** `UserDetails` interface

**Key Methods:**
- `build(User)` - Convert User entity to UserDetails
- `getAuthorities()` - Get user roles as GrantedAuthority
- `isEnabled()` - Check if account is active
- `isAccountNonLocked()` - Check if account is not locked

#### UserDetailsServiceImpl
**Purpose:** Load user for authentication

**Implements:** `UserDetailsService` interface

**Key Method:**
- `loadUserByUsername(String)` - Load user by username or email

#### JwtAuthenticationFilter
**Purpose:** Intercept requests and validate JWT tokens

**Extends:** `OncePerRequestFilter`

**Process:**
1. Extract token from Authorization header
2. Validate token using JwtUtils
3. Load user details
4. Set authentication in SecurityContext
5. Continue filter chain

#### JwtAuthenticationEntryPoint
**Purpose:** Handle authentication errors

**Implements:** `AuthenticationEntryPoint` interface

**Response:**
- HTTP 401 Unauthorized
- JSON error message with details

### 2. Configuration Package

#### SecurityConfig
**Purpose:** Configure Spring Security for JWT authentication

**Key Configurations:**

**Password Encoding:**
```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```

**CORS Configuration:**
- Allowed origins: localhost:3000, localhost:3001, production domain
- Allowed methods: GET, POST, PUT, DELETE, OPTIONS, PATCH
- Allowed headers: All
- Credentials allowed: true

**URL-Based Security:**
```java
// Public endpoints
.requestMatchers("/auth/**").permitAll()
.requestMatchers("/public/**").permitAll()

// Role-based endpoints
.requestMatchers("/admin/**").hasAnyRole("SUPER_ADMIN", "SCHOOL_ADMIN")
.requestMatchers("/super-admin/**").hasRole("SUPER_ADMIN")
// ... etc

// All other endpoints require authentication
.anyRequest().authenticated()
```

**Session Management:**
- Stateless (no server-side sessions)
- JWT tokens only

### 3. Controller Package

#### AuthController
**Purpose:** Handle authentication endpoints

**Endpoints:**

1. **POST /auth/login**
   - Authenticate user
   - Generate tokens
   - Update last login timestamp
   - Return user info and tokens

2. **POST /auth/refresh**
   - Validate refresh token
   - Generate new access and refresh tokens
   - Return new tokens

3. **POST /auth/logout**
   - Clear security context
   - Client removes tokens

### 4. DTOs

#### LoginRequest
- `usernameOrEmail` (required)
- `password` (required)

#### JwtResponse
- `accessToken`
- `refreshToken`
- `tokenType` (Bearer)
- User information (id, username, email, firstName, lastName, role)

#### RefreshTokenRequest
- `refreshToken` (required)

#### MessageResponse
- `message` (generic response)

### 5. Exception Handling

#### GlobalExceptionHandler
**Purpose:** Centralized exception handling

**Handles:**
- `MethodArgumentNotValidException` - Validation errors (400)
- `BadCredentialsException` - Invalid credentials (401)
- `UsernameNotFoundException` - User not found (404)
- `RuntimeException` - Runtime errors (500)
- `Exception` - Generic errors (500)

**Response Format:**
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 401,
  "error": "Authentication Failed",
  "message": "Invalid username or password"
}
```

## Configuration

### application.yml
```yaml
jwt:
  secret: ${JWT_SECRET:base64-encoded-secret}
  expiration: 86400000  # 24 hours
  refresh-expiration: 604800000  # 7 days
```

### Environment Variables (Production)
```bash
export JWT_SECRET=your-secure-base64-encoded-secret
export DATABASE_URL=jdbc:mysql://host:3306/database
export DATABASE_USERNAME=username
export DATABASE_PASSWORD=password
```

## Security Best Practices

1. **Token Storage (Client-Side)**
   - Use HttpOnly cookies (preferred)
   - Or secure storage (not localStorage if XSS is a concern)
   - Clear tokens on logout

2. **Secret Key**
   - Use strong, randomly generated key
   - Base64 encode for HMAC algorithms
   - Store in environment variables (production)
   - Never commit to version control

3. **HTTPS**
   - Always use HTTPS in production
   - Prevents token interception

4. **Token Expiration**
   - Short-lived access tokens (24 hours)
   - Longer refresh tokens (7 days)
   - Implement token refresh flow

5. **Password Security**
   - BCrypt with default strength (10 rounds)
   - Minimum 8 characters
   - Consider password complexity rules

6. **Rate Limiting**
   - Implement login rate limiting (TODO)
   - Prevent brute force attacks

7. **Account Lockout**
   - Lock account after failed attempts (TODO)
   - Use `isAccountNonLocked` field

## Testing Authentication

### 1. Create Test User
```sql
INSERT INTO users (username, email, password, first_name, last_name, role, is_active, is_email_verified, is_account_non_locked, created_at, updated_at)
VALUES ('testuser', 'test@example.com', '$2a$10$YourBCryptHashHere', 'Test', 'User', 'STUDENT', true, true, true, NOW(), NOW());
```

### 2. Test Login
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "usernameOrEmail": "testuser",
    "password": "password123"
  }'
```

### 3. Test Protected Endpoint
```bash
curl -X GET http://localhost:8080/users/me \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

### 4. Test Token Refresh
```bash
curl -X POST http://localhost:8080/auth/refresh \
  -H "Content-Type: application/json" \
  -d '{
    "refreshToken": "YOUR_REFRESH_TOKEN"
  }'
```

## Common Issues & Solutions

### Issue: "Invalid JWT signature"
**Cause:** Secret key mismatch or token tampered with
**Solution:** Verify JWT_SECRET is consistent

### Issue: "JWT token is expired"
**Cause:** Token expiration time exceeded
**Solution:** Use refresh token to get new access token

### Issue: "Cannot set user authentication"
**Cause:** Token format incorrect or missing
**Solution:** Ensure Authorization header: `Bearer <token>`

### Issue: "User not found"
**Cause:** User doesn't exist or inactive
**Solution:** Verify user exists and is active in database

## Next Steps

1. Implement user registration endpoint
2. Add email verification flow
3. Implement password reset with email tokens
4. Add rate limiting for login attempts
5. Implement account lockout after failed logins
6. Add OAuth2 providers (Google, Facebook)
7. Implement refresh token rotation
8. Add token blacklisting for logout

## References

- [JWT.io](https://jwt.io/) - JWT debugger and documentation
- [Spring Security Documentation](https://spring.io/projects/spring-security)
- [OWASP Authentication Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/Authentication_Cheat_Sheet.html)

