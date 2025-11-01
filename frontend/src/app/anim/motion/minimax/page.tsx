'use client';
import { motion, useScroll, useTransform } from 'framer-motion';
import { useEffect, useState } from 'react';

/**
 * FRAMER MOTION ANIMATIONS SHOWCASE
 * Examples of cool animations you can use in your Next.js project
 */

export default function AnimationsExample() {
  const [isVisible, setIsVisible] = useState(false);
  const { scrollYProgress } = useScroll();
  const opacity = useTransform(scrollYProgress, [0, 0.5], [1, 0]);

  useEffect(() => {
    setIsVisible(true);
  }, []);

  return (
    <div className="min-h-screen bg-gradient-to-br from-purple-50 to-blue-50 py-20">
      <div className="max-w-7xl mx-auto px-6 space-y-32">
        
        {/* 1. GREETING ANIMATION (like "Good morning, how can I help you today?") */}
        <section className="text-center">
          <motion.div
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.8, ease: "easeOut" }}
          >
            <motion.h1 
              className="text-5xl font-bold text-gray-800 mb-4"
              initial={{ opacity: 0, scale: 0.9 }}
              animate={{ opacity: 1, scale: 1 }}
              transition={{ duration: 0.6, delay: 0.2 }}
            >
              Good morning, how can I help you today?
            </motion.h1>
            
            <motion.p
              className="text-xl text-gray-600"
              initial={{ opacity: 0 }}
              animate={{ opacity: 1 }}
              transition={{ duration: 0.6, delay: 0.4 }}
            >
              Welcome to our interactive learning platform
            </motion.p>
          </motion.div>
        </section>

        {/* 2. STAGGERED MENU ITEMS ANIMATION */}
        <section>
          <h2 className="text-3xl font-bold text-gray-800 mb-8 text-center">
            Animated Menu Items
          </h2>
          <motion.nav
            className="flex justify-center gap-8"
            initial="hidden"
            animate="visible"
            variants={{
              hidden: { opacity: 0 },
              visible: {
                opacity: 1,
                transition: {
                  staggerChildren: 0.1,
                },
              },
            }}
          >
            {['Home', 'Curriculum', 'How it Works', 'Contact'].map((item) => (
              <motion.a
                key={item}
                href={`#${item.toLowerCase()}`}
                className="text-lg font-medium text-gray-700 hover:text-purple-600 transition-colors"
                variants={{
                  hidden: { opacity: 0, y: -20 },
                  visible: { opacity: 1, y: 0 },
                }}
                whileHover={{ scale: 1.1, color: '#9333ea' }}
                whileTap={{ scale: 0.95 }}
              >
                {item}
              </motion.a>
            ))}
          </motion.nav>
        </section>

        {/* 3. ANIMATED CARDS WITH HOVER EFFECTS */}
        <section>
          <h2 className="text-3xl font-bold text-gray-800 mb-8 text-center">
            Interactive Cards
          </h2>
          <div className="grid md:grid-cols-3 gap-8">
            {[1, 2, 3].map((i) => (
              <motion.div
                key={i}
                className="bg-white rounded-xl shadow-lg p-6 cursor-pointer"
                initial={{ opacity: 0, y: 50 }}
                whileInView={{ opacity: 1, y: 0 }}
                viewport={{ once: true }}
                transition={{ duration: 0.5, delay: i * 0.1 }}
                whileHover={{ 
                  scale: 1.05, 
                  boxShadow: '0 20px 25px -5px rgba(0, 0, 0, 0.1), 0 10px 10px -5px rgba(0, 0, 0, 0.04)',
                  transition: { duration: 0.2 }
                }}
                whileTap={{ scale: 0.98 }}
              >
                <motion.div
                  className="w-16 h-16 bg-gradient-to-r from-purple-500 to-blue-500 rounded-full mb-4 mx-auto"
                  whileHover={{ rotate: 360 }}
                  transition={{ duration: 0.6 }}
                />
                <h3 className="text-xl font-semibold mb-2 text-gray-800">Card {i}</h3>
                <p className="text-gray-600">Hover over me for cool animations!</p>
              </motion.div>
            ))}
          </div>
        </section>

        {/* 4. ANIMATED BUTTONS */}
        <section>
          <h2 className="text-3xl font-bold text-gray-800 mb-8 text-center">
            Animated Buttons
          </h2>
          <div className="flex justify-center gap-6 flex-wrap">
            {/* Button with bounce */}
            <motion.button
              className="px-8 py-3 bg-gradient-to-r from-purple-500 to-blue-500 text-white font-semibold rounded-lg shadow-lg"
              whileHover={{ scale: 1.1 }}
              whileTap={{ scale: 0.9 }}
              transition={{ type: "spring", stiffness: 400, damping: 17 }}
            >
              Bounce Button
            </motion.button>

            {/* Button with glow effect */}
            <motion.button
              className="px-8 py-3 bg-white text-purple-600 font-semibold rounded-lg shadow-lg border-2 border-purple-500"
              whileHover={{ 
                boxShadow: '0 0 20px rgba(147, 51, 234, 0.5)',
                scale: 1.05
              }}
              whileTap={{ scale: 0.95 }}
            >
              Glow Button
            </motion.button>

            {/* Button with slide effect */}
            <motion.button
              className="px-8 py-3 bg-gradient-to-r from-pink-500 to-purple-500 text-white font-semibold rounded-lg shadow-lg overflow-hidden relative"
              whileHover="hover"
              whileTap={{ scale: 0.95 }}
            >
              <motion.span
                className="absolute inset-0 bg-white opacity-0"
                variants={{
                  hover: { opacity: 0.2, transition: { duration: 0.3 } }
                }}
              />
              <span className="relative z-10">Slide Button</span>
            </motion.button>
          </div>
        </section>

        {/* 5. SCROLL-TRIGGERED ANIMATIONS */}
        <section>
          <h2 className="text-3xl font-bold text-gray-800 mb-8 text-center">
            Scroll-Triggered Animations
          </h2>
          {[1, 2, 3, 4].map((i) => (
            <motion.div
              key={i}
              className="bg-white rounded-xl shadow-lg p-8 mb-6"
              initial={{ opacity: 0, x: i % 2 === 0 ? -100 : 100 }}
              whileInView={{ opacity: 1, x: 0 }}
              viewport={{ once: true, margin: "-100px" }}
              transition={{ duration: 0.6 }}
            >
              <h3 className="text-2xl font-semibold mb-2 text-gray-800">
                Section {i}
              </h3>
              <p className="text-gray-600">
                This section animates when you scroll it into view
              </p>
            </motion.div>
          ))}
        </section>

        {/* 6. FLOATING ELEMENTS */}
        <section className="relative h-96">
          <h2 className="text-3xl font-bold text-gray-800 mb-8 text-center">
            Floating Elements
          </h2>
          <div className="relative h-64">
            {[1, 2, 3, 4, 5].map((i) => (
              <motion.div
                key={i}
                className="absolute w-12 h-12 bg-gradient-to-r from-purple-400 to-blue-400 rounded-full"
                style={{
                  left: `${i * 18}%`,
                  top: `${Math.random() * 60}%`,
                }}
                animate={{
                  y: [0, -30, 0],
                  rotate: [0, 360],
                }}
                transition={{
                  duration: 3 + i * 0.5,
                  repeat: Infinity,
                  ease: "easeInOut",
                  delay: i * 0.2,
                }}
              />
            ))}
          </div>
        </section>

        {/* 7. TEXT REVEAL ANIMATION */}
        <section>
          <h2 className="text-3xl font-bold text-gray-800 mb-8 text-center">
            Text Reveal Animation
          </h2>
          <motion.div
            className="text-center"
            initial="hidden"
            whileInView="visible"
            viewport={{ once: true }}
          >
            {['Where', 'Learning', 'Meets', 'Fun'].map((word, i) => (
              <motion.span
                key={i}
                className="inline-block text-5xl font-bold text-transparent bg-clip-text bg-gradient-to-r from-purple-600 to-blue-600 mx-2"
                variants={{
                  hidden: { opacity: 0, y: 50 },
                  visible: { 
                    opacity: 1, 
                    y: 0,
                    transition: { delay: i * 0.1, duration: 0.5 }
                  }
                }}
              >
                {word}
              </motion.span>
            ))}
          </motion.div>
        </section>

        {/* 8. STAGGERED LIST ANIMATION */}
        <section>
          <h2 className="text-3xl font-bold text-gray-800 mb-8 text-center">
            Staggered List
          </h2>
          <motion.ul
            className="max-w-2xl mx-auto space-y-4"
            initial="hidden"
            whileInView="visible"
            viewport={{ once: true }}
            variants={{
              visible: {
                transition: {
                  staggerChildren: 0.1
                }
              }
            }}
          >
            {['Interactive lessons', 'Fun games', 'Progress tracking', 'Rewards system', 'Parent dashboard'].map((item, i) => (
              <motion.li
                key={i}
                className="bg-white rounded-lg shadow-md p-4 flex items-center"
                variants={{
                  hidden: { opacity: 0, x: -50 },
                  visible: { opacity: 1, x: 0 }
                }}
                whileHover={{ x: 10, backgroundColor: '#f3e8ff' }}
              >
                <span className="text-2xl mr-4">✓</span>
                <span className="text-lg text-gray-700">{item}</span>
              </motion.li>
            ))}
          </motion.ul>
        </section>

        {/* 9. MORPHING SHAPES */}
        <section>
          <h2 className="text-3xl font-bold text-gray-800 mb-8 text-center">
            Morphing Shapes
          </h2>
          <div className="flex justify-center gap-8">
            <motion.div
              className="w-24 h-24 bg-gradient-to-r from-purple-500 to-pink-500"
              animate={{
                borderRadius: ['0%', '50%', '0%'],
                rotate: [0, 180, 360],
              }}
              transition={{
                duration: 3,
                repeat: Infinity,
                ease: "easeInOut"
              }}
            />
            <motion.div
              className="w-24 h-24 bg-gradient-to-r from-blue-500 to-purple-500"
              animate={{
                scale: [1, 1.5, 1],
                rotate: [0, -180, -360],
              }}
              transition={{
                duration: 3,
                repeat: Infinity,
                ease: "easeInOut"
              }}
            />
          </div>
        </section>

      </div>
    </div>
  );
}
