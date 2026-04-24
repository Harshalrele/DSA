/*
 * Bober Clinic note: Contains code or settings for Examination.ts.
 * File: frontend-main/frontend-main/src/types/Examination.ts
 */
import { ExaminationType, RightsLevel } from ".";

type Examination = {
  code: string;
  description: string;
  type: ExaminationType;
  rightsLevel: RightsLevel;
};
export type { Examination };


