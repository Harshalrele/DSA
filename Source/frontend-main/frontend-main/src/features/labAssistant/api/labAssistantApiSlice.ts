import { apiSlice } from "@/services/api/apiSlice";
import { LabExaminationSearchRequest, LaboratoryExamination } from "@/types";
import {
  LabExaminationCancelRequest,
  LabExaminationCompleteRequest,
} from "../types";

const labAssistantApiSlice = apiSlice.injectEndpoints({
  endpoints: (builder) => ({
    getAssistantExaminations: builder.query<
      LaboratoryExamination[],
      Partial<LabExaminationSearchRequest>
    >({
      query: (examParams) => ({
        url: "/api/assistant/search-examination",
        method: "GET",
        params: examParams,
      }),
    }),
    getAssistantExaminationById: builder.query<LaboratoryExamination, string>({
      query: (examinationId) => ({
        url: `/api/assistant/get-examination/${examinationId}`,
        method: "GET",
      }),
    }),
    cancelLabExamination: builder.mutation<
      LaboratoryExamination,
      LabExaminationCancelRequest
    >({
      query: (body) => ({
        url: "/api/assistant/cancel-examination",
        method: "POST",
        body,
      }),
    }),
    completeLabExamination: builder.mutation<
      LaboratoryExamination,
      LabExaminationCompleteRequest
    >({
      query: (body) => ({
        url: "/api/assistant/complete-examination",
        method: "POST",
        body,
      }),
    }),
  }),
});

export const {
  useCancelLabExaminationMutation,
  useCompleteLabExaminationMutation,
  useGetAssistantExaminationByIdQuery,
  useGetAssistantExaminationsQuery,
  useLazyGetAssistantExaminationByIdQuery,
  useLazyGetAssistantExaminationsQuery,
} = labAssistantApiSlice;
