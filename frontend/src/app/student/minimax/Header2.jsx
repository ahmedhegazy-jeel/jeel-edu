// components/Header.jsx
'use client'
import { useState, useEffect } from 'react';

export default function Header2({ onMenuToggle }) {
    const [studentInfo, setStudentInfo] = useState({
        name: "Ahmed",
        avatar: "/images/assets/avatars/bear-explorer.png",
        points: 1250,
        level: 5,
        streak: 7,
        currentCurriculum: "Math Adventures"
    });

    return (
        <header className="bg-white shadow-lg border-b-4 border-[#ff6b6b]">
            <div className="px-4 py-3">
                <div className="flex items-center justify-between">
                    {/* Left section - Menu & Logo */}
                    <div className="flex items-center space-x-4">
                        <button
                            onClick={onMenuToggle}
                            className="lg:hidden p-2 rounded-lg bg-[#4ecdc4] text-white hover:bg-[#45b7af] transition-colors"
                        >
                            <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4 6h16M4 12h16M4 18h16" />
                            </svg>
                        </button>
                        
                        <div className="flex items-center space-x-3">
                            <div className="w-10 h-10 bg-gradient-to-br from-[#ff6b6b] to-[#4ecdc4] rounded-full flex items-center justify-center">
                                <span className="text-white font-bold text-lg">K</span>
                            </div>
                            <h1 className="text-xl font-bold text-gray-800 hidden sm:block">Kids Learn Hub</h1>
                        </div>
                    </div>

                    {/* Center section - Current Activity */}
                    <div className="hidden md:flex items-center space-x-4 bg-[#fffbf0] px-4 py-2 rounded-full border-2 border-[#ffe66d]">
                        <div className="w-8 h-8 bg-[#4ecdc4] rounded-full flex items-center justify-center">
                            <span className="text-white text-sm font-bold">📚</span>
                        </div>
                        <span className="text-[#2d3436] font-medium">{studentInfo.currentCurriculum}</span>
                    </div>

                    {/* Right section - Student Info */}
                    <div className="flex items-center space-x-4">
                        {/* Streak Fire */}
                        <div className="hidden sm:flex items-center space-x-1 bg-[#ff6b6b] px-3 py-1 rounded-full">
                            <span className="text-xl">🔥</span>
                            <span className="text-white font-bold text-sm">{studentInfo.streak}</span>
                        </div>

                        {/* Points */}
                        <div className="hidden sm:flex items-center space-x-1 bg-[#ffe66d] px-3 py-1 rounded-full">
                            <span className="text-xl">⭐</span>
                            <span className="text-[#2d3436] font-bold text-sm">{studentInfo.points}</span>
                        </div>

                        {/* Level */}
                        <div className="hidden md:flex items-center space-x-2 bg-[#4ecdc4] px-3 py-1 rounded-full">
                            <span className="text-white text-sm font-bold">Lv.{studentInfo.level}</span>
                        </div>

                        {/* Avatar */}
                        <div className="w-10 h-10 rounded-full border-3 border-[#ffe66d] overflow-hidden">
                            <img 
                                src={studentInfo.avatar} 
                                alt="Student Avatar"
                                className="w-full h-full object-cover"
                            />
                        </div>
                    </div>
                </div>
            </div>
        </header>
    );
}