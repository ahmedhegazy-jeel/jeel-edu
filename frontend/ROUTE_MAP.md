# JeelEducation LMS - Complete Route Map

This document provides a complete map of all routes in the frontend application.

## 🔓 Public Routes

| Route | Description |
|-------|-------------|
| `/` | Landing page with features showcase |
| `/login` | User authentication |
| `/register` | New user registration (Student, Parent, Teacher) |

## 🔐 Protected Routes

### 👨‍🎓 Student Routes

#### Dashboard & Learning Hub
| Route | Description |
|-------|-------------|
| `/student/dashboard` | Student dashboard with progress overview |
| `/student/learn` | Learning hub - Browse all curriculums |
| `/student/learn/curriculum/[id]` | View curriculum with units & enroll |
| `/student/learn/unit/[id]` | View unit with lessons |
| `/student/learn/lesson/[id]` | View lesson with activities |
| `/student/learn/activity/[id]` | Interactive activity viewer (all 8 types) |

### 👨‍🏫 Teacher Routes

#### Dashboard
| Route | Description |
|-------|-------------|
| `/teacher/dashboard` | Teacher dashboard with curriculum management |

### 👨‍👩‍👧 Parent Routes

#### Dashboard
| Route | Description |
|-------|-------------|
| `/parent/dashboard` | Parent dashboard for tracking children |

### 🏫 School Admin Routes

#### Dashboard
| Route | Description |
|-------|-------------|
| `/school-admin/dashboard` | School admin dashboard |

### 👑 Super Admin Routes

#### Dashboard
| Route | Description |
|-------|-------------|
| `/super-admin/dashboard` | Super admin dashboard with system overview |

## 🛠️ Management Routes (Admin/Teacher Access)

### School Management
| Route | Description |
|-------|-------------|
| `/schools` | List all schools |
| `/schools/create` | Create new school |
| `/schools/[id]` | View school details (placeholder) |
| `/schools/[id]/edit` | Edit school (placeholder) |

### Curriculum Management
| Route | Description |
|-------|-------------|
| `/curriculums` | List all curriculums (with filters) |
| `/curriculums/create` | Create new curriculum |
| `/curriculums/[id]` | View curriculum with units |
| `/curriculums/[id]/edit` | Edit curriculum (placeholder) |
| `/curriculums/[id]/units/create` | Create unit in curriculum |

### Unit Management
| Route | Description |
|-------|-------------|
| `/units/[id]` | View unit with lessons |
| `/units/[id]/edit` | Edit unit (placeholder) |
| `/units/[id]/lessons/create` | Create lesson in unit |

### Lesson Management
| Route | Description |
|-------|-------------|
| `/lessons/[id]` | View lesson with activities |
| `/lessons/[id]/edit` | Edit lesson (placeholder) |
| `/lessons/[id]/activities/create` | Select activity type |
| `/lessons/[id]/activities/create/text` | Create text activity |
| `/lessons/[id]/activities/create/quiz` | Create quiz activity |
| `/lessons/[id]/activities/create/pdf` | Create PDF activity |
| `/lessons/[id]/activities/create/audio` | Create audio activity |
| `/lessons/[id]/activities/create/video` | Create video activity |
| `/lessons/[id]/activities/create/book` | Create book activity |
| `/lessons/[id]/activities/create/interactive` | Create interactive activity |
| `/lessons/[id]/activities/create/homework` | Create homework activity |

### Activity Management
| Route | Description |
|-------|-------------|
| `/activities/[id]` | View activity details (placeholder) |
| `/activities/[id]/edit` | Edit activity (placeholder) |

## 📊 Complete Route Summary

### By Category

**Authentication & Public**: 3 routes
- Landing, Login, Register

**Dashboards**: 5 routes
- Student, Teacher, Parent, School Admin, Super Admin

**School Management**: 4 routes
- List, Create, View, Edit

**Curriculum Hierarchy**: 20 routes
- Curriculum: List, Create, Detail, Edit, Create Unit
- Unit: Detail, Edit, Create Lesson
- Lesson: Detail, Edit, Activity Type Selector
- Activity: 8 Creation Forms, View, Edit

**Student Learning**: 5 routes
- Learning Hub, Curriculum View, Unit View, Lesson View, Activity Viewer

**Total Routes**: 37+ routes

## 🎯 Activity Types Supported

All routes support **8 different activity types**:

1. **📄 TEXT** - Text content with audio narration
2. **📝 QUIZ** - Assessments with questions
3. **📑 PDF** - PDF documents with audio
4. **🔊 AUDIO** - Audio lessons (with/without music)
5. **🎥 VIDEO** - Video lessons (with/without music)
6. **📖 BOOK** - Interactive multi-page books
7. **🎮 INTERACTIVE** - External educational activities
8. **📋 HOMEWORK** - Assignments with file uploads

## 🎨 Design Features

- ✅ Gradient backgrounds
- ✅ Colorful cards
- ✅ Hover animations
- ✅ Transform effects
- ✅ Progress bars
- ✅ Points badges
- ✅ Status indicators
- ✅ Empty states
- ✅ Loading states
- ✅ Success celebrations
- ✅ Emoji icons
- ✅ Responsive grids

## 🚀 Quick Navigation Examples

### For Students:
```
/student/dashboard → /student/learn → Select Curriculum → 
  Select Unit → Select Lesson → Complete Activity → Earn Points!
```

### For Teachers:
```
/teacher/dashboard → /curriculums/create → Add Units → 
  Add Lessons → Add Activities (8 types) → Publish!
```

### For School Admins:
```
/school-admin/dashboard → /schools/create → Manage Teachers → 
  Manage Students → View Reports
```

### For Super Admins:
```
/super-admin/dashboard → Manage Users → Manage Schools → 
  Manage Curriculums → View System Stats
```

## 📱 Responsive Design

All routes are fully responsive and work on:
- 📱 Mobile (< 768px)
- 💻 Tablet (768px - 1280px)
- 🖥️ Desktop (> 1280px)

## 🔒 Security

- All protected routes check authentication
- Role-based access control on each dashboard
- Automatic redirect to login if not authenticated
- Automatic redirect based on user role after login

---

**Last Updated**: October 13, 2025
**Status**: Phase 4 - 82% Complete (14/17 tasks)

