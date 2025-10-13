'use client';

import { useState } from 'react';
import { useRouter } from 'next/navigation';
import Link from 'next/link';
import Navbar from '@/components/Navbar';
import { curriculumAPI } from '@/lib/api';

export default function CreateCurriculumPage() {
  const router = useRouter();
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [formData, setFormData] = useState({
    name: '',
    icon: '',
    status: 'DRAFT',
  });

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    try {
      const response = await curriculumAPI.create(formData);
      router.push(`/curriculums/${response.data.id}`);
    } catch (err: any) {
      setError(err.response?.data?.message || 'Failed to create curriculum');
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
              <h1 className="text-3xl font-bold text-gray-900">Create New Curriculum 📚</h1>
              <p className="mt-2 text-gray-600">Add a new learning curriculum to the system</p>
            </div>
            <Link
              href="/curriculums"
              className="px-6 py-2 bg-gray-200 text-gray-700 rounded-lg hover:bg-gray-300"
            >
              ← Back to Curriculums
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
            {/* Curriculum Name */}
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                Curriculum Name *
              </label>
              <input
                type="text"
                required
                className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-primary-500"
                placeholder="e.g., Arabic Language Level 1"
                value={formData.name}
                onChange={(e) => setFormData({ ...formData, name: e.target.value })}
              />
              <p className="mt-1 text-xs text-gray-500">
                Choose a clear, descriptive name for your curriculum
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
                placeholder="📚 (optional)"
                value={formData.icon}
                onChange={(e) => setFormData({ ...formData, icon: e.target.value })}
                maxLength={10}
              />
              <p className="mt-1 text-xs text-gray-500">
                Add an emoji or short icon to make your curriculum visually identifiable
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
                <option value="ARCHIVED">Archived - Hidden from new enrollments</option>
              </select>
              <p className="mt-1 text-xs text-gray-500">
                Set to DRAFT while building your curriculum, then ACTIVE when ready
              </p>
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
                    <p>After creating the curriculum, you can:</p>
                    <ul className="list-disc ml-5 mt-2 space-y-1">
                      <li>Add Units to organize your content</li>
                      <li>Add Lessons within each unit</li>
                      <li>Create Activities for each lesson (Text, Video, Quiz, etc.)</li>
                      <li>Set the curriculum to ACTIVE when ready for students</li>
                    </ul>
                  </div>
                </div>
              </div>
            </div>

            {/* Actions */}
            <div className="pt-6 border-t flex justify-end space-x-4">
              <Link
                href="/curriculums"
                className="px-6 py-2 bg-gray-200 text-gray-700 rounded-lg hover:bg-gray-300"
              >
                Cancel
              </Link>
              <button
                type="submit"
                disabled={loading}
                className="px-6 py-2 bg-primary-600 text-white rounded-lg hover:bg-primary-700 disabled:bg-gray-400 disabled:cursor-not-allowed"
              >
                {loading ? 'Creating...' : '✓ Create Curriculum'}
              </button>
            </div>
          </form>
        </div>

        {/* Quick Tips */}
        <div className="mt-6 bg-white shadow rounded-lg p-6">
          <h2 className="text-lg font-semibold text-gray-900 mb-4">💡 Quick Tips</h2>
          <div className="space-y-3 text-sm text-gray-600">
            <div className="flex items-start">
              <span className="mr-2">✓</span>
              <p>
                <strong>Start with DRAFT:</strong> Build your curriculum structure before making it
                active
              </p>
            </div>
            <div className="flex items-start">
              <span className="mr-2">✓</span>
              <p>
                <strong>Plan your structure:</strong> Think about Units (chapters) → Lessons
                (topics) → Activities (content)
              </p>
            </div>
            <div className="flex items-start">
              <span className="mr-2">✓</span>
              <p>
                <strong>Use clear names:</strong> Help students and teachers easily understand what
                they'll learn
              </p>
            </div>
            <div className="flex items-start">
              <span className="mr-2">✓</span>
              <p>
                <strong>Test before activating:</strong> Review all content before setting to
                ACTIVE status
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

