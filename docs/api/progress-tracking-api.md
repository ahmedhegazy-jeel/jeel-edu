# Progress Tracking API Documentation

## Base URL
```
http://localhost:8080/progress
```

## Overview
The Progress Tracking API provides comprehensive tracking of student learning progress across curriculums, activities, and quizzes. It tracks completion, scores, time spent, and provides detailed analytics.

---

## Endpoints (12 total)

### 1. Enroll Student in Curriculum

**Endpoint:** `POST /progress/enroll/{studentId}`

**Authorization:** SUPER_ADMIN, SCHOOL_ADMIN, TEACHER

**Description:** Enrolls a student in a curriculum and initializes progress tracking.

**Request Body:**
```json
{
  "curriculumId": 1
}
```

**Success Response (201 Created):**
```json
{
  "id": 1,
  "studentId": 5,
  "studentName": "Sara Mohammed",
  "curriculumId": 1,
  "curriculumName": "Arabic Language Curriculum",
  "totalUnits": 3,
  "completedUnits": 0,
  "totalLessons": 12,
  "completedLessons": 0,
  "totalActivities": 48,
  "completedActivities": 0,
  "totalPoints": 480,
  "earnedPoints": 0,
  "completionPercentage": 0.0,
  "startedAt": "2024-01-15T10:30:00",
  "completedAt": null,
  "isCompleted": false,
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00"
}
```

---

### 2. Get My Progress Summary (Student)

**Endpoint:** `GET /progress/my-summary`

**Authorization:** STUDENT only

**Description:** Get comprehensive progress summary for the current logged-in student.

**Success Response (200 OK):**
```json
{
  "studentId": 5,
  "studentName": "Sara Mohammed",
  "totalCurriculums": 2,
  "completedCurriculums": 0,
  "inProgressCurriculums": 2,
  "totalActivitiesCompleted": 25,
  "totalPointsEarned": 250,
  "averageScore": 85.5,
  "totalQuizzesTaken": 8,
  "totalQuizzesPassed": 7,
  "curriculumProgress": [
    {
      "curriculumId": 1,
      "curriculumName": "Arabic Language",
      "completionPercentage": 52.0,
      ...
    }
  ]
}
```

---

### 3. Get Student Progress Summary (Admin/Teacher/Parent)

**Endpoint:** `GET /progress/student/{studentId}/summary`

**Authorization:** SUPER_ADMIN, SCHOOL_ADMIN, TEACHER, PARENT

**Description:** Get comprehensive progress summary for a specific student.

**Success Response (200 OK):** Same as My Progress Summary

---

### 4. Get Student Progress for Curriculum

**Endpoint:** `GET /progress/student/{studentId}/curriculum/{curriculumId}`

**Authorization:** SUPER_ADMIN, SCHOOL_ADMIN, TEACHER, PARENT, STUDENT

**Description:** Get detailed progress for a student in a specific curriculum.

**Success Response (200 OK):** StudentProgressDTO

---

### 5. Get All Progress for Student

**Endpoint:** `GET /progress/student/{studentId}`

**Authorization:** SUPER_ADMIN, SCHOOL_ADMIN, TEACHER, PARENT

**Description:** Get all curriculum progress for a student.

**Success Response (200 OK):** Array of StudentProgressDTO

---

### 6. Get My Progress List (Student)

**Endpoint:** `GET /progress/my-progress`

**Authorization:** STUDENT only

**Description:** Get all curriculum progress for the current logged-in student.

**Success Response (200 OK):** Array of StudentProgressDTO

---

### 7. Start Activity

**Endpoint:** `POST /progress/activity/{activityId}/start`

**Authorization:** STUDENT only

**Description:** Mark when a student starts an activity (tracks access time).

**Success Response (200 OK):**
```json
{
  "id": 1,
  "studentId": 5,
  "studentName": "Sara Mohammed",
  "activityId": 10,
  "activityTitle": "Read About Letter Alif",
  "activityType": "TEXT",
  "isCompleted": false,
  "isPassed": false,
  "earnedPoints": null,
  "attemptCount": 0,
  "startedAt": "2024-01-15T10:35:00",
  "lastAccessedAt": "2024-01-15T10:35:00",
  "completedAt": null,
  "timeSpentSeconds": 0
}
```

---

### 8. Complete Activity

**Endpoint:** `POST /progress/activity/{activityId}/complete`

**Authorization:** STUDENT only

**Description:** Mark an activity as completed with earned points.

**Request Body:**
```json
{
  "earnedPoints": 10,
  "passed": true
}
```

**Success Response (200 OK):**
```json
{
  "id": 1,
  "studentId": 5,
  "activityId": 10,
  "isCompleted": true,
  "isPassed": true,
  "earnedPoints": 10,
  "completedAt": "2024-01-15T10:40:00"
}
```

---

### 9. Get My Activity Progress

**Endpoint:** `GET /progress/activity/{activityId}`

**Authorization:** STUDENT only

**Description:** Get progress for a specific activity for the current student.

**Success Response (200 OK):** ActivityProgressDTO

---

### 10. Submit Quiz Attempt

**Endpoint:** `POST /progress/quiz/{quizId}/submit`

**Authorization:** STUDENT only

**Description:** Submit a quiz attempt with results.

**Request Body:**
```json
{
  "correctAnswers": 8,
  "timeSpent": 180
}
```

**Success Response (201 Created):**
```json
{
  "id": 1,
  "studentId": 5,
  "studentName": "Sara Mohammed",
  "quizId": 15,
  "quizTitle": "Arabic Alphabet Quiz",
  "attemptNumber": 1,
  "totalQuestions": 10,
  "correctAnswers": 8,
  "incorrectAnswers": 2,
  "score": 16,
  "percentage": 80.0,
  "passed": true,
  "timeSpentSeconds": 180,
  "startedAt": "2024-01-15T10:37:00",
  "completedAt": "2024-01-15T10:40:00",
  "createdAt": "2024-01-15T10:40:00"
}
```

---

### 11. Get My Quiz Attempts

**Endpoint:** `GET /progress/quiz/{quizId}/attempts`

**Authorization:** STUDENT only

**Description:** Get all quiz attempts for the current student.

**Success Response (200 OK):** Array of QuizAttemptDTO (ordered by attempt number, newest first)

---

### 12. Get Student Quiz Attempts (Admin/Teacher/Parent)

**Endpoint:** `GET /progress/student/{studentId}/quiz/{quizId}/attempts`

**Authorization:** SUPER_ADMIN, SCHOOL_ADMIN, TEACHER, PARENT

**Description:** Get all quiz attempts for a specific student.

**Success Response (200 OK):** Array of QuizAttemptDTO

---

## Progress Tracking Flow

### Student Learning Journey

```
1. Admin/Teacher enrolls student in curriculum
   ↓ POST /progress/enroll/{studentId}
   
2. System initializes StudentProgress
   - Calculates total units, lessons, activities
   - Sets all completion counters to 0
   - Marks as started
   
3. Student starts learning
   ↓ POST /progress/activity/{id}/start
   
4. System tracks activity access
   - Records startedAt timestamp
   - Tracks lastAccessedAt for resuming
   
5. Student completes activity
   ↓ POST /progress/activity/{id}/complete
   
6. System updates progress
   - Marks activity as completed
   - Awards points
   - Updates curriculum completion percentage
   
7. Student takes quiz
   ↓ POST /progress/quiz/{id}/submit
   
8. System records quiz attempt
   - Calculates score and percentage
   - Determines pass/fail
   - Updates activity progress
   - Updates curriculum progress
   
9. Progress is tracked at all levels
   - Activity level (individual activities)
   - Lesson level (aggregated)
   - Unit level (aggregated)
   - Curriculum level (overall)
```

---

## Progress Entities

### 1. StudentProgress
Tracks overall curriculum progress for a student.

**Fields:**
- totalUnits, completedUnits
- totalLessons, completedLessons
- totalActivities, completedActivities
- totalPoints, earnedPoints
- completionPercentage
- startedAt, completedAt

### 2. ActivityProgress
Tracks individual activity progress.

**Fields:**
- isCompleted, isPassed
- earnedPoints, attemptCount
- currentScore, scorePercentage
- startedAt, completedAt, lastAccessedAt
- timeSpentSeconds

### 3. QuizAttempt
Tracks individual quiz attempts.

**Fields:**
- attemptNumber
- totalQuestions, correctAnswers, incorrectAnswers
- score, percentage, passed
- timeSpentSeconds
- startedAt, completedAt

---

## Progress Calculations

### Completion Percentage
```
completionPercentage = (completedActivities / totalActivities) * 100
```

### Score Percentage
```
scorePercentage = (currentScore / maxScore) * 100
```

### Quiz Percentage
```
quizPercentage = (correctAnswers / totalQuestions) * 100
```

### Capacity Utilization (for reference)
```
capacityUtilization = (currentScore / scorePercentage) * 100
```

---

## Role-Based Access

| Endpoint | STUDENT | PARENT | TEACHER | SCHOOL_ADMIN | SUPER_ADMIN |
|----------|---------|--------|---------|--------------|-------------|
| Enroll student | ❌ | ❌ | ✅ | ✅ | ✅ |
| Get my summary | ✅ | ❌ | ❌ | ❌ | ❌ |
| Get my progress | ✅ | ❌ | ❌ | ❌ | ❌ |
| Get student summary | ❌ | ✅ | ✅ | ✅ | ✅ |
| Get student progress | ❌ | ✅ | ✅ | ✅ | ✅ |
| Start activity | ✅ | ❌ | ❌ | ❌ | ❌ |
| Complete activity | ✅ | ❌ | ❌ | ❌ | ❌ |
| Submit quiz | ✅ | ❌ | ❌ | ❌ | ❌ |
| Get my quiz attempts | ✅ | ❌ | ❌ | ❌ | ❌ |
| Get student quiz attempts | ❌ | ✅ | ✅ | ✅ | ✅ |

---

## Testing Examples

### Enroll Student in Curriculum
```bash
curl -X POST http://localhost:8080/progress/enroll/5 \
  -H "Authorization: Bearer TEACHER_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"curriculumId": 1}'
```

### Get My Progress Summary (as Student)
```bash
curl -X GET http://localhost:8080/progress/my-summary \
  -H "Authorization: Bearer STUDENT_TOKEN"
```

### Start Activity (as Student)
```bash
curl -X POST http://localhost:8080/progress/activity/10/start \
  -H "Authorization: Bearer STUDENT_TOKEN"
```

### Complete Activity (as Student)
```bash
curl -X POST http://localhost:8080/progress/activity/10/complete \
  -H "Authorization: Bearer STUDENT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"earnedPoints": 10, "passed": true}'
```

### Submit Quiz (as Student)
```bash
curl -X POST http://localhost:8080/progress/quiz/15/submit \
  -H "Authorization: Bearer STUDENT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"correctAnswers": 8, "timeSpent": 180}'
```

### Get Quiz Attempts (as Student)
```bash
curl -X GET http://localhost:8080/progress/quiz/15/attempts \
  -H "Authorization: Bearer STUDENT_TOKEN"
```

### View Student Progress (as Teacher)
```bash
curl -X GET http://localhost:8080/progress/student/5/summary \
  -H "Authorization: Bearer TEACHER_TOKEN"
```

---

## Database Schema

### Table: student_progress
Tracks overall curriculum progress.

| Column | Type | Description |
|--------|------|-------------|
| id | BIGINT | Primary key |
| student_id | BIGINT | FK to users |
| curriculum_id | BIGINT | FK to curriculums |
| total_units | INT | Total units in curriculum |
| completed_units | INT | Units completed |
| total_lessons | INT | Total lessons |
| completed_lessons | INT | Lessons completed |
| total_activities | INT | Total activities |
| completed_activities | INT | Activities completed |
| total_points | INT | Total possible points |
| earned_points | INT | Points earned |
| completion_percentage | DOUBLE | Progress percentage |
| started_at | DATETIME | When enrolled |
| completed_at | DATETIME | When completed (NULL if in progress) |

### Table: activity_progress
Tracks individual activity progress.

| Column | Type | Description |
|--------|------|-------------|
| id | BIGINT | Primary key |
| student_id | BIGINT | FK to users |
| activity_id | BIGINT | FK to activities |
| is_completed | BOOLEAN | Completion status |
| is_passed | BOOLEAN | Pass status |
| earned_points | INT | Points earned |
| attempt_count | INT | Number of attempts |
| max_score | INT | Maximum possible score |
| current_score | INT | Current score |
| score_percentage | DOUBLE | Score percentage |
| started_at | DATETIME | When started |
| completed_at | DATETIME | When completed |
| last_accessed_at | DATETIME | Last access time |
| time_spent_seconds | INT | Time spent |

### Table: quiz_attempts
Tracks individual quiz attempts.

| Column | Type | Description |
|--------|------|-------------|
| id | BIGINT | Primary key |
| student_id | BIGINT | FK to users |
| quiz_id | BIGINT | FK to activities |
| attempt_number | INT | Attempt number |
| total_questions | INT | Total questions |
| correct_answers | INT | Correct answers |
| incorrect_answers | INT | Incorrect answers |
| score | INT | Points scored |
| percentage | DOUBLE | Score percentage |
| passed | BOOLEAN | Pass/fail status |
| time_spent_seconds | INT | Time spent |
| started_at | DATETIME | When started |
| completed_at | DATETIME | When completed |

---

## Frontend Integration Example

### Student Dashboard Component

```javascript
// Get student's progress summary
async function getMyProgress() {
  const response = await fetch('http://localhost:8080/progress/my-summary', {
    headers: {
      'Authorization': `Bearer ${studentToken}`,
    },
  });
  
  const summary = await response.json();
  console.log(`Completed: ${summary.completedCurriculums}/${summary.totalCurriculums}`);
  console.log(`Points: ${summary.totalPointsEarned}`);
  console.log(`Average Score: ${summary.averageScore}%`);
  
  return summary;
}

// Start an activity
async function startActivity(activityId) {
  await fetch(`http://localhost:8080/progress/activity/${activityId}/start`, {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${studentToken}`,
    },
  });
}

// Complete an activity
async function completeActivity(activityId, earnedPoints, passed) {
  await fetch(`http://localhost:8080/progress/activity/${activityId}/complete`, {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${studentToken}`,
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      earnedPoints,
      passed,
    }),
  });
}

// Submit quiz
async function submitQuiz(quizId, correctAnswers, timeSpent) {
  const response = await fetch(`http://localhost:8080/progress/quiz/${quizId}/submit`, {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${studentToken}`,
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      correctAnswers,
      timeSpent,
    }),
  });
  
  const result = await response.json();
  
  if (result.passed) {
    alert(`Congratulations! You passed with ${result.percentage}%`);
  } else {
    alert(`Score: ${result.percentage}%. Try again!`);
  }
  
  return result;
}

// Get quiz attempts history
async function getQuizHistory(quizId) {
  const response = await fetch(`http://localhost:8080/progress/quiz/${quizId}/attempts`, {
    headers: {
      'Authorization': `Bearer ${studentToken}`,
    },
  });
  
  const attempts = await response.json();
  console.log(`Total attempts: ${attempts.length}`);
  console.log(`Best score: ${Math.max(...attempts.map(a => a.percentage))}%`);
  
  return attempts;
}
```

---

## Progress Dashboard Widgets

### Student Dashboard
- Overall completion percentage
- Total points earned
- Average quiz score
- Curriculums in progress
- Recent activities completed
- Achievements unlocked

### Parent Dashboard
- Child's completion percentage
- Strong/weak subjects
- Time spent learning
- Quiz performance trends
- Activity completion timeline

### Teacher Dashboard
- Class average completion
- Student performance comparison
- Activity difficulty analysis
- Quiz pass rates
- Students needing help

---

## Analytics Queries

### Curriculum Analytics
```sql
-- Average completion across all students
SELECT AVG(completion_percentage) FROM student_progress WHERE curriculum_id = 1;

-- Number of students enrolled
SELECT COUNT(*) FROM student_progress WHERE curriculum_id = 1;

-- Completion distribution
SELECT 
  CASE 
    WHEN completion_percentage = 100 THEN 'Completed'
    WHEN completion_percentage >= 50 THEN 'In Progress'
    ELSE 'Just Started'
  END as status,
  COUNT(*) as count
FROM student_progress
WHERE curriculum_id = 1
GROUP BY status;
```

### Student Analytics
```sql
-- Total points by student
SELECT student_id, SUM(earned_points) as total_points
FROM activity_progress
WHERE is_completed = true
GROUP BY student_id
ORDER BY total_points DESC;

-- Average quiz score by student
SELECT student_id, AVG(percentage) as avg_score
FROM quiz_attempts
GROUP BY student_id
ORDER BY avg_score DESC;
```

---

## Future Enhancements

1. **Badges & Achievements**
   - Award badges for milestones
   - Leaderboards
   - Streak tracking

2. **Detailed Analytics**
   - Learning patterns
   - Time-of-day performance
   - Activity type preferences

3. **Recommendations**
   - Suggest next activities
   - Identify struggling areas
   - Adaptive learning paths

4. **Parent Notifications**
   - Progress reports
   - Milestone alerts
   - Low performance warnings

5. **Gamification**
   - Points and levels
   - Competitions
   - Rewards system

---

## Related Documentation

- [Curriculum API](./curriculum-api.md)
- [User Management API](./user-management-api.md)
- [Student Dashboard Guide](../user-guides/student-dashboard.md) (future)

