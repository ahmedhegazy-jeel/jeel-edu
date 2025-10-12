# Authentication API Documentation

## Base URL
```
http://localhost:8080/api/auth
```

## Endpoints

### 1. Login (Authenticate User)

**Endpoint:** `POST /auth/login`

**Description:** Authenticates a user with username/email and password, returns JWT tokens.

**Request Headers:**
```
Content-Type: application/json
```

**Request Body:**
```json
{
  "usernameOrEmail": "admin",
  "password": "password123"
}
```

**Success Response (200 OK):**
```json
{
  "accessToken": "eyJhbGciOiJIUzUxMiJ9...",
  "refreshToken": "eyJhbGciOiJIUzUxMiJ9...",
  "tokenType": "Bearer",
  "id": 1,
  "username": "admin",
  "email": "admin@jeeleducation.com",
  "firstName": "System",
  "lastName": "Admin",
  "role": "ROLE_SUPER_ADMIN"
}
```

**Error Responses:**

**401 Unauthorized** - Invalid credentials:
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 401,
  "error": "Authentication Failed",
  "message": "Invalid username or password"
}
```

**400 Bad Request** - Validation error:
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 400,
  "error": "Validation Failed",
  "errors": {
    "usernameOrEmail": "Username or email is required",
    "password": "Password is required"
  }
}
```

---

### 2. Refresh Token

**Endpoint:** `POST /auth/refresh`

**Description:** Generates new access and refresh tokens using a valid refresh token.

**Request Headers:**
```
Content-Type: application/json
```

**Request Body:**
```json
{
  "refreshToken": "eyJhbGciOiJIUzUxMiJ9..."
}
```

**Success Response (200 OK):**
```json
{
  "accessToken": "eyJhbGciOiJIUzUxMiJ9...",
  "refreshToken": "eyJhbGciOiJIUzUxMiJ9...",
  "tokenType": "Bearer",
  "id": 1,
  "username": "admin",
  "email": "admin@jeeleducation.com",
  "firstName": "System",
  "lastName": "Admin",
  "role": "ROLE_SUPER_ADMIN"
}
```

**Error Response (400 Bad Request):**
```json
{
  "message": "Invalid or expired refresh token"
}
```

---

### 3. Logout

**Endpoint:** `POST /auth/logout`

**Description:** Clears authentication context (client should delete tokens).

**Request Headers:**
```
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9...
```

**Success Response (200 OK):**
```json
{
  "message": "User logged out successfully"
}
```

---

## Authentication Flow

### Initial Authentication
1. User sends credentials to `/auth/login`
2. Backend validates credentials
3. Backend generates access token (24h) and refresh token (7 days)
4. Backend updates user's `lastLoginAt` timestamp
5. Client stores both tokens securely

### Using Protected Endpoints
1. Client includes access token in Authorization header:
   ```
   Authorization: Bearer eyJhbGciOiJIUzUxMiJ9...
   ```
2. Backend validates token on each request
3. If valid, request proceeds; if invalid/expired, returns 401

### Token Refresh
1. When access token expires, client sends refresh token to `/auth/refresh`
2. Backend validates refresh token
3. Backend generates new access and refresh tokens
4. Client updates stored tokens

### Logout
1. Client calls `/auth/logout` (optional, for server-side cleanup)
2. Client deletes stored tokens from local storage/cookies

---

## Token Details

### Access Token
- **Type:** JWT (JSON Web Token)
- **Expiration:** 24 hours
- **Use:** Authenticate API requests
- **Claims:**
  - `sub`: Username
  - `iat`: Issued at timestamp
  - `exp`: Expiration timestamp

### Refresh Token
- **Type:** JWT (JSON Web Token)
- **Expiration:** 7 days
- **Use:** Obtain new access tokens
- **Claims:** Same as access token

### Token Format
```
Authorization: Bearer <token>
```

---

## Security Considerations

1. **Token Storage**
   - Store tokens securely (HttpOnly cookies or secure storage)
   - Never store in localStorage if XSS is a concern
   - Clear tokens on logout

2. **Password Requirements**
   - Minimum 8 characters
   - Encrypted with BCrypt before storage

3. **HTTPS Required**
   - Always use HTTPS in production
   - Prevents token interception

4. **Token Validation**
   - Tokens are validated on every request
   - Expired tokens return 401 Unauthorized
   - Invalid signatures rejected

5. **CORS Configuration**
   - Configured for localhost:3000, localhost:3001
   - Update for production domains

---

## Example: Login Flow (JavaScript)

```javascript
// Login
async function login(usernameOrEmail, password) {
  const response = await fetch('http://localhost:8080/api/auth/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      usernameOrEmail,
      password,
    }),
  });

  if (response.ok) {
    const data = await response.json();
    // Store tokens
    localStorage.setItem('accessToken', data.accessToken);
    localStorage.setItem('refreshToken', data.refreshToken);
    return data;
  } else {
    const error = await response.json();
    throw new Error(error.message);
  }
}

// Use protected endpoint
async function fetchProtectedData() {
  const accessToken = localStorage.getItem('accessToken');
  
  const response = await fetch('http://localhost:8080/api/users/me', {
    method: 'GET',
    headers: {
      'Authorization': `Bearer ${accessToken}`,
    },
  });

  if (response.ok) {
    return await response.json();
  } else if (response.status === 401) {
    // Token expired, refresh it
    await refreshAccessToken();
    // Retry request
    return fetchProtectedData();
  }
}

// Refresh token
async function refreshAccessToken() {
  const refreshToken = localStorage.getItem('refreshToken');
  
  const response = await fetch('http://localhost:8080/api/auth/refresh', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ refreshToken }),
  });

  if (response.ok) {
    const data = await response.json();
    localStorage.setItem('accessToken', data.accessToken);
    localStorage.setItem('refreshToken', data.refreshToken);
  } else {
    // Refresh token expired, redirect to login
    window.location.href = '/login';
  }
}

// Logout
async function logout() {
  const accessToken = localStorage.getItem('accessToken');
  
  await fetch('http://localhost:8080/api/auth/logout', {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${accessToken}`,
    },
  });

  // Clear tokens
  localStorage.removeItem('accessToken');
  localStorage.removeItem('refreshToken');
  
  // Redirect to login
  window.location.href = '/login';
}
```

---

## Testing with cURL

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "usernameOrEmail": "admin",
    "password": "password123"
  }'
```

### Access Protected Endpoint
```bash
curl -X GET http://localhost:8080/api/users/me \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..."
```

### Refresh Token
```bash
curl -X POST http://localhost:8080/api/auth/refresh \
  -H "Content-Type: application/json" \
  -d '{
    "refreshToken": "eyJhbGciOiJIUzUxMiJ9..."
  }'
```

### Logout
```bash
curl -X POST http://localhost:8080/api/auth/logout \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..."
```

---

## Role-Based Access Control

The following URL patterns are protected by role:

| URL Pattern | Allowed Roles |
|-------------|---------------|
| `/auth/**` | Public (no authentication) |
| `/public/**` | Public (no authentication) |
| `/api/admin/**` | SUPER_ADMIN, SCHOOL_ADMIN |
| `/api/super-admin/**` | SUPER_ADMIN only |
| `/api/school-admin/**` | SCHOOL_ADMIN only |
| `/api/teacher/**` | TEACHER only |
| `/api/parent/**` | PARENT only |
| `/api/student/**` | STUDENT only |
| All other `/api/**` | Any authenticated user |

---

## Common Errors

### 401 Unauthorized
- **Cause:** Invalid or expired token, wrong credentials
- **Solution:** Re-login or refresh token

### 403 Forbidden
- **Cause:** User lacks required role for endpoint
- **Solution:** Check user role and endpoint permissions

### 400 Bad Request
- **Cause:** Invalid request format or validation errors
- **Solution:** Check request body against schema

### 500 Internal Server Error
- **Cause:** Server-side error
- **Solution:** Check server logs

---

## Next Steps

After implementing authentication:
1. Create user registration endpoint
2. Implement email verification
3. Add password reset flow
4. Implement role-based authorization on specific endpoints
5. Add OAuth2 providers (Google, Facebook) if needed

