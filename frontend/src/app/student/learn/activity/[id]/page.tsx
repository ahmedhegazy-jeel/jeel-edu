'use client';

import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import Link from 'next/link';
import Navbar from '@/components/Navbar';
import { auth } from '@/lib/auth';
import { activityAPI, progressAPI } from '@/lib/api';

export default function StudentActivityPage({ params }: { params: { id: string } }) {
  const router = useRouter();
  const [loading, setLoading] = useState(true);
  const [activity, setActivity] = useState<any>(null);
  const [started, setStarted] = useState(false);
  const [completed, setCompleted] = useState(false);
  const [showSuccess, setShowSuccess] = useState(false);

  // Quiz state
  const [currentQuestion, setCurrentQuestion] = useState(0);
  const [selectedAnswers, setSelectedAnswers] = useState<string[]>([]);
  const [quizCompleted, setQuizCompleted] = useState(false);
  const [quizScore, setQuizScore] = useState(0);

  // Book state
  const [currentPage, setCurrentPage] = useState(0);

  useEffect(() => {
    if (!auth.isAuthenticated()) {
      router.push('/login');
      return;
    }

    loadActivity();
  }, [params.id]);

  const loadActivity = async () => {
    try {
      const response = await activityAPI.getById(parseInt(params.id));
      setActivity(response.data);
    } catch (error) {
      console.error('Failed to load activity:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleStart = async () => {
    try {
      await progressAPI.startActivity(parseInt(params.id));
      setStarted(true);
    } catch (error) {
      console.error('Failed to start activity:', error);
    }
  };

  const handleComplete = async (earnedPoints: number, passed: boolean = true) => {
    try {
      await progressAPI.completeActivity(parseInt(params.id), earnedPoints, passed);
      setCompleted(true);
      setShowSuccess(true);
      
      setTimeout(() => {
        router.push(`/student/learn/lesson/${activity.lesson?.id}`);
      }, 3000);
    } catch (error) {
      console.error('Failed to complete activity:', error);
    }
  };

  const handleQuizSubmit = async () => {
    if (!activity.questionsList) return;

    let correct = 0;
    activity.questionsList.forEach((q: any, i: number) => {
      if (selectedAnswers[i] === q.correctAnswer) {
        correct++;
      }
    });

    const score = (correct / activity.questionsList.length) * 100;
    const passed = score >= (activity.percentageToPass || 70);
    const earnedPoints = passed ? activity.points : Math.round(activity.points * 0.5);

    setQuizScore(score);
    setQuizCompleted(true);

    try {
      await progressAPI.submitQuiz(
        parseInt(params.id),
        correct,
        0 // timeSpent - can be tracked with timer
      );
      
      setTimeout(() => {
        handleComplete(earnedPoints, passed);
      }, 2000);
    } catch (error) {
      console.error('Failed to submit quiz:', error);
    }
  };

  if (loading) {
    return (
      <div>
        <Navbar />
        <div className="flex items-center justify-center min-h-screen">
          <div className="text-xl">Loading activity...</div>
        </div>
      </div>
    );
  }

  if (!activity) {
    return (
      <div>
        <Navbar />
        <div className="flex items-center justify-center min-h-screen">
          <div className="text-center">
            <p className="text-xl text-gray-500">Activity not found</p>
            <Link href="/student/learn" className="mt-4 inline-block text-primary-600">
              ← Back to Learning
            </Link>
          </div>
        </div>
      </div>
    );
  }

  // Success Modal
  if (showSuccess) {
    return (
      <div>
        <Navbar />
        <div className="flex items-center justify-center min-h-screen bg-gradient-to-br from-green-100 to-blue-100">
          <div className="text-center bg-white rounded-3xl shadow-2xl p-12 max-w-md animate-bounce">
            <div className="text-8xl mb-6">🎉</div>
            <h2 className="text-4xl font-bold text-gray-900 mb-4">Great Job!</h2>
            <p className="text-xl text-gray-600 mb-6">
              You earned <span className="text-3xl font-bold text-yellow-500">⭐ {activity.points}</span> points!
            </p>
            <p className="text-sm text-gray-500">Redirecting...</p>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 via-purple-50 to-pink-50">
      <Navbar />

      <div className="max-w-5xl mx-auto py-6 sm:px-6 lg:px-8">
        <div className="px-4 py-6 sm:px-0">
          <Link
            href={`/student/learn/lesson/${activity.lesson?.id}`}
            className="inline-flex items-center text-primary-600 hover:text-primary-700 mb-4"
          >
            ← Back to Lesson
          </Link>

          {/* Activity Header */}
          <div className="bg-white rounded-2xl shadow-xl p-8 mb-8">
            <div className="flex items-start justify-between">
              <div className="flex items-start">
                <span className="text-6xl mr-6">{getActivityIcon(activity.activityType)}</span>
                <div>
                  <h1 className="text-3xl font-extrabold text-gray-900 mb-2">
                    {activity.titleName}
                  </h1>
                  {activity.desc && (
                    <p className="text-lg text-gray-600 mb-4">{activity.desc}</p>
                  )}
                  <div className="flex items-center space-x-4">
                    <span className="px-4 py-2 bg-yellow-100 text-yellow-800 rounded-full font-bold">
                      ⭐ {activity.points} Points
                    </span>
                    <span className="px-4 py-2 bg-purple-100 text-purple-800 rounded-full font-medium">
                      {activity.activityType}
                    </span>
                    {activity.tag && (
                      <span className="px-4 py-2 bg-blue-100 text-blue-800 rounded-full">
                        🏷️ {activity.tag}
                      </span>
                    )}
                  </div>
                </div>
              </div>
            </div>
          </div>

          {/* Activity Content */}
          <div className="bg-white rounded-2xl shadow-xl p-8">
            {!started ? (
              // Start Screen
              <div className="text-center py-12">
                <div className="text-8xl mb-6">🚀</div>
                <h2 className="text-3xl font-bold text-gray-900 mb-4">Ready to Start?</h2>
                <p className="text-lg text-gray-600 mb-8">
                  Click the button below to begin this activity!
                </p>
                <button
                  onClick={handleStart}
                  className="px-12 py-4 bg-gradient-to-r from-green-500 to-blue-500 text-white rounded-full text-xl font-bold hover:from-green-600 hover:to-blue-600 transition-all transform hover:scale-110 shadow-lg"
                >
                  🎯 Start Activity
                </button>
              </div>
            ) : (
              // Activity Type Specific Content
              <>
                {/* TEXT Activity */}
                {activity.activityType === 'TEXT' && (
                  <div className="prose max-w-none">
                    <div className="mb-6">
                      {activity.textAudio && (
                        <div className="mb-4 p-4 bg-blue-50 rounded-xl">
                          <p className="text-sm text-blue-700 mb-2">🔊 Listen to the text:</p>
                          <audio controls className="w-full">
                            <source src={activity.textAudio} type="audio/mpeg" />
                            Your browser does not support audio.
                          </audio>
                        </div>
                      )}
                    </div>
                    <div className="text-lg leading-relaxed text-gray-800 whitespace-pre-wrap">
                      {activity.text}
                    </div>
                    <div className="mt-8 text-center">
                      <button
                        onClick={() => handleComplete(activity.points)}
                        className="px-8 py-3 bg-gradient-to-r from-green-500 to-blue-500 text-white rounded-full text-lg font-bold hover:from-green-600 hover:to-blue-600 transition-all"
                      >
                        ✓ Mark as Complete
                      </button>
                    </div>
                  </div>
                )}

                {/* PDF Activity */}
                {activity.activityType === 'PDF' && (
                  <div>
                    {activity.audioOfPDF && (
                      <div className="mb-6 p-4 bg-red-50 rounded-xl">
                        <p className="text-sm text-red-700 mb-2">🔊 Audio explanation:</p>
                        <audio controls className="w-full">
                          <source src={activity.audioOfPDF} type="audio/mpeg" />
                        </audio>
                      </div>
                    )}
                    <div className="bg-gray-100 rounded-xl p-8 text-center min-h-[500px] flex items-center justify-center">
                      <div>
                        <span className="text-6xl">📑</span>
                        <p className="mt-4 text-lg text-gray-700">PDF Viewer</p>
                        <a
                          href={activity.pdfURL}
                          target="_blank"
                          rel="noopener noreferrer"
                          className="mt-4 inline-block px-6 py-3 bg-red-600 text-white rounded-full hover:bg-red-700"
                        >
                          📄 Open PDF
                        </a>
                      </div>
                    </div>
                    <div className="mt-8 text-center">
                      <button
                        onClick={() => handleComplete(activity.points)}
                        className="px-8 py-3 bg-gradient-to-r from-green-500 to-blue-500 text-white rounded-full text-lg font-bold hover:from-green-600 hover:to-blue-600"
                      >
                        ✓ Mark as Complete
                      </button>
                    </div>
                  </div>
                )}

                {/* AUDIO Activity */}
                {activity.activityType === 'AUDIO' && (
                  <div className="text-center py-8">
                    <div className="text-8xl mb-8">🔊</div>
                    <h3 className="text-2xl font-bold text-gray-900 mb-8">Listen Carefully!</h3>
                    
                    <div className="max-w-xl mx-auto space-y-6">
                      {activity.audioWithMusic && (
                        <div className="bg-purple-50 rounded-xl p-6">
                          <p className="text-lg font-semibold text-purple-900 mb-3">
                            🎵 With Background Music
                          </p>
                          <audio controls className="w-full">
                            <source src={activity.audioWithMusic} type="audio/mpeg" />
                          </audio>
                        </div>
                      )}
                      
                      {activity.audioWithoutMusic && (
                        <div className="bg-blue-50 rounded-xl p-6">
                          <p className="text-lg font-semibold text-blue-900 mb-3">
                            🎧 Without Background Music
                          </p>
                          <audio controls className="w-full">
                            <source src={activity.audioWithoutMusic} type="audio/mpeg" />
                          </audio>
                        </div>
                      )}
                    </div>

                    <div className="mt-8">
                      <button
                        onClick={() => handleComplete(activity.points)}
                        className="px-8 py-3 bg-gradient-to-r from-green-500 to-blue-500 text-white rounded-full text-lg font-bold hover:from-green-600 hover:to-blue-600"
                      >
                        ✓ Mark as Complete
                      </button>
                    </div>
                  </div>
                )}

                {/* VIDEO Activity */}
                {activity.activityType === 'VIDEO' && (
                  <div>
                    <div className="text-center mb-6">
                      <h3 className="text-2xl font-bold text-gray-900">🎥 Watch & Learn</h3>
                    </div>
                    
                    <div className="space-y-6">
                      {activity.videoWithMusic && (
                        <div className="bg-pink-50 rounded-xl p-6">
                          <p className="text-lg font-semibold text-pink-900 mb-3 text-center">
                            🎵 With Background Music
                          </p>
                          <div className="bg-gray-900 rounded-xl aspect-video flex items-center justify-center">
                            <a
                              href={activity.videoWithMusic}
                              target="_blank"
                              rel="noopener noreferrer"
                              className="text-white text-6xl hover:text-pink-400"
                            >
                              ▶️
                            </a>
                          </div>
                        </div>
                      )}
                      
                      {activity.videoWithoutMusic && (
                        <div className="bg-blue-50 rounded-xl p-6">
                          <p className="text-lg font-semibold text-blue-900 mb-3 text-center">
                            🎬 Without Background Music
                          </p>
                          <div className="bg-gray-900 rounded-xl aspect-video flex items-center justify-center">
                            <a
                              href={activity.videoWithoutMusic}
                              target="_blank"
                              rel="noopener noreferrer"
                              className="text-white text-6xl hover:text-blue-400"
                            >
                              ▶️
                            </a>
                          </div>
                        </div>
                      )}
                    </div>

                    <div className="mt-8 text-center">
                      <button
                        onClick={() => handleComplete(activity.points)}
                        className="px-8 py-3 bg-gradient-to-r from-green-500 to-blue-500 text-white rounded-full text-lg font-bold hover:from-green-600 hover:to-blue-600"
                      >
                        ✓ Mark as Complete
                      </button>
                    </div>
                  </div>
                )}

                {/* BOOK Activity */}
                {activity.activityType === 'BOOK' && activity.pages && (
                  <div>
                    <div className="mb-6 text-center">
                      <h3 className="text-2xl font-bold text-gray-900">
                        📖 Page {currentPage + 1} of {activity.pages.length}
                      </h3>
                    </div>

                    <div className="bg-gradient-to-br from-yellow-50 to-orange-50 rounded-2xl p-8 min-h-[500px]">
                      {activity.pages[currentPage] && (
                        <div className="space-y-6">
                          {activity.pages[currentPage].image && (
                            <div className="text-center">
                              <img
                                src={activity.pages[currentPage].image}
                                alt={`Page ${currentPage + 1}`}
                                className="max-w-full h-auto rounded-xl shadow-lg mx-auto"
                              />
                            </div>
                          )}
                          
                          {activity.pages[currentPage].text && (
                            <div className="text-lg text-gray-800 leading-relaxed">
                              {activity.pages[currentPage].text}
                            </div>
                          )}

                          {activity.pages[currentPage].audio && (
                            <div className="p-4 bg-white rounded-xl">
                              <p className="text-sm text-gray-700 mb-2">🔊 Listen to this page:</p>
                              <audio controls className="w-full">
                                <source src={activity.pages[currentPage].audio} type="audio/mpeg" />
                              </audio>
                            </div>
                          )}
                        </div>
                      )}
                    </div>

                    <div className="mt-6 flex items-center justify-between">
                      <button
                        onClick={() => setCurrentPage(Math.max(0, currentPage - 1))}
                        disabled={currentPage === 0}
                        className="px-6 py-3 bg-gray-300 text-gray-700 rounded-full font-bold disabled:opacity-50 disabled:cursor-not-allowed hover:bg-gray-400"
                      >
                        ← Previous
                      </button>

                      <div className="flex space-x-2">
                        {activity.pages.map((_: any, i: number) => (
                          <button
                            key={i}
                            onClick={() => setCurrentPage(i)}
                            className={`w-3 h-3 rounded-full ${
                              i === currentPage ? 'bg-primary-600' : 'bg-gray-300'
                            }`}
                          />
                        ))}
                      </div>

                      {currentPage < activity.pages.length - 1 ? (
                        <button
                          onClick={() => setCurrentPage(currentPage + 1)}
                          className="px-6 py-3 bg-gradient-to-r from-primary-500 to-purple-500 text-white rounded-full font-bold hover:from-primary-600 hover:to-purple-600"
                        >
                          Next →
                        </button>
                      ) : (
                        <button
                          onClick={() => handleComplete(activity.points)}
                          className="px-6 py-3 bg-gradient-to-r from-green-500 to-blue-500 text-white rounded-full font-bold hover:from-green-600 hover:to-blue-600"
                        >
                          ✓ Finish Book
                        </button>
                      )}
                    </div>
                  </div>
                )}

                {/* INTERACTIVE Activity */}
                {activity.activityType === 'INTERACTIVE' && (
                  <div className="text-center py-8">
                    <div className="text-8xl mb-6">🎮</div>
                    <h3 className="text-2xl font-bold text-gray-900 mb-4">Interactive Activity</h3>
                    <p className="text-lg text-gray-600 mb-8">
                      Click below to open the interactive learning experience!
                    </p>
                    <a
                      href={activity.externalActivityURL}
                      target="_blank"
                      rel="noopener noreferrer"
                      className="inline-block px-8 py-4 bg-gradient-to-r from-yellow-500 to-orange-500 text-white rounded-full text-lg font-bold hover:from-yellow-600 hover:to-orange-600 shadow-lg"
                    >
                      🎮 Open Interactive Activity
                    </a>
                    <div className="mt-12">
                      <button
                        onClick={() => handleComplete(activity.points)}
                        className="px-8 py-3 bg-gradient-to-r from-green-500 to-blue-500 text-white rounded-full text-lg font-bold hover:from-green-600 hover:to-blue-600"
                      >
                        ✓ Mark as Complete
                      </button>
                    </div>
                  </div>
                )}

                {/* QUIZ Activity */}
                {activity.activityType === 'QUIZ' && activity.questionsList && !quizCompleted && (
                  <div>
                    <div className="mb-6 flex items-center justify-between">
                      <h3 className="text-2xl font-bold text-gray-900">
                        Question {currentQuestion + 1} of {activity.questionsList.length}
                      </h3>
                      <div className="px-4 py-2 bg-orange-100 text-orange-800 rounded-full font-bold">
                        Pass: {activity.percentageToPass}%
                      </div>
                    </div>

                    <div className="bg-gradient-to-br from-orange-50 to-yellow-50 rounded-2xl p-8 mb-6">
                      <p className="text-2xl font-bold text-gray-900 mb-8">
                        {activity.questionsList[currentQuestion].questionText}
                      </p>

                      <div className="space-y-4">
                        {activity.questionsList[currentQuestion].options.map((option: string, i: number) => (
                          <button
                            key={i}
                            onClick={() => {
                              const newAnswers = [...selectedAnswers];
                              newAnswers[currentQuestion] = option;
                              setSelectedAnswers(newAnswers);
                            }}
                            className={`w-full p-4 rounded-xl text-left text-lg font-medium transition-all ${
                              selectedAnswers[currentQuestion] === option
                                ? 'bg-primary-600 text-white shadow-lg transform scale-105'
                                : 'bg-white text-gray-800 hover:bg-gray-100'
                            }`}
                          >
                            {String.fromCharCode(65 + i)}. {option}
                          </button>
                        ))}
                      </div>
                    </div>

                    <div className="flex justify-between">
                      <button
                        onClick={() => setCurrentQuestion(Math.max(0, currentQuestion - 1))}
                        disabled={currentQuestion === 0}
                        className="px-6 py-3 bg-gray-300 text-gray-700 rounded-full font-bold disabled:opacity-50"
                      >
                        ← Previous
                      </button>

                      {currentQuestion < activity.questionsList.length - 1 ? (
                        <button
                          onClick={() => setCurrentQuestion(currentQuestion + 1)}
                          disabled={!selectedAnswers[currentQuestion]}
                          className="px-6 py-3 bg-gradient-to-r from-primary-500 to-purple-500 text-white rounded-full font-bold disabled:opacity-50"
                        >
                          Next →
                        </button>
                      ) : (
                        <button
                          onClick={handleQuizSubmit}
                          disabled={!selectedAnswers[currentQuestion]}
                          className="px-8 py-3 bg-gradient-to-r from-green-500 to-blue-500 text-white rounded-full font-bold disabled:opacity-50"
                        >
                          ✓ Submit Quiz
                        </button>
                      )}
                    </div>
                  </div>
                )}

                {/* Quiz Results */}
                {activity.activityType === 'QUIZ' && quizCompleted && (
                  <div className="text-center py-12">
                    <div className="text-8xl mb-6">
                      {quizScore >= (activity.percentageToPass || 70) ? '🎉' : '📚'}
                    </div>
                    <h2 className="text-4xl font-bold text-gray-900 mb-4">
                      {quizScore >= (activity.percentageToPass || 70) ? 'Excellent Work!' : 'Keep Practicing!'}
                    </h2>
                    <p className="text-2xl text-gray-600 mb-6">
                      Your Score: <span className="font-bold text-primary-600">{Math.round(quizScore)}%</span>
                    </p>
                    <p className="text-lg text-gray-500">
                      {quizScore >= (activity.percentageToPass || 70)
                        ? `You passed! You earned ${activity.points} points! 🌟`
                        : `You need ${activity.percentageToPass}% to pass. Try again!`}
                    </p>
                  </div>
                )}

                {/* HOMEWORK Activity */}
                {activity.activityType === 'HOMEWORK' && (
                  <div>
                    <div className="text-center mb-8">
                      <div className="text-6xl mb-4">📋</div>
                      <h3 className="text-2xl font-bold text-gray-900">Homework Assignment</h3>
                    </div>

                    {activity.homeworkFiles && activity.homeworkFiles.length > 0 && (
                      <div className="mb-8">
                        <h4 className="text-lg font-semibold text-gray-900 mb-4">
                          📎 Download Materials:
                        </h4>
                        <div className="space-y-3">
                          {activity.homeworkFiles.map((file: string, i: number) => (
                            <a
                              key={i}
                              href={file}
                              target="_blank"
                              rel="noopener noreferrer"
                              className="block p-4 bg-blue-50 rounded-xl hover:bg-blue-100 transition-colors"
                            >
                              <span className="text-blue-700">📄 File {i + 1}</span>
                            </a>
                          ))}
                        </div>
                      </div>
                    )}

                    <div className="bg-yellow-50 border-2 border-yellow-200 rounded-xl p-6">
                      <h4 className="text-lg font-semibold text-yellow-900 mb-4">
                        📤 Submit Your Work:
                      </h4>
                      <input
                        type="file"
                        className="w-full p-3 border-2 border-dashed border-yellow-300 rounded-xl bg-white"
                      />
                      <p className="text-sm text-yellow-700 mt-2">
                        Upload your completed homework file
                      </p>
                    </div>

                    <div className="mt-8 text-center">
                      <button
                        onClick={() => handleComplete(activity.points)}
                        className="px-8 py-3 bg-gradient-to-r from-green-500 to-blue-500 text-white rounded-full text-lg font-bold hover:from-green-600 hover:to-blue-600"
                      >
                        ✓ Submit Homework
                      </button>
                    </div>
                  </div>
                )}
              </>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}

