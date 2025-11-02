// components/Footer.jsx
export default function Footer2() {
    return (
        <footer className="bg-[#2d3436] text-white py-4 mt-auto">
            <div className="container mx-auto px-4">
                <div className="flex flex-col md:flex-row items-center justify-between">
                    <div className="flex items-center space-x-2 mb-2 md:mb-0">
                        <span className="text-[#4ecdc4]">🌟</span>
                        <span className="text-sm font-medium">Made with love for young learners</span>
                    </div>
                    
                    <div className="flex items-center space-x-4 text-xs text-gray-300">
                        <span>© 2024 Kids Learn Hub</span>
                        <span>•</span>
                        <span>Safe Learning Environment</span>
                        <span>•</span>
                        <span>Parents Dashboard</span>
                    </div>
                </div>
            </div>
        </footer>
    );
}