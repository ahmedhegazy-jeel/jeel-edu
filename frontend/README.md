# JeelEducation LMS - Frontend

Modern, responsive frontend for the JeelEducation Learning Management System built with Next.js 14, React 18, TypeScript, and Tailwind CSS.

## 🚀 Tech Stack

- **Framework**: Next.js 14 (App Router)
- **Language**: TypeScript
- **Styling**: Tailwind CSS
- **HTTP Client**: Axios
- **State Management**: React Hooks
- **Authentication**: JWT with HTTP-only cookies
- **Icons**: React Icons
- **Fonts**: Inter (Google Fonts)

## 📦 Installation

```bash
# Navigate to frontend directory
cd frontend

# Install dependencies
npm install

# Create environment file
cp .env.example .env.local

# Update .env.local with your backend API URL
```

## 🛠️ Development

```bash
# Run development server
npm run dev

# Build for production
npm run build

# Start production server
npm run start

# Run linter
npm run lint

# Fix linting issues
npm run lint:fix

# Format code with Prettier
npm run format

# Check formatting
npm run format:check
```

The development server will start at [http://localhost:3000](http://localhost:3000)

## 🏗️ Project Structure

```
frontend/
├── src/
│   ├── app/                    # Next.js 14 App Router
│   │   ├── layout.tsx          # Root layout
│   │   ├── page.tsx            # Landing page
│   │   ├── globals.css         # Global styles
│   │   ├── login/              # Login page
│   │   ├── register/           # Registration page
│   │   └── student/            # Student dashboard
│   ├── components/             # Reusable components
│   │   └── Navbar.tsx          # Navigation bar
│   ├── lib/                    # Utility libraries
│   │   ├── api.ts              # API client & service functions
│   │   └── auth.ts             # Authentication utilities
│   └── middleware.ts           # Next.js middleware (route protection)
├── public/                     # Static assets
├── .env.local                  # Environment variables (not in git)
├── .env.example                # Environment variables template
├── next.config.js              # Next.js configuration
├── tailwind.config.ts          # Tailwind CSS configuration
├── tsconfig.json               # TypeScript configuration
└── package.json                # Dependencies
```

## 🎨 Features

### ✅ Implemented

1. **Authentication System**
   - Login page with username/email support
   - Registration page with role selection (Student, Parent, Teacher)
   - JWT token management with auto-refresh
   - Protected routes with middleware
   - Logout functionality

2. **API Integration**
   - Complete API client with axios
   - Token interceptors for authentication
   - Auto token refresh on 401 errors
   - Service functions for all backend endpoints:
     - Auth API (login, register, password reset)
     - User API (profile, search)
     - Curriculum API (CRUD operations)
     - Unit, Lesson, Activity APIs
     - School API
     - Progress Tracking API
     - Admin Panel API

3. **All 5 Role-Based Dashboards** ✅
   - Student Dashboard (Progress tracking & learning hub access)
   - Teacher Dashboard (Curriculum management & student tracking)
   - Parent Dashboard (Children progress monitoring)
   - School Admin Dashboard (School-wide management)
   - Super Admin Dashboard (System overview & analytics)

4. **Complete Management System** ✅
   - **School Management**: List, Create, Search, Delete
   - **Curriculum Management**: List, Create, Filter, Detail, Delete
   - **Unit Management**: Create, List within Curriculum, Detail, Delete
   - **Lesson Management**: Create, List within Unit, Detail, Delete
   - **Activity Management**: ALL 8 types with creation forms
     - 📄 Text Activity
     - 📝 Quiz Activity (with dynamic questions)
     - 📑 PDF Activity
     - 🔊 Audio Activity (dual versions)
     - 🎥 Video Activity (dual versions)
     - 📖 Book Activity (multi-page)
     - 🎮 Interactive Activity (external URLs)
     - 📋 Homework Activity (file uploads)

5. **Student Learning Interface** ✅ **NEW!**
   - Beautiful curriculum browser
   - Enrollment system
   - Interactive activity viewers for ALL 8 types:
     - Text reader with audio
     - PDF viewer with audio
     - Audio player (with/without music)
     - Video player (with/without music)
     - Multi-page book reader
     - External interactive activity launcher
     - Quiz taking with scoring system
     - Homework submission interface
   - Progress tracking & points system
   - Gamification elements
   - Success celebrations
   - Motivational messages
   - Kid-friendly colorful design

6. **UI Components**
   - Responsive navigation bar
   - Modern landing page
   - Beautiful gradient designs
   - Tailwind CSS styling
   - Custom color schemes
   - Animated elements
   - Interactive cards

### 🔜 Coming Soon

- Edit pages for all management entities
- User management interface (SUPER_ADMIN)
- Advanced mobile optimizations
- Additional animations and transitions
- Parent-child linking system
- Real-time notifications

## 🔐 Environment Variables

Create a `.env.local` file with the following variables:

```env
NEXT_PUBLIC_API_URL=http://localhost:8080/api
NEXT_PUBLIC_APP_NAME=JeelEducation LMS
NEXT_PUBLIC_APP_VERSION=0.1.0
```

## 🌈 Styling

The app uses Tailwind CSS with a custom color palette:

- **Primary**: Blue shades (for main actions and branding)
- **Secondary**: Purple/Pink shades (for accents)
- **Font**: Inter (loaded from Google Fonts)

Custom styles can be found in:
- `tailwind.config.ts` - Tailwind configuration
- `src/app/globals.css` - Global CSS with custom scrollbar

## 📱 Responsive Design

The application is fully responsive and works on:
- Desktop (1920px and above)
- Laptop (1280px - 1920px)
- Tablet (768px - 1280px)
- Mobile (below 768px)

## 🔒 Authentication Flow

1. User logs in with username/email and password
2. Backend returns access token (1 hour) and refresh token (7 days)
3. Tokens stored in HTTP-only cookies
4. Access token sent with every API request
5. Auto-refresh when token expires (401 error)
6. Redirect to login if refresh fails

## 🚦 Route Protection

Routes are protected using Next.js middleware (`src/middleware.ts`):

- Public routes: `/`, `/login`, `/register`, `/forgot-password`
- Protected routes: Everything else (requires authentication)
- Role-based redirects after login

## 📊 API Service Architecture

All API calls go through centralized service functions in `src/lib/api.ts`:

```typescript
import { authAPI, userAPI, curriculumAPI } from '@/lib/api';

// Example usage
const response = await authAPI.login('username', 'password');
const users = await userAPI.getAllUsers();
const curriculums = await curriculumAPI.getAll();
```

## 🎯 Development Guidelines

1. **Component Structure**
   - Use functional components with hooks
   - Keep components small and focused
   - Extract reusable logic into custom hooks

2. **Styling**
   - Use Tailwind CSS utility classes
   - Follow mobile-first approach
   - Maintain consistent spacing and colors

3. **TypeScript**
   - Define interfaces for all data structures
   - Avoid `any` type when possible
   - Use strict type checking

4. **API Calls**
   - Use service functions from `lib/api.ts`
   - Handle errors gracefully
   - Show loading states

## 🐛 Troubleshooting

**Issue**: API calls failing with CORS errors
- **Solution**: Ensure backend CORS is configured to allow `http://localhost:3000`

**Issue**: Authentication not working
- **Solution**: Check that cookies are being set and backend is returning proper tokens

**Issue**: Styles not applying
- **Solution**: Ensure Tailwind is properly configured and CSS is imported in `layout.tsx`

## 📄 License

This project is part of the JeelEducation LMS system.

## 🤝 Contributing

Please follow the project's coding standards and submit pull requests for review.

---

**Status**: Phase 4 - Task 1 Complete ✅
**Next**: Continue building dashboard components and learning interfaces
