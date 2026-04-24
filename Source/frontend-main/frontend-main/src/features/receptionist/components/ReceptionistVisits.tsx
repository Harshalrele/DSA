import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogTrigger,
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
import { useCancelVisitMutation } from "@/features/receptionist/api";
import { format } from "date-fns";
import { handleError } from "@/utils";
import { useEffect } from "react";
import { toast } from "sonner";
import { VisitSearchResponse, VisitStatus } from "@/types";

type ReceptionistVisitsProps = {
  visits: VisitSearchResponse[];
  isLoading: boolean;
  refetchVisits: () => void;
};

const ReceptionistVisits: React.FC<ReceptionistVisitsProps> = ({
  visits,
  isLoading,
  refetchVisits,
}) => {
  const [cancelVisit, { isSuccess: isCancelVisitSuccess }] =
    useCancelVisitMutation();

  const onCancelHandler = async (visitId: number) => {
    try {
      await cancelVisit({ visitId: visitId }).unwrap();
    } catch (error) {
      handleError(error);
    }
  };

  useEffect(() => {
    if (isCancelVisitSuccess) {
      toast.success(`Visit cancelled successfully`);
      refetchVisits();
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [isCancelVisitSuccess]);

  if (isLoading) return <div className="text-center w-full">Loading...</div>;
  if (visits.length === 0)
    return <div className="text-center w-full">No visits found.</div>;

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
                  <Badge
                    variant="secondary"
                    // variant={visit.visit.visitStatus === "CANCELLED" ? "destructive" : "secondary"}
                  >
                    {visit.visit.visitStatus}
                  </Badge>
                </CardTitle>
                <CardDescription>
                  {format(visit.visit.scheduledDateTime, "HH:mm PPPP")}
                </CardDescription>
              </div>
              <AlertDialog>
                <AlertDialogTrigger asChild>
                  <Button
                    variant="destructive"
                    disabled={visit.visit.visitStatus != VisitStatus.REGISTERED}
                  >
                    Cancel visit
                  </Button>
                </AlertDialogTrigger>
                <AlertDialogContent>
                  <AlertDialogHeader>
                    <AlertDialogTitle>
                      Are you absolutely sure?
                    </AlertDialogTitle>
                    <AlertDialogDescription>
                      This action cannot be undone. This will permanently cancel
                      the visit.
                    </AlertDialogDescription>
                  </AlertDialogHeader>
                  <AlertDialogFooter>
                    <AlertDialogCancel>Cancel</AlertDialogCancel>
                    <AlertDialogAction
                      onClick={() => onCancelHandler(visit.visit.id)}
                    >
                      Continue
                    </AlertDialogAction>
                  </AlertDialogFooter>
                </AlertDialogContent>
              </AlertDialog>
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

export { ReceptionistVisits };
