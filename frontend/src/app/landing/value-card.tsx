import type { ReactNode } from "react";

interface ValueCardProps {
  title: string;
  icon: ReactNode;
  color: string;
}

export function ValueCard({ title, icon, color }: ValueCardProps) {
  return (
    <div className="bg-gradient-to-r from-[#A960EF] to-purple-400 rounded-xl shadow-[0px_15px_30px_rgba(43,43,43,0.04)] border border-[#E5E5E5] p-6 flex flex-col items-center justify-center h-full value-card">
      <div
        className={`${color} rounded-full w-20 h-20 flex items-center justify-center mb-4`}
      >
        {icon}
      </div>
      <h3 className="text-2xl font-bold text-gray-900 font-cairo">{title}</h3>
    </div>
  );
}
