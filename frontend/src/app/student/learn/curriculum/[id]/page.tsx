'use client';

import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import Link from 'next/link';
import Navbar from '@/components/Navbar';
import { auth } from '@/lib/auth';
import { curriculumAPI, unitAPI, progressAPI } from '@/lib/api';

export default function StudentCurriculumPage({ params }: { params: { id: string } }) {
  const router = useRouter();
  const [loading, setLoading] = useState(true);
  const [curriculum, setCurriculum] = useState<any>(null);
  const [units, setUnits] = useState<any[]>([]);
  const [progress, setProgress] = useState<any>(null);
  const [enrolling, setEnrolling] = useState(false);

  useEffect(() => {
    if (!auth.isAuthenticated()) {
      router.push('/login');
      return;
    }

    loadData();
  }, [params.id]);

  const loadData = async () => {
    try {
      const [curriculumRes, unitsRes, progressRes] = await Promise.all([
        curriculumAPI.getById(parseInt(params.id)),
        unitAPI.getByCurriculum(parseInt(params.id)),
        progressAPI.getMyProgress(),
      ]);

      setCurriculum(curriculumRes.data);
      setUnits(unitsRes.data.filter((u: any) => u.status === 'ACTIVE'));
      
      const myProgress = progressRes.data.find((p: any) => p.curriculum?.id === parseInt(params.id));
      setProgress(myProgress);
    } catch (error) {
      console.error('Failed to load data:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleEnroll = async () => {
    setEnrolling(true);
    try {
      const user = auth.getCurrentUser();
      if (user) {
        await progressAPI.enrollStudent(user.id, parseInt(params.id));
        loadData();
      }
    } catch (error) {
      console.error('Enrollment failed:', error);
      alert('Failed to enroll in curriculum');
    } finally {
      setEnrolling(false);
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
            href="/student/learn"
            className="inline-flex items-center text-primary-600 hover:text-primary-700 mb-4"
          >
            ← Back to All Curriculums
          </Link>
          
          <div className="bg-white rounded-2xl shadow-xl p-8">
            <div className="flex items-start justify-between">
              <div className="flex items-start">
                <span className="text-6xl mr-6">{curriculum.icon || '📚'}</span>
                <div>
                  <h1 className="text-4xl font-extrabold text-gray-900 mb-2">
                    {curriculum.name}
                  </h1>
                  {progress ? (
                    <div className="space-y-3">
                      <div className="flex items-center space-x-4">
                        <span className="text-lg text-gray-600">
                          🎯 {progress.completedActivities} / {progress.totalActivities} activities
                        </span>
                        <span className="text-lg text-gray-600">
                          ⭐ {progress.totalPoints} points
                        </span>
                      </div>
                      <div className="w-full max-w-md">
                        <div className="flex justify-between text-sm mb-1">
                          <span className="text-gray-600">Your Progress</span>
                          <span className="font-bold text-primary-600">
                            {Math.round(progress.completionPercentage)}%
                          </span>
                        </div>
                        <div className="w-full bg-gray-200 rounded-full h-4">
                          <div
                            className="bg-gradient-to-r from-green-400 via-blue-500 to-purple-500 h-4 rounded-full transition-all duration-500"
                            style={{ width: `${progress.completionPercentage}%` }}
                          ></div>
                        </div>
                      </div>
                    </div>
                  ) : (
                    <p className="text-lg text-gray-600">
                      Ready to start your learning journey?
                    </p>
                  )}
                </div>
              </div>
              {!progress && (
                <button
                  onClick={handleEnroll}
                  disabled={enrolling}
                  className="px-8 py-3 bg-gradient-to-r from-green-500 to-blue-500 text-white rounded-full font-bold text-lg hover:from-green-600 hover:to-blue-600 transition-all transform hover:scale-105 disabled:opacity-50"
                >
                  {enrolling ? 'Enrolling...' : '🎓 Enroll Now'}
                </button>
              )}
            </div>
          </div>
        </div>

        {/* Units Grid */}
        <div className="mt-12 px-4 sm:px-0">
          <h2 className="text-3xl font-bold text-gray-900 mb-6 flex items-center">
            <span className="mr-3">📖</span>
            Learning Units
          </h2>

          {units && units.length > 0 ? (
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
              {units.map((unit, index) => (
                <Link
                  key={unit.id}
                  href={`/student/learn/unit/${unit.id}`}
                  className="group block"
                >
                  <div className="bg-white rounded-2xl shadow-lg overflow-hidden hover:shadow-2xl transition-all duration-300 transform hover:-translate-y-2">
                    {/* Gradient Top Bar */}
                    <div className={`h-3 bg-gradient-to-r ${
                      index % 4 === 0 ? 'from-blue-400 to-blue-600' :
                      index % 4 === 1 ? 'from-purple-400 to-purple-600' :
                      index % 4 === 2 ? 'from-pink-400 to-pink-600' :
                      'from-green-400 to-green-600'
                    }`}></div>
                    
                    <div className="p-6">
                      {/* Unit Number Badge */}
                      <div className="flex items-start justify-between mb-4">
                        <div className={`w-14 h-14 rounded-full bg-gradient-to-br ${
                          index % 4 === 0 ? 'from-blue-400 to-blue-600' :
                          index % 4 === 1 ? 'from-purple-400 to-purple-600' :
                          index % 4 === 2 ? 'from-pink-400 to-pink-600' :
                          'from-green-400 to-green-600'
                        } flex items-center justify-center text-white font-bold text-2xl shadow-lg`}>
                          {index + 1}
                        </div>
                        <span className="text-3xl">{unit.icon || '📖'}</span>
                      </div>

                      <h3 className="text-xl font-bold text-gray-900 mb-3 group-hover:text-primary-600 transition-colors">
                        {unit.name}
                      </h3>

                      <div className="flex items-center space-x-3 text-sm text-gray-600 mb-4">
                        <span>📝 {unit.lessonList?.length || 0} Lessons</span>
                        {unit.audioName && <span>🔊 Audio</span>}
                      </div>

                      <div className="mt-4 text-center">
                        <span className="inline-block px-4 py-2 bg-gradient-to-r from-primary-500 to-purple-500 text-white rounded-full text-sm font-medium group-hover:from-primary-600 group-hover:to-purple-600 transition-all">
                          Explore Unit →
                        </span>
                      </div>
                    </div>
                  </div>
                </Link>
              ))}
            </div>
          ) : (
            <div className="bg-white rounded-2xl shadow-lg p-12 text-center">
              <span className="text-6xl">📖</span>
              <p className="mt-4 text-xl text-gray-500">No units available yet</p>
              <p className="text-sm text-gray-400 mt-2">
                Content is being prepared. Check back soon!
              </p>
            </div>
          )}
        </div>

        {/* Motivational Banner */}
        {progress && progress.completionPercentage > 0 && (
          <div className="mt-12 px-4 sm:px-0">
            <div className="bg-gradient-to-r from-yellow-400 via-orange-400 to-red-400 rounded-2xl shadow-xl p-8 text-white text-center">
              <h3 className="text-3xl font-bold mb-2">
                {progress.completionPercentage < 25 ? '🌱 Great Start!' :
                 progress.completionPercentage < 50 ? '🌿 Keep Going!' :
                 progress.completionPercentage < 75 ? '🌳 Amazing Progress!' :
                 progress.completionPercentage < 100 ? '🌟 Almost There!' :
                 '🏆 Congratulations!'}
              </h3>
              <p className="text-lg">
                {progress.completionPercentage < 100
                  ? `You're ${Math.round(progress.completionPercentage)}% through this curriculum. Keep up the great work!`
                  : 'You completed this curriculum! 🎉'}
              </p>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}

