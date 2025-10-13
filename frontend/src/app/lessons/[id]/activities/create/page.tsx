'use client';

import { useState, useEffect } from 'react';
import { useRouter } from 'next/navigation';
import Link from 'next/link';
import Navbar from '@/components/Navbar';
import { lessonAPI } from '@/lib/api';

const ACTIVITY_TYPES = [
  {
    type: 'TEXT',
    icon: '📄',
    name: 'Text Activity',
    description: 'Written content with optional audio narration',
    color: 'from-blue-400 to-blue-600',
  },
  {
    type: 'PDF',
    icon: '📑',
    name: 'PDF Document',
    description: 'PDF viewer with optional audio explanation',
    color: 'from-red-400 to-red-600',
  },
  {
    type: 'AUDIO',
    icon: '🔊',
    name: 'Audio Lesson',
    description: 'Audio content with/without background music',
    color: 'from-purple-400 to-purple-600',
  },
  {
    type: 'BOOK',
    icon: '📖',
    name: 'Interactive Book',
    description: 'Multi-page book with images, text, and audio',
    color: 'from-green-400 to-green-600',
  },
  {
    type: 'VIDEO',
    icon: '🎥',
    name: 'Video Content',
    description: 'Video lessons with/without background music',
    color: 'from-pink-400 to-pink-600',
  },
  {
    type: 'INTERACTIVE',
    icon: '🎮',
    name: 'Interactive Activity',
    description: 'External interactive games or activities',
    color: 'from-yellow-400 to-yellow-600',
  },
  {
    type: 'QUIZ',
    icon: '📝',
    name: 'Quiz',
    description: 'Assessment with multiple questions',
    color: 'from-orange-400 to-orange-600',
  },
  {
    type: 'HOMEWORK',
    icon: '📋',
    name: 'Homework',
    description: 'Assignments with file uploads',
    color: 'from-indigo-400 to-indigo-600',
  },
];

export default function SelectActivityTypePage({ params }: { params: { id: string } }) {
  const router = useRouter();
  const [lesson, setLesson] = useState<any>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadLesson();
  }, [params.id]);

  const loadLesson = async () => {
    try {
      const response = await lessonAPI.getById(parseInt(params.id));
      setLesson(response.data);
    } catch (error) {
      console.error('Failed to load lesson:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleSelectType = (type: string) => {
    router.push(`/lessons/${params.id}/activities/create/${type.toLowerCase()}`);
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
                Select Activity Type 🎯
              </h1>
              <p className="mt-2 text-gray-600">
                Choose the type of activity for: <span className="font-semibold">{lesson?.name}</span>
              </p>
            </div>
            <Link
              href={`/lessons/${params.id}`}
              className="px-6 py-2 bg-gray-200 text-gray-700 rounded-lg hover:bg-gray-300"
            >
              ← Back
            </Link>
          </div>
        </div>

        {/* Activity Type Grid */}
        <div className="mt-8 grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
          {ACTIVITY_TYPES.map((activityType) => (
            <button
              key={activityType.type}
              onClick={() => handleSelectType(activityType.type)}
              className="group relative bg-white p-6 rounded-xl shadow-md hover:shadow-xl transition-all duration-300 transform hover:-translate-y-1"
            >
              {/* Gradient Background */}
              <div
                className={`absolute top-0 left-0 right-0 h-2 rounded-t-xl bg-gradient-to-r ${activityType.color}`}
              ></div>

              {/* Icon */}
              <div className="flex justify-center mb-4">
                <div className={`w-20 h-20 rounded-full bg-gradient-to-br ${activityType.color} flex items-center justify-center text-4xl shadow-lg`}>
                  {activityType.icon}
                </div>
              </div>

              {/* Content */}
              <h3 className="text-lg font-semibold text-gray-900 text-center mb-2">
                {activityType.name}
              </h3>
              <p className="text-sm text-gray-500 text-center">
                {activityType.description}
              </p>

              {/* Hover Effect */}
              <div className="mt-4 opacity-0 group-hover:opacity-100 transition-opacity">
                <div className={`text-center text-sm font-medium bg-gradient-to-r ${activityType.color} bg-clip-text text-transparent`}>
                  Click to create →
                </div>
              </div>
            </button>
          ))}
        </div>

        {/* Info Box */}
        <div className="mt-12 bg-blue-50 border border-blue-200 rounded-lg p-6">
          <div className="flex">
            <div className="flex-shrink-0">
              <span className="text-3xl">💡</span>
            </div>
            <div className="ml-4">
              <h3 className="text-lg font-medium text-blue-900 mb-2">Activity Tips</h3>
              <ul className="space-y-2 text-sm text-blue-700">
                <li className="flex items-start">
                  <span className="mr-2">✓</span>
                  <span>Each activity type has unique fields tailored to its content</span>
                </li>
                <li className="flex items-start">
                  <span className="mr-2">✓</span>
                  <span>Quizzes can be used for assessment and progress tracking</span>
                </li>
                <li className="flex items-start">
                  <span className="mr-2">✓</span>
                  <span>Interactive activities can link to external learning tools</span>
                </li>
                <li className="flex items-start">
                  <span className="mr-2">✓</span>
                  <span>Books support multiple pages with images and audio per page</span>
                </li>
                <li className="flex items-start">
                  <span className="mr-2">✓</span>
                  <span>Mix different activity types to create engaging lessons</span>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

