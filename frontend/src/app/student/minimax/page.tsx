// app/student/page.tsx - Dashboard
'use client'
import { useState, useEffect } from 'react';
import Link from 'next/link';
import { studentAPI2 } from '@/lib/api';

export default function StudentDashboard() {
    const [curriculums, setCurriculums] = useState<any>([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        // Fetch curriculums
        const fetchCurriculums = async () => {
            try {
                const response = await studentAPI2.getCurriculums(1) ;//fetch('/api/student/curriculums?schoolId=1');
                const data = response.data as any;
                setCurriculums(data);
            } catch (error) {
                console.error('Error fetching curriculums:', error);
            } finally {
                setLoading(false);
            }
        };

        fetchCurriculums();
    }, []);

    const subjectIcons = {
        'MATH': '🔢',
        'ENGLISH': '🌟',
        'ARABIC': '📖'
    };

    const subjectColors = {
        'MATH': 'from-blue-400 to-blue-600',
        'ENGLISH': 'from-green-400 to-green-600',
        'ARABIC': 'from-purple-400 to-purple-600'
    };

    if (loading) {
        return (
            <div className="flex items-center justify-center h-64">
                <div className="animate-spin rounded-full h-16 w-16 border-b-2 border-[#4ecdc4]"></div>
            </div>
        );
    }

    return (
        <div className="max-w-7xl mx-auto">
            {/* Hero Section */}
            <div className="bg-gradient-to-r from-[#ff6b6b] to-[#4ecdc4] rounded-3xl p-8 mb-8 text-white">
                <div className="flex items-center justify-between">
                    <div>
                        <h1 className="text-4xl font-bold mb-2">Welcome back, Ahmed! 🎉</h1>
                        <p className="text-xl opacity-90">Ready for your next learning adventure?</p>
                        <div className="flex items-center space-x-6 mt-4">
                            <div className="bg-white/20 rounded-lg p-3">
                                <span className="text-2xl">🔥</span>
                                <span className="ml-2 font-bold">7 Day Streak!</span>
                            </div>
                            <div className="bg-white/20 rounded-lg p-3">
                                <span className="text-2xl">⭐</span>
                                <span className="ml-2 font-bold">1,250 Points</span>
                            </div>
                        </div>
                    </div>
                    <div className="hidden md:block">
                        <img 
                            src="/images/assets/avatars/bear-explorer.png" 
                            alt="Student Avatar"
                            className="w-24 h-24 object-cover rounded-full border-4 border-white/30"
                        />
                    </div>
                </div>
            </div>

            {/* Continue Learning Section */}
            <div className="mb-8">
                <h2 className="text-2xl font-bold text-[#2d3436] mb-4">📚 Continue Learning</h2>
                <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                    {curriculums?.map((curriculum: any) => (
                        <Link 
                            key={curriculum.id}
                            href={`/student/curriculum/${curriculum.id}`}
                            className="group"
                        >
                            <div className={`bg-gradient-to-br ${subjectColors[curriculum.subject as keyof typeof subjectColors]} rounded-2xl p-6 text-white transform transition-all duration-300 hover:scale-105 hover:shadow-2xl`}>
                                <div className="flex items-center justify-between mb-4">    
                                    <span className="text-4xl">{subjectIcons[curriculum.subject as keyof typeof subjectIcons]}</span>
                                    <div className="bg-white/20 rounded-full p-2">
                                        <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 5l7 7-7 7" />
                                        </svg>
                                    </div>
                                </div>
                                <h3 className="text-xl font-bold mb-2">{curriculum.name}</h3>
                                <p className="text-sm opacity-90 mb-4">{curriculum.description}</p>
                                
                                {/* Progress Bar */}
                                <div className="mb-4">
                                    <div className="flex justify-between text-sm mb-1">
                                        <span>Progress</span>
                                        <span>{curriculum.progressPercentage}%</span>
                                    </div>
                                    <div className="w-full bg-white/20 rounded-full h-2">
                                        <div 
                                            className="bg-white h-2 rounded-full transition-all duration-500"
                                            style={{ width: `${curriculum.progressPercentage}%` }}
                                        ></div>
                                    </div>
                                </div>
                                
                                <div className="flex items-center justify-between text-sm">
                                    <span>{curriculum.totalUnits} Units</span>
                                    <span>⭐ {curriculum.totalPointsEarned}</span>
                                </div>
                            </div>
                        </Link>
                    ))}
                </div>
            </div>

            {/* Recent Achievements */}
            <div className="mb-8">
                <h2 className="text-2xl font-bold text-[#2d3436] mb-4">🏆 Recent Achievements</h2>
                <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
                    {[
                        { icon: '🥇', title: 'Math Champion', color: 'bg-yellow-100 border-yellow-300' },
                        { icon: '🔥', title: 'Streak Master', color: 'bg-red-100 border-red-300' },
                        { icon: '💎', title: 'Knowledge Seeker', color: 'bg-blue-100 border-blue-300' },
                        { icon: '⭐', title: 'Star Collector', color: 'bg-purple-100 border-purple-300' }
                    ].map((achievement, index) => (
                        <div key={index} className={`${achievement.color} border-2 rounded-xl p-4 text-center transform transition-all duration-300 hover:scale-105`}>
                            <div className="text-3xl mb-2">{achievement.icon}</div>
                            <h4 className="font-bold text-sm text-[#2d3436]">{achievement.title}</h4>
                        </div>
                    ))}
                </div>
            </div>

            {/* Quick Actions */}
            <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
                <Link href="/student/minimax/profile" className="bg-white rounded-xl p-6 shadow-lg border-2 border-[#ffe66d] text-center hover:shadow-xl transition-all duration-300 transform hover:scale-105">
                    <div className="text-3xl mb-2">👤</div>
                    <h3 className="font-bold text-[#2d3436]">My Profile</h3>
                </Link>
                <Link href="/student/minimax/achievements" className="bg-white rounded-xl p-6 shadow-lg border-2 border-[#4ecdc4] text-center hover:shadow-xl transition-all duration-300 transform hover:scale-105">
                    <div className="text-3xl mb-2">🏆</div>
                    <h3 className="font-bold text-[#2d3436]">Achievements</h3>
                </Link>
                <div className="bg-white rounded-xl p-6 shadow-lg border-2 border-[#ff6b6b] text-center hover:shadow-xl transition-all duration-300 transform hover:scale-105 cursor-pointer">
                    <div className="text-3xl mb-2">⏰</div>
                    <h3 className="font-bold text-[#2d3436]">Study Time</h3>
                </div>
                <div className="bg-white rounded-xl p-6 shadow-lg border-2 border-[#a29bfe] text-center hover:shadow-xl transition-all duration-300 transform hover:scale-105 cursor-pointer">
                    <div className="text-3xl mb-2">🎮</div>
                    <h3 className="font-bold text-[#2d3436]">Fun Games</h3>
                </div>
            </div>
        </div>
    );
}