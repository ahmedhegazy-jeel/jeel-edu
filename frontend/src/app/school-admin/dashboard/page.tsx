'use client';

import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import Navbar from '@/components/Navbar';
import { auth } from '@/lib/auth';
import { userAPI, schoolAPI, curriculumAPI } from '@/lib/api';

export default function SchoolAdminDashboard() {
  const router = useRouter();
  const [loading, setLoading] = useState(true);
  const [stats, setStats] = useState({
    totalTeachers: 0,
    totalStudents: 0,
    totalCurriculums: 0,
    totalSchools: 0,
  });
  const [schools, setSchools] = useState<any[]>([]);

  useEffect(() => {
    if (!auth.isAuthenticated()) {
      router.push('/login');
      return;
    }

    if (!auth.hasRole('SCHOOL_ADMIN')) {
      router.push('/');
      return;
    }

    loadDashboardData();
  }, []);

  const loadDashboardData = async () => {
    try {
      const [usersRes, schoolsRes, curriculumsRes] = await Promise.all([
        userAPI.getAllUsers(),
        schoolAPI.getAll(),
        curriculumAPI.getAll(),
      ]);

      const allUsers = usersRes.data;
      const teachers = allUsers.filter((u: any) => u.role === 'ROLE_TEACHER');
      const students = allUsers.filter((u: any) => u.role === 'ROLE_STUDENT');

      setStats({
        totalTeachers: teachers.length,
        totalStudents: students.length,
        totalCurriculums: curriculumsRes.data.length,
        totalSchools: schoolsRes.data.length,
      });

      setSchools(schoolsRes.data);
    } catch (error) {
      console.error('Failed to load dashboard data:', error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <div>
        <Navbar />
        <div className="flex items-center justify-center min-h-screen">
          <div className="text-xl">Loading...</div>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50">
      <Navbar />

      <div className="max-w-7xl mx-auto py-6 sm:px-6 lg:px-8">
        {/* Header */}
        <div className="px-4 py-6 sm:px-0">
          <h1 className="text-3xl font-bold text-gray-900">
            School Admin Dashboard 🏫
          </h1>
          <p className="mt-2 text-gray-600">Manage your school and monitor performance</p>
        </div>

        {/* Stats Grid */}
        <div className="mt-8 grid grid-cols-1 gap-5 sm:grid-cols-2 lg:grid-cols-4">
          <div className="bg-white overflow-hidden shadow rounded-lg">
            <div className="p-5">
              <div className="flex items-center">
                <div className="flex-shrink-0">
                  <span className="text-3xl">🏫</span>
                </div>
                <div className="ml-5 w-0 flex-1">
                  <dl>
                    <dt className="text-sm font-medium text-gray-500 truncate">
                      Total Schools
                    </dt>
                    <dd className="text-2xl font-semibold text-gray-900">
                      {stats.totalSchools}
                    </dd>
                  </dl>
                </div>
              </div>
            </div>
          </div>

          <div className="bg-white overflow-hidden shadow rounded-lg">
            <div className="p-5">
              <div className="flex items-center">
                <div className="flex-shrink-0">
                  <span className="text-3xl">👨‍🏫</span>
                </div>
                <div className="ml-5 w-0 flex-1">
                  <dl>
                    <dt className="text-sm font-medium text-gray-500 truncate">
                      Total Teachers
                    </dt>
                    <dd className="text-2xl font-semibold text-gray-900">
                      {stats.totalTeachers}
                    </dd>
                  </dl>
                </div>
              </div>
            </div>
          </div>

          <div className="bg-white overflow-hidden shadow rounded-lg">
            <div className="p-5">
              <div className="flex items-center">
                <div className="flex-shrink-0">
                  <span className="text-3xl">👥</span>
                </div>
                <div className="ml-5 w-0 flex-1">
                  <dl>
                    <dt className="text-sm font-medium text-gray-500 truncate">
                      Total Students
                    </dt>
                    <dd className="text-2xl font-semibold text-gray-900">
                      {stats.totalStudents}
                    </dd>
                  </dl>
                </div>
              </div>
            </div>
          </div>

          <div className="bg-white overflow-hidden shadow rounded-lg">
            <div className="p-5">
              <div className="flex items-center">
                <div className="flex-shrink-0">
                  <span className="text-3xl">📚</span>
                </div>
                <div className="ml-5 w-0 flex-1">
                  <dl>
                    <dt className="text-sm font-medium text-gray-500 truncate">
                      Curriculums
                    </dt>
                    <dd className="text-2xl font-semibold text-gray-900">
                      {stats.totalCurriculums}
                    </dd>
                  </dl>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* Quick Actions */}
        <div className="mt-8">
          <h2 className="text-2xl font-bold text-gray-900 mb-4">Quick Actions</h2>
          <div className="grid grid-cols-1 gap-4 sm:grid-cols-2 lg:grid-cols-4">
            <Link href="/schools/create" className="bg-primary-600 text-white px-6 py-4 rounded-lg hover:bg-primary-700 transition-colors text-left block">
              <div className="text-2xl mb-2">➕</div>
              <div className="font-semibold">Add School</div>
              <div className="text-sm text-primary-100">Register new school</div>
            </Link>

            <button className="bg-secondary-600 text-white px-6 py-4 rounded-lg hover:bg-secondary-700 transition-colors text-left">
              <div className="text-2xl mb-2">👨‍🏫</div>
              <div className="font-semibold">Manage Teachers</div>
              <div className="text-sm text-secondary-100">Add or remove teachers</div>
            </button>

            <button className="bg-green-600 text-white px-6 py-4 rounded-lg hover:bg-green-700 transition-colors text-left">
              <div className="text-2xl mb-2">👥</div>
              <div className="font-semibold">Manage Students</div>
              <div className="text-sm text-green-100">Enroll students</div>
            </button>

            <button className="bg-orange-600 text-white px-6 py-4 rounded-lg hover:bg-orange-700 transition-colors text-left">
              <div className="text-2xl mb-2">📊</div>
              <div className="font-semibold">View Reports</div>
              <div className="text-sm text-orange-100">School analytics</div>
            </button>
          </div>
        </div>

        {/* Schools List */}
        <div className="mt-8">
          <h2 className="text-2xl font-bold text-gray-900 mb-4">My Schools</h2>

          {schools && schools.length > 0 ? (
            <div className="bg-white shadow overflow-hidden sm:rounded-lg">
              <ul className="divide-y divide-gray-200">
                {schools.map((school) => (
                  <li key={school.id} className="px-6 py-4 hover:bg-gray-50">
                    <div className="flex items-center justify-between">
                      <div className="flex items-center">
                        <div className="flex-shrink-0 h-12 w-12 bg-primary-100 rounded-lg flex items-center justify-center">
                          <span className="text-2xl">🏫</span>
                        </div>
                        <div className="ml-4">
                          <h3 className="text-lg font-medium text-gray-900">
                            {school.name}
                          </h3>
                          <p className="text-sm text-gray-500">
                            {school.city}, {school.country}
                          </p>
                          <p className="text-xs text-gray-400 mt-1">
                            Capacity: {school.currentStudents || 0} / {school.maxCapacity || 'N/A'}
                          </p>
                        </div>
                      </div>
                      <div className="flex space-x-2">
                        <button className="px-4 py-2 bg-primary-600 text-white rounded-lg hover:bg-primary-700 text-sm">
                          Manage
                        </button>
                        <button className="px-4 py-2 bg-gray-200 text-gray-700 rounded-lg hover:bg-gray-300 text-sm">
                          View Details
                        </button>
                      </div>
                    </div>
                  </li>
                ))}
              </ul>
            </div>
          ) : (
            <div className="bg-white shadow rounded-lg p-8 text-center text-gray-500">
              <p>No schools found.</p>
              <p className="text-sm mt-2">Register your first school to get started!</p>
              <button className="mt-4 px-6 py-2 bg-primary-600 text-white rounded-lg hover:bg-primary-700">
                Add School
              </button>
            </div>
          )}
        </div>

        {/* Performance Overview */}
        <div className="mt-8">
          <h2 className="text-2xl font-bold text-gray-900 mb-4">Performance Overview</h2>
          <div className="bg-white shadow rounded-lg p-6">
            <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
              <div className="text-center">
                <p className="text-sm text-gray-500">Average Student Score</p>
                <p className="text-3xl font-bold text-primary-600 mt-2">--</p>
                <p className="text-xs text-gray-400 mt-1">No data available</p>
              </div>
              <div className="text-center">
                <p className="text-sm text-gray-500">Course Completion Rate</p>
                <p className="text-3xl font-bold text-green-600 mt-2">--</p>
                <p className="text-xs text-gray-400 mt-1">No data available</p>
              </div>
              <div className="text-center">
                <p className="text-sm text-gray-500">Active Students</p>
                <p className="text-3xl font-bold text-secondary-600 mt-2">--</p>
                <p className="text-xs text-gray-400 mt-1">No data available</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

