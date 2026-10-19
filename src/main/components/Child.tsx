import type {child} from "../constants/constants.tsx";
const glass = 'bg-white/10 backdrop-blur-xl border border-white/25 shadow-[0_8px_32px_rgba(0,0,0,0.35),inset_0_1px_0_rgba(255,255,255,0.35)]';
interface ChildProps{
    child: child;
    pick: (idx:number)=>void;
    idx: number;
    isClickable: boolean;
    isRaised: boolean;
    isSelected: boolean;
}
const Child = ({child, pick, idx, isClickable, isRaised, isSelected}: ChildProps) => {
    return (
        <button key={child.name}
                onClick={() => pick(idx)}
                disabled={!isClickable}
                className='absolute flex flex-col items-center outline-none transition-transform duration-300'
                style={{ top: `${child.pos[0]}vh`, left: `${child.pos[1]}vw` }}
        >
            <img src={isRaised ? child.img_active : child.img}
                 className={`transition-all duration-300 ${
                     isSelected ? 'drop-shadow-[0_0_22px_rgba(34,211,238,0.95)]'
                         : isClickable ? 'drop-shadow-[0_0_10px_rgba(34,211,238,0.5)]' : ''
                 }`}
            />
            <span className={`ml-[7vh] border !border-cyan-900 rounded-full px-3 py-1 text-lg font-medium text-black ${glass}`}
            >
                {child.name}
            </span>
        </button>
    );
};

export default Child;
