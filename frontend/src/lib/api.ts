import axios from 'axios';
import Cookies from 'js-cookie';

const API_URL = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080/api';

// Create axios instance
const api = axios.create({
  baseURL: API_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Request interceptor to add auth token
api.interceptors.request.use(
  (config) => {
    const token = Cookies.get('accessToken');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Response interceptor to handle token refresh
api.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;

    if (error.response?.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true;

      try {
        const refreshToken = Cookies.get('refreshToken');
        if (refreshToken) {
          const response = await axios.post(`${API_URL}/auth/refresh`, {
            refreshToken,
          });

          const { accessToken, refreshToken: newRefreshToken } = response.data;

          Cookies.set('accessToken', accessToken);
          Cookies.set('refreshToken', newRefreshToken);

          originalRequest.headers.Authorization = `Bearer ${accessToken}`;
          return api(originalRequest);
        }
      } catch (refreshError) {
        Cookies.remove('accessToken');
        Cookies.remove('refreshToken');
        window.location.href = '/login';
        return Promise.reject(refreshError);
      }
    }

    return Promise.reject(error);
  }
);

export default api;

// API service functions
export const authAPI = {
  login: (usernameOrEmail: string, password: string) =>
    api.post('/auth/login', { usernameOrEmail, password }),
  
  register: (data: any, role: 'student' | 'parent' | 'teacher') =>
    api.post(`/auth/register/${role}`, data),
  
  logout: () => api.post('/auth/logout'),
  
  refreshToken: (refreshToken: string) =>
    api.post('/auth/refresh', { refreshToken }),
  
  requestPasswordReset: (email: string) =>
    api.post('/auth/password-reset/request', { email }),
  
  confirmPasswordReset: (token: string, newPassword: string) =>
    api.post('/auth/password-reset/confirm', { token, newPassword }),
};

export const userAPI = {
  getCurrentUser: () => api.get('/users/me'),
  
  updateProfile: (data: any) => api.put('/users/me', data),
  
  getAllUsers: () => api.get('/users'),
  
  getUserById: (id: number) => api.get(`/users/${id}`),
  
  searchUsers: (searchTerm: string) => api.get(`/users/search?searchTerm=${searchTerm}`),
};

export const curriculumAPI = {
  getAll: () => api.get('/curriculums'),
  
  getById: (id: number) => api.get(`/curriculums/${id}`),
  
  getByStatus: (status: string) => api.get(`/curriculums/status/${status}`),
  
  create: (data: any) => api.post('/curriculums', data),
  
  update: (id: number, data: any) => api.put(`/curriculums/${id}`, data),
  
  delete: (id: number) => api.delete(`/curriculums/${id}`),
  
  search: (searchTerm: string) => api.get(`/curriculums/search?searchTerm=${searchTerm}`),
};

export const unitAPI = {
  getByCurriculum: (curriculumId: number) =>
    api.get(`/units/curriculum/${curriculumId}`),
  
  getById: (id: number) => api.get(`/units/${id}`),
  
  create: (data: any) => api.post('/units', data),
  
  update: (id: number, data: any) => api.put(`/units/${id}`, data),
  
  delete: (id: number) => api.delete(`/units/${id}`),
};

export const lessonAPI = {
  getByUnit: (unitId: number) => api.get(`/lessons/unit/${unitId}`),
  
  getById: (id: number) => api.get(`/lessons/${id}`),
  
  create: (data: any) => api.post('/lessons', data),
  
  update: (id: number, data: any) => api.put(`/lessons/${id}`, data),
  
  delete: (id: number) => api.delete(`/lessons/${id}`),
};

export const activityAPI = {
  getByLesson: (lessonId: number) => api.get(`/activities/lesson/${lessonId}`),
  
  getById: (id: number) => api.get(`/activities/${id}`),
  
  create: (data: any) => api.post('/activities', data),
  
  update: (id: number, data: any) => api.put(`/activities/${id}`, data),
  
  delete: (id: number) => api.delete(`/activities/${id}`),
};

export const schoolAPI = {
  getAll: () => api.get('/schools'),
  
  getActive: () => api.get('/schools/active'),
  
  getById: (id: number) => api.get(`/schools/${id}`),
  
  create: (data: any) => api.post('/schools', data),
  
  update: (id: number, data: any) => api.put(`/schools/${id}`, data),
  
  delete: (id: number) => api.delete(`/schools/${id}`),
  
  search: (searchTerm: string) => api.get(`/schools/search?searchTerm=${searchTerm}`),
};

export const progressAPI = {
  getMyProgress: () => api.get('/progress/my-progress'),
  
  getMySummary: () => api.get('/progress/my-summary'),
  
  getStudentProgress: (studentId: number) =>
    api.get(`/progress/student/${studentId}`),
  
  enrollStudent: (studentId: number, curriculumId: number) =>
    api.post(`/progress/enroll/${studentId}`, { curriculumId }),
  
  startActivity: (activityId: number) =>
    api.post(`/progress/activity/${activityId}/start`),
  
  completeActivity: (activityId: number, earnedPoints: number, passed: boolean) =>
    api.post(`/progress/activity/${activityId}/complete`, { earnedPoints, passed }),
  
  submitQuiz: (quizId: number, correctAnswers: number, timeSpent: number) =>
    api.post(`/progress/quiz/${quizId}/submit`, { correctAnswers, timeSpent }),
  
  getQuizAttempts: (quizId: number) =>
    api.get(`/progress/quiz/${quizId}/attempts`),
};

export const adminAPI = {
  getSystemStats: () => api.get('/admin-panel/stats/system'),
  
  getCurriculumAnalytics: (curriculumId: number) =>
    api.get(`/admin-panel/analytics/curriculum/${curriculumId}`),
  
  getTopPerformers: (limit: number = 10) =>
    api.get(`/admin-panel/students/top-performers?limit=${limit}`),
  
  getAllStudentsPerformance: () =>
    api.get('/admin-panel/students/performance'),
  
  getStudentPerformance: (studentId: number) =>
    api.get(`/admin-panel/students/${studentId}/performance`),
  
  getCurriculumLeaderboard: (curriculumId: number, limit: number = 10) =>
    api.get(`/admin-panel/curriculum/${curriculumId}/leaderboard?limit=${limit}`),
};

