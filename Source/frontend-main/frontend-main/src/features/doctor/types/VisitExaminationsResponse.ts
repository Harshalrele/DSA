import { LaboratoryExamination, PhysicalExamination } from "@/types";

type VisitExaminationsResponse = {
  labExaminationList: LaboratoryExamination[];
  physicalExaminationList: PhysicalExamination[];
};
export type { VisitExaminationsResponse };
