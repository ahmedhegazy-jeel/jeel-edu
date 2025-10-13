'use client';

import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import Link from 'next/link';
import Navbar from '@/components/Navbar';
import { auth } from '@/lib/auth';
import { unitAPI, lessonAPI } from '@/lib/api';

export default function StudentUnitPage({ params }: { params: { id: string } }) {
  const router = useRouter();
  const [loading, setLoading] = useState(true);
  const [unit, setUnit] = useState<any>(null);
  const [lessons, setLessons] = useState<any[]>([]);

  useEffect(() => {
    if (!auth.isAuthenticated()) {
      router.push('/login');
      return;
    }

    loadData();
  }, [params.id]);

  const loadData = async () => {
    try {
      const [unitRes, lessonsRes] = await Promise.all([
        unitAPI.getById(parseInt(params.id)),
        lessonAPI.getByUnit(parseInt(params.id)),
      ]);

      setUnit(unitRes.data);
      setLessons(lessonsRes.data.filter((l: any) => l.status === 'ACTIVE'));
    } catch (error) {
      console.error('Failed to load data:', error);
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

  if (!unit) {
    return (
      <div>
        <Navbar />
        <div className="flex items-center justify-center min-h-screen">
          <div className="text-center">
            <p className="text-xl text-gray-500">Unit not found</p>
            <Link href="/student/learn" className="mt-4 inline-block text-primary-600">
              ← Back to Learning
            </Link>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 via-purple-50 to-pink-50">
      <Navbar />

      <div className="max-w-7xl mx-auto py-6 sm:px-6 lg:px-8">
        {/* Header */}
        <div className="px-4 py-6 sm:px-0">
          <Link
            href={`/student/learn/curriculum/${unit.curriculum?.id}`}
            className="inline-flex items-center text-primary-600 hover:text-primary-700 mb-4"
          >
            ← Back to Curriculum
          </Link>

          <div className="bg-white rounded-2xl shadow-xl p-8">
            <div className="flex items-center">
              <div className="w-20 h-20 rounded-full bg-gradient-to-br from-blue-400 to-purple-600 flex items-center justify-center text-white font-bold text-3xl shadow-lg mr-6">
                {unit.icon || '📖'}
              </div>
              <div className="flex-1">
                <h1 className="text-4xl font-extrabold text-gray-900 mb-2">
                  {unit.name}
                </h1>
                <div className="flex items-center space-x-4 text-gray-600">
                  <span>📝 {lessons.length} Lessons</span>
                  {unit.audioName && <span>🔊 Has Audio Guide</span>}
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* Lessons */}
        <div className="mt-12 px-4 sm:px-0">
          <h2 className="text-3xl font-bold text-gray-900 mb-6 flex items-center">
            <span className="mr-3">📝</span>
            Lessons
          </h2>

          {lessons && lessons.length > 0 ? (
            <div className="space-y-4">
              {lessons.map((lesson, index) => (
                <Link
                  key={lesson.id}
                  href={`/student/learn/lesson/${lesson.id}`}
                  className="group block"
                >
                  <div className="bg-white rounded-xl shadow-lg overflow-hidden hover:shadow-2xl transition-all duration-300 transform hover:scale-[1.02]">
                    <div className="flex items-center p-6">
                      {/* Lesson Number */}
                      <div className={`flex-shrink-0 w-16 h-16 rounded-full bg-gradient-to-br ${
                        index % 4 === 0 ? 'from-blue-400 to-blue-600' :
                        index % 4 === 1 ? 'from-purple-400 to-purple-600' :
                        index % 4 === 2 ? 'from-pink-400 to-pink-600' :
                        'from-green-400 to-green-600'
                      } flex items-center justify-center text-white font-bold text-2xl shadow-lg mr-6`}>
                        {index + 1}
                      </div>

                      {/* Lesson Info */}
                      <div className="flex-1">
                        <div className="flex items-center mb-2">
                          <span className="text-3xl mr-3">{lesson.icon || '📝'}</span>
                          <h3 className="text-2xl font-bold text-gray-900 group-hover:text-primary-600 transition-colors">
                            {lesson.name}
                          </h3>
                        </div>
                        <div className="flex items-center space-x-4 text-sm text-gray-600">
                          <span>🎯 {lesson.activityList?.length || 0} Activities</span>
                          {lesson.audioName && <span>🔊 Audio Available</span>}
                        </div>
                      </div>

                      {/* Arrow */}
                      <div className="flex-shrink-0 ml-4">
                        <div className="w-12 h-12 rounded-full bg-gradient-to-r from-primary-500 to-purple-500 flex items-center justify-center text-white text-2xl group-hover:from-primary-600 group-hover:to-purple-600 transition-all">
                          →
                        </div>
                      </div>
                    </div>
                  </div>
                </Link>
              ))}
            </div>
          ) : (
            <div className="bg-white rounded-2xl shadow-lg p-12 text-center">
              <span className="text-6xl">📝</span>
              <p className="mt-4 text-xl text-gray-500">No lessons available yet</p>
              <p className="text-sm text-gray-400 mt-2">
                Content is being prepared!
              </p>
            </div>
          )}
        </div>

        {/* Encouragement Banner */}
        <div className="mt-12 px-4 sm:px-0">
          <div className="bg-gradient-to-r from-purple-500 to-pink-500 rounded-2xl shadow-xl p-8 text-white text-center">
            <h3 className="text-2xl font-bold mb-2">💪 You Can Do It!</h3>
            <p className="text-lg">
              Complete each lesson to master this unit and earn amazing rewards!
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}

