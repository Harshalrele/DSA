import {
  Badge,
  Button,
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
  Dialog,
  DialogTrigger,
  ScrollArea,
  ScrollBar,
} from "@/components/ui";
import { useEffect, useState } from "react";
import { useGetVisitExaminationsQuery } from "../api";
import { handleError } from "@/utils";
import { format } from "date-fns";
import { LaboratoryExamDialog } from ".";

type DoctorLaboratoryExaminationProps = {
  visitId: number;
  shouldBeDisabled?: boolean;
};

const DoctorLaboratoryExamination: React.FC<
  DoctorLaboratoryExaminationProps
> = ({ visitId, shouldBeDisabled = false }) => {
  const [open, setOpen] = useState(false);

  const {
    data: visitExaminations,
    isLoading: isGetVisitExaminationsLoading,
    isError: isGetVisitExaminationsError,
    error: visitExaminationsError,
    refetch: refetchVisitExaminations,
  } = useGetVisitExaminationsQuery({
    visitId: visitId,
    examinationType: "LABORATORY",
  });

  const laboratoryVisitExaminations =
    visitExaminations?.labExaminationList || [];

  useEffect(() => {
    if (isGetVisitExaminationsError) {
      handleError(visitExaminationsError);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [isGetVisitExaminationsError]);

  if (isGetVisitExaminationsLoading) {
    return <div className="text-center">Loading...</div>;
  }

  return (
    <div className="h-full flex flex-col gap-4">
      <div className="flex flex-row justify-between items-center gap-4">
        <div className="text-2xl font-bold">Laboratory examinations:</div>
        <Dialog open={open} onOpenChange={setOpen}>
          <DialogTrigger asChild>
            <Button
              className="self-end"
              variant="outline"
              disabled={shouldBeDisabled}
            >
              Order laboratory examination
            </Button>
          </DialogTrigger>
          <LaboratoryExamDialog
            setOpen={setOpen}
            visitId={visitId}
            refetchVisitExaminations={refetchVisitExaminations}
          />
        </Dialog>
      </div>
      <ScrollArea className="w-full h-full">
        <div className="flex flex-col gap-4">
          {/* TODO może jakąś tabeleczkę tutaj strzelić????? */}
          {laboratoryVisitExaminations.length ? (
            laboratoryVisitExaminations.map((exam) => (
              <Card key={exam.id} className="w-full">
                <CardHeader>
                  <CardTitle className="flex flex-row gap-4">
                    <div className="font-bold">
                      {exam.examinationDictionary.description}
                    </div>
                    <Badge variant="secondary">{exam.status}</Badge>
                  </CardTitle>
                  <CardDescription>
                    Ordered on: {format(exam.orderedDateTime, "HH:mm PPPP")}
                  </CardDescription>
                </CardHeader>
                <CardContent className="space-y-2">
                  {exam.result && (
                    <p className="text-sm">
                      <strong>Result: </strong>
                      {exam.result}
                    </p>
                  )}
                  <p className="text-sm">
                    <strong>Code: </strong>
                    {exam.examinationDictionary.code}
                  </p>
                  <p className="text-sm">
                    <strong>Rights Level: </strong>
                    {exam.examinationDictionary.rightsLevel}
                  </p>
                  {exam.doctorNotices && (
                    <p className="text-sm">
                      <strong>Doctor's Notes: </strong>
                      {exam.doctorNotices}
                    </p>
                  )}
                  {exam.supervisorNotices && (
                    <p className="text-sm">
                      <strong>Supervisor's Notes: </strong>
                      {exam.supervisorNotices}
                    </p>
                  )}
                  {exam.cancellationReason && (
                    <p className="text-sm">
                      <strong>Cancellation Reason: </strong>
                      {exam.cancellationReason}
                    </p>
                  )}
                </CardContent>
              </Card>
            ))
          ) : (
            <div>No examinations found.</div>
          )}
        </div>
        <ScrollBar orientation="vertical" />
      </ScrollArea>
    </div>
  );
};

export { DoctorLaboratoryExamination };
