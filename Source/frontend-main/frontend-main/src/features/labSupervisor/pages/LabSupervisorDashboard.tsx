import { Button, Calendar } from "@/components/ui";
import { useGetSupervisorExaminationsQuery } from "../api";
import { handleError } from "@/utils";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { LabSupervisorExaminations } from "../components";
import { LabExaminationStatus } from "@/types";
import { format, startOfDay } from "date-fns";

const LabSupervisorDashboard: React.FC = () => {
  const [date, setDate] = useState<Date | undefined>(startOfDay(new Date()));

  const {
    data: examinations = [],
    isLoading: isGetLabExaminationsLoading,
    isError: isGetLabExaminationsError,
    error: examinationsError,
  } = useGetSupervisorExaminationsQuery(
    {
      status: LabExaminationStatus.COMPLETED,
      orderedDateTime: date ? format(date, "HH:mm dd.MM.yyyy") : undefined,
    },
    { refetchOnMountOrArgChange: true }
  );

  useEffect(() => {
    if (isGetLabExaminationsError) {
      handleError(examinationsError);
    }
  }, [isGetLabExaminationsError, examinationsError]);

  return (
    <div className="h-full flex justify-between items-start gap-4">
      <Calendar
        mode="single"
        selected={date}
        onSelect={setDate}
        className="border rounded-md p-4"
      />
      <div className="w-full h-full flex flex-col gap-4">
        <div className="flex justify-between gap-4">
          <div className="text-2xl font-bold">
            {date
              ? `${format(date, "dd.MM.yyyy")} examinations:`
              : `Examinations:`}
          </div>
          <Button variant="outline" asChild>
            <Link to={`all-tests`}>See all examinations</Link>
          </Button>
        </div>
        <LabSupervisorExaminations
          examinations={examinations}
          isLoading={isGetLabExaminationsLoading}
        />
      </div>
    </div>
  );
};

export { LabSupervisorDashboard };
