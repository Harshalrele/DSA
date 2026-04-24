/*
 * Bober Clinic note: Contains code or settings for Visit.ts.
 * File: frontend-main/frontend-main/src/types/Visit.ts
 */
import { LaboratoryExamination, PhysicalExamination, VisitStatus } from ".";

type Visit = {
  description: string;
  diagnostics: string;
  id: number;
  labExaminationList: LaboratoryExamination[];
  physicalExaminationList: PhysicalExamination[];
  scheduledDateTime: Date;
  visitStatus: VisitStatus;
};
export type { Visit };


