import { Link, useNavigate, useParams } from "react-router-dom";
import { useGetDoctorVisitsQuery } from "../api";
import { skipToken } from "@reduxjs/toolkit/query";
import { handleError } from "@/utils";
import { useEffect } from "react";
import { Button } from "@/components/ui";
import { DoctorVisits } from "../components";

const FullMedicalHistory: React.FC = () => {
  const navigate = useNavigate();
  const params = useParams<{ insuranceId: string }>();
  const insuranceId = params.insuranceId;

  const {
    data: visits = [],
    isLoading: isGetVisitsLoading,
    isError: isGetVisitsError,
    error: visitsError,
  } = useGetDoctorVisitsQuery(
    insuranceId ? { patientInsuranceId: insuranceId } : skipToken
  );

  useEffect(() => {
    if (isGetVisitsError) {
      handleError(visitsError);
      navigate("/doctor");
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [isGetVisitsError]);

  if (!visits)
    return (
      <div className="flex flex-col items-center gap-4">
        <div>Medical history of the patient is empty.</div>
        <Button asChild>
          <Link to={"/doctor"}>Go back to dashboard</Link>
        </Button>
      </div>
    );
  return (
    <div className="w-full h-full flex flex-col justify-between gap-4">
      <DoctorVisits visits={visits} isLoading={isGetVisitsLoading} />
      <Button
        variant="outline"
        className="self-start w-[286px]"
        onClick={() => navigate(-1)}
      >
        Back
      </Button>
    </div>
  );
};

export { FullMedicalHistory };
