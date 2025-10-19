# Docker Deployment Guide

This guide explains how to deploy JeelEducation LMS using Docker and Docker Compose.

## 📋 Prerequisites

- Docker 20.10 or higher
- Docker Compose 2.0 or higher
- At least 4GB RAM available
- 10GB disk space

## 🚀 Quick Start (Local Development)

### 1. Clone the Repository

```bash
git clone <repository-url>
cd jeeleducation-lms
```

### 2. Configure Environment Variables

```bash
# Copy the example environment file
cp .env.docker .env

# Edit .env with your configuration
nano .env
```

**Important**: Update the following in `.env`:
- `JWT_SECRET`: Use a strong, random 256-bit key
- `MYSQL_ROOT_PASSWORD`: Strong MySQL root password
- `MYSQL_PASSWORD`: Strong MySQL user password
- `MAIL_USERNAME` & `MAIL_PASSWORD`: Your SMTP credentials

### 3. Start All Services

```bash
# Start all services (MySQL, Backend, Frontend)
docker-compose up -d

# View logs
docker-compose logs -f

# Check service status
docker-compose ps
```

### 4. Access the Application

- **Frontend**: http://localhost:3000
- **Backend API**: http://localhost:8080/api
- **API Documentation**: http://localhost:8080/swagger-ui.html (if enabled)

### 5. Initial Setup

The MySQL database will be automatically initialized. To create a super admin user, you can:

```bash
# Connect to backend container
docker-compose exec backend sh

# Or use the API to create the first super admin
curl -X POST http://localhost:8080/auth/register/super-admin \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "email": "admin@jeeleducation.com",
    "password": "Admin123!",
    "firstName": "Super",
    "lastName": "Admin"
  }'
```

## 🛠️ Docker Commands

### Start Services

```bash
# Start all services
docker-compose up -d

# Start specific service
docker-compose up -d backend

# Start with build (force rebuild)
docker-compose up -d --build
```

### Stop Services

```bash
# Stop all services
docker-compose down

# Stop and remove volumes (WARNING: deletes data!)
docker-compose down -v
```

### View Logs

```bash
# All services
docker-compose logs -f

# Specific service
docker-compose logs -f backend
docker-compose logs -f frontend
docker-compose logs -f mysql

# Last 100 lines
docker-compose logs --tail=100 backend
```

### Rebuild Services

```bash
# Rebuild all services
docker-compose build

# Rebuild specific service
docker-compose build backend
docker-compose build frontend

# Rebuild and start
docker-compose up -d --build
```

### Execute Commands in Container

```bash
# Backend shell
docker-compose exec backend sh

# MySQL shell
docker-compose exec mysql mysql -u root -p

# Frontend shell
docker-compose exec frontend sh
```

## 🔧 Configuration

### Backend Configuration

The backend uses environment variables defined in `.env`:

- **Database**: Connects to MySQL service
- **JWT**: Token signing and expiration
- **Email**: SMTP configuration for password reset
- **Profile**: Switch between dev/prod configurations

### Frontend Configuration

- **NEXT_PUBLIC_API_URL**: Backend API URL
- Built as standalone for optimal Docker performance

### MySQL Configuration

- **Port**: 3306 (mapped to host)
- **Data**: Persisted in Docker volume `mysql-data`
- **Initialization**: Runs init scripts on first start

## 📊 Service Architecture

```
┌─────────────────┐
│   Frontend      │
│  (Next.js)      │
│  Port: 3000     │
└────────┬────────┘
         │
         │ HTTP
         │
┌────────▼────────┐
│   Backend       │
│  (Spring Boot)  │
│  Port: 8080     │
└────────┬────────┘
         │
         │ JDBC
         │
┌────────▼────────┐
│   MySQL         │
│  Port: 3306     │
└─────────────────┘
```

## 🔒 Security Best Practices

1. **Change Default Passwords**: Update all passwords in `.env`
2. **JWT Secret**: Use a strong, random 256-bit key
3. **HTTPS**: Use reverse proxy (Nginx) with SSL in production
4. **Database**: Restrict MySQL port in production
5. **Environment Variables**: Never commit `.env` to version control

## 🌐 Production Deployment

### Using Docker Compose in Production

1. **Update Environment Variables**:
   ```bash
   # Use production values
   SPRING_PROFILE=prod
   JWT_SECRET=<strong-production-secret>
   MYSQL_ROOT_PASSWORD=<strong-password>
   ```

2. **Use External Database** (Recommended):
   - Comment out MySQL service in `docker-compose.yml`
   - Point backend to external MySQL (AWS RDS, etc.)
   - Update `SPRING_DATASOURCE_URL`

3. **Add Reverse Proxy**:
   - Use Nginx for SSL termination
   - Configure domain routing
   - Add rate limiting

4. **Enable Monitoring**:
   - Add logging aggregation
   - Set up health checks
   - Configure alerts

### Recommended Production Stack

```yaml
services:
  nginx:
    image: nginx:alpine
    ports:
      - "80:80"
      - "443:443"
    volumes:
      - ./nginx.conf:/etc/nginx/nginx.conf
      - ./ssl:/etc/nginx/ssl
    depends_on:
      - backend
      - frontend

  backend:
    # ... existing config
    environment:
      SPRING_PROFILES_ACTIVE: prod

  frontend:
    # ... existing config
    environment:
      NEXT_PUBLIC_API_URL: https://api.yourdomain.com
```

## 🐛 Troubleshooting

### Backend Not Starting

```bash
# Check logs
docker-compose logs backend

# Common issues:
# 1. MySQL not ready → Wait for health check
# 2. Port 8080 in use → Change port mapping
# 3. Database connection error → Check credentials
```

### Frontend Not Loading

```bash
# Check logs
docker-compose logs frontend

# Common issues:
# 1. API URL incorrect → Check NEXT_PUBLIC_API_URL
# 2. Build failed → Run docker-compose build frontend
# 3. Port 3000 in use → Change port mapping
```

### Database Connection Issues

```bash
# Test MySQL connection
docker-compose exec mysql mysql -u lmsuser -p jeeleducation_lms

# Check if database exists
docker-compose exec mysql mysql -u root -p -e "SHOW DATABASES;"

# Recreate database
docker-compose down -v
docker-compose up -d
```

### Rebuild Everything

```bash
# Complete cleanup and rebuild
docker-compose down -v
docker system prune -a
docker-compose up -d --build
```

## 📈 Monitoring

### Health Checks

```bash
# Backend health
curl http://localhost:8080/actuator/health

# Check all services
docker-compose ps
```

### Resource Usage

```bash
# Check resource usage
docker stats

# Check disk usage
docker system df
```

## 🔄 Updates and Maintenance

### Update Application

```bash
# Pull latest code
git pull origin main

# Rebuild and restart
docker-compose down
docker-compose up -d --build
```

### Backup Database

```bash
# Backup MySQL data
docker-compose exec mysql mysqldump -u root -p jeeleducation_lms > backup.sql

# Restore from backup
docker-compose exec -T mysql mysql -u root -p jeeleducation_lms < backup.sql
```

### Update Dependencies

**Backend:**
```bash
cd backend
mvn versions:display-dependency-updates
# Update pom.xml as needed
```

**Frontend:**
```bash
cd frontend
npm outdated
npm update
```

## 📚 Additional Resources

- [Docker Documentation](https://docs.docker.com/)
- [Docker Compose Documentation](https://docs.docker.com/compose/)
- [Next.js Docker Deployment](https://nextjs.org/docs/deployment#docker-image)
- [Spring Boot Docker Guide](https://spring.io/guides/gs/spring-boot-docker/)

---

**Last Updated**: October 13, 2025

