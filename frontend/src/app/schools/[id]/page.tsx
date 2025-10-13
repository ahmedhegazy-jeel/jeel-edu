'use client';

import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import Link from 'next/link';
import Navbar from '@/components/Navbar';
import { auth } from '@/lib/auth';
import { schoolAPI } from '@/lib/api';

export default function SchoolDetailPage({ params }: { params: { id: string } }) {
  const router = useRouter();
  const [loading, setLoading] = useState(true);
  const [school, setSchool] = useState<any>(null);

  useEffect(() => {
    if (!auth.isAuthenticated()) {
      router.push('/login');
      return;
    }

    loadSchool();
  }, [params.id]);

  const loadSchool = async () => {
    try {
      const response = await schoolAPI.getById(parseInt(params.id));
      setSchool(response.data);
    } catch (error) {
      console.error('Failed to load school:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async () => {
    if (!confirm('Are you sure you want to delete this school? This action cannot be undone.')) return;

    try {
      await schoolAPI.delete(parseInt(params.id));
      router.push('/schools');
    } catch (error) {
      console.error('Delete failed:', error);
      alert('Failed to delete school');
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

  if (!school) {
    return (
      <div>
        <Navbar />
        <div className="flex items-center justify-center min-h-screen">
          <div className="text-center">
            <p className="text-xl text-gray-500">School not found</p>
            <Link href="/schools" className="mt-4 inline-block text-primary-600">
              ← Back to Schools
            </Link>
          </div>
        </div>
      </div>
    );
  }

  const capacityPercentage = school.maxCapacity
    ? ((school.currentStudents || 0) / school.maxCapacity) * 100
    : 0;

  return (
    <div className="min-h-screen bg-gray-50">
      <Navbar />

      <div className="max-w-7xl mx-auto py-6 sm:px-6 lg:px-8">
        <div className="px-4 py-6 sm:px-0">
          <Link
            href="/schools"
            className="inline-flex items-center text-primary-600 hover:text-primary-700 mb-4"
          >
            ← Back to Schools
          </Link>

          {/* Header */}
          <div className="bg-white rounded-lg shadow-lg p-8">
            <div className="flex items-start justify-between">
              <div className="flex items-start">
                <div className="w-20 h-20 bg-primary-100 rounded-lg flex items-center justify-center mr-6">
                  <span className="text-4xl">🏫</span>
                </div>
                <div>
                  <h1 className="text-3xl font-bold text-gray-900 mb-2">{school.name}</h1>
                  <div className="flex items-center space-x-4">
                    <span
                      className={`px-3 py-1 rounded-full text-sm font-medium ${
                        school.status === 'ACTIVE'
                          ? 'bg-green-100 text-green-800'
                          : 'bg-gray-100 text-gray-800'
                      }`}
                    >
                      {school.status}
                    </span>
                    {school.foundedYear && (
                      <span className="text-sm text-gray-500">
                        Founded: {school.foundedYear}
                      </span>
                    )}
                  </div>
                </div>
              </div>
              <div className="flex space-x-2">
                <Link
                  href={`/schools/${school.id}/edit`}
                  className="px-6 py-2 bg-primary-600 text-white rounded-lg hover:bg-primary-700"
                >
                  Edit School
                </Link>
                <button
                  onClick={handleDelete}
                  className="px-6 py-2 bg-red-600 text-white rounded-lg hover:bg-red-700"
                >
                  Delete
                </button>
              </div>
            </div>
          </div>

          {/* Details Grid */}
          <div className="mt-8 grid grid-cols-1 md:grid-cols-2 gap-6">
            {/* Contact Information */}
            <div className="bg-white rounded-lg shadow p-6">
              <h2 className="text-xl font-semibold text-gray-900 mb-4">Contact Information</h2>
              <div className="space-y-3">
                <div>
                  <p className="text-sm text-gray-500">Address</p>
                  <p className="text-gray-900">{school.address}</p>
                  <p className="text-gray-700">
                    {school.city}, {school.state && `${school.state}, `}{school.country}
                  </p>
                  {school.postalCode && <p className="text-gray-700">{school.postalCode}</p>}
                </div>
                {school.phone && (
                  <div>
                    <p className="text-sm text-gray-500">Phone</p>
                    <p className="text-gray-900">📞 {school.phone}</p>
                  </div>
                )}
                {school.email && (
                  <div>
                    <p className="text-sm text-gray-500">Email</p>
                    <p className="text-gray-900">📧 {school.email}</p>
                  </div>
                )}
                {school.website && (
                  <div>
                    <p className="text-sm text-gray-500">Website</p>
                    <a
                      href={school.website}
                      target="_blank"
                      rel="noopener noreferrer"
                      className="text-primary-600 hover:text-primary-700"
                    >
                      🌐 {school.website}
                    </a>
                  </div>
                )}
              </div>
            </div>

            {/* Capacity Information */}
            <div className="bg-white rounded-lg shadow p-6">
              <h2 className="text-xl font-semibold text-gray-900 mb-4">Capacity</h2>
              <div className="space-y-4">
                {school.maxCapacity ? (
                  <>
                    <div className="flex items-center justify-between">
                      <span className="text-gray-700">Current Students</span>
                      <span className="text-2xl font-bold text-primary-600">
                        {school.currentStudents || 0}
                      </span>
                    </div>
                    <div className="flex items-center justify-between">
                      <span className="text-gray-700">Max Capacity</span>
                      <span className="text-2xl font-bold text-gray-900">
                        {school.maxCapacity}
                      </span>
                    </div>
                    <div>
                      <div className="flex justify-between text-sm mb-2">
                        <span className="text-gray-600">Utilization</span>
                        <span className="font-medium">{Math.round(capacityPercentage)}%</span>
                      </div>
                      <div className="w-full bg-gray-200 rounded-full h-3">
                        <div
                          className={`h-3 rounded-full transition-all ${
                            capacityPercentage >= 90
                              ? 'bg-red-600'
                              : capacityPercentage >= 70
                              ? 'bg-yellow-600'
                              : 'bg-green-600'
                          }`}
                          style={{ width: `${capacityPercentage}%` }}
                        ></div>
                      </div>
                    </div>
                    <div className="flex items-center justify-between pt-4 border-t">
                      <span className="text-gray-700">Available Spots</span>
                      <span className="text-xl font-bold text-green-600">
                        {school.maxCapacity - (school.currentStudents || 0)}
                      </span>
                    </div>
                  </>
                ) : (
                  <p className="text-gray-500">No capacity limit set</p>
                )}
              </div>
            </div>
          </div>

          {/* Admin Information */}
          <div className="mt-8 bg-white rounded-lg shadow p-6">
            <h2 className="text-xl font-semibold text-gray-900 mb-4">School Administration</h2>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              {school.adminEmail && (
                <div>
                  <p className="text-sm text-gray-500">Admin Email</p>
                  <p className="text-gray-900">📧 {school.adminEmail}</p>
                </div>
              )}
              {school.adminMobile && (
                <div>
                  <p className="text-sm text-gray-500">Admin Mobile</p>
                  <p className="text-gray-900">📱 {school.adminMobile}</p>
                </div>
              )}
            </div>
          </div>

          {/* Timestamps */}
          {(school.createdAt || school.updatedAt) && (
            <div className="mt-8 bg-white rounded-lg shadow p-6">
              <h2 className="text-xl font-semibold text-gray-900 mb-4">Timestamps</h2>
              <div className="grid grid-cols-1 md:grid-cols-2 gap-6 text-sm">
                {school.createdAt && (
                  <div>
                    <p className="text-gray-500">Created</p>
                    <p className="text-gray-900">
                      {new Date(school.createdAt).toLocaleString()}
                    </p>
                  </div>
                )}
                {school.updatedAt && (
                  <div>
                    <p className="text-gray-500">Last Updated</p>
                    <p className="text-gray-900">
                      {new Date(school.updatedAt).toLocaleString()}
                    </p>
                  </div>
                )}
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}

