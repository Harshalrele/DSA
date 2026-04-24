/*
 * Bober Clinic note: Contains code or settings for LabExaminationSearchRequest.ts.
 * File: frontend-main/frontend-main/src/types/LabExaminationSearchRequest.ts
 */
import { LabExaminationStatus, RightsLevel } from ".";

type LabExaminationSearchRequest = {
  status: LabExaminationStatus;
  examinationCode: string;
  labAssistantId: string;
  rightsLevel: RightsLevel;
  orderedDateTime: string;
};
export type { LabExaminationSearchRequest };


