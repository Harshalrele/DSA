/*
 * Bober Clinic note: Contains code or settings for VisitExaminationsResponse.ts.
 * File: frontend-main/frontend-main/src/features/doctor/types/VisitExaminationsResponse.ts
 */
import { LaboratoryExamination, PhysicalExamination } from "@/types";

type VisitExaminationsResponse = {
  labExaminationList: LaboratoryExamination[];
  physicalExaminationList: PhysicalExamination[];
};
export type { VisitExaminationsResponse };


