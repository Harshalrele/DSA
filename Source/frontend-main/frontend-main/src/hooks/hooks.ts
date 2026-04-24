/*
 * Bober Clinic note: Contains code or settings for hooks.ts.
 * File: frontend-main/frontend-main/src/hooks/hooks.ts
 */
import { AppDispatch, RootState } from "@/services/state/store";
import { TypedUseSelectorHook, useDispatch, useSelector } from "react-redux";

export const useAppDispatch = () => useDispatch<AppDispatch>();
export const useAppSelector: TypedUseSelectorHook<RootState> = useSelector;


