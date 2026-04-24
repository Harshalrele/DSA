/*
 * Bober Clinic note: Contains code or settings for PhysicalExamination.ts.
 * File: frontend-main/frontend-main/src/types/PhysicalExamination.ts
 */
import { Examination } from "@/types";

type PhysicalExamination = {
  examinationDateTime: Date;
  examinationDictionary: Examination;
  id: number;
  result: string;
};
export type { PhysicalExamination };


