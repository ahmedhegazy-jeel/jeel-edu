// app/student/profile/page.tsx - Student Profile
'use client'
import { useState, useEffect } from 'react';
import Link from 'next/link';
import { studentAPI2 } from '@/lib/api';

export default function StudentProfile() {
    const [profile, setProfile] = useState<any>(null);
    const [loading, setLoading] = useState(true);
    const [selectedAvatar, setSelectedAvatar] = useState<string>('bear-explorer');

    useEffect(() => {
        const fetchProfile = async () => {
            try {
                const response = await studentAPI2.getStudentProfile(1) ;//fetch('/api/student/profile/1');
                const data = response.data as any;
                setProfile(data);
                setSelectedAvatar(data.avatarUrl.split('/').pop().replace('.png', ''));
            } catch (error) {
                console.error('Error fetching profile:', error);
            } finally {
                setLoading(false);
            }
        };

        fetchProfile(); 
    }, []);

    const availableAvatars = [
        { id: 'bear-explorer', name: 'Bear Explorer', emoji: '🐻' },
        { id: 'cat-scholar', name: 'Cat Scholar', emoji: '🐱' },
        { id: 'fox-adventurer', name: 'Fox Adventurer', emoji: '🦊' },
        { id: 'panda-happy', name: 'Happy Panda', emoji: '🐼' },
        { id: 'owl-wise', name: 'Wise Owl', emoji: '🦉' },
        { id: 'rabbit-student', name: 'Student Rabbit', emoji: '🐰' },
        { id: 'elephant-gentle', name: 'Gentle Elephant', emoji: '🐘' },
        { id: 'penguin-cool', name: 'Cool Penguin', emoji: '🐧' },
        { id: 'lion-brave', name: 'Brave Lion', emoji: '🦁' }
    ];

    const availableBadges = [
        { id: 'math-champion', name: 'Math Champion', icon: '🥇', rarity: 'rare', earned: true },
        { id: 'streak-master', name: 'Streak Master', icon: '🔥', rarity: 'rare', earned: true },
        { id: 'knowledge-gem', name: 'Knowledge Seeker', icon: '💎', rarity: 'common', earned: true },
        { id: 'perfect-score', name: 'Perfect Score', icon: '⭐', rarity: 'epic', earned: false },
        { id: 'speed-learner', name: 'Speed Learner', icon: '⚡', rarity: 'rare', earned: false },
        { id: 'unit-master', name: 'Unit Master', icon: '👑', rarity: 'legendary', earned: false }
    ];

    const getRarityColor = (rarity: string) => {
        const colors = {
            'common': 'border-gray-300 bg-gray-50',
            'rare': 'border-blue-300 bg-blue-50',
            'epic': 'border-purple-300 bg-purple-50',
            'legendary': 'border-yellow-300 bg-yellow-50'
        };
        return colors[rarity as keyof typeof colors] || colors['common'];
    };

    if (loading) {
        return (
            <div className="flex items-center justify-center h-64">
                <div className="animate-spin rounded-full h-16 w-16 border-b-2 border-[#4ecdc4]"></div>
            </div>
        );
    }

    if (!profile) {
        return (
            <div className="text-center py-12">
                <h2 className="text-2xl font-bold text-[#2d3436] mb-4">Profile not found</h2>
                <Link href="/student/minimax/" className="text-[#4ecdc4] hover:underline">
                    ← Back to Dashboard
                </Link>
            </div>
        );
    }

    return (
        <div className="max-w-6xl mx-auto">
            {/* Header */}
            <div className="bg-gradient-to-r from-[#4ecdc4] to-[#45b7af] rounded-3xl p-8 mb-8 text-white">
                <div className="flex items-center justify-between">
                    <div>
                        <Link href="/student/minimax/" className="inline-flex items-center text-white/80 hover:text-white mb-4">
                            <svg className="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 19l-7-7 7-7" />
                            </svg>
                            Back to Dashboard
                        </Link>
                        <h1 className="text-4xl font-bold mb-2">My Profile 👤</h1>
                        <p className="text-xl opacity-90">Manage your learning journey</p>
                    </div>
                    <div className="hidden md:block">
                        <div className="w-24 h-24 rounded-full border-4 border-white/30 overflow-hidden">
                            <img 
                                src={profile.avatarUrl} 
                                alt="Profile"
                                className="w-full h-full object-cover"
                            />
                        </div>
                    </div>
                </div>
            </div>

            <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
                {/* Profile Info */}
                <div className="lg:col-span-1">
                    <div className="bg-white rounded-2xl p-6 shadow-lg border-2 border-[#4ecdc4] mb-6">
                        <div className="text-center mb-6">
                            <div className="w-20 h-20 rounded-full border-4 border-[#ffe66d] overflow-hidden mx-auto mb-4">
                                <img 
                                    src={profile.avatarUrl} 
                                    alt="Profile"
                                    className="w-full h-full object-cover"
                                />
                            </div>
                            <h2 className="text-2xl font-bold text-[#2d3436]">{profile.firstName} {profile.lastName}</h2>
                            <p className="text-gray-600">@{profile.username}</p>
                            <p className="text-sm text-gray-500">{profile.schoolName}</p>
                        </div>

                        {/* Stats */}
                        <div className="grid grid-cols-2 gap-4">
                            <div className="text-center bg-[#fffbf0] rounded-lg p-3">
                                <div className="text-2xl font-bold text-[#2d3436]">{profile.currentLevel}</div>
                                <div className="text-xs text-gray-600">Level</div>
                            </div>
                            <div className="text-center bg-[#fffbf0] rounded-lg p-3">
                                <div className="text-2xl font-bold text-[#ff6b6b]">{profile.totalPoints}</div>
                                <div className="text-xs text-gray-600">Points</div>
                            </div>
                            <div className="text-center bg-[#fffbf0] rounded-lg p-3">
                                <div className="text-2xl font-bold text-[#4ecdc4]">{profile.currentStreak}</div>
                                <div className="text-xs text-gray-600">Day Streak</div>
                            </div>
                            <div className="text-center bg-[#fffbf0] rounded-lg p-3">
                                <div className="text-2xl font-bold text-[#a29bfe]">{profile.totalLessonsCompleted}</div>
                                <div className="text-xs text-gray-600">Lessons</div>
                            </div>
                        </div>
                    </div>

                    {/* Level Progress */}
                    <div className="bg-white rounded-2xl p-6 shadow-lg border-2 border-[#ffe66d]">
                        <h3 className="text-lg font-bold text-[#2d3436] mb-4">📈 Level Progress</h3>
                        <div className="mb-2">
                            <div className="flex justify-between text-sm mb-1">
                                <span>Level {profile.currentLevel}</span>
                                <span>Level {profile.currentLevel + 1}</span>
                            </div>
                            <div className="w-full bg-gray-200 rounded-full h-3">
                                <div className="bg-gradient-to-r from-[#4ecdc4] to-[#45b7af] h-3 rounded-full" style={{ width: '60%' }}></div>
                            </div>
                        </div>
                        <p className="text-sm text-gray-600 text-center">{profile.levelProgress}</p>
                    </div>
                </div>

                {/* Avatar Selection & Badges */}
                <div className="lg:col-span-2">
                    {/* Avatar Selection */}
                    <div className="bg-white rounded-2xl p-6 shadow-lg border-2 border-[#4ecdc4] mb-6">
                        <h3 className="text-xl font-bold text-[#2d3436] mb-4">🎭 Choose Your Avatar</h3>
                        <div className="grid grid-cols-3 md:grid-cols-5 gap-4">
                            {availableAvatars.map((avatar) => (
                                <button
                                    key={avatar.id}
                                    onClick={() => setSelectedAvatar(avatar.id)}
                                    className={`p-3 rounded-xl border-2 transition-all duration-300 transform hover:scale-105 ${
                                        selectedAvatar === avatar.id 
                                            ? 'border-[#4ecdc4] bg-[#4ecdc4]/10' 
                                            : 'border-gray-200 hover:border-[#4ecdc4]'
                                    }`}
                                >
                                    <div className="text-3xl mb-1">{avatar.emoji}</div>
                                    <div className="text-xs font-medium text-[#2d3436]">{avatar.name}</div>
                                </button>
                            ))}
                        </div>
                    </div>

                    {/* Badges Collection */}
                    <div className="bg-white rounded-2xl p-6 shadow-lg border-2 border-[#ffe66d]">
                        <h3 className="text-xl font-bold text-[#2d3436] mb-4">🏆 Badge Collection</h3>
                        <div className="grid grid-cols-2 md:grid-cols-3 gap-4">
                            {availableBadges.map((badge) => (
                                <div
                                    key={badge.id}
                                    className={`p-4 rounded-xl border-2 ${getRarityColor(badge.rarity)} ${
                                        !badge.earned ? 'opacity-50 grayscale' : ''
                                    } transition-all duration-300 hover:scale-105`}
                                >
                                    <div className="text-center">
                                        <div className="text-3xl mb-2">{badge.icon}</div>
                                        <h4 className="font-bold text-sm text-[#2d3436] mb-1">{badge.name}</h4>
                                        <span className={`text-xs px-2 py-1 rounded-full font-medium ${
                                            badge.rarity === 'legendary' ? 'bg-yellow-200 text-yellow-800' :
                                            badge.rarity === 'epic' ? 'bg-purple-200 text-purple-800' :
                                            badge.rarity === 'rare' ? 'bg-blue-200 text-blue-800' :
                                            'bg-gray-200 text-gray-800'
                                        }`}>
                                            {badge.rarity.toUpperCase()}
                                        </span>
                                        {!badge.earned && (
                                            <div className="mt-2 text-xs text-gray-500">
                                                🔒 Not earned yet
                                            </div>
                                        )}
                                    </div>
                                </div>
                            ))}
                        </div>
                    </div>
                </div>
            </div>

            {/* Recent Achievements */}
            <div className="mt-8">
                <div className="bg-[#fffbf0] rounded-2xl p-6 border-2 border-[#ffe66d]">
                    <h3 className="text-xl font-bold text-[#2d3436] mb-4">🎉 Recent Achievements</h3>
                    <div className="space-y-4">
                        {profile.recentAchievements.map((achievement: any, index:number) => (
                            <div key={index} className="flex items-center space-x-4 bg-white rounded-lg p-4 border border-[#ffe66d]">
                                <div className="w-12 h-12 rounded-full bg-[#4ecdc4] flex items-center justify-center">
                                    <span className="text-xl">{achievement.iconUrl.split('/').pop().replace('.png', '') === 'math-champion' ? '🥇' : achievement.iconUrl.split('/').pop().replace('.png', '') === 'streak-master' ? '🔥' : '💎'}</span>
                                </div>
                                <div className="flex-1">
                                    <h4 className="font-bold text-[#2d3436]">{achievement.name}</h4>
                                    <p className="text-sm text-gray-600">{achievement.description}</p>
                                </div>
                                <div className="text-right">
                                    <div className="text-xs text-gray-500">
                                        {new Date(achievement.earnedAt).toLocaleDateString()}
                                    </div>
                                </div>
                            </div>
                        ))}
                    </div>
                </div>
            </div>

            {/* Study Statistics */}
            <div className="mt-8 grid grid-cols-1 md:grid-cols-3 gap-6">
                <div className="bg-[#4ecdc4] text-white rounded-xl p-6 text-center">
                    <div className="text-3xl mb-2">⏰</div>
                    <div className="text-2xl font-bold mb-1">{profile.totalTimeSpentHours}h</div>
                    <div className="text-sm opacity-90">Study Time</div>
                </div>
                <div className="bg-[#ff6b6b] text-white rounded-xl p-6 text-center">
                    <div className="text-3xl mb-2">📚</div>
                    <div className="text-2xl font-bold mb-1">{profile.totalLessonsCompleted}</div>
                    <div className="text-sm opacity-90">Lessons Completed</div>
                </div>
                <div className="bg-[#ffe66d] text-[#2d3436] rounded-xl p-6 text-center">
                    <div className="text-3xl mb-2">🔥</div>
                    <div className="text-2xl font-bold mb-1">{profile.currentStreak}</div>
                    <div className="text-sm opacity-90">Day Streak</div>
                </div>
            </div>
        </div>
    );
}