// components/Header.jsx
'use client'
import Link from 'next/link'
import Image from 'next/image'

export default function Header({ onMenuToggle }) {
  return (
    <header className="bg-[#2ccee5] shadow-sm border-b border-gray-200 sticky top-0 z-30">
      <div className="flex items-center justify-between h-16 px-4 sm:px-6 lg:px-8">
        {/* Left Section - Logo and Mobile Menu Button */}
        <div className="flex items-center">
          {/* Mobile Menu Button */}
          <button
            onClick={onMenuToggle}
            className="lg:hidden p-2 rounded-md text-gray-600 hover:text-gray-900 hover:bg-gray-100"
          >
            <svg className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4 6h16M4 12h16M4 18h16" />
            </svg>
          </button>
          
          {/* Logo */}
          <Link href="/" className="ml-2 lg:ml-0 flex items-center">
            <span className="text-xl font-bold text-gray-900">
              <Image src="/images/logo1.png" alt="Logo" width={60} height={60} />
            </span>
          </Link>
        </div>

        {/* Right Section - Navigation & User Menu */}
        <nav className="hidden md:flex items-center space-x-4">
          <Link href="/dashboard" className="text-gray-700 hover:text-gray-900">Dashboard</Link>
          <Link href="/profile" className="text-gray-700 hover:text-gray-900">Profile</Link>
          <Link href="/settings" className="text-gray-700 hover:text-gray-900">Settings</Link>
        </nav>

        {/* Mobile Navigation (optional) */}
        <div className="md:hidden">
          {/* Mobile user menu or additional buttons */}
        </div>
      </div>
    </header>
  )
}