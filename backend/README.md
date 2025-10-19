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
✅ **PROGRESS TRACKING SYSTEM COMPLETE** (12 endpoints)
  - ✅ 3 progress entities (StudentProgress, ActivityProgress, QuizAttempt)
  - ✅ Student enrollment in curriculums
  - ✅ Activity start/complete tracking
  - ✅ Quiz submission and scoring
  - ✅ Real-time progress updates
  - ✅ Comprehensive analytics and summaries
✅ **ADMIN PANEL BACKEND COMPLETE** (6 endpoints)
  - ✅ System-wide statistics dashboard
  - ✅ Curriculum analytics and insights
  - ✅ Student performance tracking
  - ✅ Top performers and leaderboards
✅ **AUTOMATED TESTING COMPLETE**
  - ✅ 11 test files with 40+ test cases
  - ✅ Controller, service, repository, entity, security tests
  - ✅ H2 in-memory database configuration
  - ✅ Integration and unit tests
  - ✅ Maven test integration

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

## API Endpoints (104 total)

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
- ✅ `GET /users/me` - Get current user profile
- ✅ `PUT /users/me` - Update current user profile

### User Management - Admin (12 endpoints)
- ✅ `GET /users` - Get all users
- ✅ `GET /users/{id}` - Get user by ID
- ✅ `GET /users/search` - Search users
- ✅ `GET /users/role/{role}` - Get users by role
- ✅ `GET /users/stats` - Get user statistics
- ✅ `POST /users` - Create user
- ✅ `PUT /users/{id}` - Update user
- ✅ `PATCH /users/{id}/activate` - Activate user
- ✅ `PATCH /users/{id}/deactivate` - Deactivate user
- ✅ `PATCH /users/{id}/lock` - Lock user account
- ✅ `PATCH /users/{id}/unlock` - Unlock user account
- ✅ `DELETE /users/{id}` - Delete user (SUPER_ADMIN only)

### Admin Dashboard (4 endpoints)
- ✅ `GET /admin/dashboard` - Admin dashboard
- ✅ `GET /admin/teachers` - Get all teachers
- ✅ `GET /admin/students` - Get all students
- ✅ `GET /admin/parents` - Get all parents

### Teacher Endpoints (2 endpoints)
- ✅ `GET /teacher/dashboard` - Teacher dashboard
- ✅ `GET /teacher/students` - View students

### Parent Endpoints (2 endpoints)
- ✅ `GET /parent/dashboard` - Parent dashboard
- ✅ `GET /parent/children/progress` - Children progress

### Student Endpoints (3 endpoints)
- ✅ `GET /student/dashboard` - Student dashboard
- ✅ `GET /student/curriculums` - View curriculums
- ✅ `GET /student/progress` - View progress

### Curriculum Management (37 endpoints)

**Curriculum (7 endpoints):**
- ✅ `POST /curriculums` - Create curriculum
- ✅ `GET /curriculums` - Get all curriculums
- ✅ `GET /curriculums/{id}` - Get curriculum by ID
- ✅ `GET /curriculums/status/{status}` - Get by status
- ✅ `GET /curriculums/search` - Search curriculums
- ✅ `PUT /curriculums/{id}` - Update curriculum
- ✅ `DELETE /curriculums/{id}` - Delete curriculum

**Unit (9 endpoints):**
- ✅ `POST /units` - Create unit
- ✅ `GET /units` - Get all units
- ✅ `GET /units/{id}` - Get unit by ID
- ✅ `GET /units/curriculum/{curriculumId}` - Get units by curriculum
- ✅ `GET /units/curriculum/{curriculumId}/status/{status}` - Get by curriculum and status
- ✅ `GET /units/status/{status}` - Get by status
- ✅ `GET /units/curriculum/{curriculumId}/search` - Search in curriculum
- ✅ `PUT /units/{id}` - Update unit
- ✅ `DELETE /units/{id}` - Delete unit

**Lesson (9 endpoints):**
- ✅ `POST /lessons` - Create lesson
- ✅ `GET /lessons` - Get all lessons
- ✅ `GET /lessons/{id}` - Get lesson by ID
- ✅ `GET /lessons/unit/{unitId}` - Get lessons by unit
- ✅ `GET /lessons/unit/{unitId}/status/{status}` - Get by unit and status
- ✅ `GET /lessons/status/{status}` - Get by status
- ✅ `GET /lessons/unit/{unitId}/search` - Search in unit
- ✅ `PUT /lessons/{id}` - Update lesson
- ✅ `DELETE /lessons/{id}` - Delete lesson

**Activity (12 endpoints):**
- ✅ `POST /activities` - Create activity
- ✅ `GET /activities` - Get all activities
- ✅ `GET /activities/{id}` - Get activity by ID
- ✅ `GET /activities/lesson/{lessonId}` - Get activities by lesson
- ✅ `GET /activities/lesson/{lessonId}/status/{status}` - Get by lesson and status
- ✅ `GET /activities/type/{type}` - Get by activity type
- ✅ `GET /activities/lesson/{lessonId}/type/{type}` - Get by lesson and type
- ✅ `GET /activities/tag/{tag}` - Get by tag
- ✅ `GET /activities/lesson/{lessonId}/search` - Search in lesson
- ✅ `GET /activities/type/{type}/count` - Count by type
- ✅ `PUT /activities/{id}` - Update activity
- ✅ `DELETE /activities/{id}` - Delete activity

### School Management (14 endpoints)
- ✅ `POST /schools` - Create school (auto-creates SCHOOL_ADMIN user)
- ✅ `GET /schools` - Get all schools
- ✅ `GET /schools/{id}` - Get school by ID
- ✅ `GET /schools/active` - Get active schools
- ✅ `GET /schools/status` - Get by status
- ✅ `GET /schools/city/{city}` - Get by city
- ✅ `GET /schools/country/{country}` - Get by country
- ✅ `GET /schools/search` - Search schools
- ✅ `GET /schools/capacity/available` - Get schools with capacity
- ✅ `GET /schools/stats` - Get school statistics
- ✅ `PUT /schools/{id}` - Update school
- ✅ `PATCH /schools/{id}/activate` - Activate school
- ✅ `PATCH /schools/{id}/deactivate` - Deactivate school
- ✅ `DELETE /schools/{id}` - Delete school

### Progress Tracking (12 endpoints)
- ✅ `POST /progress/enroll/{studentId}` - Enroll student in curriculum
- ✅ `GET /progress/my-summary` - Get student's progress summary
- ✅ `GET /progress/student/{studentId}/summary` - Get student summary (admin view)
- ✅ `GET /progress/student/{studentId}/curriculum/{curriculumId}` - Get specific progress
- ✅ `GET /progress/student/{studentId}` - Get all student progress
- ✅ `GET /progress/my-progress` - Get own progress list
- ✅ `POST /progress/activity/{activityId}/start` - Start activity
- ✅ `POST /progress/activity/{activityId}/complete` - Complete activity
- ✅ `GET /progress/activity/{activityId}` - Get activity progress
- ✅ `POST /progress/quiz/{quizId}/submit` - Submit quiz attempt
- ✅ `GET /progress/quiz/{quizId}/attempts` - Get own quiz attempts
- ✅ `GET /progress/student/{studentId}/quiz/{quizId}/attempts` - Get student quiz attempts

### Admin Panel (6 endpoints)
- ✅ `GET /admin-panel/stats/system` - System-wide statistics
- ✅ `GET /admin-panel/analytics/curriculum/{curriculumId}` - Curriculum analytics
- ✅ `GET /admin-panel/students/top-performers` - Top performing students
- ✅ `GET /admin-panel/students/performance` - All students performance
- ✅ `GET /admin-panel/students/{studentId}/performance` - Student performance details
- ✅ `GET /admin-panel/curriculum/{curriculumId}/leaderboard` - Curriculum leaderboard

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
   - Health check: `http://localhost:8080/actuator/health`

## Documentation

### API Documentation
- [API Endpoints Summary](../docs/endpoints-summary.md) - Quick reference for all 104 endpoints
- [Authentication API](../docs/authentication-api.md) - Authentication endpoints
- [Password Reset API](../docs/password-reset-api.md) - Password reset flow
- [User Management API](../docs/user-management-api.md) - User management endpoints
- [Curriculum API](../docs/curriculum-api.md) - Curriculum management (37 endpoints)
- [School Management API](../docs/school-management-api.md) - School management (14 endpoints)
- [Progress Tracking API](../docs/progress-tracking-api.md) - Progress tracking (12 endpoints)
- [Admin Panel API](../docs/admin-panel-api.md) - Admin panel analytics (6 endpoints)

### Developer Guides
- [Backend Setup Guide](../docs/developer-guides/backend-setup.md) - Installation & setup
- [Authentication Implementation](../docs/developer-guides/authentication-implementation.md) - Technical guide
- [Testing Guide](../docs/developer-guides/testing-guide.md) - How to run and write tests

### Architecture & Database
- [Curriculum System Overview](../docs/architecture/curriculum-system-overview.md) - System architecture
- [Database Schema](../docs/database/user-schema.md) - Database structure

### Project Status
- [Implementation Summary](../docs/IMPLEMENTATION_SUMMARY.md) - Complete project status
- [Backend Verification](../docs/BACKEND_VERIFICATION.md) - Verification report

