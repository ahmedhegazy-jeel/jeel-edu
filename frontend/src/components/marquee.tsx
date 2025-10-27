'use client';
import {
  Marquee,
  MarqueeContent,
  MarqueeFade,
  MarqueeItem,
} from '@/components/ui/shadcn-io/marquee';

const Example = (props: { arr: string[],icons:React.ReactNode[] }) => (    
  <div className="flex size-full items-center justify-center bg-[#837095] mt-5 py-1">
    <Marquee>

      <MarqueeContent>        
        {props.arr.map((s, index) => (
          <MarqueeItem className="mt-2 h-30 w-50 mr-5" key={index}>
            

            <div className="flex items-center gap-2 px-2 text-base xl:text-l first-item">
                <span className='text-gray-100 text-xl font-bold'>{props.icons[index]} {s}</span>
            </div>

          </MarqueeItem>
        ))}
      </MarqueeContent>
    </Marquee>
  </div>
);
export default Example;

