import Cookies from 'js-cookie';
import { authAPI } from './api';

export interface User {
  id: number;
  username: string;
  email: string;
  firstName: string;
  lastName: string;
  role: string;
}

export interface AuthResponse {
  accessToken: string;
  refreshToken: string;
  id: number;
  username: string;
  email: string;
  firstName: string;
  lastName: string;
  role: string;
}

export const auth = {
  /**
   * Login user and store tokens
   */
  login: async (usernameOrEmail: string, password: string): Promise<AuthResponse> => {
    const response = await authAPI.login(usernameOrEmail, password);
    const data: AuthResponse = response.data;

    // Store tokens in cookies
    Cookies.set('accessToken', data.accessToken, { expires: 1 }); // 1 day
    Cookies.set('refreshToken', data.refreshToken, { expires: 7 }); // 7 days
    Cookies.set('user', JSON.stringify({
      id: data.id,
      username: data.username,
      email: data.email,
      firstName: data.firstName,
      lastName: data.lastName,
      role: data.role,
    }), { expires: 1 });

    return data;
  },

  /**
   * Logout user and clear tokens
   */
  logout: async (): Promise<void> => {
    try {
      await authAPI.logout();
    } catch (error) {
      console.error('Logout error:', error);
    } finally {
      Cookies.remove('accessToken');
      Cookies.remove('refreshToken');
      Cookies.remove('user');
    }
  },

  /**
   * Register new user
   */
  register: async (
    data: any,
    role: 'student' | 'parent' | 'teacher'
  ): Promise<any> => {
    const response = await authAPI.register(data, role);
    return response.data;
  },

  /**
   * Get current user from cookies
   */
  getCurrentUser: (): User | null => {
    const userStr = Cookies.get('user');
    if (!userStr) return null;
    
    try {
      return JSON.parse(userStr);
    } catch {
      return null;
    }
  },

  /**
   * Check if user is authenticated
   */
  isAuthenticated: (): boolean => {
    return !!Cookies.get('accessToken');
  },

  /**
   * Check if user has specific role
   */
  hasRole: (role: string): boolean => {
    const user = auth.getCurrentUser();
    return user?.role === role || user?.role === `ROLE_${role}`;
  },

  /**
   * Check if user is admin (SUPER_ADMIN or SCHOOL_ADMIN)
   */
  isAdmin: (): boolean => {
    const user = auth.getCurrentUser();
    return user?.role === 'ROLE_SUPER_ADMIN' || user?.role === 'ROLE_SCHOOL_ADMIN';
  },

  /**
   * Get access token
   */
  getAccessToken: (): string | undefined => {
    return Cookies.get('accessToken');
  },
};

