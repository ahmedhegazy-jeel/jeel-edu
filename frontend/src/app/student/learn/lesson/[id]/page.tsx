'use client';

import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import Link from 'next/link';
import Navbar from '@/components/Navbar';
import { auth } from '@/lib/auth';
import { lessonAPI, activityAPI } from '@/lib/api';

export default function StudentLessonPage({ params }: { params: { id: string } }) {
  const router = useRouter();
  const [loading, setLoading] = useState(true);
  const [lesson, setLesson] = useState<any>(null);
  const [activities, setActivities] = useState<any[]>([]);

  useEffect(() => {
    if (!auth.isAuthenticated()) {
      router.push('/login');
      return;
    }

    loadData();
  }, [params.id]);

  const loadData = async () => {
    try {
      const [lessonRes, activitiesRes] = await Promise.all([
        lessonAPI.getById(parseInt(params.id)),
        activityAPI.getByLesson(parseInt(params.id)),
      ]);

      setLesson(lessonRes.data);
      setActivities(activitiesRes.data.filter((a: any) => a.status === 'ACTIVE'));
    } catch (error) {
      console.error('Failed to load data:', error);
    } finally {
      setLoading(false);
    }
  };

  const getActivityIcon = (type: string) => {
    const icons: { [key: string]: string } = {
      TEXT: '📄',
      PDF: '📑',
      AUDIO: '🔊',
      BOOK: '📖',
      VIDEO: '🎥',
      INTERACTIVE: '🎮',
      QUIZ: '📝',
      HOMEWORK: '📋',
    };
    return icons[type] || '🎯';
  };

  const getActivityGradient = (index: number) => {
    const gradients = [
      'from-blue-400 to-blue-600',
      'from-purple-400 to-purple-600',
      'from-pink-400 to-pink-600',
      'from-green-400 to-green-600',
      'from-yellow-400 to-yellow-600',
      'from-red-400 to-red-600',
      'from-indigo-400 to-indigo-600',
      'from-orange-400 to-orange-600',
    ];
    return gradients[index % gradients.length];
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

  if (!lesson) {
    return (
      <div>
        <Navbar />
        <div className="flex items-center justify-center min-h-screen">
          <div className="text-center">
            <p className="text-xl text-gray-500">Lesson not found</p>
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
            href={`/student/learn/unit/${lesson.unit?.id}`}
            className="inline-flex items-center text-primary-600 hover:text-primary-700 mb-4"
          >
            ← Back to Unit
          </Link>

          <div className="bg-white rounded-2xl shadow-xl p-8">
            <div className="flex items-center">
              <div className="w-20 h-20 rounded-full bg-gradient-to-br from-purple-400 to-pink-600 flex items-center justify-center text-4xl shadow-lg mr-6">
                {lesson.icon || '📝'}
              </div>
              <div className="flex-1">
                <h1 className="text-4xl font-extrabold text-gray-900 mb-2">
                  {lesson.name}
                </h1>
                <div className="flex items-center space-x-4 text-gray-600">
                  <span>🎯 {activities.length} Activities</span>
                  <span>⭐ {activities.reduce((sum, a) => sum + (a.points || 0), 0)} Total Points</span>
                  {lesson.audioName && <span>🔊 Audio Guide Available</span>}
                </div>
              </div>
            </div>

            {lesson.audioName && (
              <div className="mt-6 p-4 bg-gradient-to-r from-purple-50 to-pink-50 rounded-xl">
                <div className="flex items-center">
                  <span className="text-2xl mr-3">🔊</span>
                  <div>
                    <p className="font-semibold text-gray-900">Audio Guide Available</p>
                    <p className="text-sm text-gray-600">Listen to the lesson introduction</p>
                  </div>
                </div>
              </div>
            )}
          </div>
        </div>

        {/* Activities */}
        <div className="mt-12 px-4 sm:px-0">
          <h2 className="text-3xl font-bold text-gray-900 mb-6 flex items-center">
            <span className="mr-3">🎯</span>
            Learning Activities
          </h2>

          {activities && activities.length > 0 ? (
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              {activities.map((activity, index) => (
                <Link
                  key={activity.id}
                  href={`/student/learn/activity/${activity.id}`}
                  className="group block"
                >
                  <div className="bg-white rounded-2xl shadow-lg overflow-hidden hover:shadow-2xl transition-all duration-300 transform hover:-translate-y-2">
                    {/* Gradient Top Bar */}
                    <div className={`h-3 bg-gradient-to-r ${getActivityGradient(index)}`}></div>

                    <div className="p-6">
                      <div className="flex items-start justify-between mb-4">
                        {/* Activity Number & Icon */}
                        <div className="flex items-center">
                          <div className={`w-12 h-12 rounded-full bg-gradient-to-br ${getActivityGradient(index)} flex items-center justify-center text-white font-bold text-lg shadow-lg mr-4`}>
                            {index + 1}
                          </div>
                          <span className="text-4xl">{getActivityIcon(activity.activityType)}</span>
                        </div>
                        
                        {/* Points Badge */}
                        <div className="px-4 py-2 bg-gradient-to-r from-yellow-400 to-orange-400 text-white rounded-full font-bold shadow-lg">
                          ⭐ {activity.points}
                        </div>
                      </div>

                      <h3 className="text-xl font-bold text-gray-900 mb-2 group-hover:text-primary-600 transition-colors">
                        {activity.titleName}
                      </h3>

                      {activity.desc && (
                        <p className="text-sm text-gray-600 mb-4 line-clamp-2">
                          {activity.desc}
                        </p>
                      )}

                      <div className="flex items-center justify-between">
                        <div className="flex items-center space-x-3 text-sm">
                          <span className={`px-3 py-1 rounded-full font-medium ${
                            activity.activityType === 'TEXT' ? 'bg-blue-100 text-blue-800' :
                            activity.activityType === 'PDF' ? 'bg-red-100 text-red-800' :
                            activity.activityType === 'AUDIO' ? 'bg-purple-100 text-purple-800' :
                            activity.activityType === 'BOOK' ? 'bg-green-100 text-green-800' :
                            activity.activityType === 'VIDEO' ? 'bg-pink-100 text-pink-800' :
                            activity.activityType === 'INTERACTIVE' ? 'bg-yellow-100 text-yellow-800' :
                            activity.activityType === 'QUIZ' ? 'bg-orange-100 text-orange-800' :
                            'bg-indigo-100 text-indigo-800'
                          }`}>
                            {activity.activityType}
                          </span>
                          {activity.tag && (
                            <span className="text-gray-500">🏷️ {activity.tag}</span>
                          )}
                        </div>

                        <div className="text-right">
                          <span className="inline-block px-4 py-2 bg-gradient-to-r from-primary-500 to-purple-500 text-white rounded-full text-sm font-medium group-hover:from-primary-600 group-hover:to-purple-600 transition-all">
                            Start Activity →
                          </span>
                        </div>
                      </div>
                    </div>
                  </div>
                </Link>
              ))}
            </div>
          ) : (
            <div className="bg-white rounded-2xl shadow-lg p-12 text-center">
              <span className="text-6xl">🎯</span>
              <p className="mt-4 text-xl text-gray-500">No activities available yet</p>
              <p className="text-sm text-gray-400 mt-2">
                Activities are being prepared!
              </p>
            </div>
          )}
        </div>

        {/* Encouragement */}
        <div className="mt-12 px-4 sm:px-0">
          <div className="bg-gradient-to-r from-green-400 via-blue-500 to-purple-500 rounded-2xl shadow-xl p-8 text-white text-center">
            <h3 className="text-3xl font-bold mb-2">🌟 Ready to Learn?</h3>
            <p className="text-lg">
              Complete all activities to master this lesson and move forward!
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}

