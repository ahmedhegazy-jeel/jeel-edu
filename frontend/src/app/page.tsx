import Link from 'next/link';

export default function Home() {
  return (
    <main className="flex min-h-screen flex-col items-center justify-center p-24 bg-gradient-to-br from-primary-50 to-secondary-50">
      <div className="z-10 max-w-5xl w-full items-center justify-center font-sans text-sm lg:flex">
        <div className="text-center">
          <h1 className="text-6xl font-bold mb-6 bg-gradient-to-r from-primary-600 to-secondary-600 bg-clip-text text-transparent">
            JeelEducation LMS
          </h1>
          <p className="text-xl text-gray-600 mb-8">
            Modern E-Learning Platform for Kids
          </p>
          <p className="text-lg text-gray-500 mb-12 max-w-2xl mx-auto">
            A comprehensive Learning Management System designed to provide an engaging and
            effective learning experience for children, with powerful management tools for
            teachers and tracking capabilities for parents.
          </p>

          <div className="flex gap-4 justify-center flex-wrap">
            <Link
              href="/login"
              className="px-8 py-3 bg-primary-600 text-white rounded-lg hover:bg-primary-700 transition-colors font-medium"
            >
              Login
            </Link>
            <Link
              href="/register"
              className="px-8 py-3 bg-white text-primary-600 border-2 border-primary-600 rounded-lg hover:bg-primary-50 transition-colors font-medium"
            >
              Register
            </Link>
          </div>

          <div className="mt-16 grid grid-cols-1 md:grid-cols-3 gap-8 text-left">
            <div className="p-6 bg-white rounded-lg shadow-md">
              <h3 className="text-xl font-semibold mb-3 text-primary-700">For Students</h3>
              <p className="text-gray-600">
                Engaging and interactive learning experience with gamification, quizzes, and
                multimedia content.
              </p>
            </div>

            <div className="p-6 bg-white rounded-lg shadow-md">
              <h3 className="text-xl font-semibold mb-3 text-primary-700">For Teachers</h3>
              <p className="text-gray-600">
                Powerful curriculum management tools, student progress tracking, and content
                creation capabilities.
              </p>
            </div>

            <div className="p-6 bg-white rounded-lg shadow-md">
              <h3 className="text-xl font-semibold mb-3 text-primary-700">For Parents</h3>
              <p className="text-gray-600">
                Real-time progress tracking, performance analytics, and insights into your
                child's learning journey.
              </p>
            </div>
          </div>

          <div className="mt-12 text-sm text-gray-500">
            <p>Supported Features:</p>
            <div className="flex gap-6 justify-center mt-4 flex-wrap">
              <span className="px-4 py-2 bg-primary-100 text-primary-800 rounded-full">
                📚 Curriculum Management
              </span>
              <span className="px-4 py-2 bg-secondary-100 text-secondary-800 rounded-full">
                🏫 School Management
              </span>
              <span className="px-4 py-2 bg-green-100 text-green-800 rounded-full">
                📊 Progress Tracking
              </span>
              <span className="px-4 py-2 bg-orange-100 text-orange-800 rounded-full">
                👥 User Management
              </span>
            </div>
          </div>
        </div>
      </div>
    </main>
  );
}

