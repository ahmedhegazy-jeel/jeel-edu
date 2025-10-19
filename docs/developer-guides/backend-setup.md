# Backend Setup Guide

## Prerequisites

Before setting up the backend, ensure you have the following installed:

- **Java JDK 17+** - [Download](https://www.oracle.com/java/technologies/downloads/)
- **Maven 3.8+** - [Download](https://maven.apache.org/download.cgi)
- **MySQL 8.0+** - [Download](https://dev.mysql.com/downloads/)
- **IDE** (recommended):
  - IntelliJ IDEA (Community or Ultimate)
  - Eclipse IDE for Java EE Developers
  - VS Code with Java Extension Pack

## Initial Setup

### 1. Install Dependencies

#### Verify Java Installation
```bash
java -version
# Should show: java version "17.x.x" or higher
```

#### Verify Maven Installation
```bash
mvn -version
# Should show: Apache Maven 3.8.x or higher
```

### 2. MySQL Database Setup

#### Start MySQL Server
```bash
# Windows
net start MySQL80

# Linux/Mac
sudo systemctl start mysql
# or
brew services start mysql
```

#### Create Database (Optional)
The application will auto-create the database, but you can create it manually:

```sql
CREATE DATABASE jeeleducation_lms CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

#### Default Configuration
The application uses these default credentials (configured in `application.yml`):
- **Host**: localhost:3306
- **Username**: root
- **Password**: root
- **Database**: jeeleducation_lms

To change these, either:
1. Update `application.yml` directly, or
2. Set environment variables (recommended for production)

### 3. Build the Project

Navigate to the backend directory:
```bash
cd backend
```

Install dependencies and build:
```bash
mvn clean install
```

This will:
- Download all Maven dependencies
- Compile the Java code
- Run unit tests
- Create the JAR file

### 4. Run the Application

#### Using Maven
```bash
mvn spring-boot:run
```

#### Using Maven with specific profile
```bash
# Development profile
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Production profile
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

#### Using JAR file
```bash
java -jar target/lms-backend-0.1.0.jar
```

#### With specific profile
```bash
java -jar target/lms-backend-0.1.0.jar --spring.profiles.active=dev
```

### 5. Verify Application is Running

The application should start on port 8080 with context path `/api`.

Check the health endpoint:
```bash
curl http://localhost:8080/actuator/health
```

Or simply open in browser:
```
http://localhost:8080/api
```

You should see logs indicating successful startup:
```
Started LmsApplication in X.XXX seconds
```

## Development Profiles

### Default Profile
- Uses `application.yml`
- DDL mode: `update` (creates/updates tables)
- SQL logging: enabled

### Development Profile (`dev`)
- Uses `application-dev.yml`
- DDL mode: `create-drop` (recreates tables on each start)
- Enhanced logging
- Auto-reload enabled via DevTools

To activate:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Production Profile (`prod`)
- Uses `application-prod.yml`
- DDL mode: `validate` (only validates schema)
- Minimal logging
- Requires environment variables

To activate:
```bash
export DATABASE_URL=jdbc:mysql://production-host:3306/jeeleducation_lms
export DATABASE_USERNAME=prod_user
export DATABASE_PASSWORD=secure_password
export JWT_SECRET=very-secure-secret-key
java -jar target/lms-backend-0.1.0.jar --spring.profiles.active=prod
```

## IDE Setup

### IntelliJ IDEA

1. **Import Project**
   - File → Open → Select `backend` folder
   - Choose "Maven" as project type
   - Wait for dependencies to download

2. **Enable Lombok**
   - File → Settings → Plugins
   - Search for "Lombok" and install
   - File → Settings → Build, Execution, Deployment → Compiler → Annotation Processors
   - Check "Enable annotation processing"

3. **Run Configuration**
   - Run → Edit Configurations
   - Add New → Spring Boot
   - Main class: `com.jeeleducation.lms.LmsApplication`
   - Active profiles: `dev`

4. **Code Style**
   - The project includes `.editorconfig`
   - IntelliJ will automatically apply settings

### Eclipse

1. **Import Project**
   - File → Import → Maven → Existing Maven Projects
   - Select `backend` folder

2. **Enable Lombok**
   - Download lombok.jar
   - Run: `java -jar lombok.jar`
   - Select Eclipse installation directory

3. **Run Configuration**
   - Right-click on `LmsApplication.java`
   - Run As → Spring Boot App

### VS Code

1. **Install Extensions**
   - Extension Pack for Java
   - Spring Boot Extension Pack
   - Lombok Annotations Support

2. **Open Project**
   - File → Open Folder → Select `backend`

3. **Run Application**
   - Press F5 or use Run and Debug panel

## Common Issues

### Issue: Port 8080 already in use
**Solution**: Either stop the other application or change the port in `application.yml`:
```yaml
server:
  port: 8081
```

### Issue: Cannot connect to MySQL
**Solutions**:
1. Verify MySQL is running
2. Check username/password in `application.yml`
3. Ensure MySQL is listening on port 3306

### Issue: Lombok not working
**Solutions**:
1. Enable annotation processing in IDE
2. Ensure Lombok plugin is installed
3. Rebuild the project

### Issue: Build fails
**Solutions**:
1. Run `mvn clean` first
2. Delete `.m2/repository` cache
3. Check Java version (must be 17+)
4. Check internet connection for downloading dependencies

## Code Quality

### Run Checkstyle
```bash
mvn checkstyle:check
```

### Run Tests
```bash
mvn test
```

### Generate Code Coverage Report
```bash
mvn jacoco:report
```

## Hot Reload (Development)

Spring Boot DevTools is included for automatic restart on code changes.

For best results:
1. Enable "Build project automatically" in IDE
2. Make code changes
3. Application will restart automatically

## Next Steps

Once the backend is running:
1. Proceed to implement authentication (JWT)
2. Test user creation and login endpoints
3. Implement role-based authorization
4. Build remaining business logic

For more information:
- See `docs/` for API documentation (coming soon)
- See `docs/database/user-schema.md` for database schema details

