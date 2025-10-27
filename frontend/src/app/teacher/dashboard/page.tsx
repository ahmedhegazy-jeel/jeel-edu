'use client';

import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import Navbar from '@/components/Navbar';
import { auth } from '@/lib/auth';
import { userAPI, curriculumAPI, schoolAPI } from '@/lib/api';
import Link from 'next/link';

export default function TeacherDashboard() {
  const router = useRouter();
  const [loading, setLoading] = useState(true);
  const [stats, setStats] = useState<any>({
    totalStudents: 0,
    totalCurriculums: 0,
    totalSchools: 0,
    activeCurriculums: 0,
  });
  const [curriculums, setCurriculums] = useState<any[]>([]);

  useEffect(() => {
    if (!auth.isAuthenticated()) {
      router.push('/login');
      return;
    }

    if (!auth.hasRole('TEACHER')) {
      router.push('/');
      return;
    }

    loadDashboardData();
  }, []);

  const loadDashboardData = async () => {
    try {
      const [curriculumsRes, usersRes] = await Promise.all([
        curriculumAPI.getAll(),
        userAPI.getAllUsers(),
      ]);

      const allCurriculums = curriculumsRes.data;
      const allUsers = usersRes.data;

      const students = allUsers.filter((u: any) => u.role === 'ROLE_STUDENT');
      const activeCurrs = allCurriculums.filter((c: any) => c.status === 'ACTIVE');

      setStats({
        totalStudents: students.length,
        totalCurriculums: allCurriculums.length,
        totalSchools: 0, // Will be populated if teacher has school info
        activeCurriculums: activeCurrs.length,
      });

      setCurriculums(allCurriculums);
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
            Teacher Dashboard 👨‍🏫
          </h1>
          <p className="mt-2 text-gray-600">Manage your curriculums and track student progress</p>
        </div>

        {/* Stats Grid */}
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
                      Total Curriculums
                    </dt>
                    <dd className="text-2xl font-semibold text-gray-900">
                      {stats.totalCurriculums}
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
                  <span className="text-3xl">✅</span>
                </div>
                <div className="ml-5 w-0 flex-1">
                  <dl>
                    <dt className="text-sm font-medium text-gray-500 truncate">
                      Active Curriculums
                    </dt>
                    <dd className="text-2xl font-semibold text-gray-900">
                      {stats.activeCurriculums}
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
                      Schools
                    </dt>
                    <dd className="text-2xl font-semibold text-gray-900">
                      {stats.totalSchools}
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
            <Link href="/curriculums/create" className="bg-primary-600 text-white px-6 py-4 rounded-lg hover:bg-primary-700 transition-colors text-left block">
              <div className="text-2xl mb-2">➕</div>
              <div className="font-semibold">Create Curriculum</div>
              <div className="text-sm text-primary-100">Add new learning content</div>
            </Link>

            <button className="bg-secondary-600 text-white px-6 py-4 rounded-lg hover:bg-secondary-700 transition-colors text-left">
              <div className="text-2xl mb-2">📊</div>
              <div className="font-semibold">View Reports</div>
              <div className="text-sm text-secondary-100">Student performance</div>
            </button>

            <button className="bg-green-600 text-white px-6 py-4 rounded-lg hover:bg-green-700 transition-colors text-left">
              <div className="text-2xl mb-2">👥</div>
              <div className="font-semibold">Manage Students</div>
              <div className="text-sm text-green-100">Enroll & track progress</div>
            </button>

            <button className="bg-orange-600 text-white px-6 py-4 rounded-lg hover:bg-orange-700 transition-colors text-left">
              <div className="text-2xl mb-2">📝</div>
              <div className="font-semibold">Create Quiz</div>
              <div className="text-sm text-orange-100">Add assessments</div>
            </button>
          </div>
        </div>

        {/* Curriculums List */}
        <div className="mt-8">
          <h2 className="text-2xl font-bold text-gray-900 mb-4">My Curriculums</h2>

          {curriculums && curriculums.length > 0 ? (
            <div className="bg-white shadow overflow-hidden sm:rounded-lg">
              <ul className="divide-y divide-gray-200">
                {curriculums.map((curriculum) => (
                  <li key={curriculum.id} className="px-6 py-4 hover:bg-gray-50">
                    <div className="flex items-center justify-between">
                      <div className="flex items-center">
                        <div className="flex-shrink-0 h-12 w-12 bg-primary-100 rounded-lg flex items-center justify-center">
                          <span className="text-2xl">📚</span>
                        </div>
                        <div className="ml-4">
                          <h3 className="text-lg font-medium text-gray-900">
                            {curriculum.name}
                          </h3>
                          <p className="text-sm text-gray-500">
                            Status: {' '}
                            <span
                              className={`px-2 py-1 rounded-full text-xs ${
                                curriculum.status === 'ACTIVE'
                                  ? 'bg-green-100 text-green-800'
                                  : curriculum.status === 'DRAFT'
                                  ? 'bg-yellow-100 text-yellow-800'
                                  : 'bg-gray-100 text-gray-800'
                              }`}
                            >
                              {curriculum.status}
                            </span>
                          </p>
                        </div>
                      </div>
                      <div className="flex space-x-2">
                        <Link
                          href={`/curriculums/${curriculum.id}/edit`}
                          className="px-4 py-2 bg-primary-600 text-white rounded-lg hover:bg-primary-700 text-sm"
                        >
                          Edit
                        </Link>
                        <Link
                          href={`/curriculums/${curriculum.id}`}
                          className="px-4 py-2 bg-gray-200 text-gray-700 rounded-lg hover:bg-gray-300 text-sm"
                        >
                          View
                        </Link>
                      </div>
                    </div>
                  </li>
                ))}
              </ul>
            </div>
          ) : (
            <div className="bg-white shadow rounded-lg p-8 text-center text-gray-500">
              <p>No curriculums found.</p>
              <p className="text-sm mt-2">Create your first curriculum to get started!</p>
            </div>
          )}
        </div>

        {/* Recent Activity */}
        <div className="mt-8">
          <h2 className="text-2xl font-bold text-gray-900 mb-4">Recent Activity</h2>
          <div className="bg-white shadow rounded-lg p-6">
            <div className="text-center text-gray-500">
              <p>No recent activity</p>
              <p className="text-sm mt-2">Activity will appear here as students interact with your content</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

