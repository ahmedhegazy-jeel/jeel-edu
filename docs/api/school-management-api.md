# School Management API Documentation

## Base URL
```
http://localhost:8080/api/schools
```

## Overview
The School Management API provides endpoints for managing educational institutions. When a school is created, a SCHOOL_ADMIN user is automatically created for school administration.

---

## Endpoints (14 total)

### 1. Create School

**Endpoint:** `POST /api/schools`

**Authorization:** SUPER_ADMIN only

**Description:** Creates a new school and automatically creates a SCHOOL_ADMIN user for the school.

**Request Body:**
```json
{
  "name": "Al-Azhar International School",
  "description": "Premier international school for Arabic and Islamic education",
  "icon": "https://example.com/icons/school1.png",
  "logo": "https://example.com/logos/azhar.png",
  "adminMobile": "+201234567890",
  "adminEmail": "admin@azhar-school.com",
  "adminPassword": "SecurePassword123",
  "address": "123 Education Street",
  "city": "Cairo",
  "country": "Egypt",
  "postalCode": "12345",
  "phoneNumber": "+20212345678",
  "faxNumber": "+20212345679",
  "website": "https://azhar-school.com",
  "studentCapacity": 500
}
```

**Success Response (201 Created):**
```json
{
  "id": 1,
  "name": "Al-Azhar International School",
  "description": "Premier international school for Arabic and Islamic education",
  "icon": "https://example.com/icons/school1.png",
  "logo": "https://example.com/logos/azhar.png",
  "adminMobile": "+201234567890",
  "adminEmail": "admin@azhar-school.com",
  "address": "123 Education Street",
  "city": "Cairo",
  "country": "Egypt",
  "postalCode": "12345",
  "phoneNumber": "+20212345678",
  "faxNumber": "+20212345679",
  "website": "https://azhar-school.com",
  "isActive": true,
  "studentCapacity": 500,
  "currentStudentCount": 0,
  "availableCapacity": 500,
  "capacityUtilization": 0.0,
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00"
}
```

**Note:** This endpoint also creates a SCHOOL_ADMIN user with:
- Username: Generated from email (e.g., "admin" from "admin@azhar-school.com")
- Email: adminEmail
- Password: adminPassword (encrypted with BCrypt)
- Role: SCHOOL_ADMIN

**Error Response (400 Bad Request):**
```json
{
  "message": "Admin email is already in use"
}
```

---

### 2. Get All Schools

**Endpoint:** `GET /api/schools`

**Authorization:** SUPER_ADMIN only

**Success Response (200 OK):** Array of school DTOs

---

### 3. Get School by ID

**Endpoint:** `GET /api/schools/{id}`

**Authorization:** SUPER_ADMIN, SCHOOL_ADMIN

**Success Response (200 OK):** School DTO

---

### 4. Get Active Schools

**Endpoint:** `GET /api/schools/active`

**Authorization:** All authenticated users

**Description:** Get all active schools (public information).

**Success Response (200 OK):** Array of active school DTOs

---

### 5. Get Schools by Status

**Endpoint:** `GET /api/schools/status?isActive={true|false}`

**Authorization:** SUPER_ADMIN only

**Query Parameters:**
- `isActive` (required): true or false

**Success Response (200 OK):** Array of schools with specified status

---

### 6. Get Schools by City

**Endpoint:** `GET /api/schools/city/{city}`

**Authorization:** All authenticated users

**Path Parameters:**
- `city`: City name (e.g., "Cairo")

**Success Response (200 OK):** Array of schools in specified city

---

### 7. Get Schools by Country

**Endpoint:** `GET /api/schools/country/{country}`

**Authorization:** All authenticated users

**Path Parameters:**
- `country`: Country name (e.g., "Egypt")

**Success Response (200 OK):** Array of schools in specified country

---

### 8. Search Schools

**Endpoint:** `GET /api/schools/search?searchTerm={term}`

**Authorization:** All authenticated users

**Query Parameters:**
- `searchTerm` (required): Search term for school name

**Success Response (200 OK):** Array of matching schools

---

### 9. Get Schools with Available Capacity

**Endpoint:** `GET /api/schools/capacity/available`

**Authorization:** All authenticated users

**Description:** Get schools that have available student capacity.

**Success Response (200 OK):** Array of schools with available capacity

---

### 10. Get School Statistics

**Endpoint:** `GET /api/schools/stats`

**Authorization:** SUPER_ADMIN only

**Success Response (200 OK):**
```json
{
  "totalSchools": 25,
  "activeSchools": 23,
  "totalStudents": 12500
}
```

---

### 11. Update School

**Endpoint:** `PUT /api/schools/{id}`

**Authorization:** SUPER_ADMIN, SCHOOL_ADMIN

**Request Body:** Same as create (all fields optional)

**Success Response (200 OK):** Updated school DTO

---

### 12. Activate School

**Endpoint:** `PATCH /api/schools/{id}/activate`

**Authorization:** SUPER_ADMIN only

**Success Response (200 OK):**
```json
{
  "message": "School activated successfully"
}
```

---

### 13. Deactivate School

**Endpoint:** `PATCH /api/schools/{id}/deactivate`

**Authorization:** SUPER_ADMIN only

**Success Response (200 OK):**
```json
{
  "message": "School deactivated successfully"
}
```

---

### 14. Delete School

**Endpoint:** `DELETE /api/schools/{id}`

**Authorization:** SUPER_ADMIN only

**Success Response (200 OK):**
```json
{
  "message": "School deleted successfully"
}
```

---

## School Creation Flow

```
1. Super Admin creates school → POST /api/schools
   ↓
2. System creates School entity
   ↓
3. System creates SCHOOL_ADMIN user automatically
   - Username: Generated from admin email
   - Role: SCHOOL_ADMIN
   - Email: School admin email
   - Password: Encrypted with BCrypt
   ↓
4. School Admin can now login and manage their school
   ↓
5. School Admin can:
   - View their school details
   - Update school information
   - Manage teachers and students
   - Assign curriculums
```

---

## Role-Based Access

| Endpoint | SUPER_ADMIN | SCHOOL_ADMIN | TEACHER | PARENT | STUDENT |
|----------|-------------|--------------|---------|--------|---------|
| Create School | ✅ | ❌ | ❌ | ❌ | ❌ |
| Get All Schools | ✅ | ❌ | ❌ | ❌ | ❌ |
| Get School by ID | ✅ | ✅ | ❌ | ❌ | ❌ |
| Get Active Schools | ✅ | ✅ | ✅ | ✅ | ✅ |
| Update School | ✅ | ✅ (own) | ❌ | ❌ | ❌ |
| Activate/Deactivate | ✅ | ❌ | ❌ | ❌ | ❌ |
| Delete School | ✅ | ❌ | ❌ | ❌ | ❌ |
| Get by City/Country | ✅ | ✅ | ✅ | ✅ | ✅ |
| Search Schools | ✅ | ✅ | ✅ | ✅ | ✅ |

---

## Capacity Management

### Capacity Fields
- `studentCapacity`: Maximum number of students
- `currentStudentCount`: Current enrolled students
- `availableCapacity`: Calculated field (capacity - current)
- `capacityUtilization`: Percentage of capacity used

### Example
```json
{
  "studentCapacity": 500,
  "currentStudentCount": 350,
  "availableCapacity": 150,
  "capacityUtilization": 70.0
}
```

---

## Testing Examples

### Create School
```bash
curl -X POST http://localhost:8080/api/schools \
  -H "Authorization: Bearer SUPER_ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Al-Azhar International School",
    "adminMobile": "+201234567890",
    "adminEmail": "admin@azhar-school.com",
    "adminPassword": "SecurePassword123",
    "city": "Cairo",
    "country": "Egypt",
    "studentCapacity": 500
  }'
```

### Get All Active Schools
```bash
curl -X GET http://localhost:8080/api/schools/active \
  -H "Authorization: Bearer TOKEN"
```

### Search Schools
```bash
curl -X GET "http://localhost:8080/api/schools/search?searchTerm=Azhar" \
  -H "Authorization: Bearer TOKEN"
```

### Get Schools by City
```bash
curl -X GET http://localhost:8080/api/schools/city/Cairo \
  -H "Authorization: Bearer TOKEN"
```

### Get Schools with Capacity
```bash
curl -X GET http://localhost:8080/api/schools/capacity/available \
  -H "Authorization: Bearer TOKEN"
```

### Update School
```bash
curl -X PUT http://localhost:8080/api/schools/1 \
  -H "Authorization: Bearer ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "currentStudentCount": 350,
    "phoneNumber": "+20212345678"
  }'
```

### Get School Statistics
```bash
curl -X GET http://localhost:8080/api/schools/stats \
  -H "Authorization: Bearer SUPER_ADMIN_TOKEN"
```

---

## Database Schema

### Table: `schools`

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| `id` | BIGINT | PRIMARY KEY, AUTO_INCREMENT | School ID |
| `name` | VARCHAR(200) | NOT NULL | School name |
| `description` | VARCHAR(1000) | NULL | School description |
| `icon` | VARCHAR(500) | NULL | Icon URL |
| `logo` | VARCHAR(500) | NULL | Logo URL |
| `admin_mobile` | VARCHAR(20) | NOT NULL | Admin mobile number |
| `admin_email` | VARCHAR(100) | NOT NULL, UNIQUE | Admin email |
| `address` | VARCHAR(500) | NULL | School address |
| `city` | VARCHAR(100) | NULL | City |
| `country` | VARCHAR(100) | NULL | Country |
| `postal_code` | VARCHAR(20) | NULL | Postal code |
| `phone_number` | VARCHAR(20) | NULL | School phone |
| `fax_number` | VARCHAR(20) | NULL | Fax number |
| `website` | VARCHAR(500) | NULL | Website URL |
| `is_active` | BOOLEAN | NOT NULL, DEFAULT TRUE | Active status |
| `student_capacity` | INT | NULL | Max students |
| `current_student_count` | INT | NULL | Current students |
| `created_at` | DATETIME | NOT NULL | Creation timestamp |
| `updated_at` | DATETIME | NOT NULL | Update timestamp |

### Indexes
- Primary Key: `id`
- Unique: `admin_email`
- Index: `city`, `country`, `is_active`

---

## Business Logic

### Automatic User Creation
When a school is created, the system automatically:
1. Validates admin email is unique
2. Creates the School entity
3. Creates a SCHOOL_ADMIN user with:
   - Username derived from email
   - Email = adminEmail
   - Password = adminPassword (encrypted)
   - Role = SCHOOL_ADMIN
4. Returns the created school

If user creation fails, the school creation is rolled back (transaction management).

### Capacity Management
- Schools can define maximum student capacity
- System tracks current student count
- `availableCapacity` and `capacityUtilization` are calculated fields
- Endpoint to find schools with available capacity

---

## Error Responses

### 400 Bad Request
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 400,
  "error": "Validation Failed",
  "errors": {
    "adminEmail": "Admin email is already in use"
  }
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
  "error": "Not Found",
  "message": "School not found with id: 999"
}
```

---

## Integration with User System

### School Admin User
When a school is created, a SCHOOL_ADMIN user is automatically created:

```json
{
  "username": "adminazharschool",
  "email": "admin@azhar-school.com",
  "firstName": "Al-Azhar International School",
  "lastName": "Admin",
  "mobile": "+201234567890",
  "role": "SCHOOL_ADMIN",
  "isActive": true
}
```

The school admin can then:
- Login to the system
- View their school details
- Update school information
- Manage teachers and students in their school
- Assign curriculums to students

---

## Future Enhancements

1. **School-User Relationships**
   - Link teachers to specific schools
   - Link students to specific schools
   - Multi-school support for teachers

2. **School Curriculums**
   - Assign curriculums to schools
   - School-specific curriculum customization

3. **School Analytics**
   - Student performance by school
   - Teacher performance by school
   - Curriculum completion rates

4. **School Billing**
   - Subscription management
   - Payment tracking
   - Invoice generation

5. **School Settings**
   - Custom branding
   - Academic calendar
   - Grade levels
   - Class management

---

## Related Documentation

- [User Management API](./user-management-api.md)
- [Curriculum API](./curriculum-api.md)
- [Database Schema](../database/user-schema.md)

