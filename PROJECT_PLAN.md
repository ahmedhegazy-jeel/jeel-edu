
# PROJECT_PLAN.md

# 🚀 Project Plan Template
	This file serves as the **source of truth** for project progress.  
	AI assistants should use this document to know **what has been done**, **what needs to be done next**, and **how to execute it**.  

## 📌 Project Overview
- **Project Name**: [JeelEducation-LMS Website]
- **Goal**: [create a modern full-stack web application for an E-Learning management system that avail      
             learnning experience for KIDS to learn Arabic and some other curriculums .
			 and allow for teachers management and parents tracking ]
             
- **Target Audience:** [Kids to apply a smooth learning experience , and Partener to track their children progress and school admin and teachers to manage curriculums / study-plans ]
- **Key Features:** [
						1- curriculums management
						2- schools management 
						3- users management
						4- curriculums learning in an attractive way
					]			 
			 
- **Tech Stack**:
  - Frontend: [React / Next.js + Tailwind CSS]
  - Backend: [Springboot + maven , lombok , flyway ,mapper]
  - Database: [MySQL]
  - Auth: [JWT / OAuth / Role-based access RBAC]
  - Deployment: [Docker + Vercel (frontend) + Docker + AWS (backend)]

---

## Milestones & Step-by-Step Implementation Plan

### Phase 1: Planning & Setup
1- [x] Define project requirements and scope
2- [x] Set up development environment
3- [x] Initialize Git repo
4- [x] Create project folder structure (`frontend/`, `backend/`, `docs/`)
5- [x] Setup linting & formatting (Prettier, ESLint)

---

### Phase 2: Develop authentication system
1- [x] Create user model and roles (superAdmin,schoolAdmin, teacher, parent, student)
2- [x] Setup authentication (JWT or OAuth)
3- [x] Implement Role-based authorization APIs
4- [x] Build login & register endpoints
5- [x] Build password reset flow

---

### Phase 3: Backend APIs
1- [x] Setup API framework (Springboot)
2- [x] Configure database
3- [x] Implement database schema
4- [x] Create API endpoints like Implement user management APIs
5- [x] Curriculum management as following :
    a- [x] create Curriculum  with fields  { name / icon  / status / unitList} . 
    b- [x] create Unit , that group lessons , with fields :  {name / audioName / icon / curriculum / status , lessonList }  .
    c- [x] create Lesson , consist of activities , with fields :  {name / audioName / icon / unit / status , activityList } .
    d- [x] create Activity  , with common fields like  {titleName / titleAudioName / desc / icon / status / points / tag or topic } : 
            - [x] System have some pre-defined activity types , which are :
                -[x] Text :  that has the above common fields plus :  text / textAudio
                -[x] PDF  :  that has the above common fields plus :     pdfURL / audioOfPDF
                -[x] AUDIO : that has the above common fields plus :   audioWithMusic / audioWithoutMusic
                -[x] BOOK :  that has the above common fields plus :    list of pages . each page has fields : {image / text /        
                                                                     audio-generatedByAI}
                -[x] VIDEO : that has the above common fields plus :  videoWithMusic / videoWithoutMusic 
                -[x] Interactive :   that has the above common fields plus :     externalActivityURL
                -[x] QUIZ :   that has the above common fields plus :    percentageToPass  / attemptNum / NoOfQuestions / questionsList
                -[x] HOMEWORK :   that has the above common fields plus :     Uploaded HomeWork Files
			
			- [x] Create the entities and DTOs and Controller with the endpoints and service and repository  for each of the above entities .								

6- [x] School management , with fields like {name , icon , adminMobile , adminEmail, adminPassword }
    a- [x] Create the entities and DTOs and Controller with the endpoints and service and repository  for school .
7- [x] Implement progress tracking APIs
8- [x] Build admin panel
9- [x] Write automated tests for APIs

---

### Phase 4: Frontend Development
1- [x] Setup Next.js project with TypeScript and Tailwind CSS
2- [x] Create authentication pages (login & register)
3- [x] Setup API client and authentication utilities
4- [x] Build navigation component
5- [x] Create landing page
6- [x] Implement route protection middleware
7- [x] Build student dashboard
8- [x] Design and implement teacher dashboard
9- [x] Design and implement parent dashboard
10- [x] Design and implement school admin dashboard
11- [x] Design and implement super admin dashboard
12- [x] Create React components for:
    a- [x] School management screens
    b- [x] Curriculum management screens  
    c- [x] Unit management screens
    d- [x] Lesson management screens
    e- [x] Activity components (All 8 types complete: Text, Quiz, PDF, Audio, Video, Book, Interactive, Homework)
13- [x] Build student learning interface (attractive and stylish)
14- [x] Implement CSS styling and full responsive design (Polish mobile views)
15- [x] Add advanced interactivity and animations and transitions
16- [x] Create all CRUD pages for management
17- [x] Add mobile-optimized views

---

### Phase 5: Integration
1- [] Connect frontend with backend APIs
2- [] Implement error handling & validation
3- [] Add loading states & skeleton screens
4- [] Test all functionalities
5- [] Test role-specific dashboards
6- [] Security audit

---

### Phase 6: Deployment
1- [x] Write Dockerfile for backend
2- [x] Write docker-compose for local dev
3- [x] Setup CI/CD pipeline (Bitbucket CI)
4- [partial] Configure domain and SSL (Documentation provided)
5- [x] Choose hosting platform (AWS for backend, Vercel for frontend)
6- [partial] Deploy backend to [AWS] (Configuration ready, deployment pending)
7- [partial] Deploy frontend to [Vercel] (Configuration ready, deployment pending)

---

### Phase 7: Testing & QA
1- [] Write unit tests (frontend + backend)
2- [] Write integration tests
3- [] Write end-to-end tests (Cypress / Playwright)
4- [] Manual testing of critical flows

---

### Phase 8: Maintenance & Iteration
1- [] Create `CHANGELOG.md`
2- [] Add monitoring (Sentry)
3- [] Collect feedback from users
4- [] Plan next feature iteration

---

## 📖 Notes for AI Workflow
1. **Context Reminder**  
    - Always refer to this file before generating code.  
	- Keep AI updated: After each completed step, mark `[x]` and paste this plan back to AI.
	- Context continuity: AI uses this file to know what’s done and what’s next.


✅ This document ensures continuity between **you** and **AI**.  
✅ Acts as a **Kanban board inside Markdown**.  
✅ Keeps project **organized, trackable, and executable step-by-step**.   
