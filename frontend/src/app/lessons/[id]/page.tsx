'use client';

import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import Link from 'next/link';
import Navbar from '@/components/Navbar';
import { auth } from '@/lib/auth';
import { lessonAPI, activityAPI } from '@/lib/api';

export default function LessonDetailPage({ params }: { params: { id: string } }) {
  const router = useRouter();
  const [loading, setLoading] = useState(true);
  const [lesson, setLesson] = useState<any>(null);
  const [activities, setActivities] = useState<any[]>([]);

  useEffect(() => {
    if (!auth.isAuthenticated()) {
      router.push('/login');
      return;
    }

    loadLessonData();
  }, [params.id]);

  const loadLessonData = async () => {
    try {
      const [lessonRes, activitiesRes] = await Promise.all([
        lessonAPI.getById(parseInt(params.id)),
        activityAPI.getByLesson(parseInt(params.id)),
      ]);

      setLesson(lessonRes.data);
      setActivities(activitiesRes.data);
    } catch (error) {
      console.error('Failed to load lesson:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleDeleteActivity = async (activityId: number) => {
    if (!confirm('Are you sure you want to delete this activity?')) return;

    try {
      await activityAPI.delete(activityId);
      loadLessonData();
    } catch (error) {
      console.error('Delete failed:', error);
      alert('Failed to delete activity');
    }
  };

  const getActivityTypeIcon = (type: string) => {
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

  const getActivityTypeColor = (type: string) => {
    const colors: { [key: string]: string } = {
      TEXT: 'bg-blue-100 text-blue-800',
      PDF: 'bg-red-100 text-red-800',
      AUDIO: 'bg-purple-100 text-purple-800',
      BOOK: 'bg-green-100 text-green-800',
      VIDEO: 'bg-pink-100 text-pink-800',
      INTERACTIVE: 'bg-yellow-100 text-yellow-800',
      QUIZ: 'bg-orange-100 text-orange-800',
      HOMEWORK: 'bg-indigo-100 text-indigo-800',
    };
    return colors[type] || 'bg-gray-100 text-gray-800';
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
            <Link href="/curriculums" className="mt-4 inline-block text-primary-600 hover:text-primary-700">
              ← Back to Curriculums
            </Link>
          </div>
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
              <div className="flex items-center">
                <Link
                  href={`/units/${lesson.unit?.id}`}
                  className="mr-4 text-gray-400 hover:text-gray-600"
                >
                  ← Back to Unit
                </Link>
                <h1 className="text-3xl font-bold text-gray-900">
                  {lesson.icon} {lesson.name}
                </h1>
              </div>
              <div className="mt-2 flex items-center space-x-4">
                <span
                  className={`px-3 py-1 rounded-full text-sm font-medium ${
                    lesson.status === 'ACTIVE'
                      ? 'bg-green-100 text-green-800'
                      : lesson.status === 'DRAFT'
                      ? 'bg-yellow-100 text-yellow-800'
                      : 'bg-gray-100 text-gray-800'
                  }`}
                >
                  {lesson.status}
                </span>
                {lesson.audioName && (
                  <span className="text-sm text-gray-500">🔊 {lesson.audioName}</span>
                )}
              </div>
            </div>
            <div className="flex space-x-2">
              <Link
                href={`/lessons/${lesson.id}/edit`}
                className="px-6 py-2 bg-primary-600 text-white rounded-lg hover:bg-primary-700"
              >
                Edit Lesson
              </Link>
              <Link
                href={`/lessons/${lesson.id}/activities/create`}
                className="px-6 py-2 bg-green-600 text-white rounded-lg hover:bg-green-700"
              >
                ➕ Add Activity
              </Link>
            </div>
          </div>
        </div>

        {/* Stats */}
        <div className="mt-8 grid grid-cols-1 gap-5 sm:grid-cols-4">
          <div className="bg-white overflow-hidden shadow rounded-lg">
            <div className="p-5">
              <div className="flex items-center">
                <div className="flex-shrink-0">
                  <span className="text-3xl">🎯</span>
                </div>
                <div className="ml-5">
                  <p className="text-sm text-gray-500">Total Activities</p>
                  <p className="text-2xl font-bold text-gray-900">{activities.length}</p>
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
                <div className="ml-5">
                  <p className="text-sm text-gray-500">Active</p>
                  <p className="text-2xl font-bold text-green-600">
                    {activities.filter((a) => a.status === 'ACTIVE').length}
                  </p>
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
                <div className="ml-5">
                  <p className="text-sm text-gray-500">Total Points</p>
                  <p className="text-2xl font-bold text-blue-600">
                    {activities.reduce((sum, a) => sum + (a.points || 0), 0)}
                  </p>
                </div>
              </div>
            </div>
          </div>

          <div className="bg-white overflow-hidden shadow rounded-lg">
            <div className="p-5">
              <div className="flex items-center">
                <div className="flex-shrink-0">
                  <span className="text-3xl">📝</span>
                </div>
                <div className="ml-5">
                  <p className="text-sm text-gray-500">Quizzes</p>
                  <p className="text-2xl font-bold text-orange-600">
                    {activities.filter((a) => a.activityType === 'QUIZ').length}
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* Activities List */}
        <div className="mt-8">
          <h2 className="text-2xl font-bold text-gray-900 mb-4">Activities</h2>

          {activities && activities.length > 0 ? (
            <div className="space-y-4">
              {activities.map((activity, index) => (
                <div
                  key={activity.id}
                  className="bg-white shadow rounded-lg overflow-hidden hover:shadow-lg transition-shadow"
                >
                  <div className="p-6">
                    <div className="flex items-start justify-between">
                      <div className="flex items-start flex-1">
                        <div className="flex-shrink-0 h-12 w-12 bg-gradient-to-br from-orange-100 to-yellow-100 rounded-lg flex items-center justify-center mr-4">
                          <span className="text-2xl">
                            {getActivityTypeIcon(activity.activityType)}
                          </span>
                        </div>
                        <div className="flex-1">
                          <div className="flex items-center justify-between">
                            <div>
                              <h3 className="text-lg font-semibold text-gray-900">
                                {index + 1}. {activity.titleName}
                              </h3>
                              {activity.desc && (
                                <p className="text-sm text-gray-500 mt-1">{activity.desc}</p>
                              )}
                            </div>
                            <div className="flex items-center space-x-2">
                              <span
                                className={`px-3 py-1 rounded-full text-xs font-medium ${getActivityTypeColor(
                                  activity.activityType
                                )}`}
                              >
                                {activity.activityType}
                              </span>
                              <span
                                className={`px-3 py-1 rounded-full text-xs font-medium ${
                                  activity.status === 'ACTIVE'
                                    ? 'bg-green-100 text-green-800'
                                    : activity.status === 'DRAFT'
                                    ? 'bg-yellow-100 text-yellow-800'
                                    : 'bg-gray-100 text-gray-800'
                                }`}
                              >
                                {activity.status}
                              </span>
                            </div>
                          </div>
                          <div className="mt-3 flex items-center space-x-4 text-sm text-gray-500">
                            <span>⭐ {activity.points} points</span>
                            {activity.tag && <span>🏷️ {activity.tag}</span>}
                            {activity.titleAudioName && <span>🔊 Has audio</span>}
                          </div>
                        </div>
                      </div>
                      <div className="flex space-x-2 ml-4">
                        <Link
                          href={`/activities/${activity.id}`}
                          className="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 text-sm"
                        >
                          View
                        </Link>
                        <Link
                          href={`/activities/${activity.id}/edit`}
                          className="px-4 py-2 bg-primary-600 text-white rounded-lg hover:bg-primary-700 text-sm"
                        >
                          Edit
                        </Link>
                        <button
                          onClick={() => handleDeleteActivity(activity.id)}
                          className="px-4 py-2 bg-red-600 text-white rounded-lg hover:bg-red-700 text-sm"
                        >
                          Delete
                        </button>
                      </div>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          ) : (
            <div className="bg-white shadow rounded-lg p-12 text-center">
              <span className="text-6xl">🎯</span>
              <p className="mt-4 text-gray-500">No activities yet</p>
              <p className="text-sm text-gray-400 mt-2">
                Add your first activity to start building this lesson
              </p>
              <Link
                href={`/lessons/${lesson.id}/activities/create`}
                className="mt-6 inline-block px-6 py-3 bg-primary-600 text-white rounded-lg hover:bg-primary-700"
              >
                ➕ Add First Activity
              </Link>
            </div>
          )}
        </div>

        {/* Activity Types Guide */}
        {activities.length === 0 && (
          <div className="mt-8 bg-white shadow rounded-lg p-6">
            <h3 className="text-lg font-semibold text-gray-900 mb-4">
              Available Activity Types
            </h3>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              {[
                { type: 'TEXT', icon: '📄', desc: 'Written content with audio narration' },
                { type: 'PDF', icon: '📑', desc: 'PDF documents with audio' },
                { type: 'AUDIO', icon: '🔊', desc: 'Audio lessons with/without music' },
                { type: 'BOOK', icon: '📖', desc: 'Interactive book with pages' },
                { type: 'VIDEO', icon: '🎥', desc: 'Video content with/without music' },
                { type: 'INTERACTIVE', icon: '🎮', desc: 'External interactive activities' },
                { type: 'QUIZ', icon: '📝', desc: 'Assessments with questions' },
                { type: 'HOMEWORK', icon: '📋', desc: 'Homework assignments' },
              ].map((item) => (
                <div key={item.type} className="flex items-start p-3 border rounded-lg">
                  <span className="text-2xl mr-3">{item.icon}</span>
                  <div>
                    <p className="font-medium text-gray-900">{item.type}</p>
                    <p className="text-sm text-gray-500">{item.desc}</p>
                  </div>
                </div>
              ))}
            </div>
          </div>
        )}
      </div>
    </div>
  );
}

