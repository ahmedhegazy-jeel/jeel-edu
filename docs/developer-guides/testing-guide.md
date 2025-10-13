# Testing Guide - JeelEducation LMS Backend

## Overview
This guide covers the testing strategy and how to run tests for the JeelEducation LMS backend.

---

## Test Structure

### Test Types

1. **Unit Tests** - Test individual components in isolation
   - Entity tests (business logic methods)
   - Utility tests (JWT, helpers)

2. **Integration Tests** - Test components working together
   - Controller tests (API endpoints with MockMvc)
   - Service tests (business logic with database)
   - Repository tests (database queries)

3. **End-to-End Tests** - Test complete user flows (future)

---

## Test Files Created

### Controller Tests (1)
- `AuthControllerTest.java` - Authentication endpoint tests
  - Login with valid credentials
  - Login with invalid credentials
  - Login with username or email
  - Token generation verification

### Service Tests (3)
- `UserServiceTest.java` - User management business logic
  - Create user
  - Duplicate username/email handling
  - Get users by role
  
- `CurriculumServiceTest.java` - Curriculum management
  - Create curriculum
  - Get by ID, status
  - Update and delete
  - Search functionality

- `SchoolServiceTest.java` - School management
  - Create school with auto-admin user
  - Capacity calculations
  - Geographic filtering

### Repository Tests (2)
- `UserRepositoryTest.java` - User data access
  - Find by username, email
  - Exists checks
  - Count by role
  
- `CurriculumRepositoryTest.java` - Curriculum queries
  - Find by status
  - Search by name
  - Count operations

### Security Tests (1)
- `JwtUtilsTest.java` - JWT token operations
  - Token generation
  - Token validation
  - Username extraction
  - Token expiration checks

### Entity Tests (4)
- `UserTest.java` - User entity logic
  - Full name generation
  - Role checks
  - Admin status
  - Account status checks

- `SchoolTest.java` - School entity logic
  - Capacity management
  - Availability calculations
  - Utilization percentage

- `StudentProgressTest.java` - Progress tracking
  - Completion checks
  - Percentage calculations

- `ActivityProgressTest.java` - Activity completion
  - Score calculations
  - Mark as completed

- `QuizAttemptTest.java` - Quiz scoring
  - Score calculation
  - Pass/fail determination

**Total Test Files: 11**
**Total Test Cases: 40+**

---

## Running Tests

### Run All Tests

```bash
cd backend
mvn test
```

### Run Specific Test Class

```bash
mvn test -Dtest=AuthControllerTest
mvn test -Dtest=UserServiceTest
```

### Run Specific Test Method

```bash
mvn test -Dtest=AuthControllerTest#testLoginSuccess
```

### Run Tests with Coverage

```bash
mvn test jacoco:report
```

Coverage report will be generated in `target/site/jacoco/index.html`

### Run Tests in IDE

**IntelliJ IDEA:**
1. Right-click on test file or package
2. Select "Run Tests"
3. View results in Run panel

**VS Code:**
1. Install "Test Runner for Java" extension
2. Click run button next to test method
3. View results in Test Explorer

---

## Test Configuration

### H2 In-Memory Database
Tests use H2 database instead of MySQL:
- Fast test execution
- Isolated test environment
- No MySQL dependency for testing
- Auto-cleanup after tests

### Test Profile
Configuration in `src/test/resources/application-test.yml`:
```yaml
spring:
  datasource:
    url: jdbc:h2:mem:testdb
  jpa:
    hibernate:
      ddl-auto: create-drop
```

### Test Annotations

**@SpringBootTest** - Full application context
- Used for integration tests
- Loads all beans
- Slower but comprehensive

**@DataJpaTest** - JPA repository tests only
- Loads only JPA components
- Uses embedded database
- Fast and focused

**@AutoConfigureMockMvc** - MockMvc support
- Test REST controllers
- Simulates HTTP requests
- No server startup needed

**@Transactional** - Transaction rollback
- Each test runs in a transaction
- Auto-rollback after test
- Database stays clean

---

## Test Coverage

### Current Coverage

| Component | Test Files | Test Cases | Coverage |
|-----------|------------|------------|----------|
| Controllers | 1 | 4 | Auth endpoints |
| Services | 3 | 12 | Core business logic |
| Repositories | 2 | 7 | Data access queries |
| Entities | 4 | 12 | Business methods |
| Security | 1 | 6 | JWT operations |
| **Total** | **11** | **41** | **Core functionality** |

### Test Coverage by Layer

**Entity Layer:** 4 test files
- User, School, StudentProgress, ActivityProgress, QuizAttempt

**Repository Layer:** 2 test files
- UserRepository, CurriculumRepository

**Service Layer:** 3 test files
- UserService, CurriculumService, SchoolService

**Controller Layer:** 1 test file
- AuthController

**Security Layer:** 1 test file
- JwtUtils

---

## Writing New Tests

### Entity Test Example

```java
@Test
void testEntityMethod() {
    MyEntity entity = MyEntity.builder()
            .field1("value1")
            .field2(100)
            .build();

    assertEquals("value1", entity.getField1());
    assertEquals(100, entity.getField2());
}
```

### Repository Test Example

```java
@DataJpaTest
class MyRepositoryTest {
    
    @Autowired
    private TestEntityManager entityManager;
    
    @Autowired
    private MyRepository repository;
    
    @Test
    void testFindByName() {
        MyEntity entity = new MyEntity();
        entity.setName("test");
        entityManager.persist(entity);
        entityManager.flush();

        Optional<MyEntity> found = repository.findByName("test");

        assertTrue(found.isPresent());
        assertEquals("test", found.get().getName());
    }
}
```

### Service Test Example

```java
@SpringBootTest
@Transactional
class MyServiceTest {
    
    @Autowired
    private MyService service;
    
    @Test
    void testCreateEntity() {
        CreateRequest request = new CreateRequest();
        request.setName("test");

        ResponseDTO result = service.create(request);

        assertNotNull(result);
        assertEquals("test", result.getName());
    }
}
```

### Controller Test Example

```java
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MyControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    void testEndpoint() throws Exception {
        RequestDTO request = new RequestDTO();
        
        mockMvc.perform(post("/api/resource")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());
    }
}
```

---

## Best Practices

1. **Arrange-Act-Assert Pattern**
   ```java
   @Test
   void testExample() {
       // Arrange - Setup test data
       MyEntity entity = new MyEntity();
       
       // Act - Execute the operation
       String result = entity.doSomething();
       
       // Assert - Verify the result
       assertEquals("expected", result);
   }
   ```

2. **Test Naming**
   - Use descriptive names: `testCreateUserWithValidData()`
   - Indicate expected behavior: `testLoginWithInvalidCredentialsShouldFail()`

3. **One Assertion Per Test** (when possible)
   - Makes failures easier to diagnose
   - Keeps tests focused

4. **Use @BeforeEach for Setup**
   ```java
   @BeforeEach
   void setUp() {
       // Common setup for all tests
   }
   ```

5. **Clean Up with @Transactional**
   - Tests run in transactions
   - Auto-rollback keeps database clean

6. **Test Edge Cases**
   - Null values
   - Empty strings
   - Boundary values
   - Error conditions

---

## Continuous Integration

### Maven Surefire Plugin
Tests run automatically during Maven build:

```bash
mvn clean install
```

This will:
1. Compile code
2. Run all tests
3. Generate reports
4. Build JAR file

### CI/CD Pipeline (Future)

```yaml
# Example GitHub Actions / Bitbucket Pipelines
steps:
  - name: Run Tests
    run: mvn test
  
  - name: Generate Coverage Report
    run: mvn jacoco:report
  
  - name: Publish Results
    uses: publish-test-results
```

---

## Test Data Management

### Test Database
- H2 in-memory database
- Created fresh for each test run
- Schema auto-generated from entities
- No test data persistence

### Test Data Creation
```java
@BeforeEach
void setUp() {
    testUser = User.builder()
            .username("testuser")
            .email("test@example.com")
            .password(passwordEncoder.encode("password123"))
            .firstName("Test")
            .lastName("User")
            .role(Role.STUDENT)
            .build();
    userRepository.save(testUser);
}
```

---

## Troubleshooting

### Tests Failing
1. Check test database configuration
2. Verify test data setup
3. Check for transaction issues
4. Review error messages in console

### Slow Tests
1. Use @DataJpaTest instead of @SpringBootTest when possible
2. Mock dependencies when appropriate
3. Limit test data creation

### Database Issues
1. Ensure H2 dependency is in pom.xml
2. Check application-test.yml configuration
3. Verify schema compatibility

---

## Future Test Enhancements

1. **More Controller Tests**
   - Test all REST endpoints
   - Test authorization
   - Test validation errors

2. **Performance Tests**
   - Load testing
   - Stress testing
   - Response time verification

3. **Security Tests**
   - Penetration testing
   - Authorization bypass attempts
   - SQL injection prevention

4. **Integration Tests**
   - End-to-end user flows
   - Multi-step processes
   - External service integration

5. **Test Coverage Goals**
   - 80%+ code coverage
   - 100% critical path coverage
   - All business logic tested

---

## Running Tests in Different Environments

### Local Development
```bash
mvn test
```

### CI/CD Pipeline
```bash
mvn clean test -Dspring.profiles.active=test
```

### Generate Test Report
```bash
mvn surefire-report:report
```

Report generated in: `target/site/surefire-report.html`

---

## Test Maintenance

1. **Update tests when code changes**
2. **Keep tests independent**
3. **Avoid test interdependencies**
4. **Use meaningful test data**
5. **Document complex test scenarios**
6. **Review test failures promptly**

---

**Last Updated**: 2025-10-12
**Test Coverage**: Core functionality covered
**Test Files**: 11
**Test Cases**: 40+

