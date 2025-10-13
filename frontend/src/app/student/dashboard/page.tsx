'use client';

import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import Navbar from '@/components/Navbar';
import { auth } from '@/lib/auth';
import { progressAPI } from '@/lib/api';

export default function StudentDashboard() {
  const router = useRouter();
  const [loading, setLoading] = useState(true);
  const [summary, setSummary] = useState<any>(null);

  useEffect(() => {
    if (!auth.isAuthenticated()) {
      router.push('/login');
      return;
    }

    if (!auth.hasRole('STUDENT')) {
      router.push('/');
      return;
    }

    loadProgressSummary();
  }, []);

  const loadProgressSummary = async () => {
    try {
      const response = await progressAPI.getMySummary();
      setSummary(response.data);
    } catch (error) {
      console.error('Failed to load progress:', error);
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
          <div className="flex items-center justify-between">
            <div>
              <h1 className="text-3xl font-bold text-gray-900">
                Welcome, {summary?.studentName || 'Student'}! 👋
              </h1>
              <p className="mt-2 text-gray-600">Here's your learning progress</p>
            </div>
            <Link
              href="/student/learn"
              className="px-6 py-3 bg-gradient-to-r from-green-500 to-blue-500 text-white rounded-full font-bold hover:from-green-600 hover:to-blue-600 transition-all transform hover:scale-105 shadow-lg"
            >
              🚀 Start Learning
            </Link>
          </div>
        </div>

        {/* Stats Grid */}
        <div className="mt-8 grid grid-cols-1 gap-5 sm:grid-cols-2 lg:grid-cols-4">
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
                      {summary?.totalCurriculums || 0}
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
                      Activities Completed
                    </dt>
                    <dd className="text-2xl font-semibold text-gray-900">
                      {summary?.totalActivitiesCompleted || 0}
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
                    <dt className="text-sm font-medium text-gray-500 truncate">Points Earned</dt>
                    <dd className="text-2xl font-semibold text-gray-900">
                      {summary?.totalPointsEarned || 0}
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
                      Average Score
                    </dt>
                    <dd className="text-2xl font-semibold text-gray-900">
                      {summary?.averageScore ? `${Math.round(summary.averageScore)}%` : 'N/A'}
                    </dd>
                  </dl>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* Progress Section */}
        <div className="mt-8">
          <h2 className="text-2xl font-bold text-gray-900 mb-4">My Curriculums</h2>
          
          {summary?.curriculumProgress && summary.curriculumProgress.length > 0 ? (
            <div className="bg-white shadow overflow-hidden sm:rounded-lg">
              {summary.curriculumProgress.map((progress: any) => (
                <div key={progress.curriculumId} className="border-b border-gray-200 last:border-0">
                  <div className="px-6 py-4">
                    <div className="flex items-center justify-between">
                      <div className="flex-1">
                        <h3 className="text-lg font-medium text-gray-900">
                          {progress.curriculumName}
                        </h3>
                        <p className="text-sm text-gray-500 mt-1">
                          {progress.completedActivities} / {progress.totalActivities} activities
                          completed
                        </p>
                      </div>
                      <div className="text-right">
                        <span className="text-2xl font-bold text-primary-600">
                          {Math.round(progress.completionPercentage)}%
                        </span>
                      </div>
                    </div>
                    <div className="mt-3">
                      <div className="w-full bg-gray-200 rounded-full h-2.5">
                        <div
                          className="bg-primary-600 h-2.5 rounded-full transition-all"
                          style={{ width: `${progress.completionPercentage}%` }}
                        ></div>
                      </div>
                    </div>
                    <div className="mt-4">
                      <Link
                        href={`/student/learn/curriculum/${progress.curriculumId}`}
                        className="inline-block px-4 py-2 bg-gradient-to-r from-primary-500 to-purple-500 text-white rounded-full text-sm font-medium hover:from-primary-600 hover:to-purple-600"
                      >
                        Continue Learning →
                      </Link>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          ) : (
            <div className="bg-white shadow rounded-lg p-8 text-center text-gray-500">
              <p>No curriculums enrolled yet.</p>
              <p className="text-sm mt-2">Contact your teacher to get started!</p>
            </div>
          )}
        </div>

        {/* Quiz Stats */}
        {summary && summary.totalQuizzesTaken > 0 && (
          <div className="mt-8">
            <h2 className="text-2xl font-bold text-gray-900 mb-4">Quiz Performance</h2>
            <div className="bg-white shadow rounded-lg p-6">
              <div className="grid grid-cols-2 gap-4">
                <div>
                  <p className="text-sm text-gray-500">Total Quizzes Taken</p>
                  <p className="text-xl font-bold text-gray-900">{summary.totalQuizzesTaken}</p>
                </div>
                <div>
                  <p className="text-sm text-gray-500">Quizzes Passed</p>
                  <p className="text-xl font-bold text-green-600">{summary.totalQuizzesPassed}</p>
                </div>
              </div>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}

