# Admin Panel API Documentation

## Base URL
```
http://localhost:8080/admin-panel
```

## Overview
The Admin Panel API provides comprehensive analytics, reports, and statistics for system administrators. It aggregates data across users, schools, curriculums, and student progress.

---

## Endpoints (6 total)

### 1. Get System Statistics

**Endpoint:** `GET /admin-panel/stats/system`

**Authorization:** SUPER_ADMIN only

**Description:** Get comprehensive system-wide statistics including users, schools, curriculums, and progress metrics.

**Success Response (200 OK):**
```json
{
  "totalUsers": 1523,
  "activeUsers": 1489,
  "totalStudents": 850,
  "totalTeachers": 125,
  "totalParents": 520,
  "totalSchoolAdmins": 25,
  "totalSuperAdmins": 3,
  "totalSchools": 25,
  "activeSchools": 23,
  "totalStudentsEnrolled": 12500,
  "totalCurriculums": 15,
  "publishedCurriculums": 12,
  "draftCurriculums": 3,
  "totalUnits": 180,
  "totalLessons": 720,
  "totalActivities": 2880,
  "activitiesByType": {
    "TEXT": 500,
    "PDF": 300,
    "AUDIO": 400,
    "VIDEO": 450,
    "INTERACTIVE": 200,
    "QUIZ": 600,
    "BOOK": 250,
    "HOMEWORK": 180
  },
  "totalEnrollments": 2550,
  "completedCurriculums": 385,
  "averageCompletionRate": 68.5,
  "totalQuizzesTaken": 4800,
  "averageQuizScore": 76.8,
  "systemStatus": "HEALTHY"
}
```

---

### 2. Get Curriculum Analytics

**Endpoint:** `GET /admin-panel/analytics/curriculum/{curriculumId}`

**Authorization:** SUPER_ADMIN, SCHOOL_ADMIN

**Description:** Get detailed analytics for a specific curriculum.

**Success Response (200 OK):**
```json
{
  "curriculumId": 1,
  "curriculumName": "Arabic Language Curriculum",
  "status": "PUBLISHED",
  "totalUnits": 12,
  "totalLessons": 48,
  "totalActivities": 192,
  "totalPoints": 1920,
  "enrolledStudents": 450,
  "completedStudents": 85,
  "averageCompletionRate": 72.5,
  "averageScore": 78.3,
  "totalQuizzes": 48,
  "quizPassRate": 82.5
}
```

---

### 3. Get Top Performing Students

**Endpoint:** `GET /admin-panel/students/top-performers?limit={limit}`

**Authorization:** SUPER_ADMIN, SCHOOL_ADMIN

**Description:** Get top performing students sorted by average quiz score.

**Query Parameters:**
- `limit` (optional): Number of students to return (default: 10)

**Success Response (200 OK):**
```json
[
  {
    "studentId": 42,
    "studentName": "Sara Mohammed",
    "studentEmail": "sara.mohammed@example.com",
    "enrolledCurriculums": 3,
    "completedCurriculums": 1,
    "totalActivitiesCompleted": 95,
    "totalPointsEarned": 950,
    "averageCompletionRate": 85.5,
    "averageQuizScore": 94.2,
    "totalQuizzesTaken": 24,
    "totalQuizzesPassed": 23,
    "lastActiveAt": "2024-01-15T10:30:00",
    "performanceLevel": "Excellent"
  },
  {
    "studentId": 37,
    "studentName": "Ahmed Ali",
    "studentEmail": "ahmed.ali@example.com",
    "enrolledCurriculums": 2,
    "completedCurriculums": 2,
    "totalActivitiesCompleted": 128,
    "totalPointsEarned": 1280,
    "averageCompletionRate": 100.0,
    "averageQuizScore": 91.5,
    "totalQuizzesTaken": 32,
    "totalQuizzesPassed": 30,
    "lastActiveAt": "2024-01-15T09:15:00",
    "performanceLevel": "Excellent"
  }
]
```

---

### 4. Get All Students Performance

**Endpoint:** `GET /admin-panel/students/performance`

**Authorization:** SUPER_ADMIN, SCHOOL_ADMIN

**Description:** Get performance metrics for all students in the system.

**Success Response (200 OK):** Array of StudentPerformanceDTO

---

### 5. Get Student Performance Details

**Endpoint:** `GET /admin-panel/students/{studentId}/performance`

**Authorization:** SUPER_ADMIN, SCHOOL_ADMIN

**Description:** Get detailed performance metrics for a specific student.

**Success Response (200 OK):** StudentPerformanceDTO

---

### 6. Get Curriculum Leaderboard

**Endpoint:** `GET /admin-panel/curriculum/{curriculumId}/leaderboard?limit={limit}`

**Authorization:** SUPER_ADMIN, SCHOOL_ADMIN

**Description:** Get top performing students in a specific curriculum.

**Query Parameters:**
- `limit` (optional): Number of students to return (default: 10)

**Success Response (200 OK):** Array of StudentPerformanceDTO (sorted by points earned)

---

## Dashboard Widgets

### Super Admin Dashboard

**System Overview Widget:**
```javascript
// GET /admin-panel/stats/system
{
  totalUsers: 1523,
  totalSchools: 25,
  totalCurriculums: 15,
  averageCompletionRate: 68.5%
}
```

**Top Performers Widget:**
```javascript
// GET /admin-panel/students/top-performers?limit=5
[
  { studentName: "Sara M.", avgScore: 94.2, performanceLevel: "Excellent" },
  { studentName: "Ahmed A.", avgScore: 91.5, performanceLevel: "Excellent" },
  ...
]
```

**Activity Distribution Widget:**
```javascript
// From system stats
activitiesByType: {
  QUIZ: 600,
  VIDEO: 450,
  TEXT: 500,
  ...
}
```

### School Admin Dashboard

**School Performance:**
```javascript
// GET /schools/{id} + analytics
{
  schoolName: "Al-Azhar School",
  totalStudents: 350,
  averagePerformance: 76.8,
  topCurriculum: "Arabic Language"
}
```

**Student Performance Table:**
```javascript
// GET /admin-panel/students/performance
[
  { student: "Sara M.", completion: 85%, avgScore: 94.2 },
  { student: "Ahmed A.", completion: 100%, avgScore: 91.5 },
  ...
]
```

### Curriculum Analytics Dashboard

**Curriculum Stats:**
```javascript
// GET /admin-panel/analytics/curriculum/{id}
{
  curriculumName: "Arabic Language",
  enrolledStudents: 450,
  completedStudents: 85,
  averageCompletionRate: 72.5%,
  totalActivities: 192
}
```

**Leaderboard:**
```javascript
// GET /admin-panel/curriculum/{id}/leaderboard?limit=10
[
  { rank: 1, student: "Sara M.", points: 950, completion: 95% },
  { rank: 2, student: "Ahmed A.", points: 920, completion: 92% },
  ...
]
```

---

## Performance Levels

Students are automatically categorized based on average quiz score:

| Score Range | Performance Level |
|-------------|-------------------|
| 90% - 100% | Excellent |
| 75% - 89% | Good |
| 60% - 74% | Average |
| Below 60% | Needs Improvement |

---

## Frontend Integration Example

### React Admin Dashboard

```javascript
// Fetch system statistics
async function getSystemStats() {
  const response = await fetch('http://localhost:8080/admin-panel/stats/system', {
    headers: {
      'Authorization': `Bearer ${adminToken}`,
    },
  });
  
  const stats = await response.json();
  
  return {
    users: {
      total: stats.totalUsers,
      students: stats.totalStudents,
      teachers: stats.totalTeachers,
    },
    schools: {
      total: stats.totalSchools,
      active: stats.activeSchools,
    },
    curriculums: {
      total: stats.totalCurriculums,
      published: stats.publishedCurriculums,
    },
    progress: {
      avgCompletion: stats.averageCompletionRate,
      avgQuizScore: stats.averageQuizScore,
    },
  };
}

// Fetch top performers
async function getTopPerformers(limit = 10) {
  const response = await fetch(
    `http://localhost:8080/admin-panel/students/top-performers?limit=${limit}`,
    {
      headers: {
        'Authorization': `Bearer ${adminToken}`,
      },
    }
  );
  
  const students = await response.json();
  
  return students.map((student, index) => ({
    rank: index + 1,
    name: student.studentName,
    score: student.averageQuizScore,
    points: student.totalPointsEarned,
    level: student.performanceLevel,
  }));
}

// Fetch curriculum analytics
async function getCurriculumAnalytics(curriculumId) {
  const response = await fetch(
    `http://localhost:8080/admin-panel/analytics/curriculum/${curriculumId}`,
    {
      headers: {
        'Authorization': `Bearer ${adminToken}`,
      },
    }
  );
  
  const analytics = await response.json();
  
  return {
    name: analytics.curriculumName,
    students: {
      enrolled: analytics.enrolledStudents,
      completed: analytics.completedStudents,
      completion: analytics.averageCompletionRate,
    },
    content: {
      units: analytics.totalUnits,
      lessons: analytics.totalLessons,
      activities: analytics.totalActivities,
      points: analytics.totalPoints,
    },
  };
}

// Get student performance
async function getStudentPerformance(studentId) {
  const response = await fetch(
    `http://localhost:8080/admin-panel/students/${studentId}/performance`,
    {
      headers: {
        'Authorization': `Bearer ${adminToken}`,
      },
    }
  );
  
  return await response.json();
}
```

---

## Testing Examples

### Get System Statistics
```bash
curl -X GET http://localhost:8080/admin-panel/stats/system \
  -H "Authorization: Bearer SUPER_ADMIN_TOKEN"
```

### Get Curriculum Analytics
```bash
curl -X GET http://localhost:8080/admin-panel/analytics/curriculum/1 \
  -H "Authorization: Bearer ADMIN_TOKEN"
```

### Get Top 5 Performers
```bash
curl -X GET "http://localhost:8080/admin-panel/students/top-performers?limit=5" \
  -H "Authorization: Bearer ADMIN_TOKEN"
```

### Get All Students Performance
```bash
curl -X GET http://localhost:8080/admin-panel/students/performance \
  -H "Authorization: Bearer ADMIN_TOKEN"
```

### Get Student Performance
```bash
curl -X GET http://localhost:8080/admin-panel/students/42/performance \
  -H "Authorization: Bearer ADMIN_TOKEN"
```

### Get Curriculum Leaderboard
```bash
curl -X GET "http://localhost:8080/admin-panel/curriculum/1/leaderboard?limit=10" \
  -H "Authorization: Bearer ADMIN_TOKEN"
```

---

## Role-Based Access

| Endpoint | SUPER_ADMIN | SCHOOL_ADMIN | TEACHER | PARENT | STUDENT |
|----------|-------------|--------------|---------|--------|---------|
| System Stats | ✅ | ❌ | ❌ | ❌ | ❌ |
| Curriculum Analytics | ✅ | ✅ | ❌ | ❌ | ❌ |
| Top Performers | ✅ | ✅ | ❌ | ❌ | ❌ |
| All Students Performance | ✅ | ✅ | ❌ | ❌ | ❌ |
| Student Performance | ✅ | ✅ | ❌ | ❌ | ❌ |
| Curriculum Leaderboard | ✅ | ✅ | ❌ | ❌ | ❌ |

---

## Use Cases

### 1. Super Admin Dashboard
**Goal:** Monitor entire system health

**Widgets:**
- Total users by role (pie chart)
- Active vs inactive users
- Total schools by status
- Curriculum distribution (draft vs published)
- Activity type distribution (bar chart)
- Average completion rate (gauge)
- Average quiz score (gauge)
- Top performing students (table)

### 2. School Admin Dashboard
**Goal:** Monitor school performance

**Widgets:**
- School student count
- Enrolled curriculums
- Top performing students in school
- Curriculum completion rates
- Quiz pass rates
- Activity completion trends

### 3. Curriculum Manager View
**Goal:** Analyze curriculum effectiveness

**Widgets:**
- Enrollment numbers
- Completion rate
- Average time to complete
- Activity difficulty analysis
- Quiz pass rates
- Student feedback (future)

### 4. Performance Reports
**Goal:** Generate performance reports

**Reports:**
- Student progress reports
- Curriculum effectiveness reports
- School performance comparisons
- Teacher effectiveness (future)
- Time-based trends (future)

---

## Data Aggregations

### System Stats Includes:
- User counts by role
- School statistics
- Curriculum metrics
- Activity breakdown by type
- Progress and completion metrics
- Quiz performance metrics

### Curriculum Analytics Includes:
- Content structure (units, lessons, activities)
- Enrollment numbers
- Completion statistics
- Average performance
- Quiz metrics

### Student Performance Includes:
- Enrollment and completion counts
- Activities completed
- Points earned
- Average scores
- Quiz statistics
- Performance level classification

---

## Future Enhancements

1. **Time-based Analytics**
   - Daily/weekly/monthly reports
   - Trend analysis
   - Growth metrics

2. **Comparative Analytics**
   - School-to-school comparison
   - Curriculum effectiveness comparison
   - Teacher performance comparison

3. **Predictive Analytics**
   - At-risk student identification
   - Completion prediction
   - Performance forecasting

4. **Export Functionality**
   - PDF reports
   - CSV data export
   - Excel dashboards

5. **Real-time Dashboards**
   - Live activity feed
   - Real-time enrollments
   - Active users count

6. **Custom Reports**
   - Report builder
   - Scheduled reports
   - Email delivery

7. **Data Visualization**
   - Charts and graphs
   - Heat maps
   - Progress timelines

---

## Related Endpoints

### Also useful for admin panels:

**From UserController:**
- GET /users/stats - User statistics

**From AdminController:**
- GET /admin/dashboard - Admin dashboard data

**From SchoolController:**
- GET /schools/stats - School statistics

**From Progress APIs:**
- GET /progress/student/{id}/summary - Individual student analytics

---

## Performance Considerations

1. **Caching**: System stats can be cached (updates every 5-15 minutes)
2. **Pagination**: Large datasets should be paginated
3. **Indexes**: Database indexes on frequently queried fields
4. **Lazy Loading**: Use lazy loading for large data sets
5. **Async Processing**: Heavy reports can be processed asynchronously

---

## Related Documentation

- [User Management API](./user-management-api.md)
- [Curriculum API](./curriculum-api.md)
- [Progress Tracking API](./progress-tracking-api.md)
- [School Management API](./school-management-api.md)

