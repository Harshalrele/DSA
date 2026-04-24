import { Sex } from "@/types";

type PatientCreateRequest = {
  firstName: string;
  lastName: string;
  email: string;
  nationalIdNumber: string;
  insuranceId: string;
  sex: Sex;
};
export type { PatientCreateRequest };
