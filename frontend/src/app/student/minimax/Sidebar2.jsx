// components/Sidebar.jsx
'use client'
import Link from 'next/link';

export default function Sidebar2({ isOpen, onClose }) {
    const menuItems = [
        {
            name: 'My Learning',
            icon: '📚',
            href: '/student/minimax/dashboard',
            active: true
        },
        {
            name: 'Math Adventures',
            icon: '🔢',
            href: '/student/minimax/curriculum/1'
        },
        {
            name: 'English Explorer',
            icon: '🌟',
            href: '/student/minimax/curriculum/2'
        },
        {
            name: 'Arabic Discovery',
            icon: '📖',
            href: '/student/minimax/curriculum/3'
        },
        {
            name: 'My Profile',
            icon: '👤',
            href: '/student/minimax/profile'
        },
        {
            name: 'Achievements',
            icon: '🏆',
            href: '/student/minimax/achievements'
        }
    ];

    return (
        <>
            {/* Desktop Sidebar */}
            <div className="hidden lg:flex flex-col w-64 bg-white shadow-xl border-r-4 border-[#4ecdc4]">
                <div className="p-6 border-b-2 border-[#ffe66d]">
                    <h2 className="text-lg font-bold text-[#2d3436]">Navigation</h2>
                </div>
                
                <nav className="flex-1 p-4">
                    <ul className="space-y-2">
                        {menuItems.map((item, index) => (
                            <li key={index}>
                                <Link 
                                    href={item.href}
                                    className={`flex items-center space-x-3 p-3 rounded-lg transition-all duration-200 hover:bg-[#fffbf0] ${
                                        item.active 
                                            ? 'bg-[#4ecdc4] text-white shadow-lg transform scale-105' 
                                            : 'text-[#2d3436] hover:shadow-md'
                                    }`}
                                >
                                    <span className="text-2xl">{item.icon}</span>
                                    <span className="font-medium">{item.name}</span>
                                    {item.active && (
                                        <span className="ml-auto w-2 h-2 bg-[#ffe66d] rounded-full"></span>
                                    )}
                                </Link>
                            </li>
                        ))}
                    </ul>
                </nav>
            </div>

            {/* Mobile Sidebar */}
            {isOpen && (
                <div className="fixed inset-0 z-30 lg:hidden">
                    <div className="fixed inset-0 bg-black bg-opacity-50" onClick={onClose}></div>
                    <div className="fixed left-0 top-0 h-full w-64 bg-white shadow-xl border-r-4 border-[#4ecdc4] transform transition-transform duration-300">
                        <div className="p-6 border-b-2 border-[#ffe66d]">
                            <div className="flex items-center justify-between">
                                <h2 className="text-lg font-bold text-[#2d3436]">Navigation</h2>
                                <button
                                    onClick={onClose}
                                    className="p-2 rounded-lg bg-[#ff6b6b] text-white hover:bg-[#ff5252] transition-colors"
                                >
                                    <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" />
                                    </svg>
                                </button>
                            </div>
                        </div>
                        
                        <nav className="flex-1 p-4">
                            <ul className="space-y-2">
                                {menuItems.map((item, index) => (
                                    <li key={index}>
                                        <Link 
                                            href={item.href}
                                            onClick={onClose}
                                            className={`flex items-center space-x-3 p-3 rounded-lg transition-all duration-200 hover:bg-[#fffbf0] ${
                                                item.active 
                                                    ? 'bg-[#4ecdc4] text-white shadow-lg transform scale-105' 
                                                    : 'text-[#2d3436] hover:shadow-md'
                                            }`}
                                        >
                                            <span className="text-2xl">{item.icon}</span>
                                            <span className="font-medium">{item.name}</span>
                                        </Link>
                                    </li>
                                ))}
                            </ul>
                        </nav>
                    </div>
                </div>
            )}
        </>
    );
}