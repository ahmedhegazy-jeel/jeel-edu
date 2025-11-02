// app/student/curriculum/[id]/unit/[unitId]/lesson/[lessonId]/page.tsx - Lesson Content
'use client'
import { useState, useEffect } from 'react';
import Link from 'next/link';
import { useParams } from 'next/navigation';
import { studentAPI2 } from '@/lib/api';

export default function LessonContent() {
    const params = useParams();
    const { id: curriculumId, unitId, lessonId } = params;
    const [lessonContent, setLessonContent] = useState<any>(null);
    const [currentBlockIndex, setCurrentBlockIndex] = useState<number>(0);
    const [loading, setLoading] = useState(true);
    const [progress, setProgress] = useState<number>(0); 

    useEffect(() => {
        const fetchLessonContent = async () => {
            try {
                const response = await studentAPI2.getLessonContent(parseInt(lessonId as string)) ;//fetch(`/api/student/lesson-content/${lessonId}`);
                const data = response.data as any;
                setLessonContent(data);
            } catch (error) {
                console.error('Error fetching lesson content:', error);
            } finally {
                setLoading(false);
            }
        };

        if (lessonId) {
            fetchLessonContent();
        }
    }, [lessonId]);

    const nextBlock = () => {
        if (lessonContent && currentBlockIndex < lessonContent.contentBlocks.length - 1) {
            setCurrentBlockIndex(currentBlockIndex + 1);
            setProgress(((currentBlockIndex + 1) / lessonContent.contentBlocks.length) * 100);
        }
    };

    const previousBlock = () => {
        if (currentBlockIndex > 0) {
            setCurrentBlockIndex(currentBlockIndex - 1);
            setProgress((currentBlockIndex / lessonContent.contentBlocks.length) * 100);
        }
    };

    const renderContentBlock = (block: any) => {
        switch (block.type) {
            case 'TEXT':
                return (
                    <div className="bg-white rounded-xl p-8 shadow-lg">
                        <p className="text-lg leading-relaxed text-[#2d3436]">{block.textContent}</p>
                    </div>
                );
            
            case 'VIDEO':
                return (
                    <div className="bg-white rounded-xl p-8 shadow-lg">
                        <div className="aspect-video bg-gray-200 rounded-lg flex items-center justify-center mb-4">
                            <div className="text-center">
                                <div className="text-6xl mb-4">📹</div>
                                <p className="text-gray-600">Video: {block.caption}</p>
                                <button className="mt-4 bg-[#4ecdc4] text-white px-6 py-2 rounded-full hover:bg-[#45b7af] transition-colors">
                                    ▶️ Play Video
                                </button>
                            </div>
                        </div>
                    </div>
                );
            
            case 'INTERACTIVE':
                return (
                    <div className="bg-white rounded-xl p-8 shadow-lg">
                        <div className="text-center">
                            <div className="text-6xl mb-4">🎮</div>
                            <h3 className="text-xl font-bold text-[#2d3436] mb-4">Interactive Activity</h3>
                            <p className="text-gray-600 mb-6">{block.textContent}</p>
                            <button className="bg-[#ff6b6b] text-white px-6 py-2 rounded-full hover:bg-[#ff5252] transition-colors">
                                🚀 Start Activity
                            </button>
                        </div>
                    </div>
                );
            
            case 'IMAGE_OVERLAY':
                return (
                    <div className="bg-white rounded-xl p-8 shadow-lg">
                        <div className="relative aspect-video bg-gray-200 rounded-lg overflow-hidden">
                            <div className="absolute inset-0 bg-black bg-opacity-50 flex items-center justify-center">
                                <div className="text-center text-white">
                                    <p className="text-xl font-bold mb-2">{block.overlayText}</p>
                                    <p className="text-sm opacity-90">{block.caption}</p>
                                </div>
                            </div>
                        </div>
                        <p className="text-center text-gray-600 mt-4">{block.textContent}</p>
                    </div>
                );
            
            default:
                return (
                    <div className="bg-white rounded-xl p-8 shadow-lg">
                        <p className="text-gray-600">Content type not supported: {block.type}</p>
                    </div>
                );
        }
    };

    if (loading) {
        return (
            <div className="flex items-center justify-center h-64">
                <div className="animate-spin rounded-full h-16 w-16 border-b-2 border-[#4ecdc4]"></div>
            </div>
        );
    }

    if (!lessonContent) {
        return (
            <div className="text-center py-12">
                <h2 className="text-2xl font-bold text-[#2d3436] mb-4">Lesson not found</h2>
                <Link href={`/student/minimax//curriculum/${curriculumId}/unit/${unitId}`} className="text-[#4ecdc4] hover:underline">
                    ← Back to Lesson Path
                </Link>
            </div>
        );
    }

    const currentBlock = lessonContent.contentBlocks[currentBlockIndex];

    return (
        <div className="max-w-4xl mx-auto">
            {/* Header */}
            <div className="bg-gradient-to-r from-[#4ecdc4] to-[#45b7af] rounded-3xl p-8 mb-8 text-white">
                <div className="flex items-center justify-between">
                    <div>
                        <Link href={`/student/minimax//curriculum/${curriculumId}/unit/${unitId}`} className="inline-flex items-center text-white/80 hover:text-white mb-4">
                            <svg className="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 19l-7-7 7-7" />
                            </svg>
                            Back to Lesson Path
                        </Link>
                        <h1 className="text-3xl font-bold mb-2">{lessonContent.title}</h1>
                        <div className="flex items-center space-x-4">
                            <span className="bg-white/20 rounded-lg px-3 py-1">⏱️ {lessonContent.estimatedMinutes} min</span>
                            <span className="bg-white/20 rounded-lg px-3 py-1">⭐ Max {lessonContent.maxStars} stars</span>
                        </div>
                    </div>
                    <div className="hidden md:block">
                        <img 
                            src="/images/assets/icons/lesson-spot.png" 
                            alt="Lesson"
                            className="w-16 h-16 object-cover rounded-full border-4 border-white/30"
                        />
                    </div>
                </div>
                
                {/* Progress Bar */}
                <div className="mt-6">
                    <div className="flex justify-between text-sm mb-2">
                        <span>Progress</span>
                        <span>{Math.round(progress)}% Complete</span>
                    </div>
                    <div className="w-full bg-white/20 rounded-full h-3">
                        <div 
                            className="bg-white h-3 rounded-full transition-all duration-500"
                            style={{ width: `${progress}%` }}
                        ></div>
                    </div>
                </div>
            </div>

            {/* Content Block */}
            <div className="mb-8">
                {currentBlock && renderContentBlock(currentBlock)}
            </div>

            {/* Navigation */}
            <div className="flex items-center justify-between mb-8">
                <button
                    onClick={previousBlock}
                    disabled={currentBlockIndex === 0}
                    className={`flex items-center space-x-2 px-6 py-3 rounded-full font-medium transition-all ${
                        currentBlockIndex === 0
                            ? 'bg-gray-200 text-gray-400 cursor-not-allowed'
                            : 'bg-[#4ecdc4] text-white hover:bg-[#45b7af] hover:scale-105'
                    }`}
                >
                    <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 19l-7-7 7-7" />
                    </svg>
                    <span>Previous</span>
                </button>

                <div className="text-center">
                    <span className="text-sm text-gray-500">
                        Step {currentBlockIndex + 1} of {lessonContent.contentBlocks.length}
                    </span>
                    <div className="flex space-x-1 mt-2">
                        {lessonContent.contentBlocks.map((_: any , index: number) => (
                            <div
                                key={index}
                                className={`w-2 h-2 rounded-full ${
                                    index === currentBlockIndex ? 'bg-[#4ecdc4]' :
                                    index < currentBlockIndex ? 'bg-[#ffe66d]' :
                                    'bg-gray-300'
                                }`}
                            />
                        ))}
                    </div>
                </div>

                <button
                    onClick={nextBlock}
                    disabled={currentBlockIndex === lessonContent.contentBlocks.length - 1}
                    className={`flex items-center space-x-2 px-6 py-3 rounded-full font-medium transition-all ${
                        currentBlockIndex === lessonContent.contentBlocks.length - 1
                            ? 'bg-gray-200 text-gray-400 cursor-not-allowed'
                            : 'bg-[#4ecdc4] text-white hover:bg-[#45b7af] hover:scale-105'
                    }`}
                >
                    <span>Next</span>
                    <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 5l7 7-7 7" />
                    </svg>
                </button>
            </div>

            {/* Assessment Section */}
            {currentBlockIndex === lessonContent.contentBlocks.length - 1 && (
                <div className="bg-[#fffbf0] rounded-2xl p-8 border-2 border-[#ffe66d]">
                    <h3 className="text-xl font-bold text-[#2d3436] mb-4">🎯 Quick Assessment</h3>
                    <div className="space-y-4">
                        {lessonContent.assessmentQuestions.map((question: string, index:number) => (
                            <div key={index} className="bg-white rounded-lg p-4 border border-[#ffe66d]">
                                <p className="font-medium text-[#2d3436] mb-2">{index + 1}. {question}</p>
                                <div className="space-y-2">
                                    {['A', 'B', 'C', 'D'].map((option) => (
                                        <label key={option} className="flex items-center space-x-2 cursor-pointer hover:bg-gray-50 p-2 rounded">
                                            <input type="radio" name={`question-${index}`} className="text-[#4ecdc4]" />
                                            <span className="text-gray-700">{option}. Sample answer option</span>
                                        </label>
                                    ))}
                                </div>
                            </div>
                        ))}
                    </div>
                    
                    <div className="flex justify-center mt-6">
                        <button className="bg-[#ff6b6b] text-white px-8 py-3 rounded-full font-bold hover:bg-[#ff5252] transition-colors transform hover:scale-105">
                            🎯 Submit Assessment
                        </button>
                    </div>
                </div>
            )}
        </div>
    );
}