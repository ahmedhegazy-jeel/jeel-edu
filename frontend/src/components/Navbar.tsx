'use client';

import Link from 'next/link';
import { useRouter } from 'next/navigation';
import { auth } from '@/lib/auth';
import { useState, useEffect } from 'react';

export default function Navbar() {
  const router = useRouter();
  const [user, setUser] = useState<any>(null);
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);
  const [isLanding, setIsLanding] = useState(false);

  useEffect(() => {
    setUser(auth.getCurrentUser());
    setIsLanding(window.location.pathname === '/');
  }, []);

  const handleLogout = async () => {
    await auth.logout();
    router.push('/login');
  };

  const getDashboardLink = () => {
    if (!user) return '/';
    
    const role = user.role;

    if (role.includes('STUDENT')) return '/student/dashboard';
    if (role.includes('TEACHER')) return '/teacher/dashboard';
    if (role.includes('PARENT')) return '/parent/dashboard';
    if (role.includes('SCHOOL_ADMIN')) return '/school-admin/dashboard';
    if (role.includes('SUPER_ADMIN')) return '/super-admin/dashboard';
    return '/';
  };

  const GetLandingLinks = ()=>{
    return (
      <>
      {isLanding && (
        <></>
      )}
      </>
    );
  }

  return (
    <nav className="bg-white shadow-md sticky top-0 z-50">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between h-16">
          {/* Logo */}
          <div className="flex items-center">
            <Link href={getDashboardLink()} className="flex-shrink-0 flex items-center">
            </Link>
          </div>

          {/* Desktop Menu */}
          <div className="hidden md:flex items-center space-x-4">
            {user ? (
              <>
                <GetLandingLinks/>
                <span className="text-blue-700 font-bold hidden lg:inline">
                  {user.firstName} {user.lastName}
                </span>
                <span className="text-xs px-2 py-1 bg-primary-100 text-primary-800 rounded-full">
                  {user.role.replace('ROLE_', '')}
                </span>
                <button
                  onClick={handleLogout}
                  className="px-4 py-2 text-sm font-medium text-white bg-red-600 hover:bg-red-700 rounded-lg transition-colors"
                >
                  Logout
                </button>
              </>
            ) : (
              <>
                <GetLandingLinks/>

                <Link href="/login" className="hidden px-4 py-2 border rounded-lg hover:bg-blue-600 hover:text-white transition">Login2</Link>
                <Link href="/login" className="px-4 py-2 text-sm font-medium text-primary-600 hover:text-primary-700">
                  Login
                </Link>
                <Link
                  href="/register"
                  className="px-4 py-2 text-sm font-medium text-white bg-primary-600 hover:bg-primary-700 rounded-lg transition-colors"
                >
                  Register
                </Link>
              </>
            )}
          </div>

          {/* Mobile Menu Button */}
          <div className="flex items-center md:hidden">
            <button
              onClick={() => setMobileMenuOpen(!mobileMenuOpen)}
              className="inline-flex items-center justify-center p-2 rounded-md text-gray-700 hover:text-primary-600 hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-inset focus:ring-primary-500"
            >
              <span className="sr-only">Open menu</span>
              {mobileMenuOpen ? (
                <svg className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" />
                </svg>
              ) : (
                <svg className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4 6h16M4 12h16M4 18h16" />
                </svg>
              )}
            </button>
          </div>
        </div>
      </div>

      {/* Mobile Menu */}
      {mobileMenuOpen && (
        <div className="md:hidden border-t border-gray-200 bg-white">
          <div className="px-4 pt-2 pb-3 space-y-1">
            {user ? (
              <>
                <div className="px-3 py-2 text-base font-medium text-gray-900 border-b border-gray-200">
                  {user.firstName} {user.lastName}
                  <span className="block text-xs text-gray-500 mt-1">
                    {user.role.replace('ROLE_', '')}
                  </span>
                </div>
                <Link
                  href={getDashboardLink()}
                  className="block px-3 py-2 rounded-md text-base font-medium text-gray-700 hover:text-primary-600 hover:bg-gray-50"
                  onClick={() => setMobileMenuOpen(false)}
                >
                  Dashboard
                </Link>
                {user.role.includes('STUDENT') && (
                  <Link
                    href="/student/learn"
                    className="block px-3 py-2 rounded-md text-base font-medium text-gray-700 hover:text-primary-600 hover:bg-gray-50"
                    onClick={() => setMobileMenuOpen(false)}
                  >
                    🚀 Start Learning
                  </Link>
                )}
                {(user.role.includes('TEACHER') || user.role.includes('ADMIN')) && (
                  <>
                    <Link
                      href="/curriculums"
                      className="block px-3 py-2 rounded-md text-base font-medium text-gray-700 hover:text-primary-600 hover:bg-gray-50"
                      onClick={() => setMobileMenuOpen(false)}
                    >
                      Curriculums
                    </Link>
                    <Link
                      href="/schools"
                      className="block px-3 py-2 rounded-md text-base font-medium text-gray-700 hover:text-primary-600 hover:bg-gray-50"
                      onClick={() => setMobileMenuOpen(false)}
                    >
                      Schools
                    </Link>
                  </>
                )}
                <button
                  onClick={() => {
                    handleLogout();
                    setMobileMenuOpen(false);
                  }}
                  className="w-full text-left px-3 py-2 rounded-md text-base font-medium text-red-600 hover:text-red-700 hover:bg-red-50"
                >
                  Logout
                </button>
              </>
            ) : (
              <>
                <Link
                  href="/login"
                  className="block px-3 py-2 rounded-md text-base font-medium text-gray-700 hover:text-primary-600 hover:bg-gray-50"
                  onClick={() => setMobileMenuOpen(false)}
                >
                  Login
                </Link>
                <Link
                  href="/register"
                  className="block px-3 py-2 rounded-md text-base font-medium text-primary-600 hover:text-primary-700 hover:bg-primary-50"
                  onClick={() => setMobileMenuOpen(false)}
                >
                  Register
                </Link>
              </>
            )}
          </div>
        </div>
      )}
    </nav>
  );
}

