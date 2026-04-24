import { LabExaminationSearchRequest } from "@/types";
import { handleError } from "@/utils";
import { useEffect, useState } from "react";
import { useGetSupervisorExaminationsQuery } from "../api";
import { LabSupervisorExaminations } from "../components";
import { AllTestsSearch } from "@/components";

const LabSupervisorAllTests: React.FC = () => {
  const [searchParams, setSearchParams] = useState<
    Partial<LabExaminationSearchRequest>
  >({});

  const {
    data: labExaminations = [],
    isLoading: isGetLabExaminationsLoading,
    isError: isGetLabExaminationsError,
    error: labExaminationsError,
  } = useGetSupervisorExaminationsQuery(searchParams, {
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
      <LabSupervisorExaminations
        examinations={labExaminations}
        isLoading={isGetLabExaminationsLoading}
      />
    </div>
  );
};

export { LabSupervisorAllTests };
