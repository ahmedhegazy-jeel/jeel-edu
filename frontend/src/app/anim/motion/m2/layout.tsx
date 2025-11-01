'use client';

import './f.css';
import Providers from './providers';
import Link from 'next/link';

export default function Mootion2Layout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <div>
        <div> In Sub Motion2 layout </div>
        <div>
        
        <nav style={{ padding: '1rem', borderBottom: '1px solid #ccc' }}>
            <Link href="/anim/motion/m2/p1" style={{ marginRight: '1rem' }} scroll={false}>
            PAGE 1
            </Link>
            <Link href="/anim/motion/m2/p2" style={{ marginRight: '1rem' }} scroll={false}>
            PAGE 2
            </Link>
        </nav>
        <Providers>
            {children}
        </Providers>                
        
        </div>
    </div>
  );
}
