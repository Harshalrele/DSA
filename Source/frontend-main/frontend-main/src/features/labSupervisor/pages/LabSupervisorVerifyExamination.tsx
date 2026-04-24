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

import { z } from "zod";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";

import { toast } from "sonner";
import {
  useApproveLabExaminationMutation,
  useGetSupervisorExaminationByIdQuery,
  useRejectLabExaminationMutation,
} from "../api";
import {
  LabExaminationApproveRequest,
  LabExaminationRejectRequest,
} from "../types";

const approveSchema = z.object({
  supervisorNotices: z
    .string()
    .max(255, {
      message: "Notes must not be longer than 255 characters.",
    })
    .optional(),
});

const rejectSchema = z.object({
  supervisorNotices: z
    .string()
    .min(1, { message: "Notes are required." })
    .max(255, {
      message: "Notes must not be longer than 255 characters.",
    }),
});

type ApproveSchemaValues = z.infer<typeof approveSchema>;
type RejectSchemaValues = z.infer<typeof rejectSchema>;

const LabSupervisorVerifyExamination: React.FC = () => {
  const [isApproveDialogOpen, setIsApproveDialogOpen] = useState(false);
  const [isRejectDialogOpen, setIsRejectDialogOpen] = useState(false);

  const navigate = useNavigate();
  const params = useParams<{ examinationId: string }>();
  const examinationId = params.examinationId;

  const [approveExamination, { isSuccess: isApproveExaminationSuccess }] =
    useApproveLabExaminationMutation();
  const [rejectExamination, { isSuccess: isRejectExaminationSuccess }] =
    useRejectLabExaminationMutation();

  const {
    data: examination,
    isLoading: isGetExaminationLoading,
    isError: isGetExaminationError,
    error: examinationError,
    refetch: refetchExamination,
  } = useGetSupervisorExaminationByIdQuery(examinationId ?? skipToken);

  const approveForm = useForm<ApproveSchemaValues>({
    resolver: zodResolver(approveSchema),
    defaultValues: { supervisorNotices: "" },
    mode: "onChange",
  });

  const rejectForm = useForm<RejectSchemaValues>({
    resolver: zodResolver(rejectSchema),
    defaultValues: { supervisorNotices: "" },
    mode: "onChange",
  });

  const onApproveHandler = async (approveSchemaValues: ApproveSchemaValues) => {
    if (!examinationId) return;
    const examApproveRequest: LabExaminationApproveRequest = {
      examinationId: examinationId,
      supervisorNotices: approveSchemaValues.supervisorNotices,
    };

    try {
      await approveExamination(examApproveRequest).unwrap();
    } catch (error) {
      handleError(error);
    }
  };

  const onRejectHandler = async (rejectSchemaValues: RejectSchemaValues) => {
    if (!examinationId) return;
    const examRejectRequest: LabExaminationRejectRequest = {
      examinationId: examinationId,
      supervisorNotices: rejectSchemaValues.supervisorNotices,
    };

    try {
      await rejectExamination(examRejectRequest).unwrap();
    } catch (error) {
      handleError(error);
    }
  };

  useEffect(() => {
    if (isApproveExaminationSuccess) {
      approveForm.reset();
      setIsApproveDialogOpen(false);
      refetchExamination();
      toast.info(`Examination approved`);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [isApproveExaminationSuccess]);

  useEffect(() => {
    if (isRejectExaminationSuccess) {
      rejectForm.reset();
      setIsRejectDialogOpen(false);
      refetchExamination();
      toast.info(`Examination rejected`);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [isRejectExaminationSuccess]);

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
        </CardContent>
      </Card>
      <div className="flex flex-row gap-4 justify-between">
        <Button variant="outline" asChild className="min-w-[200px]">
          <Link to="/lab-supervisor">Back</Link>
        </Button>
        <div className="flex gap-4">
          <Dialog
            open={isRejectDialogOpen}
            onOpenChange={setIsRejectDialogOpen}
          >
            <DialogTrigger asChild>
              <Button variant="outline">Reject examination</Button>
            </DialogTrigger>
            <DialogContent className="sm:max-w-md">
              <DialogHeader>
                <DialogTitle>Reject examination</DialogTitle>
              </DialogHeader>
              <Form {...rejectForm}>
                <form onSubmit={rejectForm.handleSubmit(onRejectHandler)}>
                  <FormField
                    control={rejectForm.control}
                    name="supervisorNotices"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>Supervisor notes</FormLabel>
                        <FormControl>
                          <Textarea placeholder="Notes..." {...field} />
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
                    <Button type="submit">Reject examination</Button>
                  </DialogFooter>
                </form>
              </Form>
            </DialogContent>
          </Dialog>
          <Dialog
            open={isApproveDialogOpen}
            onOpenChange={setIsApproveDialogOpen}
          >
            <DialogTrigger asChild>
              <Button>Approve examination</Button>
            </DialogTrigger>
            <DialogContent className="sm:max-w-md">
              <DialogHeader>
                <DialogTitle>Approve examination</DialogTitle>
              </DialogHeader>
              <Form {...approveForm}>
                <form onSubmit={approveForm.handleSubmit(onApproveHandler)}>
                  <FormField
                    control={approveForm.control}
                    name="supervisorNotices"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>Supervisor notes</FormLabel>
                        <FormControl>
                          <Textarea placeholder="Notes..." {...field} />
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
                    <Button type="submit">Approve examination</Button>
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

export { LabSupervisorVerifyExamination };
