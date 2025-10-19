# User Management API Documentation

## Base URL
```
http://localhost:8080/api
```

## Overview
The User Management API provides comprehensive user management functionality with role-based access control (RBAC). Different endpoints are accessible based on user roles.

---

## Public Endpoints (No Authentication Required)

### 1. Register Student

**Endpoint:** `POST /auth/register/student`

**Description:** Register a new student account (public self-registration).

**Request Body:**
```json
{
  "username": "student1",
  "email": "student1@example.com",
  "password": "password123",
  "firstName": "Sara",
  "lastName": "Mohammed",
  "mobile": "+201234567890",
  "bio": "Excited to learn!"
}
```

**Success Response (201 Created):**
```json
{
  "id": 5,
  "username": "student1",
  "email": "student1@example.com",
  "firstName": "Sara",
  "lastName": "Mohammed",
  "mobile": "+201234567890",
  "role": "STUDENT",
  "isActive": true,
  "isEmailVerified": false,
  "isAccountNonLocked": true,
  "bio": "Excited to learn!",
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00"
}
```

### 2. Register Parent

**Endpoint:** `POST /auth/register/parent`

**Description:** Register a new parent account (public self-registration).

**Request Body:** Same as student registration

**Success Response:** Same format with `"role": "PARENT"`

### 3. Register Teacher

**Endpoint:** `POST /auth/register/teacher`

**Description:** Register a new teacher account (requires admin approval).

**Request Body:** Same as student registration

**Success Response (201 Created):**
```json
{
  "message": "Teacher registration successful. Account pending approval.",
  "user": {
    "id": 6,
    "username": "teacher1",
    "email": "teacher1@example.com",
    "role": "TEACHER",
    ...
  }
}
```

---

## User Profile Endpoints (Authenticated Users)

### 4. Get Current User Profile

**Endpoint:** `GET /users/me`

**Description:** Get the profile of the currently authenticated user.

**Headers:**
```
Authorization: Bearer <access_token>
```

**Success Response (200 OK):**
```json
{
  "id": 1,
  "username": "admin",
  "email": "admin@jeeleducation.com",
  "firstName": "System",
  "lastName": "Admin",
  "mobile": null,
  "role": "SUPER_ADMIN",
  "isActive": true,
  "isEmailVerified": true,
  "isAccountNonLocked": true,
  "profilePictureUrl": null,
  "bio": null,
  "createdAt": "2024-01-01T00:00:00",
  "updatedAt": "2024-01-15T10:30:00",
  "lastLoginAt": "2024-01-15T10:00:00"
}
```

### 5. Update Current User Profile

**Endpoint:** `PUT /users/me`

**Description:** Update the profile of the currently authenticated user.

**Headers:**
```
Authorization: Bearer <access_token>
```

**Request Body:**
```json
{
  "email": "newemail@example.com",
  "firstName": "UpdatedFirst",
  "lastName": "UpdatedLast",
  "mobile": "+201234567899",
  "bio": "Updated bio",
  "profilePictureUrl": "https://example.com/avatar.jpg"
}
```

**Success Response (200 OK):** Updated user DTO

---

## Admin Endpoints (SUPER_ADMIN, SCHOOL_ADMIN)

### 6. Get All Users

**Endpoint:** `GET /users`

**Authorization:** SUPER_ADMIN, SCHOOL_ADMIN

**Success Response (200 OK):**
```json
[
  {
    "id": 1,
    "username": "admin",
    ...
  },
  {
    "id": 2,
    "username": "teacher1",
    ...
  }
]
```

### 7. Get User by ID

**Endpoint:** `GET /users/{id}`

**Authorization:** SUPER_ADMIN, SCHOOL_ADMIN

**Success Response (200 OK):** User DTO

### 8. Search Users

**Endpoint:** `GET /users/search?searchTerm={term}`

**Authorization:** SUPER_ADMIN, SCHOOL_ADMIN

**Description:** Search users by name, email, or username.

**Parameters:**
- `searchTerm` (required): Search term

**Success Response (200 OK):** Array of matching users

### 9. Get Users by Role

**Endpoint:** `GET /users/role/{role}`

**Authorization:** SUPER_ADMIN, SCHOOL_ADMIN

**Description:** Get all users with a specific role.

**Path Parameters:**
- `role`: SUPER_ADMIN, SCHOOL_ADMIN, TEACHER, PARENT, or STUDENT

**Success Response (200 OK):** Array of users with specified role

### 10. Create User

**Endpoint:** `POST /users`

**Authorization:** SUPER_ADMIN, SCHOOL_ADMIN

**Request Body:**
```json
{
  "username": "newuser",
  "email": "newuser@example.com",
  "password": "password123",
  "firstName": "New",
  "lastName": "User",
  "mobile": "+201234567890",
  "role": "TEACHER",
  "bio": "Teacher bio"
}
```

**Success Response (201 Created):** Created user DTO

### 11. Update User

**Endpoint:** `PUT /users/{id}`

**Authorization:** SUPER_ADMIN, SCHOOL_ADMIN

**Request Body:** Same as update profile

**Success Response (200 OK):** Updated user DTO

### 12. Activate User

**Endpoint:** `PATCH /users/{id}/activate`

**Authorization:** SUPER_ADMIN, SCHOOL_ADMIN

**Success Response (200 OK):**
```json
{
  "message": "User activated successfully"
}
```

### 13. Deactivate User

**Endpoint:** `PATCH /users/{id}/deactivate`

**Authorization:** SUPER_ADMIN, SCHOOL_ADMIN

**Success Response (200 OK):**
```json
{
  "message": "User deactivated successfully"
}
```

### 14. Lock User Account

**Endpoint:** `PATCH /users/{id}/lock`

**Authorization:** SUPER_ADMIN, SCHOOL_ADMIN

**Success Response (200 OK):**
```json
{
  "message": "User account locked successfully"
}
```

### 15. Unlock User Account

**Endpoint:** `PATCH /users/{id}/unlock`

**Authorization:** SUPER_ADMIN, SCHOOL_ADMIN

**Success Response (200 OK):**
```json
{
  "message": "User account unlocked successfully"
}
```

### 16. Get User Statistics

**Endpoint:** `GET /users/stats`

**Authorization:** SUPER_ADMIN, SCHOOL_ADMIN

**Success Response (200 OK):**
```json
{
  "total": 150,
  "superAdmins": 2,
  "schoolAdmins": 5,
  "teachers": 30,
  "parents": 50,
  "students": 63
}
```

---

## Super Admin Only Endpoints

### 17. Delete User

**Endpoint:** `DELETE /users/{id}`

**Authorization:** SUPER_ADMIN only

**Success Response (200 OK):**
```json
{
  "message": "User deleted successfully"
}
```

---

## Admin Dashboard Endpoints

### 18. Get Admin Dashboard

**Endpoint:** `GET /admin/dashboard`

**Authorization:** SUPER_ADMIN, SCHOOL_ADMIN

**Success Response (200 OK):**
```json
{
  "totalUsers": 150,
  "totalStudents": 63,
  "totalTeachers": 30,
  "totalParents": 50,
  "totalSchoolAdmins": 5,
  "activeUsers": 142
}
```

### 19. Get All Teachers

**Endpoint:** `GET /admin/teachers`

**Authorization:** SUPER_ADMIN, SCHOOL_ADMIN

**Success Response (200 OK):** Array of teacher users

### 20. Get All Students

**Endpoint:** `GET /admin/students`

**Authorization:** SUPER_ADMIN, SCHOOL_ADMIN

**Success Response (200 OK):** Array of student users

### 21. Get All Parents

**Endpoint:** `GET /admin/parents`

**Authorization:** SUPER_ADMIN, SCHOOL_ADMIN

**Success Response (200 OK):** Array of parent users

---

## Teacher Endpoints

### 22. Get Teacher Dashboard

**Endpoint:** `GET /teacher/dashboard`

**Authorization:** TEACHER only

**Success Response (200 OK):**
```json
{
  "totalStudents": 63,
  "message": "Welcome to Teacher Dashboard"
}
```

### 23. Get Students (Teacher View)

**Endpoint:** `GET /teacher/students`

**Authorization:** TEACHER only

**Success Response (200 OK):** Array of student users

---

## Parent Endpoints

### 24. Get Parent Dashboard

**Endpoint:** `GET /parent/dashboard`

**Authorization:** PARENT only

**Success Response (200 OK):**
```json
{
  "message": "Welcome to Parent Dashboard",
  "children": 0
}
```

### 25. Get Children Progress

**Endpoint:** `GET /parent/children/progress`

**Authorization:** PARENT only

**Success Response (200 OK):**
```json
{
  "message": "Children progress tracking coming soon"
}
```

---

## Student Endpoints

### 26. Get Student Dashboard

**Endpoint:** `GET /student/dashboard`

**Authorization:** STUDENT only

**Success Response (200 OK):**
```json
{
  "message": "Welcome to Student Learning Dashboard",
  "curriculums": 0,
  "progress": 0
}
```

### 27. Get Student Curriculums

**Endpoint:** `GET /student/curriculums`

**Authorization:** STUDENT only

**Success Response (200 OK):**
```json
{
  "message": "Curriculum learning interface coming soon"
}
```

### 28. Get Student Progress

**Endpoint:** `GET /student/progress`

**Authorization:** STUDENT only

**Success Response (200 OK):**
```json
{
  "message": "Progress tracking coming soon"
}
```

---

## Role-Based Access Summary

| Endpoint Pattern | Allowed Roles |
|-----------------|---------------|
| `/auth/**` | Public (no auth) |
| `/users/me` | All authenticated users |
| `/users` (CRUD) | SUPER_ADMIN, SCHOOL_ADMIN |
| `/admin/**` | SUPER_ADMIN, SCHOOL_ADMIN |
| `/teacher/**` | TEACHER |
| `/parent/**` | PARENT |
| `/student/**` | STUDENT |
| DELETE `/users/{id}` | SUPER_ADMIN only |

---

## Error Responses

### 400 Bad Request
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 400,
  "error": "Validation Failed",
  "errors": {
    "email": "Email is already in use",
    "username": "Username is already taken"
  }
}
```

### 401 Unauthorized
```json
{
  "status": 401,
  "error": "Unauthorized",
  "message": "Full authentication is required to access this resource"
}
```

### 403 Forbidden
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 403,
  "error": "Forbidden",
  "message": "Access Denied"
}
```

### 404 Not Found
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 404,
  "error": "User Not Found",
  "message": "User not found with id: 999"
}
```

---

## Testing with cURL

### Register Student
```bash
curl -X POST http://localhost:8080/auth/register/student \
  -H "Content-Type: application/json" \
  -d '{
    "username": "student1",
    "email": "student1@example.com",
    "password": "password123",
    "firstName": "Sara",
    "lastName": "Mohammed"
  }'
```

### Get Current User
```bash
curl -X GET http://localhost:8080/users/me \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

### Get All Users (Admin)
```bash
curl -X GET http://localhost:8080/users \
  -H "Authorization: Bearer ADMIN_ACCESS_TOKEN"
```

### Create User (Admin)
```bash
curl -X POST http://localhost:8080/users \
  -H "Authorization: Bearer ADMIN_ACCESS_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "teacher1",
    "email": "teacher1@example.com",
    "password": "password123",
    "firstName": "Ahmed",
    "lastName": "Hassan",
    "role": "TEACHER"
  }'
```

### Get Admin Dashboard
```bash
curl -X GET http://localhost:8080/admin/dashboard \
  -H "Authorization: Bearer ADMIN_ACCESS_TOKEN"
```

---

## Next Steps

- Implement email verification
- Add password reset functionality
- Implement parent-child relationship tracking
- Add curriculum assignment to students
- Implement progress tracking
- Add bulk user operations
- Implement user import/export

