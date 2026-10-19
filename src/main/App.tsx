import { useEffect} from 'react';
import '../index.css';
import bg from '../assets/img/bg.png';
import {children, type MicrofrontendBus, questions} from './constants/constants.tsx';

import {Child, JudgeModal, Question, ResCard, Scale} from "./components";
import {useJudge} from "./features/useJudge.tsx";

const glass = 'bg-white/10 backdrop-blur-xl border border-white/25 shadow-[0_8px_32px_rgba(0,0,0,0.35),inset_0_1px_0_rgba(255,255,255,0.35)]';
const App = () => {
    const j=useJudge();

    const pick = (idx: number) => {
        if (j.outcome !== null || !j.raised.includes(idx)) return;
        j.setSelected(idx);
    };
    const next = () => j.setQIndex((i) => i + 1);
    const score = j.results.filter(Boolean).length;

    useEffect(() => {
        if (j.finished){
            j.setResults([]);
            const parentWindow = window.parent as Window & { microfrontendEventBus?: MicrofrontendBus };
            if (parentWindow.microfrontendEventBus) {
                parentWindow.microfrontendEventBus.emit('getPoints', { points: score * 3 });
            }
        }
    }, []);

    return (
        <div className="relative h-screen w-screen overflow-hidden text-white"
            style={{ backgroundImage: `url("${bg}")`, backgroundSize: 'cover', backgroundRepeat: 'no-repeat' }}
        >
            {children.map((child, idx) => {
                return (
                    <Child
                        child={child}
                        pick={pick}
                        idx={idx}
                        isClickable={j.raised.includes(idx) && j.outcome === null}
                        isRaised={j.raised.includes(idx)}
                        isSelected={j.selected === idx}
                    />
                );
            })}

            <div className="absolute inset-x-0 top-0 z-20 flex flex-col items-center gap-3 px-4 pt-4">
                <Scale
                    glass={glass}
                    results={j.results}
                    qIndex={j.qIndex}
                    finished={j.finished}
                    score={score}
                />

                {j.question && (
                    <Question
                        glass={glass}
                        qIndex={j.qIndex}
                        question={j.question}
                    />
                )}
            </div>

            {j.question && j.selectedAnswer && (
                <JudgeModal
                    outcome={j.outcome}
                    selected={j.selected}
                    selectedAnswer={j.selectedAnswer}
                    question={j.question}
                    next={next}
                    qIndex={j.qIndex}
                    judge={j.judge}
                />
            )}

            {j.finished && (
                <ResCard onClose={()=>j.setQIndex(0)} title='Урок окончен'>
                    <p className="text-6xl font-bold tabular-nums">
                        {score}
                        <span className="text-white/50">/{questions.length}</span>
                    </p>
                    <p className="text-white text-md">
                        {score === questions.length
                            ? 'Вы нашли правильный ответ в каждом вопросе'
                            : score >= 3
                                ? 'Хороший результат, но есть над чем поработать'
                                : 'Многие оценки были ошибочными. Попробуйте ещё раз'}
                    </p>
                </ResCard>
            )}
        </div>
    );
};

export default App;
