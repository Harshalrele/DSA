/*
 * Bober Clinic note: Contains code or settings for VisitSearchRequest.ts.
 * File: frontend-main/frontend-main/src/types/VisitSearchRequest.ts
 */
import { VisitStatus } from ".";

type VisitSearchRequest = {
  patientFirstName: string;
  patientLastName: string;
  patientInsuranceId: string;
  doctorFirstName: string;
  doctorLastName: string;
  doctorNpwzId: string;
  status: VisitStatus;
  scheduledDate: string;
};
export type { VisitSearchRequest };


