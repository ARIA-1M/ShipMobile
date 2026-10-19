import {useEffect, useState} from "react";
import {children, type Outcome, questions} from "../constants/constants.tsx";

const shuffle = <T,>(arr: T[]): T[] => {
    const a = [...arr];
    for (let i = a.length - 1; i > 0; i--) {
        const j = Math.floor(Math.random() * (i + 1));
        [a[i], a[j]] = [a[j], a[i]];
    }
    return a;
};
export function useJudge(){
    const [qIndex, setQIndex] = useState(0);
    const [raised, setRaised] = useState<number[]>([]);
    const [selected, setSelected] = useState<number | null>(null);
    const [outcome, setOutcome] = useState<Outcome | null>(null);
    const [results, setResults] = useState<boolean[]>([]);

    const finished = qIndex >= questions.length;
    const question = finished ? null : questions[qIndex];

    const selectedAnswer =
        question && selected !== null ? question.studentAnswers.find((a) => a.children === selected + 1) : undefined;

    const judge = (verdict: boolean) => {
        if (!selectedAnswer || outcome !== null || selected === null) return;

        if (selectedAnswer.isCorrect && verdict) {
            setOutcome('success');
            setResults((r) => [...r, true]);
            return;
        }

        if (selectedAnswer.isCorrect && !verdict) {
            setOutcome('missed');
            setResults((r) => [...r, false]);
            return;
        }

        if (!selectedAnswer.isCorrect && verdict) {
            setOutcome('false-accept');
            setResults((r) => [...r, false]);
            return;
        }

        setRaised((p) => p.filter((i) => i !== selected));
        setSelected(null);
    };

    useEffect(() => {
        if (finished) return;
        const q = questions[qIndex];
        const correctChild = q.studentAnswers.find((a) => a.isCorrect)!.children - 1;
        const others = shuffle(children.map((_, i) => i).filter((i) => i !== correctChild)).slice(0, 3);
        const order = shuffle([correctChild, ...others]);

        setRaised([]);
        setSelected(null);
        setOutcome(null);

        const timers = order.map((idx, i) => window.setTimeout(() => setRaised((p) => [...p, idx]), 800 + i * 450));
        return () => timers.forEach(clearTimeout);
    }, [qIndex, finished]);

    return {
        outcome,
        raised,
        setSelected,
        setQIndex,
        results,
        finished,
        setResults,
        selected,
        qIndex,
        question,
        selectedAnswer,
        judge,
    }
}
