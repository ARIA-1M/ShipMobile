import {questions} from "../constants/constants.tsx";

interface ScaleProps{
    glass: string;
    results: boolean[];
    qIndex: number;
    finished: boolean;
    score: number
}
const Scale = ({glass, results, qIndex, finished, score}: ScaleProps) => {
    return (
        <div className={`flex w-full max-w-xl items-center gap-3 rounded-2xl px-4 py-3 ${glass}`}>
            <div className="flex flex-1 gap-2">
                {questions.map((q, i) => {
                    const done = i < results.length;
                    const current = i === qIndex && !finished;
                    return (
                        <div
                            key={q.id}
                            className={`h-3 flex-1 rounded-full border transition-all duration-500 ${
                                done
                                    ? results[i]
                                        ? 'border-emerald-200/70 bg-emerald-400 shadow-[0_0_14px_rgba(52,211,153,0.9)]'
                                        : 'border-rose-200/70 bg-rose-500 shadow-[0_0_14px_rgba(244,63,94,0.9)]'
                                    : current
                                        ? 'animate-pulse border-cyan-200/70 bg-cyan-300/30'
                                        : 'border-white/20 bg-white/10'
                            }`}
                        />
                    );
                })}
            </div>
            <span className="text-sm tabular-nums text-white/80">
                {score}/{questions.length}
            </span>
        </div>
    );
};

export default Scale;
