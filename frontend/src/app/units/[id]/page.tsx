'use client';

import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import Link from 'next/link';
import Navbar from '@/components/Navbar';
import { auth } from '@/lib/auth';
import { unitAPI, lessonAPI } from '@/lib/api';

export default function UnitDetailPage({ params }: { params: { id: string } }) {
  const router = useRouter();
  const [loading, setLoading] = useState(true);
  const [unit, setUnit] = useState<any>(null);
  const [lessons, setLessons] = useState<any[]>([]);

  useEffect(() => {
    if (!auth.isAuthenticated()) {
      router.push('/login');
      return;
    }

    loadUnitData();
  }, [params.id]);

  const loadUnitData = async () => {
    try {
      const [unitRes, lessonsRes] = await Promise.all([
        unitAPI.getById(parseInt(params.id)),
        lessonAPI.getByUnit(parseInt(params.id)),
      ]);

      setUnit(unitRes.data);
      setLessons(lessonsRes.data);
    } catch (error) {
      console.error('Failed to load unit:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleDeleteLesson = async (lessonId: number) => {
    if (!confirm('Are you sure you want to delete this lesson?')) return;

    try {
      await lessonAPI.delete(lessonId);
      loadUnitData();
    } catch (error) {
      console.error('Delete failed:', error);
      alert('Failed to delete lesson');
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
                  href={`/curriculums/${unit.curriculum?.id}`}
                  className="mr-4 text-gray-400 hover:text-gray-600"
                >
                  ← Back to Curriculum
                </Link>
                <h1 className="text-3xl font-bold text-gray-900">
                  {unit.icon} {unit.name}
                </h1>
              </div>
              <div className="mt-2 flex items-center space-x-4">
                <span
                  className={`px-3 py-1 rounded-full text-sm font-medium ${
                    unit.status === 'ACTIVE'
                      ? 'bg-green-100 text-green-800'
                      : unit.status === 'DRAFT'
                      ? 'bg-yellow-100 text-yellow-800'
                      : 'bg-gray-100 text-gray-800'
                  }`}
                >
                  {unit.status}
                </span>
                {unit.audioName && (
                  <span className="text-sm text-gray-500">🔊 {unit.audioName}</span>
                )}
              </div>
            </div>
            <div className="flex space-x-2">
              <Link
                href={`/units/${unit.id}/edit`}
                className="px-6 py-2 bg-primary-600 text-white rounded-lg hover:bg-primary-700"
              >
                Edit Unit
              </Link>
              <Link
                href={`/units/${unit.id}/lessons/create`}
                className="px-6 py-2 bg-green-600 text-white rounded-lg hover:bg-green-700"
              >
                ➕ Add Lesson
              </Link>
            </div>
          </div>
        </div>

        {/* Stats */}
        <div className="mt-8 grid grid-cols-1 gap-5 sm:grid-cols-3">
          <div className="bg-white overflow-hidden shadow rounded-lg">
            <div className="p-5">
              <div className="flex items-center">
                <div className="flex-shrink-0">
                  <span className="text-3xl">📝</span>
                </div>
                <div className="ml-5">
                  <p className="text-sm text-gray-500">Total Lessons</p>
                  <p className="text-2xl font-bold text-gray-900">{lessons.length}</p>
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
                  <p className="text-sm text-gray-500">Active Lessons</p>
                  <p className="text-2xl font-bold text-green-600">
                    {lessons.filter((l) => l.status === 'ACTIVE').length}
                  </p>
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
                <div className="ml-5">
                  <p className="text-sm text-gray-500">Total Activities</p>
                  <p className="text-2xl font-bold text-blue-600">
                    {lessons.reduce((sum, l) => sum + (l.activityList?.length || 0), 0)}
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* Lessons List */}
        <div className="mt-8">
          <h2 className="text-2xl font-bold text-gray-900 mb-4">Lessons</h2>

          {lessons && lessons.length > 0 ? (
            <div className="space-y-4">
              {lessons.map((lesson, index) => (
                <div
                  key={lesson.id}
                  className="bg-white shadow rounded-lg overflow-hidden hover:shadow-lg transition-shadow"
                >
                  <div className="p-6">
                    <div className="flex items-start justify-between">
                      <div className="flex items-start flex-1">
                        <div className="flex-shrink-0 h-12 w-12 bg-purple-100 rounded-lg flex items-center justify-center mr-4">
                          <span className="text-xl font-bold text-purple-600">
                            {index + 1}
                          </span>
                        </div>
                        <div className="flex-1">
                          <div className="flex items-center justify-between">
                            <h3 className="text-lg font-semibold text-gray-900">
                              {lesson.icon} {lesson.name}
                            </h3>
                            <span
                              className={`px-3 py-1 rounded-full text-xs font-medium ${
                                lesson.status === 'ACTIVE'
                                  ? 'bg-green-100 text-green-800'
                                  : lesson.status === 'DRAFT'
                                  ? 'bg-yellow-100 text-yellow-800'
                                  : 'bg-gray-100 text-gray-800'
                              }`}
                            >
                              {lesson.status}
                            </span>
                          </div>
                          <div className="mt-2 text-sm text-gray-500">
                            <p>
                              🎯 {lesson.activityList?.length || 0} Activities
                              {lesson.audioName && <span className="ml-4">🔊 Has audio</span>}
                            </p>
                          </div>
                        </div>
                      </div>
                      <div className="flex space-x-2 ml-4">
                        <Link
                          href={`/lessons/${lesson.id}`}
                          className="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 text-sm"
                        >
                          View
                        </Link>
                        <Link
                          href={`/lessons/${lesson.id}/edit`}
                          className="px-4 py-2 bg-primary-600 text-white rounded-lg hover:bg-primary-700 text-sm"
                        >
                          Edit
                        </Link>
                        <button
                          onClick={() => handleDeleteLesson(lesson.id)}
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
              <span className="text-6xl">📝</span>
              <p className="mt-4 text-gray-500">No lessons yet</p>
              <p className="text-sm text-gray-400 mt-2">
                Add your first lesson to start building this unit
              </p>
              <Link
                href={`/units/${unit.id}/lessons/create`}
                className="mt-6 inline-block px-6 py-3 bg-primary-600 text-white rounded-lg hover:bg-primary-700"
              >
                ➕ Add First Lesson
              </Link>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}

