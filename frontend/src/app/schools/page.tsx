'use client';

import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import Link from 'next/link';
import Navbar from '@/components/Navbar';
import { auth } from '@/lib/auth';
import { schoolAPI } from '@/lib/api';

export default function SchoolsPage() {
  const router = useRouter();
  const [loading, setLoading] = useState(true);
  const [schools, setSchools] = useState<any[]>([]);
  const [searchTerm, setSearchTerm] = useState('');

  useEffect(() => {
    if (!auth.isAuthenticated()) {
      router.push('/login');
      return;
    }

    if (!auth.isAdmin()) {
      router.push('/');
      return;
    }

    loadSchools();
  }, []);

  const loadSchools = async () => {
    try {
      const response = await schoolAPI.getAll();
      setSchools(response.data);
    } catch (error) {
      console.error('Failed to load schools:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!searchTerm.trim()) {
      loadSchools();
      return;
    }

    try {
      const response = await schoolAPI.search(searchTerm);
      setSchools(response.data);
    } catch (error) {
      console.error('Search failed:', error);
    }
  };

  const handleDelete = async (id: number) => {
    if (!confirm('Are you sure you want to delete this school?')) return;

    try {
      await schoolAPI.delete(id);
      loadSchools();
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

  return (
    <div className="min-h-screen bg-gray-50">
      <Navbar />

      <div className="max-w-7xl mx-auto py-6 sm:px-6 lg:px-8">
        {/* Header */}
        <div className="px-4 py-6 sm:px-0">
          <div className="flex justify-between items-center">
            <div>
              <h1 className="text-3xl font-bold text-gray-900">Schools Management 🏫</h1>
              <p className="mt-2 text-gray-600">Manage all schools in the system</p>
            </div>
            <Link
              href="/schools/create"
              className="px-6 py-3 bg-primary-600 text-white rounded-lg hover:bg-primary-700 transition-colors font-medium"
            >
              ➕ Add New School
            </Link>
          </div>
        </div>

        {/* Search Bar */}
        <div className="mt-8 px-4 sm:px-0">
          <form onSubmit={handleSearch} className="flex gap-4">
            <input
              type="text"
              placeholder="Search schools by name, city, or country..."
              className="flex-1 px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-primary-500"
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
            />
            <button
              type="submit"
              className="px-6 py-2 bg-secondary-600 text-white rounded-lg hover:bg-secondary-700"
            >
              🔍 Search
            </button>
            {searchTerm && (
              <button
                type="button"
                onClick={() => {
                  setSearchTerm('');
                  loadSchools();
                }}
                className="px-6 py-2 bg-gray-200 text-gray-700 rounded-lg hover:bg-gray-300"
              >
                Clear
              </button>
            )}
          </form>
        </div>

        {/* Stats */}
        <div className="mt-8 grid grid-cols-1 gap-5 sm:grid-cols-3">
          <div className="bg-white overflow-hidden shadow rounded-lg">
            <div className="p-5">
              <div className="flex items-center">
                <div className="flex-shrink-0">
                  <span className="text-3xl">🏫</span>
                </div>
                <div className="ml-5">
                  <p className="text-sm text-gray-500">Total Schools</p>
                  <p className="text-2xl font-bold text-gray-900">{schools.length}</p>
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
                  <p className="text-sm text-gray-500">Active Schools</p>
                  <p className="text-2xl font-bold text-green-600">
                    {schools.filter((s) => s.status === 'ACTIVE').length}
                  </p>
                </div>
              </div>
            </div>
          </div>

          <div className="bg-white overflow-hidden shadow rounded-lg">
            <div className="p-5">
              <div className="flex items-center">
                <div className="flex-shrink-0">
                  <span className="text-3xl">👥</span>
                </div>
                <div className="ml-5">
                  <p className="text-sm text-gray-500">Total Capacity</p>
                  <p className="text-2xl font-bold text-blue-600">
                    {schools.reduce((sum, s) => sum + (s.maxCapacity || 0), 0)}
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* Schools List */}
        <div className="mt-8">
          {schools && schools.length > 0 ? (
            <div className="bg-white shadow overflow-hidden sm:rounded-lg">
              <ul className="divide-y divide-gray-200">
                {schools.map((school) => (
                  <li key={school.id} className="px-6 py-4 hover:bg-gray-50">
                    <div className="flex items-center justify-between">
                      <div className="flex items-center flex-1">
                        <div className="flex-shrink-0 h-16 w-16 bg-primary-100 rounded-lg flex items-center justify-center">
                          <span className="text-3xl">🏫</span>
                        </div>
                        <div className="ml-6 flex-1">
                          <div className="flex items-center justify-between">
                            <h3 className="text-lg font-medium text-gray-900">
                              {school.name}
                            </h3>
                            <span
                              className={`px-3 py-1 rounded-full text-xs font-medium ${
                                school.status === 'ACTIVE'
                                  ? 'bg-green-100 text-green-800'
                                  : 'bg-gray-100 text-gray-800'
                              }`}
                            >
                              {school.status}
                            </span>
                          </div>
                          <div className="mt-2 grid grid-cols-2 gap-4 text-sm text-gray-500">
                            <div>
                              <p>📍 {school.city}, {school.country}</p>
                              <p>📧 {school.adminEmail}</p>
                            </div>
                            <div>
                              <p>📞 {school.adminMobile || 'N/A'}</p>
                              <p>👥 Capacity: {school.currentStudents || 0} / {school.maxCapacity || 'N/A'}</p>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div className="flex space-x-2 ml-4">
                        <Link
                          href={`/schools/${school.id}`}
                          className="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 text-sm"
                        >
                          View
                        </Link>
                        <Link
                          href={`/schools/${school.id}/edit`}
                          className="px-4 py-2 bg-primary-600 text-white rounded-lg hover:bg-primary-700 text-sm"
                        >
                          Edit
                        </Link>
                        <button
                          onClick={() => handleDelete(school.id)}
                          className="px-4 py-2 bg-red-600 text-white rounded-lg hover:bg-red-700 text-sm"
                        >
                          Delete
                        </button>
                      </div>
                    </div>
                  </li>
                ))}
              </ul>
            </div>
          ) : (
            <div className="bg-white shadow rounded-lg p-12 text-center">
              <span className="text-6xl">🏫</span>
              <p className="mt-4 text-gray-500">No schools found</p>
              <p className="text-sm text-gray-400 mt-2">
                {searchTerm ? 'Try a different search term' : 'Add your first school to get started'}
              </p>
              {!searchTerm && (
                <Link
                  href="/schools/create"
                  className="mt-6 inline-block px-6 py-3 bg-primary-600 text-white rounded-lg hover:bg-primary-700"
                >
                  ➕ Add New School
                </Link>
              )}
            </div>
          )}
        </div>
      </div>
    </div>
  );
}

