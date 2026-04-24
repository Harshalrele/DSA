import { Button, Calendar } from "@/components/ui";
import { useGetDoctorVisitsQuery } from "@/features/doctor/api";
import { handleError } from "@/utils";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { DoctorVisits } from "../components";
import { format, startOfDay } from "date-fns";

const DoctorDashboard: React.FC = () => {
  const [date, setDate] = useState<Date | undefined>(startOfDay(new Date()));

  const {
    data: visits = [],
    isLoading: isGetVisitsLoading,
    isError: isGetVisitsError,
    error: visitsError,
  } = useGetDoctorVisitsQuery(
    {
      scheduledDate: date ? format(date, "HH:mm dd.MM.yyyy") : undefined,
    },
    { refetchOnMountOrArgChange: true }
  );

  useEffect(() => {
    if (isGetVisitsError) {
      handleError(visitsError);
    }
  }, [isGetVisitsError, visitsError]);

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
            {date ? `${format(date, "dd.MM.yyyy")} visits:` : `Visits:`}
          </div>
          <Button variant="outline" asChild>
            <Link to={`all-visits`}>See all visits</Link>
          </Button>
        </div>
        <DoctorVisits visits={visits} isLoading={isGetVisitsLoading} />
      </div>
    </div>
  );
};

export { DoctorDashboard };
