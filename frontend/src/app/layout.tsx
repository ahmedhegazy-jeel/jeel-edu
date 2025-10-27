import type { Metadata } from 'next';
import { Inter } from 'next/font/google';
import './globals.css';

const inter = Inter({ subsets: ['latin'], variable: '--font-inter' });

export const metadata: Metadata = {
  title: 'JeelEducation LMS - E-Learning Platform for Kids',
  description: 'Modern E-Learning Management System for Arabic and curriculum learning',
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="en">
      <head>
        <meta charSet="utf-8" />
        <meta name="viewport" content="width=device-width,initial-scale=1" />
        <title>Kids Learn Hub — Where Learning Meets Fun</title>               
          {/*body { font-family: Inter, system-ui, sans-serif; }
          //h1,h2,h3,h4,h5,h6 { font-family: Poppins, sans-serif; }
          */}        
      </head>
      <body className={`${inter.variable} font-sans antialiased`}>{children}</body>
    </html>
  );
}

