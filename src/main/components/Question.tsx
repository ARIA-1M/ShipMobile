import {questions} from "../constants/constants.tsx";

interface QuestionProps{
    glass: string;
    qIndex: number;
    question: {
        text: string
    };
}

const Question = ({glass, qIndex, question}: QuestionProps) => {
    return (
        <div className={`w-full max-w-xl rounded-2xl px-6 py-4 ${glass}`}>
            <p className="text-xs text-cyan-200/80">
                Вопрос {qIndex + 1} из {questions.length}
            </p>
            <p className="mt-1 text-lg font-semibold leading-snug sm:text-xl">{question.text}</p>
        </div>
    );
};

export default Question;
