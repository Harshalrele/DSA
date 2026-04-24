import { apiSlice } from "@/services/api/apiSlice";
import {
  Doctor,
  Patient,
  Receptionist,
  Visit,
  VisitSearchRequest,
  VisitSearchResponse,
} from "@/types";
import { PatientCreateRequest, VisitCreateRequest } from "../types";

type PatientSearchResponse = Patient & { visitList: Visit[] };

type PatientSearchRequest = {
  firstName: string;
  lastName: string;
  nationalIdNumber: number;
  insuranceId: number;
};

type DoctorSearchResponse = Doctor & { visitList: Visit[] };

type DoctorSearchRequest = {
  firstName: string;
  lastName: string;
  nationalIdNumber: number;
  npwzId: number;
};

type VisitCreateResponse = Visit & {
  patient: Patient;
  receptionist: Receptionist;
  selectedDoctor: Doctor;
};

type VisitCancelResponse = unknown; //TODO

type VisitCancelRequest = {
  visitId: number;
};

const receptionistApiSlice = apiSlice.injectEndpoints({
  endpoints: (builder) => ({
    getPatients: builder.query<
      PatientSearchResponse[],
      Partial<PatientSearchRequest>
    >({
      query: (patientParams) => ({
        url: "/api/receptionist/search-patient",
        method: "GET",
        params: patientParams,
      }),
    }),
    getDoctors: builder.query<
      DoctorSearchResponse[],
      Partial<DoctorSearchRequest>
    >({
      query: (doctorParams) => ({
        url: "/api/receptionist/search-doctor",
        method: "GET",
        params: doctorParams,
      }),
    }),
    getReceptionistVisits: builder.query<
      VisitSearchResponse[],
      Partial<VisitSearchRequest>
    >({
      query: (visitParams) => ({
        url: "/api/receptionist/search-visit",
        method: "GET",
        params: visitParams,
      }),
    }),
    createPatient: builder.mutation<Patient, PatientCreateRequest>({
      query: (body) => ({
        url: "/api/receptionist/create-patient",
        method: "POST",
        body,
      }),
    }),
    createVisit: builder.mutation<VisitCreateResponse, VisitCreateRequest>({
      query: (body) => ({
        url: "/api/receptionist/create-visit",
        method: "POST",
        body,
      }),
    }),
    cancelVisit: builder.mutation<VisitCancelResponse, VisitCancelRequest>({
      query: (body) => ({
        url: "/api/receptionist/cancel-visit",
        method: "POST",
        body,
      }),
    }),
  }),
});

export const {
  useCancelVisitMutation,
  useCreatePatientMutation,
  useCreateVisitMutation,
  useGetDoctorsQuery,
  useGetPatientsQuery,
  useGetReceptionistVisitsQuery,
  useLazyGetDoctorsQuery,
  useLazyGetPatientsQuery,
  useLazyGetReceptionistVisitsQuery,
} = receptionistApiSlice;
