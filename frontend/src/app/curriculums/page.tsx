'use client';

import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import Link from 'next/link';
import Navbar from '@/components/Navbar';
import { auth } from '@/lib/auth';
import { curriculumAPI } from '@/lib/api';

export default function CurriculumsPage() {
  const router = useRouter();
  const [loading, setLoading] = useState(true);
  const [curriculums, setCurriculums] = useState<any[]>([]);
  const [filter, setFilter] = useState('ALL');
  const [searchTerm, setSearchTerm] = useState('');

  useEffect(() => {
    if (!auth.isAuthenticated()) {
      router.push('/login');
      return;
    }

    loadCurriculums();
  }, [filter]);

  const loadCurriculums = async () => {
    try {
      let response;
      if (filter === 'ALL') {
        response = await curriculumAPI.getAll();
      } else {
        response = await curriculumAPI.getByStatus(filter);
      }
      setCurriculums(response.data);
    } catch (error) {
      console.error('Failed to load curriculums:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!searchTerm.trim()) {
      loadCurriculums();
      return;
    }

    try {
      const response = await curriculumAPI.search(searchTerm);
      setCurriculums(response.data);
    } catch (error) {
      console.error('Search failed:', error);
    }
  };

  const handleDelete = async (id: number) => {
    if (!confirm('Are you sure you want to delete this curriculum?')) return;

    try {
      await curriculumAPI.delete(id);
      loadCurriculums();
    } catch (error) {
      console.error('Delete failed:', error);
      alert('Failed to delete curriculum');
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

  const statusCounts = {
    ALL: curriculums.length,
    ACTIVE: curriculums.filter((c) => c.status === 'ACTIVE').length,
    DRAFT: curriculums.filter((c) => c.status === 'DRAFT').length,
    ARCHIVED: curriculums.filter((c) => c.status === 'ARCHIVED').length,
  };

  return (
    <div className="min-h-screen bg-gray-50">
      <Navbar />

      <div className="max-w-7xl mx-auto py-6 sm:px-6 lg:px-8">
        {/* Header */}
        <div className="px-4 py-6 sm:px-0">
          <div className="flex justify-between items-center">
            <div>
              <h1 className="text-3xl font-bold text-gray-900">Curriculums Management 📚</h1>
              <p className="mt-2 text-gray-600">Manage all learning curriculums</p>
            </div>
            <Link
              href="/curriculums/create"
              className="px-6 py-3 bg-primary-600 text-white rounded-lg hover:bg-primary-700 transition-colors font-medium"
            >
              ➕ Create Curriculum
            </Link>
          </div>
        </div>

        {/* Filter Tabs */}
        <div className="mt-8 px-4 sm:px-0">
          <div className="border-b border-gray-200">
            <nav className="-mb-px flex space-x-8">
              {(['ALL', 'ACTIVE', 'DRAFT', 'ARCHIVED'] as const).map((status) => (
                <button
                  key={status}
                  onClick={() => setFilter(status)}
                  className={`${
                    filter === status
                      ? 'border-primary-500 text-primary-600'
                      : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'
                  } whitespace-nowrap py-4 px-1 border-b-2 font-medium text-sm transition-colors`}
                >
                  {status} ({statusCounts[status]})
                </button>
              ))}
            </nav>
          </div>
        </div>

        {/* Search Bar */}
        <div className="mt-6 px-4 sm:px-0">
          <form onSubmit={handleSearch} className="flex gap-4">
            <input
              type="text"
              placeholder="Search curriculums by name..."
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
                  loadCurriculums();
                }}
                className="px-6 py-2 bg-gray-200 text-gray-700 rounded-lg hover:bg-gray-300"
              >
                Clear
              </button>
            )}
          </form>
        </div>

        {/* Curriculums Grid */}
        <div className="mt-8">
          {curriculums && curriculums.length > 0 ? (
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
              {curriculums.map((curriculum) => (
                <div
                  key={curriculum.id}
                  className="bg-white shadow rounded-lg overflow-hidden hover:shadow-lg transition-shadow"
                >
                  <div className="p-6">
                    <div className="flex items-start justify-between">
                      <div className="flex-1">
                        <div className="flex items-center">
                          <span className="text-3xl mr-3">📚</span>
                          <h3 className="text-lg font-semibold text-gray-900 line-clamp-2">
                            {curriculum.name}
                          </h3>
                        </div>
                        <div className="mt-3">
                          <span
                            className={`inline-block px-3 py-1 rounded-full text-xs font-medium ${
                              curriculum.status === 'ACTIVE'
                                ? 'bg-green-100 text-green-800'
                                : curriculum.status === 'DRAFT'
                                ? 'bg-yellow-100 text-yellow-800'
                                : 'bg-gray-100 text-gray-800'
                            }`}
                          >
                            {curriculum.status}
                          </span>
                        </div>
                      </div>
                    </div>

                    <div className="mt-4 space-y-2 text-sm text-gray-500">
                      <p>📅 Created: {new Date(curriculum.createdAt).toLocaleDateString()}</p>
                      {curriculum.unitList && (
                        <p>📖 {curriculum.unitList.length} Units</p>
                      )}
                    </div>

                    <div className="mt-6 flex space-x-2">
                      <Link
                        href={`/curriculums/${curriculum.id}`}
                        className="flex-1 px-4 py-2 bg-blue-600 text-white text-center rounded-lg hover:bg-blue-700 text-sm"
                      >
                        View
                      </Link>
                      <Link
                        href={`/curriculums/${curriculum.id}/edit`}
                        className="flex-1 px-4 py-2 bg-primary-600 text-white text-center rounded-lg hover:bg-primary-700 text-sm"
                      >
                        Edit
                      </Link>
                      <button
                        onClick={() => handleDelete(curriculum.id)}
                        className="px-4 py-2 bg-red-600 text-white rounded-lg hover:bg-red-700 text-sm"
                      >
                        🗑️
                      </button>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          ) : (
            <div className="bg-white shadow rounded-lg p-12 text-center">
              <span className="text-6xl">📚</span>
              <p className="mt-4 text-gray-500">No curriculums found</p>
              <p className="text-sm text-gray-400 mt-2">
                {searchTerm || filter !== 'ALL'
                  ? 'Try adjusting your filters'
                  : 'Create your first curriculum to get started'}
              </p>
              {!searchTerm && filter === 'ALL' && (
                <Link
                  href="/curriculums/create"
                  className="mt-6 inline-block px-6 py-3 bg-primary-600 text-white rounded-lg hover:bg-primary-700"
                >
                  ➕ Create Curriculum
                </Link>
              )}
            </div>
          )}
        </div>
      </div>
    </div>
  );
}

