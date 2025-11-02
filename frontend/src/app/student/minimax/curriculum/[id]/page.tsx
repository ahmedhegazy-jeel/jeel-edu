// app/student/curriculum/[id]/page.tsx - Curriculum Detail
'use client'
import { useState, useEffect } from 'react';
import Link from 'next/link';
import { useParams } from 'next/navigation';
import { studentAPI2 } from '@/lib/api';

export default function CurriculumDetail() {
    const params = useParams();
    const curriculumId = params.id;
    const [units, setUnits] = useState<any[]>([]);
    const [curriculum, setCurriculum] = useState<any>(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => { 
        const fetchCurriculumData = async () => {
            try {
                // Fetch curriculum info
                const curriculumResponse = await studentAPI2.getCurriculums(1) ;//fetch('/api/student/curriculums?schoolId=1');
                const curriculumsData = curriculumResponse.data as any;
                const currentCurriculum = curriculumsData.find((c: { id: { toString: () => string | string[]; }; }) => c.id.toString() === curriculumId);
                setCurriculum(currentCurriculum);

                // Fetch units
                const unitsResponse = await studentAPI2.getUnits(parseInt(curriculumId as string)) ;//fetch(`/api/student/units/${curriculumId}`);
                const unitsData = unitsResponse.data as any;
                setUnits(unitsData);
            } catch (error) {
                console.error('Error fetching curriculum data:', error);
            } finally {
                setLoading(false);
            }
        };

        if (curriculumId) {
            fetchCurriculumData();
        }
    }, [curriculumId]);

    const getThemeIcon = (theme: string) => {
        const icons = {
            'magical-forest': '🌳',
            'shape-realm': '🔷',
            'addition-quest': '➕',
            'default': '⭐'
        };
        return icons[theme as keyof typeof icons] || icons['default'];
    };

    if (loading) {
        return (
            <div className="flex items-center justify-center h-64">
                <div className="animate-spin rounded-full h-16 w-16 border-b-2 border-[#4ecdc4]"></div>
            </div>
        );
    }

    if (!curriculum) {
        return (
            <div className="text-center py-12">
                <h2 className="text-2xl font-bold text-[#2d3436] mb-4">Curriculum not found</h2>
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
                        <h1 className="text-4xl font-bold mb-2">{curriculum.name}</h1>
                        <p className="text-xl opacity-90">{curriculum.description}</p>
                        <div className="flex items-center space-x-6 mt-4">
                            <div className="bg-white/20 rounded-lg p-3">
                                <span className="text-2xl">{curriculum.subjectIcon}</span>
                                <span className="ml-2 font-bold">{curriculum.gradeLevel}</span>
                            </div>
                            <div className="bg-white/20 rounded-lg p-3">
                                <span className="text-2xl">📚</span>
                                <span className="ml-2 font-bold">{curriculum.totalUnits} Units</span>
                            </div>
                            <div className="bg-white/20 rounded-lg p-3">
                                <span className="text-2xl">⭐</span>
                                <span className="ml-2 font-bold">{curriculum.totalPointsEarned} Points</span>
                            </div>
                        </div>
                    </div>
                    <div className="hidden md:block">
                        <img 
                            src={curriculum.subjectIcon} 
                            alt={curriculum.name}
                            className="w-24 h-24 object-cover rounded-full border-4 border-white/30"
                        />
                    </div>
                </div>
                
                {/* Progress Bar */}
                <div className="mt-6">
                    <div className="flex justify-between text-sm mb-2">
                        <span>Overall Progress</span>
                        <span>{curriculum.progressPercentage}%</span>
                    </div>
                    <div className="w-full bg-white/20 rounded-full h-3">
                        <div 
                            className="bg-white h-3 rounded-full transition-all duration-500"
                            style={{ width: `${curriculum.progressPercentage}%` }}
                        ></div>
                    </div>
                </div>
            </div>

            {/* Units Grid */}
            <div className="mb-8">
                <h2 className="text-3xl font-bold text-[#2d3436] mb-6">📖 Learning Units</h2>
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                    {units.map((unit) => (
                        <Link 
                            key={unit.id}
                            href={`/student/minimax//curriculum/${curriculumId}/unit/${unit.id}`}
                            className="group"
                        >
                            <div className="bg-white rounded-2xl p-6 shadow-lg border-2 border-[#ffe66d] hover:border-[#4ecdc4] transform transition-all duration-300 hover:scale-105 hover:shadow-2xl">
                                <div className="flex items-center justify-between mb-4">
                                    <div className="w-12 h-12 bg-gradient-to-br from-[#4ecdc4] to-[#45b7af] rounded-full flex items-center justify-center">
                                        <span className="text-white font-bold text-xl">{unit.unitNumber}</span>
                                    </div>
                                    <div className="bg-[#fffbf0] rounded-full p-2">
                                        <span className="text-2xl">{getThemeIcon(unit.theme)}</span>
                                    </div>
                                </div>
                                
                                <h3 className="text-xl font-bold text-[#2d3436] mb-2">{unit.title}</h3>
                                <p className="text-gray-600 text-sm mb-4 line-clamp-2">{unit.description}</p>
                                
                                {/* Progress */}
                                <div className="mb-4">
                                    <div className="flex justify-between text-sm mb-1">
                                        <span>Progress</span>
                                        <span>{unit.completedLessons}/{unit.totalLessons}</span>
                                    </div>
                                    <div className="w-full bg-gray-200 rounded-full h-2">
                                        <div 
                                            className="bg-[#4ecdc4] h-2 rounded-full transition-all duration-500"
                                            style={{ width: `${(unit.completedLessons / unit.totalLessons) * 100}%` }}
                                        ></div>
                                    </div>
                                </div>
                                
                                {/* Stats */}
                                <div className="flex items-center justify-between text-sm text-gray-500">
                                    <span>⏱️ {unit.estimatedMinutes} min</span>
                                    <span>⭐ {unit.pointsEarned}/{unit.totalPoints}</span>
                                </div>
                            </div>
                        </Link>
                    ))}
                </div>
            </div>

            {/* Achievement Preview */}
            <div className="bg-[#fffbf0] rounded-2xl p-6 border-2 border-[#ffe66d]">
                <h3 className="text-xl font-bold text-[#2d3436] mb-4">🏆 Unit Achievements</h3>
                <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
                    <div className="text-center">
                        <div className="w-16 h-16 bg-yellow-100 rounded-full flex items-center justify-center mx-auto mb-2">
                            <span className="text-2xl">🥇</span>
                        </div>
                        <p className="text-sm font-medium text-[#2d3436]">First Unit</p>
                    </div>
                    <div className="text-center">
                        <div className="w-16 h-16 bg-green-100 rounded-full flex items-center justify-center mx-auto mb-2">
                            <span className="text-2xl">✅</span>
                        </div>
                        <p className="text-sm font-medium text-[#2d3436]">Perfect Score</p>
                    </div>
                    <div className="text-center">
                        <div className="w-16 h-16 bg-blue-100 rounded-full flex items-center justify-center mx-auto mb-2">
                            <span className="text-2xl">⚡</span>
                        </div>
                        <p className="text-sm font-medium text-[#2d3436]">Speed Learner</p>
                    </div>
                    <div className="text-center">
                        <div className="w-16 h-16 bg-purple-100 rounded-full flex items-center justify-center mx-auto mb-2">
                            <span className="text-2xl">👑</span>
                        </div>
                        <p className="text-sm font-medium text-[#2d3436]">Unit Master</p>
                    </div>
                </div>
            </div>
        </div>
    );
}