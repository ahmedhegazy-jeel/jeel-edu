'use client';

import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import Navbar from '@/components/Navbar';
import { auth } from '@/lib/auth';
import { userAPI } from '@/lib/api';

export default function ParentDashboard() {
  const router = useRouter();
  const [loading, setLoading] = useState(true);
  const [children, setChildren] = useState<any[]>([]);
  const [stats, setStats] = useState({
    totalChildren: 0,
    totalProgress: 0,
    averageScore: 0,
  });

  useEffect(() => {
    if (!auth.isAuthenticated()) {
      router.push('/login');
      return;
    }

    if (!auth.hasRole('PARENT')) {
      router.push('/');
      return;
    }

    loadDashboardData();
  }, []);

  const loadDashboardData = async () => {
    try {
      // In a real implementation, we would fetch the parent's children
      // For now, we'll show placeholder data
      setStats({
        totalChildren: 0,
        totalProgress: 0,
        averageScore: 0,
      });
      setChildren([]);
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
            Parent Dashboard 👨‍👩‍👧‍👦
          </h1>
          <p className="mt-2 text-gray-600">Track your children's learning progress</p>
        </div>

        {/* Stats Grid */}
        <div className="mt-8 grid grid-cols-1 gap-5 sm:grid-cols-3">
          <div className="bg-white overflow-hidden shadow rounded-lg">
            <div className="p-5">
              <div className="flex items-center">
                <div className="flex-shrink-0">
                  <span className="text-3xl">👶</span>
                </div>
                <div className="ml-5 w-0 flex-1">
                  <dl>
                    <dt className="text-sm font-medium text-gray-500 truncate">
                      Total Children
                    </dt>
                    <dd className="text-2xl font-semibold text-gray-900">
                      {stats.totalChildren}
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
                  <span className="text-3xl">📊</span>
                </div>
                <div className="ml-5 w-0 flex-1">
                  <dl>
                    <dt className="text-sm font-medium text-gray-500 truncate">
                      Average Progress
                    </dt>
                    <dd className="text-2xl font-semibold text-gray-900">
                      {stats.totalProgress}%
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
                  <span className="text-3xl">⭐</span>
                </div>
                <div className="ml-5 w-0 flex-1">
                  <dl>
                    <dt className="text-sm font-medium text-gray-500 truncate">
                      Average Score
                    </dt>
                    <dd className="text-2xl font-semibold text-gray-900">
                      {stats.averageScore}%
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
          <div className="grid grid-cols-1 gap-4 sm:grid-cols-3">
            <button className="bg-primary-600 text-white px-6 py-4 rounded-lg hover:bg-primary-700 transition-colors text-left">
              <div className="text-2xl mb-2">➕</div>
              <div className="font-semibold">Add Child</div>
              <div className="text-sm text-primary-100">Link your child's account</div>
            </button>

            <button className="bg-secondary-600 text-white px-6 py-4 rounded-lg hover:bg-secondary-700 transition-colors text-left">
              <div className="text-2xl mb-2">📊</div>
              <div className="font-semibold">View Reports</div>
              <div className="text-sm text-secondary-100">Detailed progress reports</div>
            </button>

            <button className="bg-green-600 text-white px-6 py-4 rounded-lg hover:bg-green-700 transition-colors text-left">
              <div className="text-2xl mb-2">💬</div>
              <div className="font-semibold">Contact Teacher</div>
              <div className="text-sm text-green-100">Get in touch</div>
            </button>
          </div>
        </div>

        {/* Children Progress */}
        <div className="mt-8">
          <h2 className="text-2xl font-bold text-gray-900 mb-4">My Children</h2>

          {children && children.length > 0 ? (
            <div className="grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-3">
              {children.map((child) => (
                <div key={child.id} className="bg-white shadow rounded-lg p-6">
                  <div className="flex items-center mb-4">
                    <div className="h-12 w-12 bg-primary-100 rounded-full flex items-center justify-center">
                      <span className="text-2xl">👶</span>
                    </div>
                    <div className="ml-4">
                      <h3 className="text-lg font-medium text-gray-900">
                        {child.name}
                      </h3>
                      <p className="text-sm text-gray-500">Grade {child.grade}</p>
                    </div>
                  </div>

                  <div className="space-y-3">
                    <div>
                      <div className="flex justify-between text-sm mb-1">
                        <span className="text-gray-600">Overall Progress</span>
                        <span className="font-medium">{child.progress}%</span>
                      </div>
                      <div className="w-full bg-gray-200 rounded-full h-2">
                        <div
                          className="bg-primary-600 h-2 rounded-full"
                          style={{ width: `${child.progress}%` }}
                        ></div>
                      </div>
                    </div>

                    <div className="pt-3 border-t">
                      <button className="w-full px-4 py-2 bg-primary-600 text-white rounded-lg hover:bg-primary-700 text-sm">
                        View Details
                      </button>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          ) : (
            <div className="bg-white shadow rounded-lg p-8 text-center text-gray-500">
              <p>No children linked yet.</p>
              <p className="text-sm mt-2">Add your child's account to start tracking their progress!</p>
              <button className="mt-4 px-6 py-2 bg-primary-600 text-white rounded-lg hover:bg-primary-700">
                Add Child
              </button>
            </div>
          )}
        </div>

        {/* Recent Activity */}
        <div className="mt-8">
          <h2 className="text-2xl font-bold text-gray-900 mb-4">Recent Activity</h2>
          <div className="bg-white shadow rounded-lg p-6">
            <div className="text-center text-gray-500">
              <p>No recent activity</p>
              <p className="text-sm mt-2">
                Your children's learning activities will appear here
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

