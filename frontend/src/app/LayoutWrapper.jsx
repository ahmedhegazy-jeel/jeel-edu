// components/LayoutWrapper.jsx
'use client'
import { useState } from 'react'
//import Header from './Header'
//import Sidebar from './Sidebar'
//import Footer from './Footer'
import Header from '@/components/Header'
import Sidebar from '@/components/Sidebar'
import Footer from '@/components/Footer'

export default function LayoutWrapper({ children }) {

    const [sidebarOpen, setSidebarOpen] = useState(false)

  return (
    <div className="flex flex-col min-h-screen">

      {/* Header */}
      <Header onMenuToggle={() => setSidebarOpen(!sidebarOpen)} />
      
      <div className="flex flex-1">
        {/* Sidebar - Hidden on mobile, shown on desktop */}
        <Sidebar isOpen={sidebarOpen} onClose={() => setSidebarOpen(false)} />
        
        {/* Main Content */}
        <main className="flex-1 flex flex-col">
                    {/* Overlay for mobile sidebar */}
                    {sidebarOpen && (
                        <div 
                        className="fixed inset-0 bg-black bg-opacity-50 z-20 lg:hidden"
                        onClick={() => setSidebarOpen(false)}
                        />
                    )}
                    
                    {/* Page Content */}
                    <div className=" p-4 md:p-6 lg:p-8 bg-[#bbeaff]"> {/* flex-1 */}
                        {children}
                    </div>
                    
                    {/* Footer */}
                    <Footer />
        </main>
      </div>
    </div>
  )
}