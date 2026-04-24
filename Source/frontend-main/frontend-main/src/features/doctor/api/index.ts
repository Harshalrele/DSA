/*
 * Bober Clinic note: Contains code or settings for index.ts.
 * File: frontend-main/frontend-main/src/features/doctor/api/index.ts
 */
import {
  useGetExaminationsQuery,
  useGetDoctorVisitsQuery,
  useLazyGetExaminationsQuery,
  useLazyGetDoctorVisitsQuery,
  useLazyGetVisitQuery,
  useGetVisitQuery,
  useAddPhysicalExaminationMutation,
  useAddLaboratoryExaminationMutation,
  useGetVisitExaminationsQuery,
  useLazyGetVisitExaminationsQuery,
  useSetVisitStatusMutation,
  useSetVisitDetailsMutation,
  useCompleteVisitMutation,
} from "./doctorApiSlice";

export {
  useAddLaboratoryExaminationMutation,
  useAddPhysicalExaminationMutation,
  useCompleteVisitMutation,
  useSetVisitDetailsMutation,
  useSetVisitStatusMutation,
  useGetExaminationsQuery,
  useGetDoctorVisitsQuery,
  useGetVisitExaminationsQuery,
  useGetVisitQuery,
  useLazyGetExaminationsQuery,
  useLazyGetDoctorVisitsQuery,
  useLazyGetVisitExaminationsQuery,
  useLazyGetVisitQuery,
};


