
export default function FooterPage() {
    return (


        <footer id="contact" className="bg-gray-900 text-gray-300 py-10">
            <div className="max-w-7xl mx-auto px-6 grid md:grid-cols-3 gap-8">
            <div>
                <h3 className="text-xl font-bold text-white mb-2">Kids Learn Hub</h3>
                <p className="text-sm">Arabic & Values education for modern learners — bridging fun and growth.</p>
            </div>
            <div>
                <h4 className="text-lg font-semibold text-white mb-2">Quick Links</h4>
                <ul className="space-y-2 text-sm">
                <li><a href="#home" className="hover:text-white">Home</a></li>
                <li><a href="#curriculum" className="hover:text-white">Curriculums</a></li>
                <li><a href="#how" className="hover:text-white">How it Works</a></li>
                </ul>
            </div>  
            <div>
                <h4 className="text-lg font-semibold text-white mb-2">Contact</h4>
                <p className="text-sm">Email: hello@kidslearnhub.example</p>
                <p className="text-sm mt-2">© 2025 Kids Learn Hub. All rights reserved.</p>
            </div>
            </div>
        </footer>
    );
}
