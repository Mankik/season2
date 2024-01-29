import { atom } from "recoil";

// Recoil 상태 정의
export const myState = atom({
  key: "myState", // 고유한 식별자
  default: 0, // 초기값
});
