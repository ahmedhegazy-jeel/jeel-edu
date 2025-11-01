'use client'

import  MotionImage  from "./motion/m1/page";

export default function Home() {
  return (
    <div className="grid grid-rows-[20px_1fr_20px] items-center justify-items-center min-h-screen p-8 pb-20 gap-16 sm:p-20 font-[family-name:var(--font-geist-sans)]">
      <div className="flex flex-col gap-[32px] row-start-2 items-center sm:items-start">
        <MotionImage
          initial={{ opacity: 0, y: 24 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.5 }}
          className="dark:invert"
          //src="/next.svg"
          src="/images/kid.png"
          alt="Next.js logo"
          width={180}
          height={38}
          priority
        />
        </div>      
    </div>
  );
}