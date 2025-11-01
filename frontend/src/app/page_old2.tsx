import Image from 'next/image';
import Example from '@/components/marquee_old';
//import { FaBook } from "react-icons/fa";
import { FaBook } from "react-icons/fa6";
import { FaAdn } from "react-icons/fa6";
import { MedalStarIcon } from "@/components/icons/list-icons";
import { HandshakeIcon, LightbulbIcon, PepoleIcon, ScaleIcon } from "@/components/icons/icons";
import { ValueCard } from './landing/value-card';
import Link from 'next/link';
import Navbar from '@/components/Navbar';


//import { ValueCard } from './value-card';

export default function LandingPage() {
    return ( 
        <div className="text-gray-800 bg-white">

                            <div className="fixed left-1/2 transform -translate-x-1/2 top-2 z-50 w-full max-w-md">
                               <div className="bg-[#ad89d0e6]/90 bg-[#6f7ce8e6]/90  backdrop-blur-sm shadow-lg rounded-full mx-4 px-6 py-3">
                                 <nav className="flex justify-between items-center space-x-4">  
                                    <a href="#home" className="hover:text-blue-600">Home</a>
                                    <a href="#curriculum" className="hover:text-blue-600">Curriculums</a>
                                    <a href="#how" className="hover:text-blue-600">How it Works</a>
                                    <a href="#contact" className="hover:text-blue-600">Contact</a>        
                                </nav>
                               </div>
                            </div>
                                           
                            {/* HERO */}
                            {/* bg-[url('/images/kid.png')] bg-cover bg-center */}
                            {/*<section id="home" className="relative bg-gradient-to-r from-blue-600 to-purple-700 text-white"> */}
                            <section id="home" className="relative bg-[url('/images/hero_bg_with_char.png')] bg-cover bg-center" style={{
    backgroundImage: "url('/images/hero_bg_with_char.png')",
  }}>


<div className="absolute inset-0 -z-10">
        <Image
          src="/images/hero_bg_with_char.png"
          alt="Background"
          fill
          className="object-cover object-center"
          priority // Important for above-the-fold images
        />
      </div>


                                <div className="max-w-7xl mx-auto px-6 py-20 grid md:grid-cols-2 gap-10 items-center">  
                                    <div>
                                        <h1 className="text-4xl md:text-5xl font-bold mb-4">Where Learning Meets Fun</h1>
                                        <p className="text-lg mb-6 text-blue-100">Engaging Arabic language and Values education for kids — empowering students, teachers, and parents in a joyful way.</p>
                                        <div className="flex space-x-4">
                                            <Link href="/register" className="px-6 py-3 bg-[#FF9F67] text-blue-700 font-semibold rounded-lg shadow hover:bg-blue-50 transition-colors font-medium" >
                                                Get Started
                                            </Link>
                                            <Link href="#demo" className="px-6 py-3 border border-white/70 rounded-lg hover:bg-white hover:text-blue-700 transition">
                                                For Schools
                                            </Link>
                                        </div>
                                    </div>
                                    <div className="flex justify-center">
                                        {/*<img src="https://cdn-icons-png.flaticon.com/512/201/201818.png" alt="Mascot" className="w-64 h-64 object-contain drop-shadow-xl" />*/}
                                        {/*<img src="/images/kid.png" alt="Mascot" width={256} height={256} />*/}
                                    </div>                                    
                                </div>
                                
                                
                            </section>

                            

                            <Example  arr={['Thoughtful/motivating reward system',
                                            'Fun games and achievement badges make learning joyful',
                                            'Plan, assign, and track progress through simple dashboards',
                                            'Detailed progress reports and personalized recommendations',
                                            'Fun games, interactive Arabic lessons, and achievement badges make learning joyful',
                                            'Plan, assign, and track progress through simple dashboards',
                                            'Stay involved through detailed progress reports and personalized recommendations'
                                          ]} 
                                            icons={['🎮', '📚', '🎧', '🎥', '🎲', '🎯']} />

                            {/* FEATURES */}
                            <section className="max-w-7xl mx-auto px-6 mt-20 text-center">
                                <h2 className="text-3xl font-bold mb-10 text-gray-800">Empowering Everyone in the Learning Journey</h2>
                                <div className="grid md:grid-cols-4 gap-8">
                                    <div className="bg-gradient-to-r from-[#A960EF] to-purple-800 rounded-xl shadow-lg p-6 hover:shadow-xl transition overflow-hidden hover:-translate-y-1">
                                        <img src="/images/school2.png" className="w-20 h-20 mx-auto mb-4" alt="Parents" />
                                        
                                        <h3 className="text-xl font-semibold mb-2">Schools</h3>
                                        <p className="text-white font-bold text-l">Monitor platform usage and performance and Generate comprehensive institutional reports</p>
                                    </div>
                                    <div className="bg-gradient-to-r from-[#A960EF] to-purple-800 rounded-xl shadow-lg p-6 hover:shadow-xl transition overflow-hidden hover:-translate-y-1">
                                        <img src="/images/student1.png" className="w-20 h-20 mx-auto mb-4" alt="Students" />
                                        <h3 className="text-xl font-semibold mb-2">Students</h3>
                                        <p className="text-white font-bold text-l">Fun games, interactive Arabic lessons, and achievement badges make learning joyful</p>
                                    </div>
                                    <div className="bg-gradient-to-r from-[#A960EF] to-purple-800 rounded-xl shadow-lg p-6 hover:shadow-xl overflow-hidden hover:-translate-y-1 transition">
                                        <img src="https://cdn-icons-png.flaticon.com/512/1995/1995574.png" className="w-20 h-20 mx-auto mb-4" alt="Teachers" />
                                        <h3 className="text-xl font-semibold mb-2">Teachers</h3>
                                        <p className="text-white font-bold text-l">Plan, assign, and track progress through simple dashboards and dynamic tools</p>
                                    </div>
                                    <div className="bg-gradient-to-r from-[#A960EF] to-purple-800 rounded-xl shadow-lg p-6 hover:shadow-xl overflow-hidden hover:-translate-y-1 transition">
                                        <img src="/images/family.png" className="w-20 h-20 mx-auto mb-4" alt="Parents" />
                                        
                                        <h3 className="text-xl font-semibold mb-2">Parents</h3>
                                        <p className="text-white font-bold text-l">Stay involved through detailed progress reports and personalized recommendations</p>
                                    </div>
                                </div>
                            </section>

                            

                            
    <section className="max-w-7xl mx-auto px-6 mt-20 text-center">
              <div className=" bg-[#836F95] bg-purple-400 !border-none !text-[#004956] !rounded-full px-4 py-2 flex items-center gap-2 !text-sm font-medium mb-4">              
                        <svg
                            className={''}
                            width="21"
                            height="21"
                            viewBox="0 0 21 21"
                            fill="none"
                            xmlns="http://www.w3.org/2000/svg"
                        >
                            <path
                            d="M10.9061 14.7834C10.5728 14.7834 10.2394 14.7584 9.92275 14.7C8.15609 14.4417 6.54775 13.425 5.53109 11.9167C4.82275 10.85 4.44775 9.60837 4.44775 8.32503C4.44775 4.7667 7.34775 1.8667 10.9061 1.8667C14.4644 1.8667 17.3644 4.7667 17.3644 8.32503C17.3644 9.60837 16.9894 10.85 16.2811 11.9167C15.2561 13.4334 13.6478 14.4417 11.8644 14.7084C11.5728 14.7584 11.2394 14.7834 10.9061 14.7834ZM10.9061 3.1167C8.03109 3.1167 5.69775 5.45003 5.69775 8.32503C5.69775 9.3667 5.99775 10.3667 6.56442 11.2167C7.38942 12.4334 8.68109 13.25 10.1144 13.4584C10.6478 13.55 11.1728 13.55 11.6644 13.4584C13.1228 13.25 14.4144 12.425 15.2394 11.2084C15.8061 10.3584 16.1061 9.35836 16.1061 8.31669C16.1144 5.45002 13.7811 3.1167 10.9061 3.1167Z"
                            fill="#004956"
                            />
                            <path
                            d="M6.29785 19.6503C6.18118 19.6503 6.07285 19.6337 5.95618 19.6087C5.41451 19.4837 4.99785 19.067 4.87285 18.5253L4.58118 17.3003C4.56451 17.2253 4.50618 17.167 4.42285 17.142L3.04785 16.817C2.53118 16.692 2.12285 16.3087 1.98118 15.8003C1.83951 15.292 1.98118 14.742 2.35618 14.367L5.60618 11.117C5.73951 10.9837 5.92285 10.917 6.10618 10.9337C6.28951 10.9503 6.45618 11.0503 6.56451 11.2087C7.38951 12.4253 8.68118 13.2503 10.1228 13.4587C10.6562 13.5503 11.1812 13.5503 11.6728 13.4587C13.1312 13.2503 14.4228 12.4253 15.2478 11.2087C15.3478 11.0503 15.5228 10.9503 15.7062 10.9337C15.8895 10.917 16.0728 10.9837 16.2062 11.117L19.4562 14.367C19.8312 14.742 19.9728 15.292 19.8312 15.8003C19.6895 16.3087 19.2728 16.7003 18.7645 16.817L17.3895 17.142C17.3145 17.1587 17.2562 17.217 17.2312 17.3003L16.9395 18.5253C16.8145 19.067 16.3978 19.4837 15.8562 19.6087C15.3145 19.742 14.7562 19.5503 14.4062 19.1253L10.9062 15.1003L7.40618 19.1337C7.12285 19.467 6.72285 19.6503 6.29785 19.6503ZM5.98118 12.517L3.23951 15.2587C3.16451 15.3337 3.17285 15.417 3.18951 15.467C3.19785 15.5087 3.23951 15.592 3.33951 15.6087L4.71451 15.9337C5.25618 16.0587 5.67285 16.4753 5.79785 17.017L6.08951 18.242C6.11451 18.3503 6.19785 18.3837 6.24785 18.4003C6.29785 18.4087 6.38118 18.417 6.45618 18.3337L9.64785 14.6587C8.23118 14.3837 6.93118 13.6253 5.98118 12.517ZM12.1645 14.6503L15.3562 18.317C15.4312 18.4087 15.5228 18.4087 15.5728 18.392C15.6228 18.3837 15.6978 18.342 15.7312 18.2337L16.0228 17.0087C16.1478 16.467 16.5645 16.0503 17.1062 15.9253L18.4812 15.6003C18.5812 15.5753 18.6228 15.5003 18.6312 15.4587C18.6478 15.417 18.6562 15.3253 18.5812 15.2503L15.8395 12.5087C14.8812 13.617 13.5895 14.3753 12.1645 14.6503Z"
                            fill="#004956"
                            />
                            <path
                            d="M12.4815 11.5671C12.2649 11.5671 12.0065 11.5087 11.6982 11.3254L10.9065 10.8504L10.1149 11.3171C9.38986 11.7504 8.91486 11.5004 8.73986 11.3754C8.56486 11.2504 8.18986 10.8754 8.38153 10.0504L8.58153 9.19207L7.91486 8.57539C7.5482 8.20872 7.41486 7.76707 7.53986 7.36707C7.66486 6.96707 8.03153 6.68373 8.5482 6.60039L9.43986 6.4504L9.86486 5.51707C10.1065 5.04207 10.4815 4.77539 10.9065 4.77539C11.3315 4.77539 11.7149 5.05041 11.9482 5.52541L12.4399 6.50874L13.2649 6.60873C13.7732 6.69207 14.1399 6.97539 14.2732 7.37539C14.3982 7.77539 14.2649 8.21706 13.8982 8.58373L13.2065 9.27541L13.4232 10.0504C13.6149 10.8754 13.2399 11.2504 13.0649 11.3754C12.9732 11.4504 12.7732 11.5671 12.4815 11.5671ZM8.91486 7.81708L9.48986 8.39205C9.75653 8.65872 9.88986 9.10874 9.80653 9.4754L9.6482 10.1421L10.3149 9.75039C10.6732 9.54206 11.1565 9.54206 11.5065 9.75039L12.1732 10.1421L12.0232 9.4754C11.9399 9.1004 12.0649 8.65872 12.3315 8.39205L12.9065 7.81708L12.1815 7.69206C11.8315 7.63372 11.4815 7.37541 11.3232 7.05874L10.9065 6.24207L10.4899 7.0754C10.3399 7.38373 9.98986 7.65041 9.63986 7.70874L8.91486 7.81708Z"
                            fill="#004956"
                            />
                        </svg>
            
                        <h2 className="text-xl font-bold text-gray-800">Our Values</h2>
              </div>
    
      <div className="container mx-auto px-4 sm:px-6 lg:px-8">
        <div className="grid grid-cols-1 lg:grid-cols-2 gap-12 lg:gap-16 xl:gap-20 items-center">
          {/* Text Content - Left Side in RTL */}
          <div className="order-2 lg:order-2">
            <div className="space-y-6">
              {/* القيم Button positioned at the start (right in RTL) */}
              <div className="flex justify-start mb-6">
               
              </div>

              <h2 className="text-3xl md:text-4xl lg:text-5xl font-bold text-[#123665] mb-4 font-cairo">
                    Our Educational Values
              </h2>
              <p className="text-[#666666] text-lg leading-relaxed font-tajawal">
                    We at Jeel Educational Platform believe in the importance of educational values in shaping the 
                    character of future generations. Values such as respect, cooperation, and innovation form the 
                    foundation of our educational environment. We strive to provide students with the knowledge and 
                    skills necessary to achieve their full potential while promoting ethical principles and positive 
                    behaviors. Join us on the journey of learning and growth.                  
              </p>
            </div>
          </div>

          {/* Values Grid - Right Side in RTL */}
          <div className="order-1 lg:order-1">
            <div className="grid grid-cols-2 gap-6">
              <ValueCard
                title={"respect"}
                color="bg-[#5A7EE6]"
                icon={<HandshakeIcon className="h-12 w-12 text-white" />}
              />
              <ValueCard
                title={"cooperation"}
                color="bg-[#91D065]"
                icon={<PepoleIcon className="h-12 w-12 text-white" />}
              />
              <ValueCard
                title={"justice"}
                color="bg-[#7AB3AF]"
                icon={<ScaleIcon className="h-12 w-12 text-white" />}
              />
              <ValueCard
                title={"innovation"}
                color="bg-[#FFC24A]"
                icon={<LightbulbIcon className="h-12 w-12 text-white" />}
              />
            </div>
          </div>
        </div>
      </div>
    </section>

                            {/* CURRICULUMS */}
                            <section id="curriculum" className="max-w-7xl mx-auto px-6 mt-24">
                                <div className="flex items-center justify-between mb-8">
                                <h2 className="text-3xl font-bold text-gray-800">Curriculums — Arabic & Values</h2>
                                <a href="#" className="text-blue-600 font-medium">View All →</a>
                                </div>
                                <div className="grid sm:grid-cols-2 md:grid-cols-4 gap-6">
                                <div className="bg-gradient-to-r from-[#E86CFF] to-purple-400 shadow-lg rounded-xl overflow-hidden hover:-translate-y-1 transition">
                                    <img src="https://cdn-icons-png.flaticon.com/512/4110/4110734.png" className="w-full h-40 object-contain bg-blue-50" alt="Grade 1" />
                                    <div className="p-4">
                                    <h4 className="font-semibold text-lg">Grade 1</h4>
                                    <p className="text-sm text-gray-600">Arabic — Basics</p>
                                    </div>
                                </div>
                                <div className="bg-gradient-to-r from-[#E86CFF] to-purple-400 shadow-lg rounded-xl overflow-hidden hover:-translate-y-1 transition">
                                    <img src="https://cdn-icons-png.flaticon.com/512/4110/4110766.png" className="w-full h-40 object-contain bg-purple-50" alt="Grade 2" /> 
                                    <div className="p-4">
                                    <h4 className="font-semibold text-lg">Grade 2</h4>
                                    <p className="text-sm text-gray-600">Values — Respect</p>
                                    </div>
                                </div>
                                <div className="bg-gradient-to-r from-[#E86CFF] to-purple-400 shadow-lg rounded-xl overflow-hidden hover:-translate-y-1 transition">
                                    <img src="https://cdn-icons-png.flaticon.com/512/4110/4110739.png" className="w-full h-40 object-contain bg-yellow-50" alt="Grade 3" />
                                    <div className="p-4">
                                    <h4 className="font-semibold text-lg">Grade 3</h4>
                                    <p className="text-sm text-gray-600">Arabic — Reading</p>
                                    </div>
                                </div>
                                <div className="bg-gradient-to-r from-[#E86CFF] to-purple-400 shadow-lg rounded-xl overflow-hidden hover:-translate-y-1 transition">
                                    <img src="https://cdn-icons-png.flaticon.com/512/4110/4110774.png" className="w-full h-40 object-contain bg-green-50" alt="Grade 4" />
                                    <div className="p-4">
                                    <h4 className="font-semibold text-lg">Grade 4</h4>
                                    <p className="text-sm text-gray-600">Values — Empathy</p>
                                    </div>
                                </div>
                                </div>
                            </section>

                            {/* HOW IT WORKS */}
                            <section id="how" className="max-w-7xl mx-auto px-6 mt-24 text-center">
                                <h2 className="text-3xl font-bold mb-10 text-gray-800">How It Works</h2>
                                <div className="grid md:grid-cols-4 gap-6 text-gray-600">
                                <div className="bg-gradient-to-r from-[#A960EF] to-purple-500 shadow-lg rounded-xl p-6">
                                    <h3 className="text-xl font-semibold text-blue-200 mb-2">1️⃣ Create Profile</h3>
                                    <p className="text-sm">Register your school, class, or family.</p>
                                </div>
                                <div className="bg-gradient-to-r from-[#A960EF] to-purple-500 shadow-lg rounded-xl p-6">
                                    <h3 className="text-xl font-semibold text-blue-200 mb-2">2️⃣ Assign Lessons</h3>
                                    <p className="text-sm">Teachers assign Arabic & Values courses.</p>
                                </div>
                                <div className="bg-gradient-to-r from-[#A960EF] to-purple-500 shadow-lg rounded-xl p-6">
                                    <h3 className="text-xl font-semibold text-blue-200 mb-2">3️⃣ Learn & Play</h3>
                                    <p className="text-sm">Kids explore interactive stories and quizzes.</p>
                                </div>
                                <div className="bg-gradient-to-r from-[#A960EF] to-purple-500 shadow-lg rounded-xl p-6">
                                    <h3 className="text-xl font-semibold text-blue-200 mb-2">4️⃣ Track Progress</h3>
                                    <p className="text-sm">Parents and teachers view analytics dashboards.</p>
                                </div>
                                </div>
                            </section> 

                            {/* TESTIMONIALS */}
                            <section className="max-w-7xl mx-auto px-6 mt-24 text-center">
                                <h2 className="text-3xl font-bold mb-10 text-gray-800">What People Say</h2>
                                <div className="grid md:grid-cols-3 gap-8">
                                <div className="bg-gradient-to-r from-[#E86CFF] to-purple-400 shadow-lg rounded-xl p-6">
                                    <p className="italic text-gray-600">“My child now loves Arabic lessons thanks to Kids Learn Hub!”</p>
                                    <h4 className="font-semibold mt-4 text-blue-700">— Parent</h4>
                                </div>
                                <div className="bg-gradient-to-r from-[#E86CFF] to-purple-400 shadow-lg rounded-xl p-6">
                                    <p className="italic text-gray-600">“A teacher’s dream — fun content, easy to manage.”</p>
                                    <h4 className="font-semibold mt-4 text-blue-700">— Teacher</h4>
                                </div>
                                <div className="bg-gradient-to-r from-[#E86CFF] to-purple-400 shadow-lg rounded-xl p-6">
                                    <p className="italic text-gray-600">“Our school improved engagement by 80%.”</p>
                                    <h4 className="font-semibold mt-4 text-blue-700">— School Admin</h4>
                                </div>
                                </div>
                            </section>

                            {/* CTA */}
                            <section className="mt-24 bg-gradient-to-r from-purple-700 to-blue-100 text-white text-center py-16">
                                <h2 className="text-3xl font-bold mb-4">Join Kids Learn Hub Today!</h2>
                                <p className="mb-8 text-blue-100">Fun, safe, and effective Arabic & Values learning for kids everywhere.</p>
                                <a href="#" className="px-8 py-3 bg-white text-blue-700 font-semibold rounded-lg shadow hover:bg-blue-50 transition">Start Learning</a>
                            </section>
            
        </div>
    )
}
