/*
 * Bober Clinic note: Contains code or settings for LabAssistantAllTests.tsx.
 * File: frontend-main/frontend-main/src/features/labAssistant/pages/LabAssistantAllTests.tsx
 */
import { handleError } from "@/utils";
import { useEffect, useState } from "react";
import { LabExaminationSearchRequest } from "@/types";
import { LabAssistantExaminations } from "../components";
import { useGetAssistantExaminationsQuery } from "../api";
import { AllTestsSearch } from "@/components";

const LabAssistantAllTests: React.FC = () => {
  const [searchParams, setSearchParams] = useState<
    Partial<LabExaminationSearchRequest>
  >({});

  const {
    data: labExaminations = [],
    isLoading: isGetLabExaminationsLoading,
    isError: isGetLabExaminationsError,
    error: labExaminationsError,
  } = useGetAssistantExaminationsQuery(searchParams, {
    refetchOnMountOrArgChange: true,
  });

  useEffect(() => {
    if (isGetLabExaminationsError) {
      handleError(labExaminationsError);
    }
  }, [isGetLabExaminationsError, labExaminationsError]);

  return (
    <div className="h-full flex justify-between items-start gap-4">
      <AllTestsSearch
        setSearchParams={setSearchParams}
        isGetLabExaminationsLoading={isGetLabExaminationsLoading}
      />
      <LabAssistantExaminations
        examinations={labExaminations}
        isLoading={isGetLabExaminationsLoading}
      />
    </div>
  );
};

export { LabAssistantAllTests };


