'use client';

import { useState } from 'react';
import { useRouter } from 'next/navigation';
import Link from 'next/link';
import { auth } from '@/lib/auth';

export default function LoginPage() {
  const router = useRouter();
  const [formData, setFormData] = useState({
    usernameOrEmail: '',
    password: '',
  });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    try {
      const response = await auth.login(formData.usernameOrEmail, formData.password);
      
      // Redirect based on role
      const role = response.role;
      if (role.includes('STUDENT')) {
        router.push('/student/dashboard');
      } else if (role.includes('TEACHER')) {
        router.push('/teacher/dashboard');
      } else if (role.includes('PARENT')) {
        router.push('/parent/dashboard');
      } else if (role.includes('SCHOOL_ADMIN')) {
        router.push('/school-admin/dashboard');
      } else if (role.includes('SUPER_ADMIN')) {
        router.push('/super-admin/dashboard');
      } else {
        router.push('/dashboard');
      }
    } catch (err: any) {
      setError(err.response?.data?.message || 'Login failed. Please check your credentials.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className=" min-h-screen flex items-center justify-center bg-gradient-to-br from-primary-50 to-secondary-50 py-12 px-4 sm:px-6 lg:px-8">
      <div className=" bg-purple-700 max-w-md w-full space-y-8 p-10 rounded-xl shadow-lg">
        <div>
          <h2 className="mt-6 text-center text-3xl font-extrabold text-gray-900">
            <span className="text-xl sm:text-2xl font-bold bg-gradient-to-r from-green-600 to-purple-200 bg-clip-text text-transparent">
              JeelEducation
            </span>              
          </h2>
          <p className="mt-2 text-center text-sm text-white-600">Sign in to your account</p>
        </div>

        <form className="mt-8 space-y-6" onSubmit={handleSubmit}>
          {error && (
            <div className="bg-red-50 border border-red-400 text-red-700 px-4 py-3 rounded relative">
              {error}
            </div>
          )}

          <div className="rounded-md shadow-sm space-y-4">
            <div>
              <label htmlFor="usernameOrEmail" className="block text-sm font-bold font-medium  text-white  mb-1">
                UserName/Email
              </label>
              <input
                id="usernameOrEmail"
                name="usernameOrEmail"
                type="text"
                required
                className="appearance-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 rounded-lg focus:outline-none focus:ring-primary-500 focus:border-primary-500 focus:z-10 sm:text-sm"
                placeholder="Enter username or email"
                value={formData.usernameOrEmail}
                onChange={(e) =>
                  setFormData({ ...formData, usernameOrEmail: e.target.value })
                }
              />
            </div>

            <div>
              <label htmlFor="password" className="block text-sm font-bold font-medium  text-white mb-1">
                Password
              </label>
              <input
                id="password"
                name="password"
                type="password"
                required
                className="appearance-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 rounded-lg focus:outline-none focus:ring-primary-500 focus:border-primary-500 focus:z-10 sm:text-sm"
                placeholder="Enter password"
                value={formData.password}
                onChange={(e) =>
                  setFormData({ ...formData, password: e.target.value })
                }
              />
            </div>
          </div>

          <div className="flex items-center justify-between">
            <div className="text-sm">
              <Link
                href="/forgot-password"
                className="font-medium font-bold text-green-300 hover:text-green-100">
                Forgot your password?
              </Link>
            </div>
          </div>

          <div>
            <button
              type="submit"
              disabled={loading}
              className="group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm 
              font-medium rounded-lg text-white bg-green-600 hover:bg-green-700 focus:outline-none 
              focus:ring-2 focus:ring-offset-2 focus:ring-primary-500 disabled:bg-gray-400 
              disabled:cursor-not-allowed"
            >
              {loading ? 'Signing in...' : 'Sign in'}
            </button>
          </div>

          <div className="text-center">
            <p className="text-sm text-white font-bold">
              Don't have an account?{' '}
              <Link href="/register" className="font-medium text-green-300 hover:text-green-100">
                Register here
              </Link>
            </p>
          </div>
        </form>
      </div>
    </div>
  );
}

