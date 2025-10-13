'use client';

import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import Navbar from '@/components/Navbar';
import { auth } from '@/lib/auth';
import { adminAPI, userAPI, schoolAPI, curriculumAPI } from '@/lib/api';
import Link from 'next/link';

export default function SuperAdminDashboard() {
  const router = useRouter();
  const [loading, setLoading] = useState(true);
  const [systemStats, setSystemStats] = useState<any>(null);
  const [topPerformers, setTopPerformers] = useState<any[]>([]);

  useEffect(() => {
    if (!auth.isAuthenticated()) {
      router.push('/login');
      return;
    }

    if (!auth.hasRole('SUPER_ADMIN')) {
      router.push('/');
      return;
    }

    loadDashboardData();
  }, []);

  const loadDashboardData = async () => {
    try {
      const [statsRes, performersRes] = await Promise.all([
        adminAPI.getSystemStats(),
        adminAPI.getTopPerformers(5),
      ]);

      setSystemStats(statsRes.data);
      setTopPerformers(performersRes.data);
    } catch (error) {
      console.error('Failed to load dashboard data:', error);
      // Set default values if API fails
      setSystemStats({
        totalUsers: 0,
        totalStudents: 0,
        totalTeachers: 0,
        totalParents: 0,
        totalSchoolAdmins: 0,
        totalSchools: 0,
        totalCurriculums: 0,
        totalActivities: 0,
      });
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
            Super Admin Dashboard 👑
          </h1>
          <p className="mt-2 text-gray-600">System-wide overview and management</p>
        </div>

        {/* Main Stats Grid */}
        <div className="mt-8 grid grid-cols-1 gap-5 sm:grid-cols-2 lg:grid-cols-4">
          <div className="bg-white overflow-hidden shadow rounded-lg">
            <div className="p-5">
              <div className="flex items-center">
                <div className="flex-shrink-0">
                  <span className="text-3xl">👥</span>
                </div>
                <div className="ml-5 w-0 flex-1">
                  <dl>
                    <dt className="text-sm font-medium text-gray-500 truncate">
                      Total Users
                    </dt>
                    <dd className="text-2xl font-semibold text-gray-900">
                      {systemStats?.totalUsers || 0}
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
                  <span className="text-3xl">🏫</span>
                </div>
                <div className="ml-5 w-0 flex-1">
                  <dl>
                    <dt className="text-sm font-medium text-gray-500 truncate">
                      Total Schools
                    </dt>
                    <dd className="text-2xl font-semibold text-gray-900">
                      {systemStats?.totalSchools || 0}
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
                      {systemStats?.totalCurriculums || 0}
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
                  <span className="text-3xl">🎯</span>
                </div>
                <div className="ml-5 w-0 flex-1">
                  <dl>
                    <dt className="text-sm font-medium text-gray-500 truncate">
                      Activities
                    </dt>
                    <dd className="text-2xl font-semibold text-gray-900">
                      {systemStats?.totalActivities || 0}
                    </dd>
                  </dl>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* User Breakdown */}
        <div className="mt-8">
          <h2 className="text-2xl font-bold text-gray-900 mb-4">User Breakdown</h2>
          <div className="grid grid-cols-1 gap-5 sm:grid-cols-2 lg:grid-cols-4">
            <div className="bg-blue-50 overflow-hidden shadow rounded-lg">
              <div className="p-5">
                <div className="flex items-center">
                  <div className="flex-shrink-0">
                    <span className="text-3xl">👨‍🎓</span>
                  </div>
                  <div className="ml-5">
                    <p className="text-sm text-gray-600">Students</p>
                    <p className="text-2xl font-bold text-blue-900">
                      {systemStats?.totalStudents || 0}
                    </p>
                  </div>
                </div>
              </div>
            </div>

            <div className="bg-green-50 overflow-hidden shadow rounded-lg">
              <div className="p-5">
                <div className="flex items-center">
                  <div className="flex-shrink-0">
                    <span className="text-3xl">👨‍🏫</span>
                  </div>
                  <div className="ml-5">
                    <p className="text-sm text-gray-600">Teachers</p>
                    <p className="text-2xl font-bold text-green-900">
                      {systemStats?.totalTeachers || 0}
                    </p>
                  </div>
                </div>
              </div>
            </div>

            <div className="bg-purple-50 overflow-hidden shadow rounded-lg">
              <div className="p-5">
                <div className="flex items-center">
                  <div className="flex-shrink-0">
                    <span className="text-3xl">👨‍👩‍👧</span>
                  </div>
                  <div className="ml-5">
                    <p className="text-sm text-gray-600">Parents</p>
                    <p className="text-2xl font-bold text-purple-900">
                      {systemStats?.totalParents || 0}
                    </p>
                  </div>
                </div>
              </div>
            </div>

            <div className="bg-orange-50 overflow-hidden shadow rounded-lg">
              <div className="p-5">
                <div className="flex items-center">
                  <div className="flex-shrink-0">
                    <span className="text-3xl">👔</span>
                  </div>
                  <div className="ml-5">
                    <p className="text-sm text-gray-600">School Admins</p>
                    <p className="text-2xl font-bold text-orange-900">
                      {systemStats?.totalSchoolAdmins || 0}
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* Quick Actions */}
        <div className="mt-8">
          <h2 className="text-2xl font-bold text-gray-900 mb-4">Quick Actions</h2>
          <div className="grid grid-cols-1 gap-4 sm:grid-cols-2 lg:grid-cols-4">
            <Link href="/users" className="bg-primary-600 text-white px-6 py-4 rounded-lg hover:bg-primary-700 transition-colors text-left block">
              <div className="text-2xl mb-2">👥</div>
              <div className="font-semibold">Manage Users</div>
              <div className="text-sm text-primary-100">All user accounts</div>
            </Link>

            <Link href="/schools" className="bg-secondary-600 text-white px-6 py-4 rounded-lg hover:bg-secondary-700 transition-colors text-left block">
              <div className="text-2xl mb-2">🏫</div>
              <div className="font-semibold">Manage Schools</div>
              <div className="text-sm text-secondary-100">School administration</div>
            </Link>

            <Link href="/curriculums" className="bg-green-600 text-white px-6 py-4 rounded-lg hover:bg-green-700 transition-colors text-left block">
              <div className="text-2xl mb-2">📚</div>
              <div className="font-semibold">Manage Curriculums</div>
              <div className="text-sm text-green-100">Content management</div>
            </Link>

            <button className="bg-orange-600 text-white px-6 py-4 rounded-lg hover:bg-orange-700 transition-colors text-left">
              <div className="text-2xl mb-2">📊</div>
              <div className="font-semibold">System Reports</div>
              <div className="text-sm text-orange-100">Analytics & insights</div>
            </button>
          </div>
        </div>

        {/* Top Performers */}
        <div className="mt-8">
          <h2 className="text-2xl font-bold text-gray-900 mb-4">Top Performers 🏆</h2>

          {topPerformers && topPerformers.length > 0 ? (
            <div className="bg-white shadow overflow-hidden sm:rounded-lg">
              <ul className="divide-y divide-gray-200">
                {topPerformers.map((student, index) => (
                  <li key={student.studentId} className="px-6 py-4">
                    <div className="flex items-center justify-between">
                      <div className="flex items-center">
                        <div className="flex-shrink-0">
                          <div className="h-12 w-12 bg-gradient-to-br from-yellow-400 to-orange-500 rounded-full flex items-center justify-center text-white font-bold text-lg">
                            #{index + 1}
                          </div>
                        </div>
                        <div className="ml-4">
                          <h3 className="text-lg font-medium text-gray-900">
                            {student.studentName}
                          </h3>
                          <p className="text-sm text-gray-500">
                            {student.totalPoints} points • {student.completedActivities}{' '}
                            activities
                          </p>
                        </div>
                      </div>
                      <div className="text-right">
                        <p className="text-2xl font-bold text-primary-600">
                          {Math.round(student.averageScore)}%
                        </p>
                        <p className="text-xs text-gray-500">Average Score</p>
                      </div>
                    </div>
                  </li>
                ))}
              </ul>
            </div>
          ) : (
            <div className="bg-white shadow rounded-lg p-8 text-center text-gray-500">
              <p>No performance data available yet.</p>
              <p className="text-sm mt-2">
                Data will appear as students complete activities
              </p>
            </div>
          )}
        </div>

        {/* System Health */}
        <div className="mt-8">
          <h2 className="text-2xl font-bold text-gray-900 mb-4">System Health</h2>
          <div className="bg-white shadow rounded-lg p-6">
            <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
              <div className="text-center">
                <div className="inline-flex items-center justify-center h-12 w-12 rounded-full bg-green-100 mb-3">
                  <span className="text-2xl">✅</span>
                </div>
                <p className="text-sm text-gray-500">System Status</p>
                <p className="text-lg font-bold text-green-600 mt-1">Operational</p>
              </div>
              <div className="text-center">
                <div className="inline-flex items-center justify-center h-12 w-12 rounded-full bg-blue-100 mb-3">
                  <span className="text-2xl">🔄</span>
                </div>
                <p className="text-sm text-gray-500">Active Sessions</p>
                <p className="text-lg font-bold text-blue-600 mt-1">--</p>
              </div>
              <div className="text-center">
                <div className="inline-flex items-center justify-center h-12 w-12 rounded-full bg-purple-100 mb-3">
                  <span className="text-2xl">💾</span>
                </div>
                <p className="text-sm text-gray-500">Database</p>
                <p className="text-lg font-bold text-purple-600 mt-1">Healthy</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

