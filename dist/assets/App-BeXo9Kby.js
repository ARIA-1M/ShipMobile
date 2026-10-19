import { a as __mf_30, c as __mf_default, n as __mf_1, o as __mf_40, r as __mf_2 } from "./_virtual_mf___mfe_internal__lesson_mf_2_vs__mf_owner__15225268071978__loadShare__shared-C6j-SLf3.js";
//#region src/assets/img/bg.png
var bg_default = "http://lesson-vs.sfera.local/assets/bg-BGhK589l.png";
//#endregion
//#region src/main/constants/constants.tsx
var images = /* #__PURE__ */ Object.assign({
	"../../assets/img/bg.png": bg_default,
	"../../assets/img/child-a1.png": "http://lesson-vs.sfera.local/assets/child-a1-pPUpLOxV.png",
	"../../assets/img/child-a2.png": "http://lesson-vs.sfera.local/assets/child-a2-niaR3OBk.png",
	"../../assets/img/child-a3.png": "http://lesson-vs.sfera.local/assets/child-a3-C50MT67B.png",
	"../../assets/img/child-a4.png": "http://lesson-vs.sfera.local/assets/child-a4-CirMrM-Y.png",
	"../../assets/img/child1.png": "http://lesson-vs.sfera.local/assets/child1-B6wOTG_X.png",
	"../../assets/img/child2.png": "http://lesson-vs.sfera.local/assets/child2-BEapwmcI.png",
	"../../assets/img/child3.png": "http://lesson-vs.sfera.local/assets/child3-goWXahPc.png",
	"../../assets/img/child4.png": "http://lesson-vs.sfera.local/assets/child4-CdCD0ayO.png"
});
var path = "../../assets/img/";
var img = (name) => images[`${path}${name}`];
var children = [
	{
		name: "Максеев Даниил",
		img: img("child1.png"),
		img_active: img("child-a1.png"),
		pos: [15.4, 2.7]
	},
	{
		name: "Пегасов Егор",
		img: img("child2.png"),
		img_active: img("child-a2.png"),
		pos: [10.3, 25.16]
	},
	{
		name: "Черноглазов Иван",
		img: img("child3.png"),
		img_active: img("child-a3.png"),
		pos: [17.5, 64.1]
	},
	{
		name: "Салазкина Яна",
		img: img("child4.png"),
		img_active: img("child-a4.png"),
		pos: [15.6, 53.5]
	}
];
var questions = [
	{
		id: 1,
		text: "Какая самая высокая горная вершина России и Европы находится на Северном Кавказе?",
		correctAnswer: "Эльбрус",
		studentAnswers: [
			{
				children: 1,
				answer: "Альпы",
				isCorrect: false
			},
			{
				children: 2,
				answer: "Эверест",
				isCorrect: false
			},
			{
				children: 3,
				answer: "Эльбрус",
				isCorrect: true
			},
			{
				children: 4,
				answer: "Кавказский хребет",
				isCorrect: false
			}
		]
	},
	{
		id: 2,
		text: "Город-герой за полярным кругом, севернее Петербурга, — это",
		correctAnswer: "Мурманск",
		studentAnswers: [
			{
				children: 1,
				answer: "Москва",
				isCorrect: false
			},
			{
				children: 2,
				answer: "Мурманск",
				isCorrect: true
			},
			{
				children: 3,
				answer: "Псков",
				isCorrect: false
			},
			{
				children: 4,
				answer: "Вологда",
				isCorrect: false
			}
		]
	},
	{
		id: 3,
		text: "Какие леса образуют тайгу?",
		correctAnswer: "Хвойные",
		studentAnswers: [
			{
				children: 1,
				answer: "Широколиственные",
				isCorrect: false
			},
			{
				children: 2,
				answer: "Смешанные",
				isCorrect: false
			},
			{
				children: 3,
				answer: "Редкие",
				isCorrect: false
			},
			{
				children: 4,
				answer: "Хвойные",
				isCorrect: true
			}
		]
	},
	{
		id: 4,
		text: "Кто открыл Америку?",
		correctAnswer: "Христофор Колумб",
		studentAnswers: [
			{
				children: 1,
				answer: "Христофор Колумб",
				isCorrect: true
			},
			{
				children: 2,
				answer: "М. П. Лазарев",
				isCorrect: false
			},
			{
				children: 3,
				answer: "Ф. Ф. Беллинсгаузен",
				isCorrect: false
			},
			{
				children: 4,
				answer: "Фернан Магеллан",
				isCorrect: false
			}
		]
	},
	{
		id: 5,
		text: "Какой самый большой орган в теле человека?",
		correctAnswer: "Кожа",
		studentAnswers: [
			{
				children: 1,
				answer: "Сердце",
				isCorrect: false
			},
			{
				children: 2,
				answer: "Кожа",
				isCorrect: true
			},
			{
				children: 3,
				answer: "Лёгкие",
				isCorrect: false
			},
			{
				children: 4,
				answer: "Печень",
				isCorrect: false
			}
		]
	}
];
//#endregion
//#region src/main/components/Scale.tsx
var Scale = ({ glass, results, qIndex, finished, score }) => {
	return /* @__PURE__ */ __mf_2("div", {
		className: `flex w-full max-w-xl items-center gap-3 rounded-2xl px-4 py-3 ${glass}`,
		children: [/* @__PURE__ */ __mf_1("div", {
			className: "flex flex-1 gap-2",
			children: questions.map((q, i) => {
				const done = i < results.length;
				return /* @__PURE__ */ __mf_1("div", { className: `h-3 flex-1 rounded-full border transition-all duration-500 ${done ? results[i] ? "border-emerald-200/70 bg-emerald-400 shadow-[0_0_14px_rgba(52,211,153,0.9)]" : "border-rose-200/70 bg-rose-500 shadow-[0_0_14px_rgba(244,63,94,0.9)]" : i === qIndex && !finished ? "animate-pulse border-cyan-200/70 bg-cyan-300/30" : "border-white/20 bg-white/10"}` }, q.id);
			})
		}), /* @__PURE__ */ __mf_2("span", {
			className: "text-sm tabular-nums text-white/80",
			children: [
				score,
				"/",
				questions.length
			]
		})]
	});
};
//#endregion
//#region src/main/components/Question.tsx
var Question = ({ glass, qIndex, question }) => {
	return /* @__PURE__ */ __mf_2("div", {
		className: `w-full max-w-xl rounded-2xl px-6 py-4 ${glass}`,
		children: [/* @__PURE__ */ __mf_2("p", {
			className: "text-xs text-cyan-200/80",
			children: [
				"Вопрос ",
				qIndex + 1,
				" из ",
				questions.length
			]
		}), /* @__PURE__ */ __mf_1("p", {
			className: "mt-1 text-lg font-semibold leading-snug sm:text-xl",
			children: question.text
		})]
	});
};
//#endregion
//#region src/main/components/Child.tsx
var glass$2 = "bg-white/10 backdrop-blur-xl border border-white/25 shadow-[0_8px_32px_rgba(0,0,0,0.35),inset_0_1px_0_rgba(255,255,255,0.35)]";
var Child = ({ child, pick, idx, isClickable, isRaised, isSelected }) => {
	return /* @__PURE__ */ __mf_2("button", {
		onClick: () => pick(idx),
		disabled: !isClickable,
		className: "absolute flex flex-col items-center outline-none transition-transform duration-300",
		style: {
			top: `${child.pos[0]}vh`,
			left: `${child.pos[1]}vw`
		},
		children: [/* @__PURE__ */ __mf_1("img", {
			src: isRaised ? child.img_active : child.img,
			className: `transition-all duration-300 ${isSelected ? "drop-shadow-[0_0_22px_rgba(34,211,238,0.95)]" : isClickable ? "drop-shadow-[0_0_10px_rgba(34,211,238,0.5)]" : ""}`
		}), /* @__PURE__ */ __mf_1("span", {
			className: `ml-[7vh] border !border-cyan-900 rounded-full px-3 py-1 text-lg font-medium text-black ${glass$2}`,
			children: child.name
		})]
	}, child.name);
};
//#endregion
//#region node_modules/react-icons/lib/iconContext.mjs
var DefaultContext = {
	color: void 0,
	size: void 0,
	className: void 0,
	style: void 0,
	attr: void 0
};
var IconContext = __mf_default.createContext && /*#__PURE__*/ __mf_default.createContext(DefaultContext);
//#endregion
//#region node_modules/react-icons/lib/iconBase.mjs
var _excluded = [
	"attr",
	"size",
	"title"
];
function _objectWithoutProperties(e, t) {
	if (null == e) return {};
	var o, r, i = _objectWithoutPropertiesLoose(e, t);
	if (Object.getOwnPropertySymbols) {
		var n = Object.getOwnPropertySymbols(e);
		for (r = 0; r < n.length; r++) o = n[r], -1 === t.indexOf(o) && {}.propertyIsEnumerable.call(e, o) && (i[o] = e[o]);
	}
	return i;
}
function _objectWithoutPropertiesLoose(r, e) {
	if (null == r) return {};
	var t = {};
	for (var n in r) if ({}.hasOwnProperty.call(r, n)) {
		if (-1 !== e.indexOf(n)) continue;
		t[n] = r[n];
	}
	return t;
}
function _extends() {
	return _extends = Object.assign ? Object.assign.bind() : function(n) {
		for (var e = 1; e < arguments.length; e++) {
			var t = arguments[e];
			for (var r in t) ({}).hasOwnProperty.call(t, r) && (n[r] = t[r]);
		}
		return n;
	}, _extends.apply(null, arguments);
}
function ownKeys(e, r) {
	var t = Object.keys(e);
	if (Object.getOwnPropertySymbols) {
		var o = Object.getOwnPropertySymbols(e);
		r && (o = o.filter(function(r) {
			return Object.getOwnPropertyDescriptor(e, r).enumerable;
		})), t.push.apply(t, o);
	}
	return t;
}
function _objectSpread(e) {
	for (var r = 1; r < arguments.length; r++) {
		var t = null != arguments[r] ? arguments[r] : {};
		r % 2 ? ownKeys(Object(t), !0).forEach(function(r) {
			_defineProperty(e, r, t[r]);
		}) : Object.getOwnPropertyDescriptors ? Object.defineProperties(e, Object.getOwnPropertyDescriptors(t)) : ownKeys(Object(t)).forEach(function(r) {
			Object.defineProperty(e, r, Object.getOwnPropertyDescriptor(t, r));
		});
	}
	return e;
}
function _defineProperty(e, r, t) {
	return (r = _toPropertyKey(r)) in e ? Object.defineProperty(e, r, {
		value: t,
		enumerable: !0,
		configurable: !0,
		writable: !0
	}) : e[r] = t, e;
}
function _toPropertyKey(t) {
	var i = _toPrimitive(t, "string");
	return "symbol" == typeof i ? i : i + "";
}
function _toPrimitive(t, r) {
	if ("object" != typeof t || !t) return t;
	var e = t[Symbol.toPrimitive];
	if (void 0 !== e) {
		var i = e.call(t, r || "default");
		if ("object" != typeof i) return i;
		throw new TypeError("@@toPrimitive must return a primitive value.");
	}
	return ("string" === r ? String : Number)(t);
}
function Tree2Element(tree) {
	return tree && tree.map((node, i) => /*#__PURE__*/ __mf_default.createElement(node.tag, _objectSpread({ key: i }, node.attr), Tree2Element(node.child)));
}
function GenIcon(data) {
	return (props) => /*#__PURE__*/ __mf_default.createElement(IconBase, _extends({ attr: _objectSpread({}, data.attr) }, props), Tree2Element(data.child));
}
function IconBase(props) {
	var elem = (conf) => {
		var attr = props.attr, size = props.size, title = props.title, svgProps = _objectWithoutProperties(props, _excluded);
		var computedSize = size || conf.size || "1em";
		var className;
		if (conf.className) className = conf.className;
		if (props.className) className = (className ? className + " " : "") + props.className;
		return /*#__PURE__*/ __mf_default.createElement("svg", _extends({
			stroke: "currentColor",
			fill: "currentColor",
			strokeWidth: "0"
		}, conf.attr, attr, svgProps, {
			className,
			style: _objectSpread(_objectSpread({ color: props.color || conf.color }, conf.style), props.style),
			height: computedSize,
			width: computedSize,
			xmlns: "http://www.w3.org/2000/svg"
		}), title && /*#__PURE__*/ __mf_default.createElement("title", null, title), props.children);
	};
	return IconContext !== void 0 ? /*#__PURE__*/ __mf_default.createElement(IconContext.Consumer, null, (conf) => elem(conf)) : elem(DefaultContext);
}
//#endregion
//#region node_modules/react-icons/fa/index.mjs
function FaCheck(props) {
	return GenIcon({
		"tag": "svg",
		"attr": { "viewBox": "0 0 512 512" },
		"child": [{
			"tag": "path",
			"attr": { "d": "M173.898 439.404l-166.4-166.4c-9.997-9.997-9.997-26.206 0-36.204l36.203-36.204c9.997-9.998 26.207-9.998 36.204 0L192 312.69 432.095 72.596c9.997-9.997 26.207-9.997 36.204 0l36.203 36.204c9.997 9.997 9.997 26.206 0 36.204l-294.4 294.401c-9.998 9.997-26.207 9.997-36.204-.001z" },
			"child": []
		}]
	})(props);
}
//#endregion
//#region node_modules/react-icons/im/index.mjs
function ImCross(props) {
	return GenIcon({
		"tag": "svg",
		"attr": {
			"version": "1.1",
			"viewBox": "0 0 16 16"
		},
		"child": [{
			"tag": "path",
			"attr": { "d": "M15.854 12.854c-0-0-0-0-0-0l-4.854-4.854 4.854-4.854c0-0 0-0 0-0 0.052-0.052 0.090-0.113 0.114-0.178 0.066-0.178 0.028-0.386-0.114-0.529l-2.293-2.293c-0.143-0.143-0.351-0.181-0.529-0.114-0.065 0.024-0.126 0.062-0.178 0.114 0 0-0 0-0 0l-4.854 4.854-4.854-4.854c-0-0-0-0-0-0-0.052-0.052-0.113-0.090-0.178-0.114-0.178-0.066-0.386-0.029-0.529 0.114l-2.293 2.293c-0.143 0.143-0.181 0.351-0.114 0.529 0.024 0.065 0.062 0.126 0.114 0.178 0 0 0 0 0 0l4.854 4.854-4.854 4.854c-0 0-0 0-0 0-0.052 0.052-0.090 0.113-0.114 0.178-0.066 0.178-0.029 0.386 0.114 0.529l2.293 2.293c0.143 0.143 0.351 0.181 0.529 0.114 0.065-0.024 0.126-0.062 0.178-0.114 0-0 0-0 0-0l4.854-4.854 4.854 4.854c0 0 0 0 0 0 0.052 0.052 0.113 0.090 0.178 0.114 0.178 0.066 0.386 0.029 0.529-0.114l2.293-2.293c0.143-0.143 0.181-0.351 0.114-0.529-0.024-0.065-0.062-0.126-0.114-0.178z" },
			"child": []
		}]
	})(props);
}
//#endregion
//#region src/main/components/JudgeModal.tsx
var outcomeTitle = {
	success: "Правильный ответ найден!",
	missed: "Вы ошиблись: это был правильный ответ",
	"false-accept": "Вы ошиблись: этот ответ неверный"
};
var glass$1 = "bg-white/10 backdrop-blur-xl border border-white/25 shadow-[0_8px_32px_rgba(0,0,0,0.35),inset_0_1px_0_rgba(255,255,255,0.35)]";
var JudgeModal = ({ outcome, selected, selectedAnswer, question, next, qIndex, judge }) => {
	return /* @__PURE__ */ __mf_1("div", {
		className: "fixed inset-0 z-40 flex items-center justify-center bg-black/60 px-4",
		children: outcome !== null ? /* @__PURE__ */ __mf_2("div", {
			className: `flex w-full max-w-md flex-col items-center gap-3 rounded-3xl px-6 py-5 text-center ${glass$1}`,
			children: [
				/* @__PURE__ */ __mf_1("div", {
					className: `flex h-14 w-14 items-center justify-center rounded-full border text-2xl ${outcome === "success" ? "border-emerald-200/70 bg-emerald-400/50 text-emerald-200 shadow-[0_0_20px_rgba(52,211,153,0.8)]" : "border-rose-200/70 bg-rose-500/50 text-rose-200 shadow-[0_0_24px_rgba(244,63,94,0.8)]"}`,
					children: outcome === "success" ? /* @__PURE__ */ __mf_1(FaCheck, {}) : /* @__PURE__ */ __mf_1(ImCross, {})
				}),
				/* @__PURE__ */ __mf_1("p", {
					className: "text-lg font-semibold",
					children: outcomeTitle[outcome]
				}),
				/* @__PURE__ */ __mf_2("p", {
					className: "text-sm text-white/75",
					children: [
						children[selected].name,
						" ответил «",
						selectedAnswer.answer,
						"» —",
						" ",
						selectedAnswer.isCorrect ? "это правильный ответ" : `правильный ответ: «${question.correctAnswer}»`,
						"."
					]
				}),
				/* @__PURE__ */ __mf_1("button", {
					onClick: next,
					className: "mt-1 rounded-full border border-cyan-200/60 bg-cyan-300/20 px-6 py-2 text-sm font-medium bg-cyan-300/35 outline-none",
					children: qIndex === questions.length - 1 ? "Показать итоги" : "Следующий вопрос"
				})
			]
		}) : /* @__PURE__ */ __mf_2("div", {
			className: `flex w-full max-w-md flex-col items-center gap-4 rounded-3xl px-6 py-5 text-center ${glass$1}`,
			children: [
				/* @__PURE__ */ __mf_2("p", {
					className: "text-sm text-cyan-200/90",
					children: [children[selected].name, " отвечает"]
				}),
				/* @__PURE__ */ __mf_1("p", {
					className: "text-2xl font-bold",
					children: selectedAnswer.answer
				}),
				/* @__PURE__ */ __mf_2("div", {
					className: "flex gap-5",
					children: [/* @__PURE__ */ __mf_1("button", {
						onClick: () => judge(false),
						className: "flex h-16 w-16 items-center justify-center rounded-full border border-rose-200/60 text-2xl text-rose-100 bg-rose-500/50 shadow-[0_0_24px_rgba(244,63,94,0.8)] outline-none",
						children: /* @__PURE__ */ __mf_1(ImCross, {})
					}), /* @__PURE__ */ __mf_1("button", {
						onClick: () => judge(true),
						className: "flex h-16 w-16 items-center justify-center rounded-full border border-emerald-200/60 text-2xl text-emerald-100 bg-emerald-400/50 shadow-[0_0_24px_rgba(52,211,153,0.8)] outline-none",
						children: /* @__PURE__ */ __mf_1(FaCheck, {})
					})]
				})
			]
		})
	});
};
//#endregion
//#region src/assets/img/line.svg
var line_default = "data:image/svg+xml,%3csvg%20width='531'%20height='55'%20viewBox='0%200%20531%2055'%20fill='none'%20xmlns='http://www.w3.org/2000/svg'%3e%3cpath%20d='M530.566%2024H27'%20stroke='%234DE4F2'/%3e%3cg%20filter='url(%23filter0_d_3_14)'%3e%3cpath%20d='M31.6564%2024.3851C31.7714%2024.4%2031.8857%2024.4202%2032%2024.4397C30.4958%2024.7677%2028.9843%2025.0643%2027.492%2025.4417C26.7069%2025.6401%2025.95%2025.9651%2025.1968%2026.2736C23.2481%2027.0726%2022.0289%2028.5074%2021.4383%2030.5555C20.8261%2032.6799%2020.4112%2034.8396%2020.1256%2037.027C19.9133%2038.6564%2019.7493%2040.2926%2019.5549%2042C19.4896%2041.2894%2019.4362%2040.6468%2019.3709%2040.0066C19.0652%2037.0187%2018.6874%2034.0428%2017.8779%2031.1418C17.728%2030.6034%2017.5291%2030.0785%2017.3399%2029.552C16.7217%2027.8335%2015.3779%2026.8847%2013.8054%2026.1905C12.0408%2025.4117%2010.179%2024.9841%208.28376%2024.6846C7.85856%2024.6172%207.43188%2024.564%207%2024.4674C7.65375%2024.3648%208.30974%2024.2727%208.96201%2024.1574C10.6064%2023.8661%2012.207%2023.4295%2013.7594%2022.793C15.8246%2021.9461%2017.1514%2020.4881%2017.7502%2018.3007C18.3075%2016.2669%2018.7245%2014.2091%2018.9954%2012.1213C19.2143%2010.4319%2019.3865%208.73656%2019.5846%207C19.6662%207.84095%2019.7367%208.6452%2019.8235%209.44721C20.1322%2012.2973%2020.5782%2015.1174%2021.2802%2017.9038C21.934%2020.4963%2023.4871%2022.134%2025.9426%2023.0169C27.8007%2023.6849%2029.7041%2024.1304%2031.6564%2024.3851Z'%20fill='url(%23paint0_radial_3_14)'/%3e%3c/g%3e%3cdefs%3e%3cfilter%20id='filter0_d_3_14'%20x='0'%20y='0'%20width='45'%20height='55'%20filterUnits='userSpaceOnUse'%20color-interpolation-filters='sRGB'%3e%3cfeFlood%20flood-opacity='0'%20result='BackgroundImageFix'/%3e%3cfeColorMatrix%20in='SourceAlpha'%20type='matrix'%20values='0%200%200%200%200%200%200%200%200%200%200%200%200%200%200%200%200%200%20127%200'%20result='hardAlpha'/%3e%3cfeOffset%20dx='3'%20dy='3'/%3e%3cfeGaussianBlur%20stdDeviation='5'/%3e%3cfeComposite%20in2='hardAlpha'%20operator='out'/%3e%3cfeColorMatrix%20type='matrix'%20values='0%200%200%200%201%200%200%200%200%201%200%200%200%200%201%200%200%200%200.3%200'/%3e%3cfeBlend%20mode='normal'%20in2='BackgroundImageFix'%20result='effect1_dropShadow_3_14'/%3e%3cfeBlend%20mode='normal'%20in='SourceGraphic'%20in2='effect1_dropShadow_3_14'%20result='shape'/%3e%3c/filter%3e%3cradialGradient%20id='paint0_radial_3_14'%20cx='0'%20cy='0'%20r='1'%20gradientUnits='userSpaceOnUse'%20gradientTransform='translate(19.5%2024.5)%20rotate(90)%20scale(17.5%2012.5)'%3e%3cstop%20stop-color='white'/%3e%3cstop%20offset='1'%20stop-color='%234DE4F2'/%3e%3c/radialGradient%3e%3c/defs%3e%3c/svg%3e";
//#endregion
//#region src/main/components/ResCard.tsx
var ResCard = ({ error = false, title, text, onClose, children }) => {
	return /* @__PURE__ */ __mf_1("div", {
		onClick: onClose,
		className: "fixed inset-0 z-10000 flex h-screen items-center justify-center overflow-hidden bg-[rgba(0,0,0,0.4)] backdrop-blur-xs",
		children: /* @__PURE__ */ __mf_2("section", {
			className: `relative w-full max-w-[35vw] rounded-[3vh] border bg-gradient-to-br from-[#4DE4F21A]  to-[rgba(143,245,254,0.3)] p-6 text-white
                backdrop-saturate-150 shadow-[0_0_35px_rgba(77,228,242,0.14),0_25px_100px_rgba(15,23,42,0.35)]',
                ${error ? "border-rose-300/50 shadow-[0_0_45px_rgba(251,113,133,0.18)]" : "border-cyan-200/35"}`,
			children: [
				/* @__PURE__ */ __mf_1("div", { className: `pointer-events-none absolute inset-0 -z-10 opacity-80
                    ${error ? "bg-[radial-gradient(circle_at_50%_0%,rgba(251,113,133,0.18),transparent_55%)]" : "bg-[radial-gradient(circle_at_50%_0%,rgba(77,228,242,0.2),transparent_55%)]"}` }),
				/* @__PURE__ */ __mf_1("div", { className: "absolute inset-x-8 top-0 h-px bg-gradient-to-r from-transparent via-cyan-200 to-transparent opacity-90 shadow-[0_0_15px_#4de4f2]" }),
				/* @__PURE__ */ __mf_1("h1", {
					className: "text-cyan-200 shadow-cyan-200/60 font-zekton pr-10 text-3xl uppercase leading-tight",
					children: title
				}),
				/* @__PURE__ */ __mf_1("img", {
					src: line_default,
					className: "h-auto  drop-shadow-[0_0_8px_rgba(77,228,242,0.8)]"
				}),
				/* @__PURE__ */ __mf_1("p", {
					className: "whitespace-pre-line font-zekton text-md leading-7 tracking-wide text-white",
					children: text
				}),
				/* @__PURE__ */ __mf_1("div", {
					className: "flex flex-col items-center gap-[2vh]",
					children
				})
			]
		})
	});
};
//#endregion
//#region src/main/features/useJudge.tsx
var shuffle = (arr) => {
	const a = [...arr];
	for (let i = a.length - 1; i > 0; i--) {
		const j = Math.floor(Math.random() * (i + 1));
		[a[i], a[j]] = [a[j], a[i]];
	}
	return a;
};
function useJudge() {
	const [qIndex, setQIndex] = __mf_40(0);
	const [raised, setRaised] = __mf_40([]);
	const [selected, setSelected] = __mf_40(null);
	const [outcome, setOutcome] = __mf_40(null);
	const [results, setResults] = __mf_40([]);
	const finished = qIndex >= questions.length;
	const question = finished ? null : questions[qIndex];
	const selectedAnswer = question && selected !== null ? question.studentAnswers.find((a) => a.children === selected + 1) : void 0;
	const judge = (verdict) => {
		if (!selectedAnswer || outcome !== null || selected === null) return;
		if (selectedAnswer.isCorrect && verdict) {
			setOutcome("success");
			setResults((r) => [...r, true]);
			return;
		}
		if (selectedAnswer.isCorrect && !verdict) {
			setOutcome("missed");
			setResults((r) => [...r, false]);
			return;
		}
		if (!selectedAnswer.isCorrect && verdict) {
			setOutcome("false-accept");
			setResults((r) => [...r, false]);
			return;
		}
		setRaised((p) => p.filter((i) => i !== selected));
		setSelected(null);
	};
	__mf_30(() => {
		if (finished) return;
		const correctChild = questions[qIndex].studentAnswers.find((a) => a.isCorrect).children - 1;
		const order = shuffle([correctChild, ...shuffle(children.map((_, i) => i).filter((i) => i !== correctChild)).slice(0, 3)]);
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
		judge
	};
}
//#endregion
//#region src/main/App.tsx
var glass = "bg-white/10 backdrop-blur-xl border border-white/25 shadow-[0_8px_32px_rgba(0,0,0,0.35),inset_0_1px_0_rgba(255,255,255,0.35)]";
var App = () => {
	const j = useJudge();
	const pick = (idx) => {
		if (j.outcome !== null || !j.raised.includes(idx)) return;
		j.setSelected(idx);
	};
	const next = () => j.setQIndex((i) => i + 1);
	const score = j.results.filter(Boolean).length;
	__mf_30(() => {
		if (j.finished) {
			j.setResults([]);
			const parentWindow = window.parent;
			if (parentWindow.microfrontendEventBus) parentWindow.microfrontendEventBus.emit("getPoints", { points: score * 3 });
		}
	}, []);
	return /* @__PURE__ */ __mf_2("div", {
		className: "relative h-screen w-screen overflow-hidden text-white",
		style: {
			backgroundImage: `url("${bg_default}")`,
			backgroundSize: "cover",
			backgroundRepeat: "no-repeat"
		},
		children: [
			children.map((child, idx) => {
				return /* @__PURE__ */ __mf_1(Child, {
					child,
					pick,
					idx,
					isClickable: j.raised.includes(idx) && j.outcome === null,
					isRaised: j.raised.includes(idx),
					isSelected: j.selected === idx
				});
			}),
			/* @__PURE__ */ __mf_2("div", {
				className: "absolute inset-x-0 top-0 z-20 flex flex-col items-center gap-3 px-4 pt-4",
				children: [/* @__PURE__ */ __mf_1(Scale, {
					glass,
					results: j.results,
					qIndex: j.qIndex,
					finished: j.finished,
					score
				}), j.question && /* @__PURE__ */ __mf_1(Question, {
					glass,
					qIndex: j.qIndex,
					question: j.question
				})]
			}),
			j.question && j.selectedAnswer && /* @__PURE__ */ __mf_1(JudgeModal, {
				outcome: j.outcome,
				selected: j.selected,
				selectedAnswer: j.selectedAnswer,
				question: j.question,
				next,
				qIndex: j.qIndex,
				judge: j.judge
			}),
			j.finished && /* @__PURE__ */ __mf_2(ResCard, {
				onClose: () => j.setQIndex(0),
				title: "Урок окончен",
				children: [/* @__PURE__ */ __mf_2("p", {
					className: "text-6xl font-bold tabular-nums",
					children: [score, /* @__PURE__ */ __mf_2("span", {
						className: "text-white/50",
						children: ["/", questions.length]
					})]
				}), /* @__PURE__ */ __mf_1("p", {
					className: "text-white text-md",
					children: score === questions.length ? "Вы нашли правильный ответ в каждом вопросе" : score >= 3 ? "Хороший результат, но есть над чем поработать" : "Многие оценки были ошибочными. Попробуйте ещё раз"
				})]
			})
		]
	});
};
//#endregion
export { App as t };
