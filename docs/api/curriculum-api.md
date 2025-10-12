# Curriculum Management API Documentation

## Base URL
```
http://localhost:8080/api
```

## Overview
The Curriculum Management API provides complete CRUD operations for managing learning content with a hierarchical structure: Curriculum → Unit → Lesson → Activity.

---

## Curriculum Endpoints (7 endpoints)

### 1. Create Curriculum

**Endpoint:** `POST /api/curriculums`

**Authorization:** SUPER_ADMIN, SCHOOL_ADMIN

**Request Body:**
```json
{
  "name": "Arabic Language Curriculum",
  "description": "Complete Arabic language learning program for kids",
  "icon": "https://example.com/icons/arabic.png",
  "status": "DRAFT",
  "displayOrder": 1
}
```

**Success Response (201 Created):**
```json
{
  "id": 1,
  "name": "Arabic Language Curriculum",
  "description": "Complete Arabic language learning program for kids",
  "icon": "https://example.com/icons/arabic.png",
  "status": "DRAFT",
  "displayOrder": 1,
  "unitCount": 0,
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00"
}
```

### 2. Get All Curriculums

**Endpoint:** `GET /api/curriculums`

**Success Response (200 OK):** Array of curriculum DTOs

### 3. Get Curriculum by ID

**Endpoint:** `GET /api/curriculums/{id}`

**Success Response (200 OK):** Curriculum DTO

### 4. Get Curriculums by Status

**Endpoint:** `GET /api/curriculums/status/{status}`

**Path Parameters:**
- `status`: DRAFT, PUBLISHED, or ARCHIVED

**Success Response (200 OK):** Array of curriculums with specified status

### 5. Search Curriculums

**Endpoint:** `GET /api/curriculums/search?searchTerm={term}`

**Query Parameters:**
- `searchTerm`: Search term for curriculum name

**Success Response (200 OK):** Array of matching curriculums

### 6. Update Curriculum

**Endpoint:** `PUT /api/curriculums/{id}`

**Authorization:** SUPER_ADMIN, SCHOOL_ADMIN

**Request Body:** Same as create (all fields optional)

**Success Response (200 OK):** Updated curriculum DTO

### 7. Delete Curriculum

**Endpoint:** `DELETE /api/curriculums/{id}`

**Authorization:** SUPER_ADMIN only

**Success Response (200 OK):**
```json
{
  "message": "Curriculum deleted successfully"
}
```

---

## Unit Endpoints (9 endpoints)

### 8. Create Unit

**Endpoint:** `POST /api/units`

**Authorization:** SUPER_ADMIN, SCHOOL_ADMIN

**Request Body:**
```json
{
  "name": "Unit 1: Arabic Alphabet",
  "description": "Introduction to Arabic letters",
  "audioName": "unit1_intro.mp3",
  "icon": "https://example.com/icons/alphabet.png",
  "status": "DRAFT",
  "displayOrder": 1,
  "curriculumId": 1
}
```

**Success Response (201 Created):**
```json
{
  "id": 1,
  "name": "Unit 1: Arabic Alphabet",
  "description": "Introduction to Arabic letters",
  "audioName": "unit1_intro.mp3",
  "icon": "https://example.com/icons/alphabet.png",
  "status": "DRAFT",
  "displayOrder": 1,
  "curriculumId": 1,
  "curriculumName": "Arabic Language Curriculum",
  "lessonCount": 0,
  "createdAt": "2024-01-15T10:35:00",
  "updatedAt": "2024-01-15T10:35:00"
}
```

### 9. Get All Units

**Endpoint:** `GET /api/units`

**Success Response (200 OK):** Array of unit DTOs

### 10. Get Unit by ID

**Endpoint:** `GET /api/units/{id}`

**Success Response (200 OK):** Unit DTO

### 11. Get Units by Curriculum

**Endpoint:** `GET /api/units/curriculum/{curriculumId}`

**Success Response (200 OK):** Array of units in curriculum (ordered by displayOrder)

### 12. Get Units by Curriculum and Status

**Endpoint:** `GET /api/units/curriculum/{curriculumId}/status/{status}`

**Success Response (200 OK):** Array of units matching criteria

### 13. Get Units by Status

**Endpoint:** `GET /api/units/status/{status}`

**Success Response (200 OK):** Array of units with specified status

### 14. Search Units in Curriculum

**Endpoint:** `GET /api/units/curriculum/{curriculumId}/search?searchTerm={term}`

**Success Response (200 OK):** Array of matching units

### 15. Update Unit

**Endpoint:** `PUT /api/units/{id}`

**Authorization:** SUPER_ADMIN, SCHOOL_ADMIN

**Success Response (200 OK):** Updated unit DTO

### 16. Delete Unit

**Endpoint:** `DELETE /api/units/{id}`

**Authorization:** SUPER_ADMIN only

**Success Response (200 OK):**
```json
{
  "message": "Unit deleted successfully"
}
```

---

## Lesson Endpoints (9 endpoints)

### 17. Create Lesson

**Endpoint:** `POST /api/lessons`

**Authorization:** SUPER_ADMIN, SCHOOL_ADMIN

**Request Body:**
```json
{
  "name": "Lesson 1: Letter Alif",
  "description": "Learn the letter Alif",
  "audioName": "lesson1_alif.mp3",
  "icon": "https://example.com/icons/alif.png",
  "status": "DRAFT",
  "displayOrder": 1,
  "unitId": 1
}
```

**Success Response (201 Created):** Lesson DTO with unitId and unitName

### 18. Get All Lessons

**Endpoint:** `GET /api/lessons`

**Success Response (200 OK):** Array of lesson DTOs

### 19. Get Lesson by ID

**Endpoint:** `GET /api/lessons/{id}`

**Success Response (200 OK):** Lesson DTO

### 20. Get Lessons by Unit

**Endpoint:** `GET /api/lessons/unit/{unitId}`

**Success Response (200 OK):** Array of lessons in unit (ordered by displayOrder)

### 21. Get Lessons by Unit and Status

**Endpoint:** `GET /api/lessons/unit/{unitId}/status/{status}`

**Success Response (200 OK):** Array of lessons matching criteria

### 22. Get Lessons by Status

**Endpoint:** `GET /api/lessons/status/{status}`

**Success Response (200 OK):** Array of lessons with specified status

### 23. Search Lessons in Unit

**Endpoint:** `GET /api/lessons/unit/{unitId}/search?searchTerm={term}`

**Success Response (200 OK):** Array of matching lessons

### 24. Update Lesson

**Endpoint:** `PUT /api/lessons/{id}`

**Authorization:** SUPER_ADMIN, SCHOOL_ADMIN

**Success Response (200 OK):** Updated lesson DTO

### 25. Delete Lesson

**Endpoint:** `DELETE /api/lessons/{id}`

**Authorization:** SUPER_ADMIN only

**Success Response (200 OK):**
```json
{
  "message": "Lesson deleted successfully"
}
```

---

## Activity Endpoints (12 endpoints)

### 26. Create Activity

**Endpoint:** `POST /api/activities`

**Authorization:** SUPER_ADMIN, SCHOOL_ADMIN

**Request Body:**
```json
{
  "titleName": "Read Alif Text",
  "titleAudioName": "alif_title.mp3",
  "description": "Read about the letter Alif",
  "icon": "https://example.com/icons/reading.png",
  "activityType": "TEXT",
  "status": "DRAFT",
  "points": 10,
  "tag": "reading",
  "topic": "alphabet",
  "displayOrder": 1,
  "lessonId": 1,
  "typeSpecificData": {
    "text": "The letter Alif is the first letter...",
    "textAudio": "alif_reading.mp3"
  }
}
```

**Success Response (201 Created):** Activity DTO

### 27. Get All Activities

**Endpoint:** `GET /api/activities`

**Success Response (200 OK):** Array of activity DTOs

### 28. Get Activity by ID

**Endpoint:** `GET /api/activities/{id}`

**Success Response (200 OK):** Activity DTO with type-specific data

### 29. Get Activities by Lesson

**Endpoint:** `GET /api/activities/lesson/{lessonId}`

**Success Response (200 OK):** Array of activities in lesson (ordered by displayOrder)

### 30. Get Activities by Lesson and Status

**Endpoint:** `GET /api/activities/lesson/{lessonId}/status/{status}`

**Success Response (200 OK):** Array of activities matching criteria

### 31. Get Activities by Type

**Endpoint:** `GET /api/activities/type/{type}`

**Path Parameters:**
- `type`: TEXT, PDF, AUDIO, VIDEO, INTERACTIVE, QUIZ, BOOK, or HOMEWORK

**Success Response (200 OK):** Array of activities of specified type

### 32. Get Activities by Lesson and Type

**Endpoint:** `GET /api/activities/lesson/{lessonId}/type/{type}`

**Success Response (200 OK):** Array of activities matching criteria

### 33. Get Activities by Tag

**Endpoint:** `GET /api/activities/tag/{tag}`

**Success Response (200 OK):** Array of activities with specified tag

### 34. Search Activities in Lesson

**Endpoint:** `GET /api/activities/lesson/{lessonId}/search?searchTerm={term}`

**Success Response (200 OK):** Array of matching activities

### 35. Count Activities by Type

**Endpoint:** `GET /api/activities/type/{type}/count`

**Success Response (200 OK):**
```json
{
  "count": 25
}
```

### 36. Update Activity

**Endpoint:** `PUT /api/activities/{id}`

**Authorization:** SUPER_ADMIN, SCHOOL_ADMIN

**Success Response (200 OK):** Updated activity DTO

### 37. Delete Activity

**Endpoint:** `DELETE /api/activities/{id}`

**Authorization:** SUPER_ADMIN only

**Success Response (200 OK):**
```json
{
  "message": "Activity deleted successfully"
}
```

---

## Activity Types & Type-Specific Data

### 1. TEXT Activity
```json
{
  "activityType": "TEXT",
  "typeSpecificData": {
    "text": "Full text content here...",
    "textAudio": "audio_file.mp3"
  }
}
```

### 2. PDF Activity
```json
{
  "activityType": "PDF",
  "typeSpecificData": {
    "pdfUrl": "https://example.com/document.pdf",
    "audioOfPdf": "pdf_audio.mp3"
  }
}
```

### 3. AUDIO Activity
```json
{
  "activityType": "AUDIO",
  "typeSpecificData": {
    "audioWithMusic": "audio_music.mp3",
    "audioWithoutMusic": "audio_plain.mp3"
  }
}
```

### 4. VIDEO Activity
```json
{
  "activityType": "VIDEO",
  "typeSpecificData": {
    "videoWithMusic": "https://example.com/video_music.mp4",
    "videoWithoutMusic": "https://example.com/video_plain.mp4"
  }
}
```

### 5. INTERACTIVE Activity
```json
{
  "activityType": "INTERACTIVE",
  "typeSpecificData": {
    "externalActivityUrl": "https://interactive-game.com/activity123"
  }
}
```

### 6. QUIZ Activity
```json
{
  "activityType": "QUIZ",
  "typeSpecificData": {
    "percentageToPass": 70,
    "attemptNumber": 3,
    "numberOfQuestions": 10
  }
}
```

### 7. BOOK Activity
```json
{
  "activityType": "BOOK",
  "typeSpecificData": {
    "pageCount": 15
  }
}
```

### 8. HOMEWORK Activity
```json
{
  "activityType": "HOMEWORK",
  "typeSpecificData": {
    "dueDate": "2024-01-20T23:59:59",
    "instructions": "Complete all exercises in chapter 1"
  }
}
```

---

## Curriculum Hierarchy Example

```json
{
  "curriculum": {
    "id": 1,
    "name": "Arabic Language",
    "status": "PUBLISHED",
    "units": [
      {
        "id": 1,
        "name": "Arabic Alphabet",
        "status": "PUBLISHED",
        "lessons": [
          {
            "id": 1,
            "name": "Letter Alif",
            "status": "PUBLISHED",
            "activities": [
              {
                "id": 1,
                "titleName": "Read About Alif",
                "activityType": "TEXT",
                "points": 10
              },
              {
                "id": 2,
                "titleName": "Alif Quiz",
                "activityType": "QUIZ",
                "points": 20
              }
            ]
          }
        ]
      }
    ]
  }
}
```

---

## Status Management

### Status Values
- **DRAFT**: Under development, not visible to students
- **PUBLISHED**: Active and visible to students
- **ARCHIVED**: No longer active, hidden from students

### Status Workflow
```
DRAFT → PUBLISHED → ARCHIVED
  ↑         ↓
  ←─────────
```

---

## Role-Based Access

| Endpoint Pattern | Allowed Roles | Permissions |
|-----------------|---------------|-------------|
| POST /api/curriculums | SUPER_ADMIN, SCHOOL_ADMIN | Create |
| POST /api/units | SUPER_ADMIN, SCHOOL_ADMIN | Create |
| POST /api/lessons | SUPER_ADMIN, SCHOOL_ADMIN | Create |
| POST /api/activities | SUPER_ADMIN, SCHOOL_ADMIN | Create |
| PUT /api/curriculums/{id} | SUPER_ADMIN, SCHOOL_ADMIN | Update |
| PUT /api/units/{id} | SUPER_ADMIN, SCHOOL_ADMIN | Update |
| PUT /api/lessons/{id} | SUPER_ADMIN, SCHOOL_ADMIN | Update |
| PUT /api/activities/{id} | SUPER_ADMIN, SCHOOL_ADMIN | Update |
| DELETE /api/curriculums/{id} | SUPER_ADMIN only | Delete |
| DELETE /api/units/{id} | SUPER_ADMIN only | Delete |
| DELETE /api/lessons/{id} | SUPER_ADMIN only | Delete |
| DELETE /api/activities/{id} | SUPER_ADMIN only | Delete |
| GET endpoints | All authenticated users | Read |

---

## Testing Examples

### Create Complete Curriculum Structure

#### Step 1: Create Curriculum
```bash
curl -X POST http://localhost:8080/api/curriculums \
  -H "Authorization: Bearer ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Arabic Language",
    "description": "Arabic curriculum for kids",
    "status": "DRAFT",
    "displayOrder": 1
  }'
```

#### Step 2: Create Unit
```bash
curl -X POST http://localhost:8080/api/units \
  -H "Authorization: Bearer ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Arabic Alphabet",
    "description": "Learn Arabic letters",
    "curriculumId": 1,
    "status": "DRAFT",
    "displayOrder": 1
  }'
```

#### Step 3: Create Lesson
```bash
curl -X POST http://localhost:8080/api/lessons \
  -H "Authorization: Bearer ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Letter Alif",
    "description": "Learn Alif",
    "unitId": 1,
    "status": "DRAFT",
    "displayOrder": 1
  }'
```

#### Step 4: Create Activity
```bash
curl -X POST http://localhost:8080/api/activities \
  -H "Authorization: Bearer ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "titleName": "Alif Reading",
    "activityType": "TEXT",
    "lessonId": 1,
    "points": 10,
    "status": "DRAFT"
  }'
```

### Query Data

#### Get All Units in Curriculum
```bash
curl -X GET http://localhost:8080/api/units/curriculum/1 \
  -H "Authorization: Bearer TOKEN"
```

#### Get All Lessons in Unit
```bash
curl -X GET http://localhost:8080/api/lessons/unit/1 \
  -H "Authorization: Bearer TOKEN"
```

#### Get All Activities in Lesson
```bash
curl -X GET http://localhost:8080/api/activities/lesson/1 \
  -H "Authorization: Bearer TOKEN"
```

#### Get Activities by Type
```bash
curl -X GET http://localhost:8080/api/activities/type/QUIZ \
  -H "Authorization: Bearer TOKEN"
```

---

## Complete Endpoint Summary

| Resource | Endpoints | Create | Read | Update | Delete |
|----------|-----------|--------|------|--------|--------|
| Curriculum | 7 | ✅ | ✅ (5) | ✅ | ✅ |
| Unit | 9 | ✅ | ✅ (7) | ✅ | ✅ |
| Lesson | 9 | ✅ | ✅ (7) | ✅ | ✅ |
| Activity | 12 | ✅ | ✅ (10) | ✅ | ✅ |
| **Total** | **37** | **4** | **29** | **4** | **4** |

---

## Next Documentation

- Quiz management API (questions and answers)
- Book page management API
- Homework file management API
- Student progress tracking API
- Curriculum assignment API

