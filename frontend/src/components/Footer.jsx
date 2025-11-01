// components/Footer.jsx
export default function Footer() {
    return (
      <footer className="bg-white border-t border-gray-200 mt-auto">
        <div className="max-w-7xl mx-auto py-6 px-4 sm:px-6 lg:px-8">
          <div className="flex flex-col md:flex-row justify-between items-center">
            <div className="text-center md:text-left">
              <p className="text-sm text-gray-600">
                &copy; {new Date().getFullYear()} Your Company. All rights reserved.
              </p>
            </div>
            <div className="mt-4 md:mt-0">
              <nav className="flex space-x-6">
                <a href="/privacy" className="text-sm text-gray-600 hover:text-gray-900">
                  Privacy Policy
                </a>
                <a href="/terms" className="text-sm text-gray-600 hover:text-gray-900">
                  Terms of Service
                </a>
                <a href="/contact" className="text-sm text-gray-600 hover:text-gray-900">
                  Contact
                </a>
              </nav>
            </div>
          </div>
        </div>
      </footer>
    )
  }