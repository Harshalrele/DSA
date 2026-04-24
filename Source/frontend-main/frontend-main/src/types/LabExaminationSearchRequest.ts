import { LabExaminationStatus, RightsLevel } from ".";

type LabExaminationSearchRequest = {
  status: LabExaminationStatus;
  examinationCode: string;
  labAssistantId: string;
  rightsLevel: RightsLevel;
  orderedDateTime: string;
};
export type { LabExaminationSearchRequest };
