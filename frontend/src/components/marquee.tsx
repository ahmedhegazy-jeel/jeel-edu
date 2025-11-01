'use client';
import {
  Marquee,
  MarqueeContent,
  MarqueeFade,
  MarqueeItem,
} from '@/components/ui/shadcn-io/marquee';

/**
 * FIXED MARQUEE COMPONENT
 * - Properly contained within layout
 * - Respects sidebar and main content boundaries
 * - No overflow to sidebar
 */

const MarqueeFixed = (props: { arr: string[], icons: React.ReactNode[] }) => (    
  <div className=""> {/*w-full bg-[#837095] mt-5 py-3 overflow-hidden */}
    {/* The key is using w-full and overflow-hidden on the container */}
    <Marquee className=""> {/*w-full */}
      <MarqueeContent>        
        {props.arr.map((s, index) => (
          <MarqueeItem 
            className="{/*h-auto w-auto mr-8 whitespace-nowrap*/}" 
            key={index}
          >
            <div className="">{/*flex items-center gap-3 px-4 text-base xl:text-lg */}
              <span className='text-gray-100 text-xl font-bold flex items-center gap-2'>
                <span className="text-2xl">{props.icons[index % props.icons.length]}</span>
                {s}
              </span>
            </div>
          </MarqueeItem>
        ))}
      </MarqueeContent>
    </Marquee>
  </div>
);

export default MarqueeFixed;
