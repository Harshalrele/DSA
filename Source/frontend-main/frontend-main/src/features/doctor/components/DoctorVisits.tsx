import { VisitSearchResponse, VisitStatus } from "@/types";
import {
  Badge,
  Button,
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
  ScrollArea,
  ScrollBar,
} from "@/components/ui";
import { format } from "date-fns";
import { useNavigate } from "react-router-dom";
import { useSetVisitStatusMutation } from "../api";
import { handleError } from "@/utils";

type DoctorVisitsProps = {
  visits: VisitSearchResponse[];
  isLoading: boolean;
};

const DoctorVisits: React.FC<DoctorVisitsProps> = ({ visits, isLoading }) => {
  const [setVisitStatus] = useSetVisitStatusMutation();

  const navigate = useNavigate();

  const onProcessVisitHandler = async (visitId: number) => {
    try {
      await setVisitStatus({
        visitId: visitId.toString(),
        visitStatus: VisitStatus.IN_PROGRESS,
      }).unwrap();
      navigate(`/doctor/process-visit/${visitId}`);
    } catch (error) {
      handleError(error);
    }
  };

  if (isLoading) return <div className="text-center w-full">Loading...</div>;
  if (visits.length === 0)
    return <div className="text-center w-full">No visits found.</div>;

  // Sort visits by scheduledDateTime
  const sortedVisits = [...visits].sort(
    (a, b) =>
      new Date(a.visit.scheduledDateTime).getTime() -
      new Date(b.visit.scheduledDateTime).getTime()
  );

  return (
    <ScrollArea className="w-full h-full">
      <div className="flex flex-col gap-4 min-w-[350px]">
        {sortedVisits.map((visit) => (
          <Card key={visit.visit.id}>
            <CardHeader className="flex-row justify-between items-start gap-4">
              <div className="space-y-1.5">
                <CardTitle className="flex flex-row gap-4">
                  <div className="font-bold">
                    {`${visit.selectedPatient.person.firstName} ${visit.selectedPatient.person.lastName}`}
                  </div>
                  <Badge variant="secondary">{visit.visit.visitStatus}</Badge>
                </CardTitle>
                <CardDescription>
                  {format(visit.visit.scheduledDateTime, "HH:mm PPPP")}
                </CardDescription>
              </div>
              {visit.visit.visitStatus === VisitStatus.IN_PROGRESS ||
              visit.visit.visitStatus === VisitStatus.REGISTERED ? (
                <Button onClick={() => onProcessVisitHandler(visit.visit.id)}>
                  Process visit
                </Button>
              ) : (
                <Button
                  onClick={() =>
                    navigate(`/doctor/view-visit/${visit.visit.id}`)
                  }
                >
                  View visit
                </Button>
              )}
            </CardHeader>
            <CardContent className="space-y-2">
              <p className="text-sm">
                <strong>Doctor: </strong>
                {`Dr. ${visit.selectedDoctor.clinicStaff.person.firstName} ${visit.selectedDoctor.clinicStaff.person.lastName}`}
              </p>
              <p className="text-sm">
                <strong>Description: </strong>
                {visit.visit.description}
              </p>
              <p className="text-sm">
                <strong>Diagnosis: </strong>
                {visit.visit.diagnostics}
              </p>
            </CardContent>
          </Card>
        ))}
      </div>
      <ScrollBar orientation="vertical" />
    </ScrollArea>
  );
};

export { DoctorVisits };
