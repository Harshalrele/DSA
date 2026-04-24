import { Examination } from "@/types";

type PhysicalExamination = {
  examinationDateTime: Date;
  examinationDictionary: Examination;
  id: number;
  result: string;
};
export type { PhysicalExamination };
