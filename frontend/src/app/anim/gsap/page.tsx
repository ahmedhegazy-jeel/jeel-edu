/*
'use client';
import styles from "./page.module.scss";

import {useLayoutEffect, useRef, useState} from "react";
import { Flip } from "gsap/dist/Flip"
import { gsap } from "gsap"
import {useGSAP} from "@gsap/react";

gsap.registerPlugin(Flip);

const Home = () =>{

    const container = useRef(null);
    const q = gsap.utils.selector(container);

    const titleRef  = useRef(null);
    const buttonRef = useRef(null);

    const [layout, setLayout] = useState();

    useGSAP(() => {
            if (!layout) return;

            Flip.from(layout, {
                duration: 2,
                ease: "power1.inOut",
                absolute: true
            });
        },{dependencies: [layout]}
    );

    const handleClick = ()=>{
        // @ts-ignore
        setLayout(Flip.getState(titleRef.current));
        // @ts-ignore
        titleRef.current.classList.remove('after');
        // @ts-ignore
        titleRef.current.classList.add('before');
    };



    return (
        <main className={'main'} ref={container}>

            <div className={'after title'} ref={titleRef} onClick={handleClick}>

                <div id={'one'}>
                    <h1 className={'heroText'} id={'ti'} >
                        TI
                    </h1>
                </div>

                <div id={'two'}>
                    <h1 className={'heroText'} id={'g'} >
                        G
                    </h1 >
                </div>

                <div id={'three'}>
                    <h1 className={'heroText'} id={'a'}>
                        A
                    </h1>
                </div>

            </div>
        </main>
    )
}

export default Home;
*/