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
import {
  useCompleteVisitMutation,
  useGetVisitQuery,
  useSetVisitDetailsMutation,
} from "@/features/doctor/api";
import { handleError } from "@/utils";
import { skipToken } from "@reduxjs/toolkit/query";
import { format } from "date-fns";
import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import {
  DoctorLaboratoryExamination,
  DoctorPhysicalExamination,
} from "../components";
import { toast } from "sonner";
import { z } from "zod";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { VisitCompleteRequest, VisitDetailsSetRequest } from "../types";

const completeSchema = z.object({
  diagnostics: z
    .string()
    .min(1, { message: "Diagnosis is required." })
    .max(255, {
      message: "Diagnosis must not be longer than 255 characters.",
    }),
});

const detailsSchema = z.object({
  description: z
    .string()
    .max(255, {
      message: "Description must not be longer than 255 characters.",
    })
    .optional(),
  diagnostics: z
    .string()
    .min(1, { message: "Diagnosis is required." })
    .max(255, {
      message: "Diagnosis must not be longer than 255 characters.",
    }),
});

type CompleteSchemaValues = z.infer<typeof completeSchema>;
type DetailsSchemaValues = z.infer<typeof detailsSchema>;

type VisitDetailsProps = {
  shouldBeDisabled?: boolean;
};

const VisitDetails: React.FC<VisitDetailsProps> = ({
  shouldBeDisabled = false,
}) => {
  const [isCompleteDialogOpen, setIsCompleteDialogOpen] = useState(false);
  const [isDetailsDialogOpen, setIsDetailsDialogOpen] = useState(false);

  const navigate = useNavigate();
  const params = useParams<{ visitId: string }>();
  const visitId = params.visitId;

  const [completeVisit, { isSuccess: isCompleteVisitSuccess }] =
    useCompleteVisitMutation();

  const [setDetails, { isSuccess: isSetDetailsSuccess }] =
    useSetVisitDetailsMutation();

  const {
    data: visit,
    isSuccess: isGetVisitSuccess,
    isFetching: isGetVisitFetching,
    isError: isGetVisitError,
    error: visitError,
    refetch: refetchVisit,
  } = useGetVisitQuery(visitId ?? skipToken, {
    refetchOnMountOrArgChange: true,
  });

  const completeForm = useForm<CompleteSchemaValues>({
    resolver: zodResolver(completeSchema),
    defaultValues: { diagnostics: "" },
    mode: "onChange",
  });

  const detailsForm = useForm<DetailsSchemaValues>({
    resolver: zodResolver(detailsSchema),
    defaultValues: { description: "", diagnostics: "" },
    mode: "onChange",
  });

  const onVisitCompleteHandler = async (data: CompleteSchemaValues) => {
    if (!visitId) return;
    const visitCompleteRequest: VisitCompleteRequest = {
      visitId: visitId,
      diagnostics: data.diagnostics,
    };

    try {
      await completeVisit(visitCompleteRequest).unwrap();
    } catch (error) {
      handleError(error);
    }
  };

  const onDetailsSetHandler = async (data: DetailsSchemaValues) => {
    if (!visitId) return;
    const visitDetailsSetRequest: VisitDetailsSetRequest = {
      visitId: visitId,
      description: data.description || "",
      diagnostics: data.diagnostics,
    };

    try {
      await setDetails(visitDetailsSetRequest).unwrap();
    } catch (error) {
      handleError(error);
    }
  };

  useEffect(() => {
    if (isCompleteVisitSuccess) {
      completeForm.reset();
      setIsCompleteDialogOpen(false);
      refetchVisit();
      toast.success(`Visit completed`);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [isCompleteVisitSuccess]);

  useEffect(() => {
    if (isSetDetailsSuccess) {
      detailsForm.reset();
      setIsDetailsDialogOpen(false);
      refetchVisit();
      toast.success(`Visit details updated`);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [isSetDetailsSuccess]);

  useEffect(() => {
    if (!isGetVisitFetching && isGetVisitSuccess) {
      completeForm.reset({ diagnostics: visit.visit.diagnostics });
      detailsForm.reset({
        description: visit.visit.description,
        diagnostics: visit.visit.diagnostics,
      });
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [isGetVisitFetching]);

  useEffect(() => {
    if (isGetVisitError) {
      handleError(visitError);
      navigate("/doctor");
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [isGetVisitError]);

  if (isGetVisitFetching) return <div className="text-center">Loading...</div>;
  if (!visit)
    return (
      <div className="flex flex-col items-center gap-4">
        <div>Error while loading the visit details.</div>
        <Button asChild>
          <Link to={"/doctor"}>Go back</Link>
        </Button>
      </div>
    );
  return (
    <div className="w-full h-full flex flex-row gap-4">
      <div className="w-2/5 h-full min-w-[286px] flex flex-col justify-between gap-4">
        <div className="flex flex-col gap-4">
          <Card>
            <CardHeader>
              <CardTitle className="flex flex-row gap-4 justify-between">
                <div className="font-bold">Visit Details:</div>
                <Badge variant="secondary">{visit.visit.visitStatus}</Badge>
              </CardTitle>
              <CardDescription>
                {format(visit.visit.scheduledDateTime, "HH:mm PPPP")}
              </CardDescription>
            </CardHeader>
            <CardContent className="space-y-2">
              <p className="text-sm">
                <strong>Patient: </strong>
                {`${visit.selectedPatient.person.firstName} ${visit.selectedPatient.person.lastName}`}
              </p>
              <p className="text-sm">
                <strong>Patient National ID: </strong>
                {visit.selectedPatient.person.nationalIdNumber}
              </p>
              <p className="text-sm">
                <strong>Patient Sex: </strong>
                {visit.selectedPatient.person.sex}
              </p>
              <p className="text-sm">
                <strong>Patient Insurance ID: </strong>
                {visit.selectedPatient.insuranceId}
              </p>
              <p className="text-sm">
                <strong>Doctor Name: </strong>
                {`Dr. ${visit.selectedDoctor.clinicStaff.person.firstName} ${visit.selectedDoctor.clinicStaff.person.lastName}`}
              </p>
            </CardContent>
          </Card>
          <Card>
            <CardHeader className="flex-row items-start justify-between gap-4">
              <div className="space-y-1.5">
                <CardTitle className="font-bold">Result</CardTitle>
                <CardDescription>
                  Patient description and doctor diagnosis
                </CardDescription>
              </div>
              <Dialog
                open={isDetailsDialogOpen}
                onOpenChange={setIsDetailsDialogOpen}
              >
                <DialogTrigger asChild>
                  <Button variant="outline" disabled={shouldBeDisabled}>
                    Edit
                  </Button>
                </DialogTrigger>
                <DialogContent className="sm:max-w-md">
                  <DialogHeader>
                    <DialogTitle>Edit Visit Details</DialogTitle>
                  </DialogHeader>
                  <Form {...detailsForm}>
                    <form
                      onSubmit={detailsForm.handleSubmit(onDetailsSetHandler)}
                      className="grid grid-cols-1 gap-4"
                    >
                      <FormField
                        control={detailsForm.control}
                        name="description"
                        render={({ field }) => (
                          <FormItem>
                            <FormLabel>Description</FormLabel>
                            <FormControl>
                              <Textarea
                                placeholder="Enter patient description..."
                                {...field}
                              />
                            </FormControl>
                            <FormMessage />
                          </FormItem>
                        )}
                      />
                      <FormField
                        control={detailsForm.control}
                        name="diagnostics"
                        render={({ field }) => (
                          <FormItem>
                            <FormLabel>Diagnosis</FormLabel>
                            <FormControl>
                              <Textarea
                                placeholder="Enter diagnosis..."
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
                        <Button type="submit">Save</Button>
                      </DialogFooter>
                    </form>
                  </Form>
                </DialogContent>
              </Dialog>
            </CardHeader>
            <CardContent className="space-y-2">
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
        </div>
        <Button variant="outline" asChild className="w-1/2 min-w-[286px]">
          <Link to="/doctor">Back</Link>
        </Button>
      </div>
      <div className="w-3/5 h-full flex flex-col gap-4">
        <div className="h-[calc(50%-2.25rem)]">
          <DoctorPhysicalExamination
            visitId={visit.visit.id}
            shouldBeDisabled={shouldBeDisabled}
          />
        </div>
        <div className="h-[calc(50%-2.25rem)]">
          <DoctorLaboratoryExamination
            visitId={visit.visit.id}
            shouldBeDisabled={shouldBeDisabled}
          />
        </div>
        <div className="flex flex-row justify-end gap-4">
          <Button variant="outline" asChild>
            <Link
              to={`/doctor/medical-history/${visit.selectedPatient.insuranceId}`}
            >
              Full medical history
            </Link>
          </Button>
          <Dialog
            open={isCompleteDialogOpen}
            onOpenChange={setIsCompleteDialogOpen}
          >
            <DialogTrigger asChild>
              <Button disabled={shouldBeDisabled}>Complete visit</Button>
            </DialogTrigger>
            <DialogContent className="sm:max-w-md">
              <DialogHeader>
                <DialogTitle>Complete visit</DialogTitle>
              </DialogHeader>
              <Form {...completeForm}>
                <form
                  onSubmit={completeForm.handleSubmit(onVisitCompleteHandler)}
                >
                  <FormField
                    control={completeForm.control}
                    name="diagnostics"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>Diagnosis</FormLabel>
                        <FormControl>
                          <Textarea
                            placeholder="Enter visit diagnosis..."
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
                    <Button type="submit">Complete visit</Button>
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

export { VisitDetails };
