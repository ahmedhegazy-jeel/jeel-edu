'use client';

import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import Link from 'next/link';
import Navbar from '@/components/Navbar';
import { auth } from '@/lib/auth';
import { curriculumAPI, unitAPI } from '@/lib/api';

export default function CurriculumDetailPage({ params }: { params: { id: string } }) {
  const router = useRouter();
  const [loading, setLoading] = useState(true);
  const [curriculum, setCurriculum] = useState<any>(null);
  const [units, setUnits] = useState<any[]>([]);

  useEffect(() => {
    if (!auth.isAuthenticated()) {
      router.push('/login');
      return;
    }

    loadCurriculumData();
  }, [params.id]);

  const loadCurriculumData = async () => {
    try {
      const [curriculumRes, unitsRes] = await Promise.all([
        curriculumAPI.getById(parseInt(params.id)),
        unitAPI.getByCurriculum(parseInt(params.id)),
      ]);

      setCurriculum(curriculumRes.data);
      setUnits(unitsRes.data);
    } catch (error) {
      console.error('Failed to load curriculum:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleDeleteUnit = async (unitId: number) => {
    if (!confirm('Are you sure you want to delete this unit?')) return;

    try {
      await unitAPI.delete(unitId);
      loadCurriculumData();
    } catch (error) {
      console.error('Delete failed:', error);
      alert('Failed to delete unit');
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

  if (!curriculum) {
    return (
      <div>
        <Navbar />
        <div className="flex items-center justify-center min-h-screen">
          <div className="text-center">
            <p className="text-xl text-gray-500">Curriculum not found</p>
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
                  href="/curriculums"
                  className="mr-4 text-gray-400 hover:text-gray-600"
                >
                  ← Back
                </Link>
                <h1 className="text-3xl font-bold text-gray-900">
                  {curriculum.icon} {curriculum.name} 
                </h1>
                
              </div>
              <div className="mt-2 flex items-center space-x-4">
                <span
                  className={`px-3 py-1 rounded-full text-sm font-medium ${
                    curriculum.status === 'ACTIVE'
                      ? 'bg-green-100 text-green-800'
                      : curriculum.status === 'DRAFT'
                      ? 'bg-yellow-100 text-yellow-800'
                      : 'bg-gray-100 text-gray-800'
                  }`}
                >
                  {curriculum.status}
                </span>
                <span className="text-sm text-gray-500">
                  Created: {new Date(curriculum.createdAt).toLocaleDateString()}
                </span>
              </div>
            </div>
            <div className="flex space-x-2">
              <Link
                href={`/curriculums/${curriculum.id}/edit`}
                className="px-6 py-2 bg-primary-600 text-white rounded-lg hover:bg-primary-700"
              >
                Edit Curriculum
              </Link>
              <Link
                href={`/curriculums/${curriculum.id}/units/create`}
                className="px-6 py-2 bg-green-600 text-white rounded-lg hover:bg-green-700"
              >
                ➕ Add Unit
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
                  <span className="text-3xl">📖</span>
                </div>
                <div className="ml-5">
                  <p className="text-sm text-gray-500">Total Units</p>
                  <p className="text-2xl font-bold text-gray-900">{units.length}</p>
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
                  <p className="text-sm text-gray-500">Active Units</p>
                  <p className="text-2xl font-bold text-green-600">
                    {units.filter((u) => u.status === 'ACTIVE').length}
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
                  <p className="text-sm text-gray-500">Total Lessons</p>
                  <p className="text-2xl font-bold text-blue-600">
                    {units.reduce((sum, u) => sum + (u.lessonList?.length || 0), 0)}
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* Units List */}
        <div className="mt-8">
          <h2 className="text-2xl font-bold text-gray-900 mb-4">Units</h2>

          {units && units.length > 0 ? (
            <div className="space-y-4">
              {units.map((unit, index) => (
                <div
                  key={unit.id}
                  className="bg-white shadow rounded-lg overflow-hidden hover:shadow-lg transition-shadow"
                >
                  <div className="p-6">
                    <div className="flex items-start justify-between">
                      <div className="flex items-start flex-1">
                        <div className="flex-shrink-0 h-12 w-12 bg-blue-100 rounded-lg flex items-center justify-center mr-4">
                          <span className="text-xl font-bold text-blue-600">
                            {index + 1}
                          </span>
                        </div>
                        <div className="flex-1">
                          <div className="flex items-center justify-between">
                            <h3 className="text-lg font-semibold text-gray-900">
                              {unit.icon} {unit.name}
                            </h3>
                            <span
                              className={`px-3 py-1 rounded-full text-xs font-medium ${
                                unit.status === 'ACTIVE'
                                  ? 'bg-green-100 text-green-800'
                                  : unit.status === 'DRAFT'
                                  ? 'bg-yellow-100 text-yellow-800'
                                  : 'bg-gray-100 text-gray-800'
                              }`}
                            >
                              {unit.status}
                            </span>
                          </div>
                          <div className="mt-2 text-sm text-gray-500">
                            <p>
                              📝 {unit.lessonList?.length || 0} Lessons
                              {unit.audioName && <span className="ml-4">🔊 Has audio</span>}
                            </p>
                          </div>
                        </div>
                      </div>
                      <div className="flex space-x-2 ml-4">
                        <Link
                          href={`/units/${unit.id}`}
                          className="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 text-sm"
                        >
                          View
                        </Link>
                        <Link
                          href={`/units/${unit.id}/edit`}
                          className="px-4 py-2 bg-primary-600 text-white rounded-lg hover:bg-primary-700 text-sm"
                        >
                          Edit
                        </Link>
                        <button
                          onClick={() => handleDeleteUnit(unit.id)}
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
              <span className="text-6xl">📖</span>
              <p className="mt-4 text-gray-500">No units yet</p>
              <p className="text-sm text-gray-400 mt-2">
                Add your first unit to start building this curriculum
              </p>
              <Link
                href={`/curriculums/${curriculum.id}/units/create`}
                className="mt-6 inline-block px-6 py-3 bg-primary-600 text-white rounded-lg hover:bg-primary-700"
              >
                ➕ Add First Unit
              </Link>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}

