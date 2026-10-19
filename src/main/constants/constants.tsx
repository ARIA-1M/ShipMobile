const images = import.meta.glob('../../assets/img/*.png', {
    eager: true,
    import: 'default',
}) as Record<string, string>;

const path = '../../assets/img/';
const img = (name: string) => images[`${path}${name}`];

export type MicrofrontendBus = { emit: (event: string, payload: { points: number }) => void; };

export type child={
    name: string;
    img: string;
    img_active: string;
    pos: number[];
}
export const children=[
    {
        name: 'Максеев Даниил',
        img: img('child1.png'),
        img_active: img('child-a1.png'),
        pos: [15.4, 2.7],
    },
    {
        name: 'Пегасов Егор',
        img: img('child2.png'),
        img_active: img('child-a2.png'),
        pos: [10.3, 25.16],
    },
    {
        name: 'Черноглазов Иван',
        img: img('child3.png'),
        img_active: img('child-a3.png'),
        pos: [17.5, 64.1],
    },
    {
        name: 'Салазкина Яна',
        img: img('child4.png'),
        img_active: img('child-a4.png'),
        pos: [15.6, 53.5],
    },
]

export interface StudentAnswer {
    children: number;
    answer: string;
    isCorrect: boolean;
}

export interface Question {
    id: number;
    text: string;
    correctAnswer: string;
    studentAnswers: StudentAnswer[];
}

export type Outcome = 'success' | 'missed' | 'false-accept';

export const questions: Question[] = [
    {
        id: 1,
        text: 'Какая самая высокая горная вершина России и Европы находится на Северном Кавказе?',
        correctAnswer: 'Эльбрус',
        studentAnswers: [
            { children: 1, answer: 'Альпы', isCorrect: false },
            { children: 2, answer: 'Эверест', isCorrect: false },
            { children: 3, answer: 'Эльбрус', isCorrect: true },
            { children: 4, answer: 'Кавказский хребет', isCorrect: false },
        ],
    },
    {
        id: 2,
        text: 'Город-герой за полярным кругом, севернее Петербурга, — это',
        correctAnswer: 'Мурманск',
        studentAnswers: [
            { children: 1, answer: 'Москва', isCorrect: false },
            { children: 2, answer: 'Мурманск', isCorrect: true },
            { children: 3, answer: 'Псков', isCorrect: false },
            { children: 4, answer: 'Вологда', isCorrect: false },
        ],
    },
    {
        id: 3,
        text: 'Какие леса образуют тайгу?',
        correctAnswer: 'Хвойные',
        studentAnswers: [
            { children: 1, answer: 'Широколиственные', isCorrect: false },
            { children: 2, answer: 'Смешанные', isCorrect: false },
            { children: 3, answer: 'Редкие', isCorrect: false },
            { children: 4, answer: 'Хвойные', isCorrect: true },
        ],
    },
    {
        id: 4,
        text: 'Кто открыл Америку?',
        correctAnswer: 'Христофор Колумб',
        studentAnswers: [
            { children: 1, answer: 'Христофор Колумб', isCorrect: true },
            { children: 2, answer: 'М. П. Лазарев', isCorrect: false },
            { children: 3, answer: 'Ф. Ф. Беллинсгаузен', isCorrect: false },
            { children: 4, answer: 'Фернан Магеллан', isCorrect: false },
        ],
    },
    {
        id: 5,
        text: 'Какой самый большой орган в теле человека?',
        correctAnswer: 'Кожа',
        studentAnswers: [
            { children: 1, answer: 'Сердце', isCorrect: false },
            { children: 2, answer: 'Кожа', isCorrect: true },
            { children: 3, answer: 'Лёгкие', isCorrect: false },
            { children: 4, answer: 'Печень', isCorrect: false },
        ],
    },
];
