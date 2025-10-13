'use client';

import { useState, useEffect } from 'react';
import { useRouter } from 'next/navigation';
import Link from 'next/link';
import Navbar from '@/components/Navbar';
import { unitAPI, lessonAPI } from '@/lib/api';

export default function CreateLessonPage({ params }: { params: { id: string } }) {
  const router = useRouter();
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [unit, setUnit] = useState<any>(null);
  const [formData, setFormData] = useState({
    name: '',
    audioName: '',
    icon: '',
    status: 'DRAFT',
    unitId: parseInt(params.id),
  });

  useEffect(() => {
    loadUnit();
  }, [params.id]);

  const loadUnit = async () => {
    try {
      const response = await unitAPI.getById(parseInt(params.id));
      setUnit(response.data);
    } catch (error) {
      console.error('Failed to load unit:', error);
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    try {
      await lessonAPI.create(formData);
      router.push(`/units/${params.id}`);
    } catch (err: any) {
      setError(err.response?.data?.message || 'Failed to create lesson');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gray-50">
      <Navbar />

      <div className="max-w-4xl mx-auto py-6 sm:px-6 lg:px-8">
        {/* Header */}
        <div className="px-4 py-6 sm:px-0">
          <div className="flex items-center justify-between">
            <div>
              <h1 className="text-3xl font-bold text-gray-900">Add New Lesson 📝</h1>
              <p className="mt-2 text-gray-600">
                Create a new lesson for: <span className="font-semibold">{unit?.name}</span>
              </p>
            </div>
            <Link
              href={`/units/${params.id}`}
              className="px-6 py-2 bg-gray-200 text-gray-700 rounded-lg hover:bg-gray-300"
            >
              ← Back
            </Link>
          </div>
        </div>

        {/* Form */}
        <div className="mt-8 bg-white shadow rounded-lg p-6">
          {error && (
            <div className="mb-6 bg-red-50 border border-red-400 text-red-700 px-4 py-3 rounded">
              {error}
            </div>
          )}

          <form onSubmit={handleSubmit} className="space-y-6">
            {/* Lesson Name */}
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                Lesson Name *
              </label>
              <input
                type="text"
                required
                className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-primary-500"
                placeholder="e.g., Learning the Letter Alif"
                value={formData.name}
                onChange={(e) => setFormData({ ...formData, name: e.target.value })}
              />
              <p className="mt-1 text-xs text-gray-500">
                Give your lesson a clear, descriptive name
              </p>
            </div>

            {/* Icon */}
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                Icon / Emoji
              </label>
              <input
                type="text"
                className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-primary-500"
                placeholder="📝 (optional)"
                value={formData.icon}
                onChange={(e) => setFormData({ ...formData, icon: e.target.value })}
                maxLength={10}
              />
            </div>

            {/* Audio Name */}
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                Audio File Name
              </label>
              <input
                type="text"
                className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-primary-500"
                placeholder="lesson-intro.mp3 (optional)"
                value={formData.audioName}
                onChange={(e) => setFormData({ ...formData, audioName: e.target.value })}
              />
              <p className="mt-1 text-xs text-gray-500">
                Reference to the audio file for this lesson (if available)
              </p>
            </div>

            {/* Status */}
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                Status *
              </label>
              <select
                required
                className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-primary-500"
                value={formData.status}
                onChange={(e) => setFormData({ ...formData, status: e.target.value })}
              >
                <option value="DRAFT">Draft - Not visible to students</option>
                <option value="ACTIVE">Active - Available to students</option>
                <option value="ARCHIVED">Archived - Hidden</option>
              </select>
            </div>

            {/* Info Box */}
            <div className="bg-blue-50 border border-blue-200 rounded-lg p-4">
              <div className="flex">
                <div className="flex-shrink-0">
                  <span className="text-2xl">ℹ️</span>
                </div>
                <div className="ml-3">
                  <h3 className="text-sm font-medium text-blue-900">Next Steps</h3>
                  <div className="mt-2 text-sm text-blue-700">
                    <p>After creating the lesson, you can add activities:</p>
                    <ul className="list-disc ml-5 mt-2 space-y-1">
                      <li>Text - Written content with audio</li>
                      <li>PDF - Document viewer</li>
                      <li>Audio - Sound files with/without music</li>
                      <li>Book - Interactive pages</li>
                      <li>Video - Video content</li>
                      <li>Interactive - External activities</li>
                      <li>Quiz - Assessments</li>
                      <li>Homework - Assignments</li>
                    </ul>
                  </div>
                </div>
              </div>
            </div>

            {/* Actions */}
            <div className="pt-6 border-t flex justify-end space-x-4">
              <Link
                href={`/units/${params.id}`}
                className="px-6 py-2 bg-gray-200 text-gray-700 rounded-lg hover:bg-gray-300"
              >
                Cancel
              </Link>
              <button
                type="submit"
                disabled={loading}
                className="px-6 py-2 bg-primary-600 text-white rounded-lg hover:bg-primary-700 disabled:bg-gray-400 disabled:cursor-not-allowed"
              >
                {loading ? 'Creating...' : '✓ Create Lesson'}
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}

