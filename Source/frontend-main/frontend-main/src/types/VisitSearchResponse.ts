import { Doctor, Patient, Visit } from ".";

type VisitSearchResponse = {
  selectedDoctor: Doctor;
  selectedPatient: Patient;
  visit: Visit;
};
export type { VisitSearchResponse };
