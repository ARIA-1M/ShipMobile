import {FaCheck} from "react-icons/fa";
import {ImCross} from "react-icons/im";
import {children, type Outcome, questions} from "../constants/constants.tsx";

const outcomeTitle: Record<Outcome, string> = {
    success: 'Правильный ответ найден!',
    missed: 'Вы ошиблись: это был правильный ответ',
    'false-accept': 'Вы ошиблись: этот ответ неверный',
};

const glass = 'bg-white/10 backdrop-blur-xl border border-white/25 shadow-[0_8px_32px_rgba(0,0,0,0.35),inset_0_1px_0_rgba(255,255,255,0.35)]';

interface JudgeProps{
    outcome: Outcome | null;
    selected: number | null;
    selectedAnswer: {
        answer: string,
        isCorrect: boolean,
    };
    question: {
        correctAnswer: string,
    };
    next: ()=>void;
    qIndex: number;
    judge: (verdict: boolean)=>void;
}

const JudgeModal = ({outcome, selected, selectedAnswer, question, next, qIndex, judge}: JudgeProps) => {
    return (
        <div className="fixed inset-0 z-40 flex items-center justify-center bg-black/60 px-4">
            {outcome !== null ? (
                <div className={`flex w-full max-w-md flex-col items-center gap-3 rounded-3xl px-6 py-5 text-center ${glass}`}>
                    <div className={`flex h-14 w-14 items-center justify-center rounded-full border text-2xl ${
                        outcome === 'success'
                            ? 'border-emerald-200/70 bg-emerald-400/50 text-emerald-200 shadow-[0_0_20px_rgba(52,211,153,0.8)]'
                            : 'border-rose-200/70 bg-rose-500/50 text-rose-200 shadow-[0_0_24px_rgba(244,63,94,0.8)]'
                    }`}
                    >
                        {outcome === 'success' ? <FaCheck /> : <ImCross />}
                    </div>
                    <p className="text-lg font-semibold">{outcomeTitle[outcome]}</p>
                    <p className="text-sm text-white/75">
                        {children[selected!].name} ответил «{selectedAnswer.answer}» —{' '}
                        {selectedAnswer.isCorrect ? 'это правильный ответ' : `правильный ответ: «${question.correctAnswer}»`}.
                    </p>
                    <button onClick={next}
                            className="mt-1 rounded-full border border-cyan-200/60 bg-cyan-300/20 px-6 py-2 text-sm font-medium bg-cyan-300/35 outline-none"
                    >
                        {qIndex === questions.length - 1 ? 'Показать итоги' : 'Следующий вопрос'}
                    </button>
                </div>
            ) : (
                <div className={`flex w-full max-w-md flex-col items-center gap-4 rounded-3xl px-6 py-5 text-center ${glass}`}>
                    <p className="text-sm text-cyan-200/90">{children[selected!].name} отвечает</p>
                    <p className="text-2xl font-bold">{selectedAnswer.answer}</p>
                    <div className="flex gap-5">
                        <button
                            onClick={() => judge(false)}
                            className="flex h-16 w-16 items-center justify-center rounded-full border border-rose-200/60 text-2xl text-rose-100 bg-rose-500/50 shadow-[0_0_24px_rgba(244,63,94,0.8)] outline-none"
                        >
                            <ImCross />
                        </button>
                        <button
                            onClick={() => judge(true)}
                            className="flex h-16 w-16 items-center justify-center rounded-full border border-emerald-200/60 text-2xl text-emerald-100 bg-emerald-400/50 shadow-[0_0_24px_rgba(52,211,153,0.8)] outline-none"
                        >
                            <FaCheck />
                        </button>
                    </div>
                </div>
            )}
        </div>
    );
};

export default JudgeModal;
