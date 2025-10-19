# Deployment Guide

Complete deployment documentation for the JeelEducation LMS system.

## 📚 Available Deployment Guides

1. **[Docker Deployment](./docker-deployment.md)** - Local development and containerized deployment
2. **[AWS Deployment](./aws-deployment.md)** - Backend deployment to AWS (EC2, ECS, Beanstalk)
3. **[Vercel Deployment](./vercel-deployment.md)** - Frontend deployment to Vercel

## 🏗️ Deployment Architecture

### Recommended Production Setup

```
┌─────────────────────────────────────────┐
│            Users/Clients                │
└──────────────┬──────────────────────────┘
               │
               │ HTTPS
               │
     ┌─────────▼─────────┐
     │  Vercel (Frontend)│
     │  Next.js App      │
     │  Port: 443        │
     └─────────┬─────────┘
               │
               │ API Calls (HTTPS)
               │
     ┌─────────▼─────────┐
     │ AWS ALB + EC2/ECS │
     │ Spring Boot API   │
     │ Port: 8080        │
     └─────────┬─────────┘
               │
               │ JDBC
               │
     ┌─────────▼─────────┐
     │   AWS RDS MySQL   │
     │   Port: 3306      │
     └───────────────────┘
```

## 🚀 Quick Start Options

### Option 1: Local Development (Docker Compose)

**Best for**: Local testing, development

```bash
# Clone repository
git clone <repo-url>
cd jeeleducation-lms

# Copy environment file
cp .env.docker .env

# Start all services
docker-compose up -d

# Access
# Frontend: http://localhost:3000
# Backend: http://localhost:8080
# MySQL: localhost:3306
```

**Pros**: Easy setup, isolated environment, all services together
**Cons**: Not suitable for production

[📖 Full Docker Guide](./docker-deployment.md)

---

### Option 2: Production Deployment

**Best for**: Production, staging environments

#### Backend → AWS
```bash
# Deploy to AWS Elastic Beanstalk
cd backend
eb init
eb create jeeleducation-prod
eb deploy
```

#### Frontend → Vercel
```bash
# Deploy to Vercel
cd frontend
vercel --prod
```

**Pros**: Scalable, reliable, managed services
**Cons**: Requires AWS/Vercel accounts

[📖 AWS Deployment Guide](./aws-deployment.md)
[📖 Vercel Deployment Guide](./vercel-deployment.md)

---

### Option 3: Manual EC2 Deployment

**Best for**: Full control, custom requirements

```bash
# SSH to EC2 instance
ssh -i key.pem ubuntu@ec2-ip

# Deploy backend
sudo systemctl start jeeleducation

# Deploy frontend (with PM2)
cd frontend
npm run build
pm2 start npm --name "frontend" -- start
```

**Pros**: Full control, flexible
**Cons**: More maintenance, manual setup

[📖 AWS EC2 Guide](./aws-deployment.md#option-3-aws-ec2-manual-deployment)

---

## 🔧 Configuration Checklist

### Before Deployment

- [ ] **Backend Built**: `cd backend && mvn clean package`
- [ ] **Frontend Built**: `cd frontend && npm run build`
- [ ] **Environment Variables**: Configured for production
- [ ] **Database**: MySQL accessible
- [ ] **Domain**: Registered and configured
- [ ] **SSL**: Certificate obtained
- [ ] **CORS**: Backend allows frontend domain

### Environment Variables

#### Backend
```env
SPRING_PROFILES_ACTIVE=prod
SPRING_DATASOURCE_URL=jdbc:mysql://db-host:3306/jeeleducation_lms
SPRING_DATASOURCE_USERNAME=lmsuser
SPRING_DATASOURCE_PASSWORD=<secure-password>
JWT_SECRET=<256-bit-secret>
JWT_EXPIRATION=3600000
JWT_REFRESH_EXPIRATION=604800000
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=<your-email>
MAIL_PASSWORD=<app-password>
```

#### Frontend
```env
NEXT_PUBLIC_API_URL=https://api.yourdomain.com
NEXT_PUBLIC_APP_NAME=JeelEducation LMS
NEXT_PUBLIC_APP_VERSION=1.0.0
```

## 🗄️ Database Setup

### Production Database (AWS RDS)

1. **Create RDS Instance**:
   - Engine: MySQL 8.0
   - Instance: db.t3.small (or larger)
   - Storage: 20GB SSD (expandable)
   - Multi-AZ: Yes (for high availability)
   - Automated backups: Enabled

2. **Security Group**:
   - Allow inbound on port 3306
   - From backend security group only
   - No public access

3. **Create Database**:
```sql
CREATE DATABASE jeeleducation_lms CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'lmsuser'@'%' IDENTIFIED BY 'secure-password';
GRANT ALL PRIVILEGES ON jeeleducation_lms.* TO 'lmsuser'@'%';
FLUSH PRIVILEGES;
```

## 🌐 Domain Configuration

### DNS Setup

**For Vercel Frontend**:
```
Type: A
Name: @
Value: 76.76.21.21 (Vercel IP)

Type: CNAME
Name: www
Value: cname.vercel-dns.com
```

**For AWS Backend**:
```
Type: A
Name: api
Value: <your-alb-or-ec2-ip>

OR

Type: CNAME
Name: api
Value: your-alb.us-east-1.elb.amazonaws.com
```

### SSL Certificates

**Vercel**: Automatic SSL (Let's Encrypt)
**AWS**: Use AWS Certificate Manager or Let's Encrypt

## 🔐 Security Considerations

### Production Checklist

- [ ] Strong passwords for all services
- [ ] JWT secret is 256-bit random key
- [ ] Database not publicly accessible
- [ ] CORS configured with specific domains
- [ ] HTTPS enforced on all connections
- [ ] Environment variables secured
- [ ] Regular security updates
- [ ] Rate limiting enabled
- [ ] Input validation on all endpoints
- [ ] SQL injection protection (JPA handles this)

### Backend Security

In `application-prod.yml`:
```yaml
spring:
  datasource:
    url: ${SPRING_DATASOURCE_URL}
    username: ${SPRING_DATASOURCE_USERNAME}
    password: ${SPRING_DATASOURCE_PASSWORD}
  
jwt:
  secret: ${JWT_SECRET}
  expiration: ${JWT_EXPIRATION}

# Disable H2 console in production
spring.h2.console.enabled: false
```

## 📊 Monitoring

### Backend Monitoring (AWS CloudWatch)

- Application logs
- Error tracking
- Performance metrics
- Custom alarms

### Frontend Monitoring (Vercel Analytics)

- Page views
- Core Web Vitals
- Error tracking
- Performance insights

### Application Monitoring

Consider adding:
- **Sentry**: Error tracking
- **New Relic**: APM
- **Datadog**: Infrastructure monitoring

## 🔄 CI/CD Pipeline

### Bitbucket Pipelines

The `bitbucket-pipelines.yml` in the root provides:

1. **Automated Testing**: Runs on every push
2. **Build Validation**: Ensures code builds
3. **Docker Images**: Builds and pushes images
4. **Deployment**: Deploys to AWS and Vercel

### Manual Deployment

**Backend to AWS**:
```bash
cd backend
mvn clean package
eb deploy
```

**Frontend to Vercel**:
```bash
cd frontend
vercel --prod
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

1. Visit frontend URL
2. Register a new account
3. Login
4. Browse curriculums
5. Test management features
6. Verify API connectivity

## 💰 Cost Estimate

### Minimal Setup (Development)
- **Vercel**: Free (Hobby plan)
- **AWS EC2**: t3.micro (~$8/month)
- **AWS RDS**: db.t3.micro (~$15/month)
- **Total**: ~$23/month

### Production Setup
- **Vercel**: $20/month (Pro plan)
- **AWS EC2/ECS**: t3.medium (~$35/month)
- **AWS RDS**: db.t3.small Multi-AZ (~$40/month)
- **AWS ALB**: ~$20/month
- **Total**: ~$115/month

### Enterprise Setup
- **Vercel**: Custom pricing
- **AWS ECS Fargate**: Auto-scaling
- **AWS RDS**: db.r5.large Multi-AZ
- **CloudFront**: CDN
- **Total**: $500+/month

## 🆘 Support and Troubleshooting

### Common Issues

1. **CORS Errors**: Update backend CORS configuration
2. **API Connection Failed**: Check `NEXT_PUBLIC_API_URL`
3. **Database Connection**: Verify credentials and network access
4. **Build Failures**: Check dependencies and build logs
5. **SSL Issues**: Verify certificate installation

### Getting Help

- Check deployment logs
- Review application logs
- Test API endpoints with Postman
- Verify environment variables
- Check security group rules (AWS)

## 📖 Additional Resources

- [Next.js Deployment Docs](https://nextjs.org/docs/deployment)
- [Vercel Documentation](https://vercel.com/docs)
- [AWS Documentation](https://docs.aws.amazon.com/)
- [Docker Documentation](https://docs.docker.com/)

---

## 🎯 Recommended Deployment Path

**For Development**:
```
Docker Compose → Local testing → Perfect for development
```

**For Production**:
```
Backend: AWS (Elastic Beanstalk or ECS)
Frontend: Vercel (Automatic deployments)
Database: AWS RDS MySQL (Managed, backed up)
```

**Benefits**:
- Frontend: Fast, CDN-backed, automatic scaling
- Backend: Scalable, reliable, easy to manage
- Database: Managed, automatic backups, high availability
- CI/CD: Automated testing and deployment

---

**Last Updated**: October 13, 2025
**Status**: Phase 6 - Deployment configuration complete

