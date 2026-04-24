import { Button, Calendar } from "@/components/ui";
import { useGetReceptionistVisitsQuery } from "@/features/receptionist/api";
import { handleError } from "@/utils";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { ReceptionistVisits } from "../components";
import { format, startOfDay } from "date-fns";

const ReceptionistDashboard: React.FC = () => {
  const [date, setDate] = useState<Date | undefined>(startOfDay(new Date()));

  const {
    data: visits = [],
    isLoading: isGetVisitsLoading,
    isError: isGetVisitsError,
    error: visitsError,
    refetch: refetchVisits,
  } = useGetReceptionistVisitsQuery(
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
          <div className="flex gap-4">
            <Button variant="default" asChild>
              <Link to={"create-patient"}>Register new patient</Link>
            </Button>
            <Button variant="default" asChild>
              <Link to={"create-visit"}>Create new visit</Link>
            </Button>
            <Button variant="outline">
              <Link to={"all-visits"}>See all visits</Link>
            </Button>
          </div>
        </div>
        <ReceptionistVisits
          visits={visits}
          isLoading={isGetVisitsLoading}
          refetchVisits={refetchVisits}
        />
      </div>
    </div>
  );
};

export { ReceptionistDashboard };
