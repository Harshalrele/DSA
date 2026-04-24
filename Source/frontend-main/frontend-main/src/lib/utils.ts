/*
 * Bober Clinic note: Contains code or settings for utils.ts.
 * File: frontend-main/frontend-main/src/lib/utils.ts
 */
import { type ClassValue, clsx } from "clsx"
import { twMerge } from "tailwind-merge"

export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs))
}


