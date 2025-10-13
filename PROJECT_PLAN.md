
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
  - Backend: [Springboot + maven]
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
1- [] Design low-fidelity wireframes
2- [] Create basic HTML structure
3- [] Create React or next components  :
    a- component for school screens .
    b- component for curriculum screens .
    c- component for unit screens .
    d- component for lesson screens , including inside it components for activity .

4- [] Implement CSS styling and responsive design
5- [] Setup UI framework (Tailwind CSS / Bootstrap)
6- [] Add JavaScript interactivity
7- [] Develop main pages and other components
8- [] Build login/register pages
9- [] Dashboard (different per role , superAdmin,schoolAdmin, teacher, parent, student)
10- [] build Student learning interface and make it attractive and stylish
11- [] Add responsive mobile views

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
1- [] Write Dockerfile for backend
2- [] Write docker-compose for local dev
3- [] Setup CI/CD pipeline (Bitbucket CI)
4- [] Configure domain and SSL
5- [] Choose hosting platform
6- [] Deploy backend to [AWS]
7- [] Deploy frontend to [Vercel]

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
