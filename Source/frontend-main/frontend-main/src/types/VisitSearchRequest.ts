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
