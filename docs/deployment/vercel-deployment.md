# Vercel Deployment Guide

This guide explains how to deploy the JeelEducation LMS frontend to Vercel.

## 📋 Overview

Vercel is the recommended platform for deploying Next.js applications, offering:
- Zero-configuration deployment
- Automatic HTTPS
- Global CDN
- Instant rollbacks
- Preview deployments for PRs

## 🚀 Quick Deployment

### Method 1: Vercel CLI (Recommended)

1. **Install Vercel CLI**:
```bash
npm install -g vercel
```

2. **Login to Vercel**:
```bash
vercel login
```

3. **Deploy**:
```bash
cd frontend
vercel

# For production
vercel --prod
```

### Method 2: Vercel Dashboard (GUI)

1. **Go to** [vercel.com](https://vercel.com)
2. **Sign up/Login** with GitHub, GitLab, or Bitbucket
3. **Import Project**:
   - Click "New Project"
   - Import your repository
   - Select `frontend` directory
   - Vercel auto-detects Next.js

4. **Configure**:
   - Framework Preset: Next.js
   - Root Directory: `frontend`
   - Build Command: `npm run build`
   - Output Directory: `.next`

5. **Add Environment Variables**:
   ```
   NEXT_PUBLIC_API_URL=https://api.yourdomain.com
   NEXT_PUBLIC_APP_NAME=JeelEducation LMS
   NEXT_PUBLIC_APP_VERSION=1.0.0
   ```

6. **Deploy**: Click "Deploy"

### Method 3: GitHub Integration (Automatic)

1. **Push to GitHub**:
```bash
git add .
git commit -m "Deploy to Vercel"
git push origin main
```

2. **Connect Repository**:
   - Go to Vercel Dashboard
   - Import Git Repository
   - Select your repo
   - Configure environment variables

3. **Automatic Deployments**:
   - Every push to `main` → Production
   - Every PR → Preview deployment
   - Instant rollbacks available

## ⚙️ Configuration

### Environment Variables

In Vercel Dashboard → Settings → Environment Variables:

**Required:**
```
NEXT_PUBLIC_API_URL=https://api.yourdomain.com
```

**Optional:**
```
NEXT_PUBLIC_APP_NAME=JeelEducation LMS
NEXT_PUBLIC_APP_VERSION=1.0.0
```

### vercel.json Configuration

Create `frontend/vercel.json`:
```json
{
  "framework": "nextjs",
  "buildCommand": "npm run build",
  "devCommand": "npm run dev",
  "installCommand": "npm install",
  "regions": ["iad1"],
  "env": {
    "NEXT_PUBLIC_API_URL": "@api_url"
  },
  "headers": [
    {
      "source": "/(.*)",
      "headers": [
        {
          "key": "Access-Control-Allow-Origin",
          "value": "*"
        }
      ]
    }
  ]
}
```

## 🌐 Custom Domain

### Add Custom Domain

1. **In Vercel Dashboard**:
   - Go to Project Settings → Domains
   - Add domain: `www.jeeleducation.com`
   - Vercel provides DNS instructions

2. **Update DNS Records**:
   - A record: `@` → `76.76.21.21`
   - CNAME record: `www` → `cname.vercel-dns.com`

3. **SSL Certificate**:
   - Automatically provisioned
   - Renews automatically
   - HTTPS enforced

### Connect Subdomain

For API subdomain:
```
api.jeeleducation.com → AWS backend
www.jeeleducation.com → Vercel frontend
```

## 🔧 Build Configuration

### Next.js Config for Vercel

Update `frontend/next.config.js`:
```javascript
const nextConfig = {
  reactStrictMode: true,
  output: 'standalone',
  env: {
    NEXT_PUBLIC_API_URL: process.env.NEXT_PUBLIC_API_URL,
  },
  images: {
    domains: ['localhost', 'your-cdn.com'],
  },
};
```

### Build Settings in Vercel

- **Framework Preset**: Next.js
- **Root Directory**: `frontend`
- **Build Command**: `npm run build`
- **Output Directory**: `.next`
- **Install Command**: `npm install`
- **Development Command**: `npm run dev`

## 📊 Performance Optimization

### Enable Analytics

In Vercel Dashboard:
- Enable Vercel Analytics
- Monitor Core Web Vitals
- Track page load times

### Speed Insights

```bash
npm install @vercel/speed-insights
```

Update `frontend/src/app/layout.tsx`:
```typescript
import { SpeedInsights } from '@vercel/speed-insights/next';

export default function RootLayout({ children }) {
  return (
    <html>
      <body>
        {children}
        <SpeedInsights />
      </body>
    </html>
  );
}
```

### Image Optimization

Images are automatically optimized by Vercel's Image Optimization API.

## 🔄 Deployment Workflow

### Production Deployment

```bash
# Deploy to production
cd frontend
vercel --prod

# Or via Git
git push origin main
```

### Preview Deployments

```bash
# Deploy preview
vercel

# Each PR automatically gets preview URL
# Example: jeeleducation-pr-123.vercel.app
```

### Rollback

```bash
# List deployments
vercel ls

# Promote previous deployment
vercel promote <deployment-url>
```

## 🚨 Troubleshooting

### Build Fails

**Error**: "Module not found"
- **Solution**: Ensure all dependencies in `package.json`
- Run `npm install` locally first

**Error**: "Build exceeded time limit"
- **Solution**: Upgrade Vercel plan or optimize build

**Error**: "Environment variable not found"
- **Solution**: Add to Vercel Dashboard → Settings → Environment Variables

### Runtime Errors

**CORS Issues**:
- Configure backend CORS to allow Vercel domain
- Add to Spring `SecurityConfig`:
  ```java
  .cors(cors -> cors.configurationSource(request -> {
      CorsConfiguration config = new CorsConfiguration();
      config.setAllowedOrigins(Arrays.asList(
          "http://localhost:3000",
          "https://yourdomain.vercel.app"
      ));
      return config;
  }))
  ```

**API Not Connecting**:
- Verify `NEXT_PUBLIC_API_URL` in Vercel
- Check backend CORS configuration
- Ensure backend is publicly accessible

## 📈 Monitoring

### Vercel Dashboard

Monitor:
- Deployment status
- Build logs
- Runtime logs
- Analytics
- Performance metrics

### Set Up Alerts

In Vercel Dashboard:
- Configure email alerts
- Set up Slack notifications
- Monitor deployment failures

## 💰 Cost

**Hobby Plan (Free)**:
- 100GB bandwidth/month
- Unlimited deployments
- Automatic HTTPS
- Good for development

**Pro Plan ($20/month)**:
- 1TB bandwidth
- Password protection
- Analytics
- Team collaboration

## 🔐 Security

### Environment Variables

**Sensitive data** (don't expose in frontend):
- JWT secrets → Backend only
- Database credentials → Backend only
- API keys → Backend only

**Frontend can access**:
- `NEXT_PUBLIC_*` variables only
- These are embedded in the client bundle

### Headers Configuration

Add security headers in `frontend/next.config.js`:
```javascript
async headers() {
  return [
    {
      source: '/(.*)',
      headers: [
        {
          key: 'X-Frame-Options',
          value: 'DENY',
        },
        {
          key: 'X-Content-Type-Options',
          value: 'nosniff',
        },
        {
          key: 'X-XSS-Protection',
          value: '1; mode=block',
        },
      ],
    },
  ];
}
```

## 🔗 Backend Integration

### Update Backend CORS

In `backend/src/main/java/com/jeeleducation/lms/config/SecurityConfig.java`:

```java
@Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList(
        "http://localhost:3000",
        "https://jeeleducation.vercel.app",
        "https://www.jeeleducation.com"
    ));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(Arrays.asList("*"));
    configuration.setAllowCredentials(true);
    
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
}
```

## 📚 Best Practices

1. **Use Environment Variables**: Never hardcode API URLs
2. **Enable Preview Deployments**: Test before production
3. **Monitor Performance**: Use Vercel Analytics
4. **Set Up Domains Early**: DNS propagation takes time
5. **Test CORS**: Ensure backend allows your domain
6. **Enable HTTPS**: Always use secure connections
7. **Optimize Images**: Use Next.js Image component
8. **Monitor Logs**: Check for runtime errors

## 🎯 Deployment Checklist

- [ ] Vercel account created
- [ ] Project connected to Git
- [ ] Environment variables configured
- [ ] Custom domain added (optional)
- [ ] SSL certificate active
- [ ] Backend CORS configured
- [ ] API connection tested
- [ ] Analytics enabled
- [ ] Monitoring set up
- [ ] Team access configured

---

**Vercel Deployment Status**: ✅ Ready to deploy!

The frontend is optimized for Vercel and can be deployed in minutes.

