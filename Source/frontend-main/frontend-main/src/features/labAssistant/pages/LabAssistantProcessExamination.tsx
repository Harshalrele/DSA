import {
  Badge,
  Button,
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
  Dialog,
  DialogClose,
  DialogContent,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
  Textarea,
} from "@/components/ui";
import { handleError } from "@/utils";
import { skipToken } from "@reduxjs/toolkit/query";
import { format } from "date-fns";
import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import {
  useCancelLabExaminationMutation,
  useCompleteLabExaminationMutation,
  useGetAssistantExaminationByIdQuery,
} from "../api";
import { z } from "zod";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import {
  LabExaminationCancelRequest,
  LabExaminationCompleteRequest,
} from "../types";
import { toast } from "sonner";

const cancelSchema = z.object({
  cancellationReason: z
    .string({
      required_error: "Cancellation reason is required.",
    })
    .max(255, {
      message: "Cancellation reason must not be longer than 255 characters.",
    }),
});

const completeSchema = z.object({
  result: z
    .string({
      required_error: "Result is required.",
    })
    .max(255, {
      message: "Result must not be longer than 255 characters.",
    }),
});

type CancelSchemaValues = z.infer<typeof cancelSchema>;
type CompleteSchemaValues = z.infer<typeof completeSchema>;

const LabAssistantProcessExamination: React.FC = () => {
  const [isCancelDialogOpen, setIsCancelDialogOpen] = useState(false);
  const [isCompleteDialogOpen, setIsCompleteDialogOpen] = useState(false);

  const navigate = useNavigate();
  const params = useParams<{ examinationId: string }>();
  const examinationId = params.examinationId;

  const [cancelExamination, { isSuccess: isCancelExaminationSuccess }] =
    useCancelLabExaminationMutation();
  const [completeExamination, { isSuccess: isCompleteExaminationSuccess }] =
    useCompleteLabExaminationMutation();

  const {
    data: examination,
    isLoading: isGetExaminationLoading,
    isError: isGetExaminationError,
    error: examinationError,
    refetch: refetchExamination,
  } = useGetAssistantExaminationByIdQuery(examinationId ?? skipToken);

  const cancelForm = useForm<CancelSchemaValues>({
    resolver: zodResolver(cancelSchema),
    defaultValues: { cancellationReason: "" },
    mode: "onChange",
  });

  const completeForm = useForm<CompleteSchemaValues>({
    resolver: zodResolver(completeSchema),
    defaultValues: { result: "" },
    mode: "onChange",
  });

  const onCancelHandler = async (cancelSchemaValues: CancelSchemaValues) => {
    if (!examinationId) return;
    const examCancelRequest: LabExaminationCancelRequest = {
      examinationId: examinationId,
      cancellationReason: cancelSchemaValues.cancellationReason,
    };

    try {
      await cancelExamination(examCancelRequest).unwrap();
    } catch (error) {
      handleError(error);
    }
  };

  const onCompleteHandler = async (
    completeSchemaValues: CompleteSchemaValues
  ) => {
    if (!examinationId) return;
    const examCompleteRequest: LabExaminationCompleteRequest = {
      examinationId: examinationId,
      result: completeSchemaValues.result,
    };

    try {
      await completeExamination(examCompleteRequest).unwrap();
    } catch (error) {
      handleError(error);
    }
  };

  useEffect(() => {
    if (isCancelExaminationSuccess) {
      cancelForm.reset();
      setIsCancelDialogOpen(false);
      refetchExamination();
      toast.info(`Examination cancelled`);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [isCancelExaminationSuccess]);

  useEffect(() => {
    if (isCompleteExaminationSuccess) {
      completeForm.reset();
      setIsCompleteDialogOpen(false);
      refetchExamination();
      toast.info(`Examination completed`);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [isCompleteExaminationSuccess]);

  useEffect(() => {
    if (isGetExaminationError) {
      handleError(examinationError);
      navigate("/lab-assistant");
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [isGetExaminationError]);

  if (isGetExaminationLoading)
    return <div className="text-center">Loading...</div>;
  if (!examination)
    return (
      <div className="flex flex-col items-center gap-4">
        <div>Error while loading the examination details.</div>
        <Button asChild>
          <Link to={"/lab-assistant"}>Go back</Link>
        </Button>
      </div>
    );

  return (
    <div className="h-full w-full min-w-[286px] flex flex-col justify-between gap-4">
      <Card>
        <CardHeader>
          <CardTitle className="flex flex-row gap-4 justify-between">
            <div className="font-bold">
              {examination.examinationDictionary.description}
            </div>
            <Badge variant="secondary">{examination.status}</Badge>
          </CardTitle>
          <CardDescription>
            Ordered on: {format(examination.orderedDateTime, "HH:mm PPPP")}
          </CardDescription>
        </CardHeader>
        <CardContent className="space-y-2">
          {examination.result && (
            <p className="text-sm">
              <strong>Result: </strong>
              {examination.result}
            </p>
          )}
          <p className="text-sm">
            <strong>Code: </strong>
            {examination.examinationDictionary.code}
          </p>
          <p className="text-sm">
            <strong>Rights Level: </strong>
            {examination.examinationDictionary.rightsLevel}
          </p>
          <p className="text-sm">
            <strong>Type: </strong>
            {examination.examinationDictionary.type}
          </p>
          <p className="text-sm">
            <strong>Execution Date: </strong>
            {format(new Date(examination.executionDateTime), "HH:mm PPPP")}
          </p>
          {examination.approvalDateTime && (
            <p className="text-sm">
              <strong>Approval Date: </strong>
              {format(new Date(examination.approvalDateTime), "HH:mm PPPP")}
            </p>
          )}
          {examination.doctorNotices && (
            <p className="text-sm">
              <strong>Doctor's Notes: </strong>
              {examination.doctorNotices}
            </p>
          )}
          {examination.supervisorNotices && (
            <p className="text-sm">
              <strong>Supervisor's Notes: </strong>
              {examination.supervisorNotices}
            </p>
          )}
          {examination.cancellationReason && (
            <p className="text-sm">
              <strong>Cancellation Reason: </strong>
              {examination.cancellationReason}
            </p>
          )}
          {/* <p className="text-sm">
            <strong>Lab Assistant: </strong>
            {examination.labAssistant ? examination.labAssistant : "N/A"}
          </p>
          <p className="text-sm">
            <strong>Lab Supervisor: </strong>
            {examination.labSupervisor ? examination.labSupervisor : "N/A"}
          </p> */}
        </CardContent>
      </Card>
      <div className="flex flex-row gap-4 justify-between">
        <Button variant="outline" asChild className="min-w-[200px]">
          <Link to="/lab-assistant">Back</Link>
        </Button>
        <div className="flex gap-4">
          <Dialog
            open={isCancelDialogOpen}
            onOpenChange={setIsCancelDialogOpen}
          >
            <DialogTrigger asChild>
              <Button variant="outline">Cancel examination</Button>
            </DialogTrigger>
            <DialogContent className="sm:max-w-md">
              <DialogHeader>
                <DialogTitle>Cancel examination</DialogTitle>
              </DialogHeader>
              <Form {...cancelForm}>
                <form onSubmit={cancelForm.handleSubmit(onCancelHandler)}>
                  <FormField
                    control={cancelForm.control}
                    name="cancellationReason"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>Cancellation reason</FormLabel>
                        <FormControl>
                          <Textarea placeholder="Reason..." {...field} />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  <DialogFooter className="sm:justify-end mt-4">
                    <DialogClose asChild>
                      <Button type="button" variant="secondary">
                        Close
                      </Button>
                    </DialogClose>
                    <Button type="submit">Cancel examination</Button>
                  </DialogFooter>
                </form>
              </Form>
            </DialogContent>
          </Dialog>
          <Dialog
            open={isCompleteDialogOpen}
            onOpenChange={setIsCompleteDialogOpen}
          >
            <DialogTrigger asChild>
              <Button>Complete examination</Button>
            </DialogTrigger>
            <DialogContent className="sm:max-w-md">
              <DialogHeader>
                <DialogTitle>Complete examination</DialogTitle>
              </DialogHeader>
              <Form {...completeForm}>
                <form onSubmit={completeForm.handleSubmit(onCompleteHandler)}>
                  <FormField
                    control={completeForm.control}
                    name="result"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>Result</FormLabel>
                        <FormControl>
                          <Textarea
                            placeholder="Enter examination result..."
                            {...field}
                          />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  <DialogFooter className="sm:justify-end mt-4">
                    <DialogClose asChild>
                      <Button type="button" variant="secondary">
                        Close
                      </Button>
                    </DialogClose>
                    <Button type="submit">Complete examination</Button>
                  </DialogFooter>
                </form>
              </Form>
            </DialogContent>
          </Dialog>
        </div>
      </div>
    </div>
  );
};

export { LabAssistantProcessExamination };
