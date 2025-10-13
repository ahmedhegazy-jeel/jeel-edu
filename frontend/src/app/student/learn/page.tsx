'use client';

import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import Link from 'next/link';
import Navbar from '@/components/Navbar';
import { auth } from '@/lib/auth';
import { curriculumAPI, progressAPI } from '@/lib/api';

export default function StudentLearnPage() {
  const router = useRouter();
  const [loading, setLoading] = useState(true);
  const [curriculums, setCurriculums] = useState<any[]>([]);
  const [myProgress, setMyProgress] = useState<any[]>([]);

  useEffect(() => {
    if (!auth.isAuthenticated()) {
      router.push('/login');
      return;
    }

    if (!auth.hasRole('STUDENT')) {
      router.push('/');
      return;
    }

    loadData();
  }, []);

  const loadData = async () => {
    try {
      const [curriculumsRes, progressRes] = await Promise.all([
        curriculumAPI.getByStatus('ACTIVE'),
        progressAPI.getMyProgress(),
      ]);

      setCurriculums(curriculumsRes.data);
      setMyProgress(progressRes.data);
    } catch (error) {
      console.error('Failed to load data:', error);
    } finally {
      setLoading(false);
    }
  };

  const getProgressForCurriculum = (curriculumId: number) => {
    return myProgress.find((p) => p.curriculum?.id === curriculumId);
  };

  if (loading) {
    return (
      <div>
        <Navbar />
        <div className="flex items-center justify-center min-h-screen">
          <div className="text-xl">Loading your learning adventure...</div>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 via-purple-50 to-pink-50">
      <Navbar />

      <div className="max-w-7xl mx-auto py-6 sm:px-6 lg:px-8">
        {/* Hero Section */}
        <div className="px-4 py-8 sm:px-0 text-center">
          <h1 className="text-5xl font-extrabold bg-gradient-to-r from-primary-600 via-purple-600 to-pink-600 bg-clip-text text-transparent mb-4">
            🌟 Start Your Learning Adventure! 🚀
          </h1>
          <p className="text-xl text-gray-600">
            Choose a curriculum and begin your journey to knowledge!
          </p>
        </div>

        {/* My Curriculums */}
        {myProgress && myProgress.length > 0 && (
          <div className="mt-12 px-4 sm:px-0">
            <h2 className="text-3xl font-bold text-gray-900 mb-6 flex items-center">
              <span className="mr-3">📚</span>
              My Learning Path
            </h2>
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
              {myProgress.map((progress) => (
                <Link
                  key={progress.curriculum.id}
                  href={`/student/learn/curriculum/${progress.curriculum.id}`}
                  className="group block"
                >
                  <div className="bg-white rounded-2xl shadow-lg overflow-hidden hover:shadow-2xl transition-all duration-300 transform hover:-translate-y-2">
                    <div className="h-3 bg-gradient-to-r from-green-400 to-blue-500"></div>
                    <div className="p-6">
                      <div className="flex items-center justify-between mb-4">
                        <span className="text-4xl">{progress.curriculum.icon || '📚'}</span>
                        <span className="text-2xl font-bold text-primary-600">
                          {Math.round(progress.completionPercentage)}%
                        </span>
                      </div>
                      <h3 className="text-xl font-bold text-gray-900 mb-2 group-hover:text-primary-600 transition-colors">
                        {progress.curriculum.name}
                      </h3>
                      <div className="mb-4">
                        <div className="w-full bg-gray-200 rounded-full h-3">
                          <div
                            className="bg-gradient-to-r from-green-400 to-blue-500 h-3 rounded-full transition-all duration-500"
                            style={{ width: `${progress.completionPercentage}%` }}
                          ></div>
                        </div>
                      </div>
                      <div className="flex items-center justify-between text-sm text-gray-600">
                        <span>✅ {progress.completedActivities} done</span>
                        <span>⭐ {progress.totalPoints} points</span>
                      </div>
                      <div className="mt-4 text-center">
                        <span className="inline-block px-4 py-2 bg-gradient-to-r from-primary-500 to-purple-500 text-white rounded-full text-sm font-medium group-hover:from-primary-600 group-hover:to-purple-600 transition-all">
                          Continue Learning →
                        </span>
                      </div>
                    </div>
                  </div>
                </Link>
              ))}
            </div>
          </div>
        )}

        {/* Available Curriculums */}
        <div className="mt-12 px-4 sm:px-0">
          <h2 className="text-3xl font-bold text-gray-900 mb-6 flex items-center">
            <span className="mr-3">🎓</span>
            Explore New Curriculums
          </h2>
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {curriculums.map((curriculum) => {
              const inProgress = getProgressForCurriculum(curriculum.id);
              
              return (
                <Link
                  key={curriculum.id}
                  href={`/student/learn/curriculum/${curriculum.id}`}
                  className="group block"
                >
                  <div className="bg-white rounded-2xl shadow-lg overflow-hidden hover:shadow-2xl transition-all duration-300 transform hover:-translate-y-2">
                    <div className="h-3 bg-gradient-to-r from-yellow-400 via-orange-400 to-red-400"></div>
                    <div className="p-6">
                      <div className="flex items-center justify-between mb-4">
                        <span className="text-4xl">{curriculum.icon || '📚'}</span>
                        {inProgress && (
                          <span className="px-3 py-1 bg-green-100 text-green-800 rounded-full text-xs font-medium">
                            In Progress
                          </span>
                        )}
                      </div>
                      <h3 className="text-xl font-bold text-gray-900 mb-3 group-hover:text-primary-600 transition-colors">
                        {curriculum.name}
                      </h3>
                      {curriculum.unitList && (
                        <div className="flex items-center text-sm text-gray-600 mb-4">
                          <span>📖 {curriculum.unitList.length} Units</span>
                        </div>
                      )}
                      <div className="mt-4 text-center">
                        <span className="inline-block px-4 py-2 bg-gradient-to-r from-yellow-500 to-orange-500 text-white rounded-full text-sm font-medium group-hover:from-yellow-600 group-hover:to-orange-600 transition-all">
                          {inProgress ? 'Continue' : 'Start Learning'} →
                        </span>
                      </div>
                    </div>
                  </div>
                </Link>
              );
            })}
          </div>

          {curriculums.length === 0 && (
            <div className="bg-white rounded-2xl shadow-lg p-12 text-center">
              <span className="text-6xl">📚</span>
              <p className="mt-4 text-xl text-gray-500">No curriculums available yet</p>
              <p className="text-sm text-gray-400 mt-2">
                Check back soon for new learning content!
              </p>
            </div>
          )}
        </div>

        {/* Fun Facts Banner */}
        <div className="mt-12 px-4 sm:px-0">
          <div className="bg-gradient-to-r from-purple-500 to-pink-500 rounded-2xl shadow-xl p-8 text-white text-center">
            <h3 className="text-2xl font-bold mb-2">🎯 Keep Learning!</h3>
            <p className="text-lg">
              Complete activities to earn points and unlock achievements!
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}

