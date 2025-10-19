# 🚀 JeelEducation LMS - Deployment Quick Start

Complete deployment guide for getting the LMS up and running.

## 📋 Prerequisites

- **Docker** & **Docker Compose** installed
- **Node.js** 18+ (for local frontend development)
- **Java 17** (for local backend development)
- **Git** for version control

## ⚡ Quick Start (5 Minutes)

### 1. Clone Repository

```bash
git clone <repository-url>
cd jeeleducation-lms
```

### 2. Configure Environment

```bash
# Copy environment template
cp .env.docker .env

# Edit with your settings (optional for local dev)
nano .env
```

### 3. Start with Docker Compose

```bash
# Start all services (MySQL, Backend, Frontend)
docker-compose up -d

# Wait for services to be ready (30-60 seconds)
docker-compose ps

# View logs
docker-compose logs -f
```

### 4. Access the Application

- **Frontend**: http://localhost:3000
- **Backend API**: http://localhost:8080/api
- **MySQL**: localhost:3306

### 5. Create First User

Visit http://localhost:3000/register and create a student, parent, or teacher account!

---

## 🎯 Deployment Options

### Option 1: Local Development (Docker Compose) ✅ READY

**Best for**: Local testing, development

**Steps**:
```bash
docker-compose up -d
```

**Access**:
- Frontend: http://localhost:3000
- Backend: http://localhost:8080

[📖 Full Docker Guide](./docs/deployment/docker-deployment.md)

---

### Option 2: Production (AWS + Vercel) ✅ CONFIGURED

**Best for**: Production deployment

**Backend (AWS)**:
```bash
cd backend
mvn clean package
eb init
eb create jeeleducation-prod
eb deploy
```

**Frontend (Vercel)**:
```bash
cd frontend
npm install -g vercel
vercel --prod
```

[📖 AWS Deployment Guide](./docs/deployment/aws-deployment.md)
[📖 Vercel Deployment Guide](./docs/deployment/vercel-deployment.md)

---

## 🐳 Docker Commands

```bash
# Start all services
docker-compose up -d

# Stop all services
docker-compose down

# View logs
docker-compose logs -f backend
docker-compose logs -f frontend
docker-compose logs -f mysql

# Rebuild services
docker-compose up -d --build

# Remove everything (including data!)
docker-compose down -v
```

## 🔧 Configuration

### Environment Variables

**Backend** (.env):
```env
SPRING_PROFILES_ACTIVE=prod
SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/jeeleducation_lms
JWT_SECRET=<your-256-bit-secret>
MAIL_HOST=smtp.gmail.com
MAIL_USERNAME=your-email@gmail.com
MAIL_PASSWORD=your-app-password
```

**Frontend** (.env.local):
```env
NEXT_PUBLIC_API_URL=http://localhost:8080/api
```

## 🌐 Production Deployment

### Step 1: Database (AWS RDS)

1. Create MySQL 8.0 RDS instance
2. Note the endpoint
3. Create database: `jeeleducation_lms`

### Step 2: Backend (AWS)

Choose one option:

**A. Elastic Beanstalk** (Easiest):
```bash
cd backend
eb init
eb create jeeleducation-prod
eb setenv SPRING_DATASOURCE_URL=jdbc:mysql://rds-endpoint:3306/jeeleducation_lms
eb deploy
```

**B. ECS with Docker**:
```bash
# Push Docker image to ECR
aws ecr get-login-password | docker login --username AWS ...
docker build -t jeeleducation-backend ./backend
docker tag jeeleducation-backend:latest <account-id>.dkr.ecr.region.amazonaws.com/backend:latest
docker push <account-id>.dkr.ecr.region.amazonaws.com/backend:latest

# Deploy ECS service
aws ecs update-service --cluster jeeleducation --service backend --force-new-deployment
```

### Step 3: Frontend (Vercel)

```bash
cd frontend
vercel --prod
```

Or connect via Vercel Dashboard:
1. Go to [vercel.com](https://vercel.com)
2. Import Git repository
3. Select `frontend` directory
4. Add environment variable: `NEXT_PUBLIC_API_URL`
5. Deploy!

### Step 4: Configure Domain

**Vercel** (Frontend):
- Add domain in Vercel Dashboard
- Update DNS: CNAME to Vercel

**AWS** (Backend):
- Use Route 53 or your DNS provider
- Point api.yourdomain.com to ALB/EC2

### Step 5: Configure CORS

Update backend to allow your Vercel domain:

```java
// backend/src/main/java/com/jeeleducation/lms/config/SecurityConfig.java
configuration.setAllowedOrigins(Arrays.asList(
    "https://yourdomain.vercel.app",
    "https://www.yourdomain.com"
));
```

## 📊 Architecture

```
Production Architecture:

Internet
   │
   ├─→ https://www.yourdomain.com (Vercel)
   │       │
   │       │ API Calls
   │       ↓
   └─→ https://api.yourdomain.com (AWS)
          │
          │ Database
          ↓
       AWS RDS MySQL
```

## 🧪 Testing Deployment

### Health Checks

**Backend**:
```bash
curl https://api.yourdomain.com/actuator/health
```

**Frontend**:
```bash
curl https://www.yourdomain.com
```

### Functionality Test

1. Visit your frontend URL
2. Register a new account
3. Login
4. Create a curriculum
5. Browse as a student
6. Verify API connectivity

## 🔒 Security

### Production Checklist

- [ ] Change default passwords
- [ ] Use strong JWT secret (256-bit)
- [ ] Enable HTTPS everywhere
- [ ] Configure CORS properly
- [ ] Restrict database access
- [ ] Use environment variables for secrets
- [ ] Enable rate limiting
- [ ] Set up monitoring
- [ ] Configure backups
- [ ] Review security groups

## 💰 Cost Estimates

### Development (Free/Low Cost)
```
Vercel:        Free (Hobby plan)
Docker Local:  Free
Total:         $0/month
```

### Production (Starter)
```
Vercel Pro:    $20/month
AWS EC2:       $35/month (t3.medium)
AWS RDS:       $40/month (db.t3.small Multi-AZ)
AWS ALB:       $20/month
Total:         ~$115/month
```

### Production (Scalable)
```
Vercel:        $20-100/month
AWS ECS:       $50-200/month (auto-scaling)
AWS RDS:       $80-300/month (larger instance)
Total:         ~$150-600/month
```

## 🆘 Troubleshooting

### Backend Won't Start

```bash
# Check logs
docker-compose logs backend

# Common fixes:
# - Wait for MySQL health check
# - Verify database credentials
# - Check port 8080 availability
```

### Frontend Can't Connect to Backend

```bash
# Verify API URL
echo $NEXT_PUBLIC_API_URL

# Check backend CORS
# Backend must allow frontend domain
```

### Database Connection Issues

```bash
# Test MySQL
docker-compose exec mysql mysql -u lmsuser -p

# Recreate database
docker-compose down -v
docker-compose up -d
```

## 📚 Documentation

- [Docker Deployment Guide](./docs/deployment/docker-deployment.md)
- [AWS Deployment Guide](./docs/deployment/aws-deployment.md)
- [Vercel Deployment Guide](./docs/deployment/vercel-deployment.md)
- [Deployment Overview](./docs/deployment/README.md)

## 🎉 You're Ready!

The application is fully configured for deployment. Choose your preferred option:

- **Quick Test**: `docker-compose up -d`
- **Production**: AWS + Vercel following the guides

---

**Deployment Status**: ✅ Configured and ready to deploy!

**Next Steps**:
1. Test locally with Docker Compose
2. Set up AWS account (if needed)
3. Set up Vercel account (if needed)
4. Deploy and enjoy!

