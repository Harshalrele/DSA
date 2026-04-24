import { AllVisitsSearch } from "@/components";
import { useGetDoctorVisitsQuery } from "@/features/doctor/api";
import { VisitSearchRequest } from "@/types";
import { handleError } from "@/utils";
import { useEffect, useState } from "react";
import { DoctorVisits } from "../components";

const DoctorAllVisits: React.FC = () => {
  const [searchParams, setSearchParams] = useState<Partial<VisitSearchRequest>>(
    {}
  );

  const {
    data: visitData = [],
    isLoading: isGetVisitsLoading,
    isError: isGetVisitsError,
    error: visitsError,
  } = useGetDoctorVisitsQuery(searchParams, {
    refetchOnMountOrArgChange: true,
  });

  useEffect(() => {
    if (isGetVisitsError) {
      handleError(visitsError);
    }
  }, [isGetVisitsError, visitsError]);

  return (
    <div className="h-full flex justify-between items-start gap-4">
      <AllVisitsSearch
        setSearchParams={setSearchParams}
        isGetVisitsLoading={isGetVisitsLoading}
      />
      <DoctorVisits visits={visitData} isLoading={isGetVisitsLoading} />
    </div>
  );
};

export { DoctorAllVisits };
