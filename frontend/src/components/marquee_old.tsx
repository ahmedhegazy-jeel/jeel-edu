/*
'use client';
import {
  Marquee,
  MarqueeContent,
  MarqueeFade,
  MarqueeItem,
} from '@/components/ui/shadcn-io/marquee';

const Marqu = (props: { arr: string[],icons:React.ReactNode[] }) => (    
  <div className="overflow-hidden bg-[#837095] mt-5 py-1">
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
export default Marqu;
*/
'use client';
import {
  Marquee,
  MarqueeContent,
  MarqueeFade,
  MarqueeItem,
} from '@/components/ui/shadcn-io/marquee';

const Marqu = (props: { arr: string[], icons: React.ReactNode[] }) => (    
  <div className="relative w-full bg-[#837095] mt-5 py-1 h-10">
    <div className="absolute inset-0 h-30 overflow-hidden">
      <Marquee>
        <MarqueeFade side="left" className="from-[#837095]" />
        <MarqueeContent>        
          {props.arr.map((s, index) => (
            <MarqueeItem className="flex-shrink-0 h-30 w-50 mr-5" key={index}>
              <div className="flex items-center gap-2 px-4 py-2 text-base xl:text-l">
                <span className='text-gray-100 text-xl font-bold whitespace-nowrap'>
                  {props.icons[index]} {s}
                </span>
              </div>
            </MarqueeItem>
          ))}
        </MarqueeContent>
        <MarqueeFade side="right" className="from-[#837095]" />
      </Marquee>
    </div>
  </div>
);
export default Marqu;