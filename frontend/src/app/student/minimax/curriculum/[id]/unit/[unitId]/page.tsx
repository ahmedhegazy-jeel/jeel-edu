// app/student/curriculum/[id]/unit/[unitId]/page.tsx - Lesson Path
'use client'
import { useState, useEffect, AwaitedReactNode, JSXElementConstructor, Key, ReactElement, ReactNode, ReactPortal, } from 'react';
import Link from 'next/link';
import { useParams } from 'next/navigation';
import { studentAPI2 } from '@/lib/api';

export default function LessonPath() {
    const params = useParams();
    const { id: curriculumId, unitId } = params;
    const [lessonPath, setLessonPath] = useState<any>(null);
    const [unit, setUnit] = useState<any>(null);
    const [loading, setLoading] = useState(true);
 
    useEffect(() => {
        
        const fetchData = async () => { 
            try {
                // Fetch lesson path
                const pathResponse = await studentAPI2.getLessonPath(parseInt(unitId as string)) ;//fetch(`/api/student/lesson-path/${unitId}`);
                const pathData = pathResponse.data;
                setLessonPath(pathData);

                // Fetch unit details
                const unitResponse = await studentAPI2.getUnits(parseInt(curriculumId as string)) ;//fetch(`/api/student/units/${curriculumId}`);
                const unitsData = unitResponse.data;
                const currentUnit = unitsData.find((u: { id: { toString: () => string | string[]; }; }) => u.id.toString() === unitId);
                setUnit(currentUnit);
            } catch (error) {
                console.error('Error fetching lesson path:', error);
            } finally {
                setLoading(false);
            }
        };

        if (curriculumId && unitId) {
            fetchData();
        }
    }, [curriculumId, unitId]);

    // Generate dynamic path based on lesson count
    const generatePath = (lessonCount: number) => {
        const width = 800;
        const height = 400;
        const stepX = width / (lessonCount + 1);
        
        let path = `M 50 ${height/2}`;
        
        for (let i = 0; i < lessonCount; i++) {
            const x = 50 + (stepX * (i + 1));
            const y = height/2 + Math.sin(i * 0.8) * 120;
            const controlX = x - stepX/2;
            const controlY = height/2 + Math.sin(i * 0.8 - 0.4) * 120;
            
            if (i === 0) {
                path += ` L ${x} ${y}`;
            } else {
                path += ` Q ${controlX} ${controlY} ${x} ${y}`;
            }
        }
        
        return { path, width, height };
    };

    // Calculate lesson positions for the path
    const calculateLessonPositions = (lessonCount: number) => {
        const positions = [];
        const width = 800;
        const height = 400;
        const stepX = width / (lessonCount + 1);
        
        for (let i = 0; i < lessonCount; i++) {
            const x = 50 + (stepX * (i + 1));
            const y = height/2 + Math.sin(i * 0.8) * 120;
            positions.push({ x, y, index: i });
        }
        
        return positions;
    };

    if (loading) {
        return (
            <div className="flex items-center justify-center h-64">
                <div className="animate-spin rounded-full h-16 w-16 border-b-2 border-[#4ecdc4]"></div>
            </div>
        );
    }

    if (!lessonPath || !unit) {
        return (
            <div className="text-center py-12">
                <h2 className="text-2xl font-bold text-[#2d3436] mb-4">Lesson path not found</h2>
                <Link href={`/student/minimax//curriculum/${curriculumId}`} className="text-[#4ecdc4] hover:underline">
                    ← Back to Units
                </Link>
            </div>
        );
    }

    const pathData = generatePath((lessonPath as any).lessons.length);
    const lessonPositions = calculateLessonPositions((lessonPath as any).lessons.length);

    const getLessonIcon = (lesson: { isBossLesson: any; type: string; }) => {
        if (lesson.isBossLesson) return '👑';
        if (lesson.type === 'INTERACTIVE') return '🎮';
        if (lesson.type === 'VIDEO') return '📹';
        return '📚';
    };

    const getLessonStatusColor = (lesson: { status: string; }) => {
        if (lesson.status === 'active') return 'bg-[#4ecdc4]';
        if (lesson.status === 'completed') return 'bg-[#ffe66d]';
        return 'bg-[#b2bec3]';
    };

    const getLessonStatusBorder = (lesson: { status: string; }) => {
        if (lesson.status === 'active') return 'border-[#4ecdc4]';
        if (lesson.status === 'completed') return 'border-[#ffe66d]';
        return 'border-[#b2bec3]';
    };

    return (
        <div className="max-w-7xl mx-auto">
            {/* Header */}
            <div className="bg-gradient-to-r from-[#ff6b6b] to-[#4ecdc4] rounded-3xl p-8 mb-8 text-white">
                <div className="flex items-center justify-between">
                    <div>
                        <Link href={`/student/minimax//curriculum/${curriculumId}`} className="inline-flex items-center text-white/80 hover:text-white mb-4">
                            <svg className="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 19l-7-7 7-7" />
                            </svg>
                            Back to Units
                        </Link>
                        <h1 className="text-4xl font-bold mb-2">{unit.title}</h1>
                        <p className="text-xl opacity-90">{unit.description}</p>
                        <div className="flex items-center space-x-6 mt-4">
                            <div className="bg-white/20 rounded-lg p-3">
                                <span className="text-2xl">📚</span>
                                <span className="ml-2 font-bold">{unit.totalLessons} Lessons</span>
                            </div>
                            <div className="bg-white/20 rounded-lg p-3">
                                <span className="text-2xl">⭐</span>
                                <span className="ml-2 font-bold">{unit.completedLessons}/{unit.totalLessons} Complete</span>
                            </div>
                            <div className="bg-white/20 rounded-lg p-3">
                                <span className="text-2xl">⏱️</span>
                                <span className="ml-2 font-bold">{unit.estimatedMinutes} min</span>
                            </div>
                        </div>
                    </div>
                    <div className="hidden md:block">
                        <img 
                            src={unit.backgroundImage} 
                            alt={unit.title}
                            className="w-24 h-24 object-cover rounded-full border-4 border-white/30"
                        />
                    </div>
                </div>
            </div>

            {/* Lesson Path Visualization */}
            <div className="bg-white rounded-3xl p-8 shadow-lg border-2 border-[#ffe66d] mb-8">
                <div className="text-center mb-6">
                    <h2 className="text-2xl font-bold text-[#2d3436] mb-2">🎯 Your Learning Adventure</h2>
                    <p className="text-gray-600">Follow the path to complete all lessons and unlock your rewards!</p>
                </div>

                {/* SVG Path */}
                <div className="relative bg-gradient-to-b from-[#e3f2fd] to-[#f3e5f5] rounded-2xl p-8 overflow-hidden">
                    <svg 
                        width="800" 
                        height="400" 
                        className="w-full h-auto max-w-4xl mx-auto"
                        viewBox="0 0 800 400"
                    >
                        {/* Path background */}
                        <path
                            d={pathData.path}
                            stroke="#e0e0e0"
                            strokeWidth="8"
                            fill="none"
                            strokeDasharray="10,5"
                            opacity="0.3"
                        />
                        
                        {/* Active path */}
                        <path
                            d={pathData.path}
                            stroke={lessonPath.pathColor}
                            strokeWidth="6"
                            fill="none"
                            className="drop-shadow-lg"
                        />
                        
                        {/* Lesson spots */}
                        {lessonPath.lessons.map((lesson: { id: Key | null | undefined; isLocked: any; lessonNumber: string | number | bigint | boolean | ReactElement<any, string | JSXElementConstructor<any>> | Iterable<ReactNode> | ReactPortal | Promise<AwaitedReactNode> | null | undefined; starsEarned: string | number | bigint | boolean | ReactElement<any, string | JSXElementConstructor<any>> | Iterable<ReactNode> | Promise<AwaitedReactNode> | null | undefined; }, index: string | number) => {
                            const position = lessonPositions[index as number];
                            return (
                                <g key={lesson.id}>
                                    {/* Lesson spot background circle */}
                                    <circle
                                        cx={position.x}
                                        cy={position.y}
                                        r="35"
                                        className={`${getLessonStatusColor(lesson as any)} drop-shadow-lg`}
                                        opacity={lesson.isLocked ? "0.3" : "1"}
                                    />
                                    
                                    {/* Lesson spot border */}
                                    <circle
                                        cx={position.x}
                                        cy={position.y}
                                        r="35"
                                        className={`${getLessonStatusBorder(lesson as any)} border-4`}
                                        fill="none"
                                        opacity={lesson.isLocked ? "0.3" : "1"}
                                    />
                                    
                                    {/* Lesson number */}
                                    <text
                                        x={position.x}
                                        y={position.y - 5}
                                        textAnchor="middle"
                                        className="fill-white font-bold text-lg pointer-events-none"
                                    >
                                        {lesson.lessonNumber}
                                    </text>
                                    
                                    {/* Stars indicator */}
                                    {lesson.starsEarned as number > 0 && (
                                        <text
                                            x={position.x}
                                            y={position.y + 10}
                                            textAnchor="middle"
                                            className="fill-white text-sm font-bold pointer-events-none"
                                        >
                                            ⭐ {lesson.starsEarned}
                                        </text>
                                    )}
                                    
                                    {/* Lock indicator for locked lessons */}
                                    {lesson.isLocked && (
                                        <text
                                            x={position.x}
                                            y={position.y + 15}
                                            textAnchor="middle"
                                            className="fill-gray-500 text-xl pointer-events-none"
                                        >
                                            🔒
                                        </text>
                                    )}
                                </g>
                            );
                        })}
                        
                        {/* Start flag */}
                        <g>
                            <circle cx="50" cy="200" r="20" className="fill-[#4ecdc4]" />
                            <text x="50" y="205" textAnchor="middle" className="fill-white font-bold">🚀</text>
                        </g>
                        
                        {/* End castle */}
                        <g>
                            <rect x="730" y="180" width="40" height="40" className="fill-[#ff6b6b]" rx="5" />
                            <text x="750" y="205" textAnchor="middle" className="fill-white text-xl">🏰</text>
                        </g>
                    </svg>
                </div>

                {/* Interactive Lesson Cards */}
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4 mt-8">
                    {lessonPath.lessons.map((lesson: { id: Key | null | undefined; isLocked: any; lessonNumber: string | number | bigint | boolean | ReactElement<any, string | JSXElementConstructor<any>> | Iterable<ReactNode> | ReactPortal | Promise<AwaitedReactNode> | null | undefined; estimatedMinutes: string | number | bigint | boolean | ReactElement<any, string | JSXElementConstructor<any>> | Iterable<ReactNode> | ReactPortal | Promise<AwaitedReactNode> | null | undefined; title: string | number | bigint | boolean | ReactElement<any, string | JSXElementConstructor<any>> | Iterable<ReactNode> | ReactPortal | Promise<AwaitedReactNode> | null | undefined; status: string; starsEarned: number; isBossLesson: any; }) => (
                        <Link
                            key={lesson.id}
                            href={lesson.isLocked ? '#' : `/student/minimax//curriculum/${curriculumId}/unit/${unitId}/lesson/${lesson.id}`}
                            className={`block transform transition-all duration-300 ${
                                lesson.isLocked 
                                    ? 'cursor-not-allowed opacity-50' 
                                    : 'hover:scale-105 hover:shadow-lg'
                            }`}
                        >
                            <div className={`bg-white rounded-xl p-4 border-2 ${getLessonStatusBorder(lesson)} shadow-md`}>
                                <div className="flex items-center justify-between mb-3">
                                    <div className={`w-12 h-12 rounded-full ${getLessonStatusColor(lesson)} flex items-center justify-center`}>
                                        <span className="text-white text-xl">
                                            {getLessonIcon(lesson as any)}
                                        </span>
                                    </div>
                                    <div className="text-right">
                                        <span className="text-sm font-bold text-gray-500">Lesson {lesson.lessonNumber}</span>
                                        <div className="text-xs text-gray-400">{lesson.estimatedMinutes} min</div>
                                    </div>
                                </div>
                                
                                <h3 className="font-bold text-[#2d3436] mb-2 text-sm">{lesson.title}</h3>
                                
                                <div className="flex items-center justify-between">
                                    <span className={`px-2 py-1 rounded-full text-xs font-medium ${
                                        lesson.status === 'active' ? 'bg-[#4ecdc4] text-white' :
                                        lesson.status === 'completed' ? 'bg-[#ffe66d] text-[#2d3436]' :
                                        'bg-gray-200 text-gray-600'
                                    }`}>
                                        {lesson.isLocked ? '🔒 Locked' : 
                                         lesson.status === 'active' ? '▶️ Active' : 
                                         '✅ Completed'}
                                    </span>
                                    
                                    {lesson.starsEarned > 0 && (
                                        <div className="flex">
                                            {Array.from({ length: lesson.starsEarned }).map((_, i) => (
                                                <span key={i} className="text-yellow-400">⭐</span>
                                            ))}
                                        </div>
                                    )}
                                </div>
                                
                                {lesson.isBossLesson && (
                                    <div className="mt-2 bg-red-100 text-red-700 px-2 py-1 rounded-full text-xs font-bold text-center">
                                        👑 BOSS LESSON
                                    </div>
                                )}
                            </div>
                        </Link>
                    ))}
                </div>
            </div>

            {/* Progress Summary */}
            <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                <div className="bg-[#4ecdc4] text-white rounded-xl p-6 text-center">
                    <div className="text-3xl font-bold mb-2">{unit.completedLessons}</div>
                    <div className="text-sm opacity-90">Lessons Completed</div>
                </div>
                <div className="bg-[#ff6b6b] text-white rounded-xl p-6 text-center">
                    <div className="text-3xl font-bold mb-2">{unit.totalLessons - unit.completedLessons}</div>
                    <div className="text-sm opacity-90">Lessons Remaining</div>
                </div>
                <div className="bg-[#ffe66d] text-[#2d3436] rounded-xl p-6 text-center">
                    <div className="text-3xl font-bold mb-2">{unit.pointsEarned}/{unit.totalPoints}</div>
                    <div className="text-sm opacity-90">Points Earned</div>
                </div>
            </div>
        </div>
    );
}