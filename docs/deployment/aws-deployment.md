# AWS Deployment Guide

This guide explains how to deploy the JeelEducation LMS backend to AWS.

## 📋 Overview

The backend can be deployed to AWS using several approaches:
1. **AWS Elastic Beanstalk** (Recommended for ease)
2. **AWS ECS (Elastic Container Service)** (Recommended for scalability)
3. **AWS EC2** (Manual deployment)
4. **AWS App Runner** (Simplest container deployment)

## 🚀 Option 1: AWS Elastic Beanstalk (Recommended)

### Prerequisites
- AWS CLI installed and configured
- EB CLI installed (`pip install awsebcli`)
- JAR file built (`mvn clean package`)

### Steps

1. **Initialize Elastic Beanstalk**:
```bash
cd backend
eb init -p docker jeeleducation-lms --region us-east-1
```

2. **Create Environment**:
```bash
eb create jeeleducation-prod \
  --database.engine mysql \
  --database.size 20 \
  --database.instance db.t3.micro \
  --database.username lmsuser
```

3. **Set Environment Variables**:
```bash
eb setenv \
  SPRING_PROFILES_ACTIVE=prod \
  JWT_SECRET="your-production-secret-key-256-bits" \
  MAIL_HOST=smtp.gmail.com \
  MAIL_PORT=587 \
  MAIL_USERNAME=your-email@gmail.com \
  MAIL_PASSWORD=your-app-password
```

4. **Deploy**:
```bash
eb deploy
```

5. **Open Application**:
```bash
eb open
```

### Configuration Files

Create `.ebextensions/01_settings.config`:
```yaml
option_settings:
  aws:elasticbeanstalk:application:environment:
    SERVER_PORT: 5000
  aws:elasticbeanstalk:container:java:
    JVM Options: "-Xmx512m"
```

## 🐳 Option 2: AWS ECS (Elastic Container Service)

### Prerequisites
- AWS CLI configured
- Docker image built and pushed to ECR

### Steps

1. **Create ECR Repository**:
```bash
aws ecr create-repository --repository-name jeeleducation-backend --region us-east-1
```

2. **Build and Push Docker Image**:
```bash
# Get ECR login
aws ecr get-login-password --region us-east-1 | \
  docker login --username AWS --password-stdin <account-id>.dkr.ecr.us-east-1.amazonaws.com

# Build image
cd backend
docker build -t jeeleducation-backend .

# Tag image
docker tag jeeleducation-backend:latest \
  <account-id>.dkr.ecr.us-east-1.amazonaws.com/jeeleducation-backend:latest

# Push image
docker push <account-id>.dkr.ecr.us-east-1.amazonaws.com/jeeleducation-backend:latest
```

3. **Create ECS Task Definition**:
```json
{
  "family": "jeeleducation-backend",
  "networkMode": "awsvpc",
  "requiresCompatibilities": ["FARGATE"],
  "cpu": "512",
  "memory": "1024",
  "containerDefinitions": [
    {
      "name": "backend",
      "image": "<account-id>.dkr.ecr.us-east-1.amazonaws.com/jeeleducation-backend:latest",
      "portMappings": [
        {
          "containerPort": 8080,
          "protocol": "tcp"
        }
      ],
      "environment": [
        {
          "name": "SPRING_PROFILES_ACTIVE",
          "value": "prod"
        },
        {
          "name": "SPRING_DATASOURCE_URL",
          "value": "jdbc:mysql://your-rds-endpoint:3306/jeeleducation_lms"
        }
      ],
      "secrets": [
        {
          "name": "JWT_SECRET",
          "valueFrom": "arn:aws:secretsmanager:us-east-1:account-id:secret:jwt-secret"
        }
      ],
      "logConfiguration": {
        "logDriver": "awslogs",
        "options": {
          "awslogs-group": "/ecs/jeeleducation-backend",
          "awslogs-region": "us-east-1",
          "awslogs-stream-prefix": "ecs"
        }
      }
    }
  ]
}
```

4. **Create ECS Service**:
```bash
aws ecs create-service \
  --cluster jeeleducation-cluster \
  --service-name backend-service \
  --task-definition jeeleducation-backend \
  --desired-count 2 \
  --launch-type FARGATE \
  --network-configuration "awsvpcConfiguration={subnets=[subnet-xxx],securityGroups=[sg-xxx],assignPublicIp=ENABLED}" \
  --load-balancers "targetGroupArn=arn:aws:elasticloadbalancing:...,containerName=backend,containerPort=8080"
```

## 🖥️ Option 3: AWS EC2 (Manual Deployment)

### Launch EC2 Instance

1. **Launch Ubuntu 22.04 instance** (t3.medium or higher)
2. **Configure Security Group**:
   - Port 22 (SSH)
   - Port 8080 (Backend)
   - Port 3306 (MySQL) - if database on same instance

### Install Dependencies

```bash
# SSH into instance
ssh -i your-key.pem ubuntu@ec2-instance-ip

# Update system
sudo apt update && sudo apt upgrade -y

# Install Java 17
sudo apt install openjdk-17-jre-headless -y

# Install MySQL
sudo apt install mysql-server -y

# Configure MySQL
sudo mysql_secure_installation
```

### Deploy Application

```bash
# Create application directory
sudo mkdir -p /opt/jeeleducation
sudo chown ubuntu:ubuntu /opt/jeeleducation

# Copy JAR file (from local machine)
scp -i your-key.pem backend/target/lms-0.0.1-SNAPSHOT.jar ubuntu@ec2-ip:/opt/jeeleducation/

# Create systemd service
sudo nano /etc/systemd/system/jeeleducation.service
```

**Service file content**:
```ini
[Unit]
Description=JeelEducation LMS Backend
After=syslog.target mysql.service

[Service]
User=ubuntu
ExecStart=/usr/bin/java -jar /opt/jeeleducation/lms-0.0.1-SNAPSHOT.jar
SuccessExitStatus=143
Environment="SPRING_PROFILES_ACTIVE=prod"
Environment="SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/jeeleducation_lms"
Environment="JWT_SECRET=your-production-secret"

[Install]
WantedBy=multi-user.target
```

**Start service**:
```bash
sudo systemctl daemon-reload
sudo systemctl enable jeeleducation
sudo systemctl start jeeleducation
sudo systemctl status jeeleducation
```

## 🗄️ Database Options

### Option 1: AWS RDS (Recommended)

1. **Create RDS MySQL Instance**:
   - Engine: MySQL 8.0
   - Instance: db.t3.micro (free tier) or db.t3.small
   - Storage: 20GB SSD
   - Enable automated backups
   - Set up VPC security groups

2. **Get Connection String**:
   - Endpoint: `your-db.xxxxx.us-east-1.rds.amazonaws.com`
   - Port: 3306
   - Database: `jeeleducation_lms`

3. **Update Backend Configuration**:
   ```bash
   SPRING_DATASOURCE_URL=jdbc:mysql://your-rds-endpoint:3306/jeeleducation_lms
   SPRING_DATASOURCE_USERNAME=lmsuser
   SPRING_DATASOURCE_PASSWORD=your-secure-password
   ```

### Option 2: MySQL on EC2

If using MySQL on the same EC2 instance:

```bash
# Create database
sudo mysql -u root -p

CREATE DATABASE jeeleducation_lms;
CREATE USER 'lmsuser'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON jeeleducation_lms.* TO 'lmsuser'@'localhost';
FLUSH PRIVILEGES;
EXIT;
```

## 🌐 Domain and SSL

### Option 1: Using AWS Certificate Manager + ALB

1. **Request SSL Certificate**:
   - Go to AWS Certificate Manager
   - Request public certificate
   - Validate domain ownership

2. **Create Application Load Balancer**:
   - Add HTTPS listener (port 443)
   - Attach SSL certificate
   - Forward to target group (backend instances)

3. **Update Route 53**:
   - Create A record pointing to ALB
   - Example: `api.yourdomain.com → ALB`

### Option 2: Using Let's Encrypt on EC2

```bash
# Install Certbot
sudo apt install certbot python3-certbot-nginx -y

# Install Nginx
sudo apt install nginx -y

# Get certificate
sudo certbot --nginx -d api.yourdomain.com

# Nginx config
sudo nano /etc/nginx/sites-available/jeeleducation
```

**Nginx configuration**:
```nginx
server {
    listen 80;
    server_name api.yourdomain.com;
    return 301 https://$server_name$request_uri;
}

server {
    listen 443 ssl;
    server_name api.yourdomain.com;

    ssl_certificate /etc/letsencrypt/live/api.yourdomain.com/fullchain.pem;
    ssl_certificate_key /etc/letsencrypt/live/api.yourdomain.com/privkey.pem;

    location / {
        proxy_pass http://localhost:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }
}
```

## 📊 Monitoring and Logs

### CloudWatch Logs

1. **Install CloudWatch Agent** on EC2:
```bash
wget https://s3.amazonaws.com/amazoncloudwatch-agent/ubuntu/amd64/latest/amazon-cloudwatch-agent.deb
sudo dpkg -i amazon-cloudwatch-agent.deb
```

2. **Configure logging**:
   - Application logs → CloudWatch Logs
   - Metrics → CloudWatch Metrics
   - Alarms for errors and high resource usage

### Application Monitoring

Add Spring Boot Actuator endpoints monitoring:
- `/actuator/health` - Health check
- `/actuator/metrics` - Application metrics
- `/actuator/info` - Application information

## 💰 Cost Optimization

**Minimal Setup (Free Tier Eligible)**:
- EC2: t3.micro (1 vCPU, 1GB RAM)
- RDS: db.t3.micro
- S3: Standard storage for media files
- Estimated: $15-25/month

**Production Setup**:
- EC2: t3.medium (2 vCPU, 4GB RAM)
- RDS: db.t3.small (Multi-AZ for HA)
- ALB: Application Load Balancer
- CloudFront: CDN for frontend
- Estimated: $80-150/month

## 🔄 CI/CD Integration

Configure Bitbucket Pipelines to deploy to AWS:

```yaml
- step:
    name: Deploy to AWS
    script:
      # Update ECS service
      - aws ecs update-service --cluster jeeleducation --service backend-service --force-new-deployment
      
      # Or deploy to Elastic Beanstalk
      - eb deploy jeeleducation-prod
```

## 🆘 Support

For deployment issues:
1. Check CloudWatch Logs
2. Review application logs
3. Verify environment variables
4. Test database connectivity
5. Check security group rules

---

**Deployment Checklist:**
- [ ] AWS account created
- [ ] Domain registered
- [ ] SSL certificate obtained
- [ ] RDS database created
- [ ] Environment variables configured
- [ ] Application deployed
- [ ] Health checks passing
- [ ] Monitoring configured
- [ ] Backups enabled
- [ ] Security audit completed

