/*
 * Bober Clinic note: Contains code or settings for VisitSearchResponse.ts.
 * File: frontend-main/frontend-main/src/types/VisitSearchResponse.ts
 */
import { Doctor, Patient, Visit } from ".";

type VisitSearchResponse = {
  selectedDoctor: Doctor;
  selectedPatient: Patient;
  visit: Visit;
};
export type { VisitSearchResponse };


