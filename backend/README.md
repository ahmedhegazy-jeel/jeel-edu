# Backend - JeelEducation LMS API

## Tech Stack
- **Framework**: Spring Boot 3.2.0
- **Build Tool**: Maven
- **Database**: MySQL 8.0+
- **Authentication**: JWT (JSON Web Tokens)
- **Authorization**: Role-Based Access Control (RBAC)
- **Java Version**: 17
- **Security**: Spring Security 6.x
- **Password Encryption**: BCrypt

## Current Status
✅ User model and roles implemented (5 roles: SUPER_ADMIN, SCHOOL_ADMIN, TEACHER, PARENT, STUDENT)
✅ JWT authentication system complete
✅ Spring Security configuration with RBAC
✅ Authentication & password reset endpoints
✅ User management with role-based dashboards
✅ Global exception handling
✅ CORS configuration
✅ **CURRICULUM MANAGEMENT SYSTEM COMPLETE** (37 endpoints)
  - ✅ 18 entities (Curriculum → Unit → Lesson → Activity + 8 activity types)
  - ✅ 4 services with full CRUD operations
  - ✅ 4 controllers with role-based authorization
  - ✅ Support for TEXT, PDF, AUDIO, VIDEO, INTERACTIVE, QUIZ, BOOK, HOMEWORK activities
✅ **SCHOOL MANAGEMENT SYSTEM COMPLETE** (14 endpoints)
  - ✅ School entity with capacity management
  - ✅ Auto-creates SCHOOL_ADMIN user on school creation
  - ✅ Geographic filtering (city, country)
  - ✅ Capacity tracking and statistics

## Project Structure
```
backend/
├── src/
│   ├── main/
│   │   ├── java/com/jeeleducation/lms/
│   │   │   ├── config/           # Security, CORS configurations
│   │   │   │   └── SecurityConfig.java
│   │   │   ├── controller/       # REST API endpoints
│   │   │   │   └── AuthController.java
│   │   │   ├── dto/              # Data Transfer Objects
│   │   │   │   ├── UserDTO.java
│   │   │   │   ├── CreateUserRequest.java
│   │   │   │   ├── UpdateUserRequest.java
│   │   │   │   ├── LoginRequest.java
│   │   │   │   ├── JwtResponse.java
│   │   │   │   ├── RefreshTokenRequest.java
│   │   │   │   └── MessageResponse.java
│   │   │   ├── entity/           # JPA entities
│   │   │   │   ├── User.java
│   │   │   │   └── Role.java (enum)
│   │   │   ├── repository/       # Database repositories
│   │   │   │   └── UserRepository.java
│   │   │   ├── mapper/           # Entity/DTO mappers
│   │   │   │   └── UserMapper.java
│   │   │   ├── security/         # JWT & Auth components
│   │   │   │   ├── JwtUtils.java
│   │   │   │   ├── UserDetailsImpl.java
│   │   │   │   ├── UserDetailsServiceImpl.java
│   │   │   │   ├── JwtAuthenticationFilter.java
│   │   │   │   └── JwtAuthenticationEntryPoint.java
│   │   │   ├── exception/        # Exception handlers
│   │   │   │   └── GlobalExceptionHandler.java
│   │   │   └── LmsApplication.java
│   │   └── resources/
│   │       ├── application.yml        # Main config
│   │       ├── application-dev.yml    # Dev profile
│   │       └── application-prod.yml   # Prod profile
│   └── test/
│       └── java/com/jeeleducation/lms/
│           └── LmsApplicationTests.java
├── pom.xml                    # Maven dependencies
├── checkstyle.xml             # Code quality rules
├── .editorconfig              # Editor configuration
└── README.md
```

## User Roles
1. **Super Admin** - System-wide management
2. **School Admin** - School-level management
3. **Teacher** - Curriculum & student management
4. **Parent** - Child progress tracking
5. **Student** - Learning interface

## Core Entities
- User (with roles)
- School
- Curriculum → Unit → Lesson → Activity
- Progress Tracking

## API Endpoints (86 total)

### Authentication (6 endpoints)
- ✅ `POST /auth/login` - User login
- ✅ `POST /auth/refresh` - Refresh access token
- ✅ `POST /auth/logout` - Logout user
- ✅ `POST /auth/register/student` - Register as student
- ✅ `POST /auth/register/parent` - Register as parent
- ✅ `POST /auth/register/teacher` - Register as teacher

### Password Reset (4 endpoints)
- ✅ `POST /auth/password-reset/request` - Request password reset
- ✅ `GET /auth/password-reset/validate` - Validate reset token
- ✅ `POST /auth/password-reset/confirm` - Confirm password reset
- ✅ `POST /auth/password-change` - Change password (authenticated)

### User Profile (2 endpoints)
- ✅ `GET /api/users/me` - Get current user profile
- ✅ `PUT /api/users/me` - Update current user profile

### User Management - Admin (12 endpoints)
- ✅ `GET /api/users` - Get all users
- ✅ `GET /api/users/{id}` - Get user by ID
- ✅ `GET /api/users/search` - Search users
- ✅ `GET /api/users/role/{role}` - Get users by role
- ✅ `GET /api/users/stats` - Get user statistics
- ✅ `POST /api/users` - Create user
- ✅ `PUT /api/users/{id}` - Update user
- ✅ `PATCH /api/users/{id}/activate` - Activate user
- ✅ `PATCH /api/users/{id}/deactivate` - Deactivate user
- ✅ `PATCH /api/users/{id}/lock` - Lock user account
- ✅ `PATCH /api/users/{id}/unlock` - Unlock user account
- ✅ `DELETE /api/users/{id}` - Delete user (SUPER_ADMIN only)

### Admin Dashboard (4 endpoints)
- ✅ `GET /api/admin/dashboard` - Admin dashboard
- ✅ `GET /api/admin/teachers` - Get all teachers
- ✅ `GET /api/admin/students` - Get all students
- ✅ `GET /api/admin/parents` - Get all parents

### Teacher Endpoints (2 endpoints)
- ✅ `GET /api/teacher/dashboard` - Teacher dashboard
- ✅ `GET /api/teacher/students` - View students

### Parent Endpoints (2 endpoints)
- ✅ `GET /api/parent/dashboard` - Parent dashboard
- ✅ `GET /api/parent/children/progress` - Children progress

### Student Endpoints (3 endpoints)
- ✅ `GET /api/student/dashboard` - Student dashboard
- ✅ `GET /api/student/curriculums` - View curriculums
- ✅ `GET /api/student/progress` - View progress

### Curriculum Management (37 endpoints)

**Curriculum (7 endpoints):**
- ✅ `POST /api/curriculums` - Create curriculum
- ✅ `GET /api/curriculums` - Get all curriculums
- ✅ `GET /api/curriculums/{id}` - Get curriculum by ID
- ✅ `GET /api/curriculums/status/{status}` - Get by status
- ✅ `GET /api/curriculums/search` - Search curriculums
- ✅ `PUT /api/curriculums/{id}` - Update curriculum
- ✅ `DELETE /api/curriculums/{id}` - Delete curriculum

**Unit (9 endpoints):**
- ✅ `POST /api/units` - Create unit
- ✅ `GET /api/units` - Get all units
- ✅ `GET /api/units/{id}` - Get unit by ID
- ✅ `GET /api/units/curriculum/{curriculumId}` - Get units by curriculum
- ✅ `GET /api/units/curriculum/{curriculumId}/status/{status}` - Get by curriculum and status
- ✅ `GET /api/units/status/{status}` - Get by status
- ✅ `GET /api/units/curriculum/{curriculumId}/search` - Search in curriculum
- ✅ `PUT /api/units/{id}` - Update unit
- ✅ `DELETE /api/units/{id}` - Delete unit

**Lesson (9 endpoints):**
- ✅ `POST /api/lessons` - Create lesson
- ✅ `GET /api/lessons` - Get all lessons
- ✅ `GET /api/lessons/{id}` - Get lesson by ID
- ✅ `GET /api/lessons/unit/{unitId}` - Get lessons by unit
- ✅ `GET /api/lessons/unit/{unitId}/status/{status}` - Get by unit and status
- ✅ `GET /api/lessons/status/{status}` - Get by status
- ✅ `GET /api/lessons/unit/{unitId}/search` - Search in unit
- ✅ `PUT /api/lessons/{id}` - Update lesson
- ✅ `DELETE /api/lessons/{id}` - Delete lesson

**Activity (12 endpoints):**
- ✅ `POST /api/activities` - Create activity
- ✅ `GET /api/activities` - Get all activities
- ✅ `GET /api/activities/{id}` - Get activity by ID
- ✅ `GET /api/activities/lesson/{lessonId}` - Get activities by lesson
- ✅ `GET /api/activities/lesson/{lessonId}/status/{status}` - Get by lesson and status
- ✅ `GET /api/activities/type/{type}` - Get by activity type
- ✅ `GET /api/activities/lesson/{lessonId}/type/{type}` - Get by lesson and type
- ✅ `GET /api/activities/tag/{tag}` - Get by tag
- ✅ `GET /api/activities/lesson/{lessonId}/search` - Search in lesson
- ✅ `GET /api/activities/type/{type}/count` - Count by type
- ✅ `PUT /api/activities/{id}` - Update activity
- ✅ `DELETE /api/activities/{id}` - Delete activity

### School Management (14 endpoints)
- ✅ `POST /api/schools` - Create school (auto-creates SCHOOL_ADMIN user)
- ✅ `GET /api/schools` - Get all schools
- ✅ `GET /api/schools/{id}` - Get school by ID
- ✅ `GET /api/schools/active` - Get active schools
- ✅ `GET /api/schools/status` - Get by status
- ✅ `GET /api/schools/city/{city}` - Get by city
- ✅ `GET /api/schools/country/{country}` - Get by country
- ✅ `GET /api/schools/search` - Search schools
- ✅ `GET /api/schools/capacity/available` - Get schools with capacity
- ✅ `GET /api/schools/stats` - Get school statistics
- ✅ `PUT /api/schools/{id}` - Update school
- ✅ `PATCH /api/schools/{id}/activate` - Activate school
- ✅ `PATCH /api/schools/{id}/deactivate` - Deactivate school
- ✅ `DELETE /api/schools/{id}` - Delete school

## Getting Started

See [Backend Setup Guide](../docs/developer-guides/backend-setup.md) for detailed setup instructions.

### Quick Start

1. **Prerequisites**: Java 17+, Maven 3.8+, MySQL 8.0+

2. **Start MySQL**:
   ```bash
   # Windows
   net start MySQL80
   ```

3. **Build & Run**:
   ```bash
   cd backend
   mvn clean install
   mvn spring-boot:run
   ```

4. **Access API**:
   - Base URL: `http://localhost:8080/api`
   - Health check: `http://localhost:8080/api/actuator/health`

## Documentation

- [API Endpoints Summary](../docs/api/endpoints-summary.md) - Quick reference for all 86 endpoints
- [Curriculum API](../docs/api/curriculum-api.md) - Curriculum management endpoints (37 endpoints)
- [School Management API](../docs/api/school-management-api.md) - School management endpoints (14 endpoints)
- [Authentication API](../docs/api/authentication-api.md) - Authentication endpoints details
- [Password Reset API](../docs/api/password-reset-api.md) - Password reset flow
- [User Management API](../docs/api/user-management-api.md) - User management endpoints details
- [Authentication Implementation](../docs/developer-guides/authentication-implementation.md) - Technical guide
- [Database Schema](../docs/database/user-schema.md) - Database structure
- [Backend Setup Guide](../docs/developer-guides/backend-setup.md) - Installation & setup

