import { ExaminationType, RightsLevel } from ".";

type Examination = {
  code: string;
  description: string;
  type: ExaminationType;
  rightsLevel: RightsLevel;
};
export type { Examination };
