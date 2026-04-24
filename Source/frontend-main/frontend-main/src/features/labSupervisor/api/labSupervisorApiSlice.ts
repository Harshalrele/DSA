import { apiSlice } from "@/services/api/apiSlice";
import { LabExaminationSearchRequest, LaboratoryExamination } from "@/types";
import {
  LabExaminationApproveRequest,
  LabExaminationRejectRequest,
} from "../types";

const labSupervisorApiSlice = apiSlice.injectEndpoints({
  endpoints: (builder) => ({
    getSupervisorExaminations: builder.query<
      LaboratoryExamination[],
      Partial<LabExaminationSearchRequest>
    >({
      query: (examParams) => ({
        url: "/api/supervisor/search-examination",
        method: "GET",
        params: examParams,
      }),
    }),
    getSupervisorExaminationById: builder.query<LaboratoryExamination, string>({
      query: (examinationId) => ({
        url: `/api/supervisor/get-examination/${examinationId}`,
        method: "GET",
      }),
    }),
    approveLabExamination: builder.mutation<
      LaboratoryExamination,
      LabExaminationApproveRequest
    >({
      query: (body) => ({
        url: "/api/supervisor/approve-examination",
        method: "POST",
        body,
      }),
    }),
    rejectLabExamination: builder.mutation<
      LaboratoryExamination,
      LabExaminationRejectRequest
    >({
      query: (body) => ({
        url: "/api/supervisor/reject-examination",
        method: "POST",
        body,
      }),
    }),
  }),
});

export const {
  useApproveLabExaminationMutation,
  useRejectLabExaminationMutation,
  useGetSupervisorExaminationByIdQuery,
  useGetSupervisorExaminationsQuery,
  useLazyGetSupervisorExaminationByIdQuery,
  useLazyGetSupervisorExaminationsQuery,
} = labSupervisorApiSlice;
