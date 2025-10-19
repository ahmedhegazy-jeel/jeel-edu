# API Endpoints Summary

## Complete Endpoint List

### Authentication Endpoints (Public)

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/auth/login` | User login | ❌ No |
| POST | `/auth/refresh` | Refresh access token | ❌ No |
| POST | `/auth/logout` | User logout | ✅ Yes |
| POST | `/auth/register/student` | Register as student | ❌ No |
| POST | `/auth/register/parent` | Register as parent | ❌ No |
| POST | `/auth/register/teacher` | Register as teacher | ❌ No |

### Password Reset Endpoints (Public)

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/auth/password-reset/request` | Request password reset | ❌ No |
| GET | `/auth/password-reset/validate` | Validate reset token | ❌ No |
| POST | `/auth/password-reset/confirm` | Confirm password reset | ❌ No |
| POST | `/auth/password-change` | Change password | ✅ Yes |

### User Profile Endpoints

| Method | Endpoint | Description | Auth Required | Roles |
|--------|----------|-------------|---------------|-------|
| GET | `/users/me` | Get current user | ✅ Yes | All |
| PUT | `/users/me` | Update current user | ✅ Yes | All |

### User Management Endpoints (Admin)

| Method | Endpoint | Description | Roles |
|--------|----------|-------------|-------|
| GET | `/users` | Get all users | SUPER_ADMIN, SCHOOL_ADMIN |
| GET | `/users/{id}` | Get user by ID | SUPER_ADMIN, SCHOOL_ADMIN |
| GET | `/users/search?searchTerm=` | Search users | SUPER_ADMIN, SCHOOL_ADMIN |
| GET | `/users/role/{role}` | Get users by role | SUPER_ADMIN, SCHOOL_ADMIN |
| GET | `/users/stats` | Get user statistics | SUPER_ADMIN, SCHOOL_ADMIN |
| POST | `/users` | Create new user | SUPER_ADMIN, SCHOOL_ADMIN |
| PUT | `/users/{id}` | Update user | SUPER_ADMIN, SCHOOL_ADMIN |
| PATCH | `/users/{id}/activate` | Activate user | SUPER_ADMIN, SCHOOL_ADMIN |
| PATCH | `/users/{id}/deactivate` | Deactivate user | SUPER_ADMIN, SCHOOL_ADMIN |
| PATCH | `/users/{id}/lock` | Lock user account | SUPER_ADMIN, SCHOOL_ADMIN |
| PATCH | `/users/{id}/unlock` | Unlock user account | SUPER_ADMIN, SCHOOL_ADMIN |
| DELETE | `/users/{id}` | Delete user | SUPER_ADMIN only |

### Admin Dashboard Endpoints

| Method | Endpoint | Description | Roles |
|--------|----------|-------------|-------|
| GET | `/admin/dashboard` | Get dashboard stats | SUPER_ADMIN, SCHOOL_ADMIN |
| GET | `/admin/teachers` | Get all teachers | SUPER_ADMIN, SCHOOL_ADMIN |
| GET | `/admin/students` | Get all students | SUPER_ADMIN, SCHOOL_ADMIN |
| GET | `/admin/parents` | Get all parents | SUPER_ADMIN, SCHOOL_ADMIN |

### Teacher Endpoints

| Method | Endpoint | Description | Roles |
|--------|----------|-------------|-------|
| GET | `/teacher/dashboard` | Get teacher dashboard | TEACHER |
| GET | `/teacher/students` | Get students | TEACHER |

### Parent Endpoints

| Method | Endpoint | Description | Roles |
|--------|----------|-------------|-------|
| GET | `/parent/dashboard` | Get parent dashboard | PARENT |
| GET | `/parent/children/progress` | Get children progress | PARENT |

### Student Endpoints

| Method | Endpoint | Description | Roles |
|--------|----------|-------------|-------|
| GET | `/student/dashboard` | Get student dashboard | STUDENT |
| GET | `/student/curriculums` | Get curriculums | STUDENT |
| GET | `/student/progress` | Get progress | STUDENT |

---

## Endpoint Count by Category

| Category | Count |
|----------|-------|
| Authentication | 6 |
| Password Reset | 4 |
| User Profile | 2 |
| User Management | 12 |
| Admin Dashboard | 4 |
| Teacher | 2 |
| Parent | 2 |
| Student | 3 |
| **Total** | **35** |

---

## Role-Based Access Matrix

| Role | Accessible Endpoints |
|------|---------------------|
| **No Auth (Public)** | 8 endpoints (login, refresh, 3x register, 3x password reset) |
| **All Authenticated** | Logout, password change, user profile (me) |
| **SUPER_ADMIN** | All endpoints including user deletion |
| **SCHOOL_ADMIN** | All except user deletion and super-admin specific |
| **TEACHER** | Profile + teacher-specific (dashboard, students) |
| **PARENT** | Profile + parent-specific (dashboard, children progress) |
| **STUDENT** | Profile + student-specific (dashboard, curriculums, progress) |

---

## HTTP Status Codes

| Code | Meaning | When |
|------|---------|------|
| 200 | OK | Successful GET, PUT, PATCH, DELETE |
| 201 | Created | Successful POST (creation) |
| 400 | Bad Request | Validation error, duplicate username/email |
| 401 | Unauthorized | Invalid/expired token, wrong credentials |
| 403 | Forbidden | Insufficient permissions for endpoint |
| 404 | Not Found | User not found |
| 500 | Internal Server Error | Unexpected server error |

---

## Quick Testing Guide

### 1. Register a Student
```bash
curl -X POST http://localhost:8080/auth/register/student \
  -H "Content-Type: application/json" \
  -d '{"username":"student1","email":"student1@test.com","password":"password123","firstName":"Sara","lastName":"Ahmed"}'
```

### 2. Login
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"usernameOrEmail":"student1","password":"password123"}'
```

### 3. Get Current Profile
```bash
curl -X GET http://localhost:8080/users/me \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

### 4. Get Student Dashboard
```bash
curl -X GET http://localhost:8080/student/dashboard \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

### 5. Create User (Admin)
```bash
curl -X POST http://localhost:8080/users \
  -H "Authorization: Bearer ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"username":"teacher1","email":"teacher1@test.com","password":"password123","firstName":"Ahmed","lastName":"Hassan","role":"TEACHER"}'
```

---

## Authentication Flow

```
1. User registers → POST /auth/register/{role}
2. User logs in → POST /auth/login → Receives access & refresh tokens
3. User accesses protected endpoint → Include "Authorization: Bearer {token}"
4. Token expires → POST /auth/refresh with refresh token
5. User logs out → POST /auth/logout (optional, client clears tokens)
```

---

## Authorization Flow

```
Request → JWT Filter validates token → Extracts user & role → Security checks:
  ├─ URL-level check (SecurityConfig)
  └─ Method-level check (@PreAuthorize)
      ├─ Authorized → Controller executes → Response
      └─ Not Authorized → 403 Forbidden
```

---

## Next Implementation Steps

- [ ] Password reset flow with email tokens
- [ ] Email verification
- [ ] Parent-child relationship management
- [ ] School management APIs
- [ ] Curriculum management APIs
- [ ] Activity management APIs
- [ ] Progress tracking APIs
- [ ] File upload for profile pictures
- [ ] Pagination for list endpoints
- [ ] Sorting and advanced filtering

---

## Documentation Links

- [Authentication API](./authentication-api.md)
- [Password Reset API](./password-reset-api.md)
- [User Management API](./user-management-api.md)
- [Authentication Implementation](../developer-guides/authentication-implementation.md)
- [Backend Setup](../developer-guides/backend-setup.md)
- [Database Schema](../database/user-schema.md)

