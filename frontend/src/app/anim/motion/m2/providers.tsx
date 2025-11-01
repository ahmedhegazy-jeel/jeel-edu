'use client';

import { motion, AnimatePresence } from 'framer-motion';
import { usePathname } from 'next/navigation';

const variants = {
  // Use a simple, reliable variant for testing first.
  out: { opacity: 0, y: 40, transition: { duration: 0.75 } },
  in: { opacity: 1, y: 0, transition: { duration: 0.75, delay: 0.5 } },
};

const variants2 = {  
    scaleDown: {
      scale: 0.8,
      y: 100,
      transition: {
        duration: 0.4
        //duration: 3.0
      }
    },
    out: {
      x: "-100%",
      transition: {
        duration: 0.4,
        delay: 0.5
      }
    },
    in: {
      scale: 0.8,
      y: 100,
      x: "100%",
      transition: {
        duration: 0.4
      }
    },
    center: {
      x: 0,
      scale: 0.8,
      transformOrigin: 'top',
      transition: {
        duration: 0.4
      }
    },
    scaleUp: {
      scale: 1,
      y: 0,
      transition: {
        duration: 0.4,
        delay: 0.5
      }
    },
};


export default function Providers({ children }: { children: React.ReactNode }) {
  const pathname = usePathname();

  return (
    <div className="effect-1">
      <AnimatePresence initial={false} mode="wait">
        <motion.div
          key={pathname}
          variants={variants2}
          //initial="out"
          //animate="in"
          //exit="out"
          initial="in"
          animate={["center", "scaleUp"]}
          exit={["scaleDown", "out"]}
        >
          {children}
        </motion.div>
      </AnimatePresence>
    </div>
  );
}
