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
import { LabExaminationStatus, LaboratoryExamination } from "@/types";
import { format } from "date-fns";
import { useNavigate } from "react-router-dom";

type LabAssistantExaminationsProps = {
  examinations: LaboratoryExamination[];
  isLoading: boolean;
};
const LabAssistantExaminations: React.FC<LabAssistantExaminationsProps> = ({
  examinations,
  isLoading,
}) => {
  const navigate = useNavigate();

  if (isLoading) return <div className="text-center w-full">Loading...</div>;
  if (examinations.length === 0)
    return <div className="text-center w-full">No examinations found.</div>;

  const sortedExaminations = [...examinations].sort(
    (a, b) =>
      new Date(a.orderedDateTime).getTime() -
      new Date(b.orderedDateTime).getTime()
  );

  return (
    <ScrollArea className="w-full h-full">
      <div className="flex flex-col gap-4 min-w-[350px]">
        {sortedExaminations.map((exam) => (
          <Card key={exam.id} className="w-full">
            <CardHeader className="flex-row justify-between items-start gap-4">
              <div className="space-y-1.5">
                <CardTitle className="flex flex-row gap-4">
                  <div className="font-bold">
                    {exam.examinationDictionary.description}
                  </div>
                  <Badge variant="secondary">{exam.status}</Badge>
                </CardTitle>
                <CardDescription>
                  Ordered on: {format(exam.orderedDateTime, "HH:mm PPPP")}
                </CardDescription>
              </div>
              <Button
                onClick={() =>
                  navigate(`/lab-assistant/process-examination/${exam.id}`)
                }
                disabled={exam.status != LabExaminationStatus.ORDERED}
              >
                Process examination
              </Button>
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
        ))}
      </div>
      <ScrollBar orientation="vertical" />
    </ScrollArea>
  );
};

export { LabAssistantExaminations };
