/*
 * Bober Clinic note: Contains code or settings for PatientCreateRequest.ts.
 * File: frontend-main/frontend-main/src/features/receptionist/types/PatientCreateRequest.ts
 */
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


