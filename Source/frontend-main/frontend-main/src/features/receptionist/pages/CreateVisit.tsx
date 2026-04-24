import {
  Button,
  Calendar,
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
  Command,
  CommandEmpty,
  CommandGroup,
  CommandInput,
  CommandItem,
  CommandList,
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
  Popover,
  PopoverContent,
  PopoverTrigger,
  TimePicker,
} from "@/components/ui";
import { cn } from "@/lib/utils";
import {
  useCreateVisitMutation,
  useGetDoctorsQuery,
  useGetPatientsQuery,
} from "@/features/receptionist/api";
import { VisitCreateRequest } from "../types";
import { handleError } from "@/utils";
import { zodResolver } from "@hookform/resolvers/zod";
import { format, startOfDay } from "date-fns";
import { CalendarIcon, Check, ChevronsUpDown } from "lucide-react";
import { useEffect } from "react";
import { useForm } from "react-hook-form";
import { Link, useNavigate } from "react-router-dom";
import { toast } from "sonner";
import { z } from "zod";

const createVisitSchema = z.object({
  patientInsuranceId: z.string({
    required_error: "Patient is required.",
  }),
  doctorNpwzId: z.string({
    required_error: "Doctor is required.",
  }),
  visitDate: z.date({
    required_error: "Date of visit is required.",
  }),
});

type CreateVisitValues = z.infer<typeof createVisitSchema>;

const CreateVisit: React.FC = () => {
  const navigate = useNavigate();

  const [createVisit, { isSuccess: isCreateVisitSuccess }] =
    useCreateVisitMutation();

  const {
    data: patients = [],
    isError: isPatientsError,
    error: patientsError,
  } = useGetPatientsQuery({});

  const {
    data: doctors = [],
    isError: isDoctorsError,
    error: doctorsError,
  } = useGetDoctorsQuery({});

  const defaultValues: Partial<CreateVisitValues> = {
    patientInsuranceId: undefined,
    doctorNpwzId: undefined,
    visitDate: undefined,
  };

  const form = useForm<CreateVisitValues>({
    resolver: zodResolver(createVisitSchema),
    defaultValues,
    mode: "onChange",
  });

  const onCreateVisitHandler = async (visitValues: CreateVisitValues) => {
    const visitCreateRequest: VisitCreateRequest = {
      doctorNpwzId: visitValues.doctorNpwzId,
      patientInsuranceId: visitValues.patientInsuranceId,
      scheduledDateTime: format(visitValues.visitDate, "HH:mm dd.MM.yyyy"),
    };

    try {
      await createVisit(visitCreateRequest).unwrap();
    } catch (error) {
      handleError(error);
    }
  };

  useEffect(() => {
    if (isCreateVisitSuccess) {
      toast.success(`Visit created successfully`);
      navigate("/receptionist");
    }
  }, [isCreateVisitSuccess, navigate]);

  useEffect(() => {
    if (isPatientsError) {
      handleError(patientsError);
    }
    if (isDoctorsError) {
      handleError(doctorsError);
    }
  }, [isPatientsError, patientsError, isDoctorsError, doctorsError]);

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onCreateVisitHandler)}>
        <Card className="max-w-[700px] mx-auto">
          <CardHeader>
            <CardTitle className="font-bold">Create New Visit</CardTitle>
            <CardDescription>
              Enter the details of the visit below
            </CardDescription>
          </CardHeader>
          <CardContent className="grid gap-4">
            <FormField
              control={form.control}
              name="patientInsuranceId"
              render={({ field }) => (
                <FormItem className="flex flex-col">
                  <FormLabel>Patient</FormLabel>
                  <Popover>
                    <PopoverTrigger asChild>
                      <FormControl>
                        <Button
                          variant="outline"
                          role="combobox"
                          className={cn(
                            "justify-between font-normal",
                            !field.value && "text-muted-foreground"
                          )}
                        >
                          {field.value
                            ? (() => {
                                const patient = patients.find(
                                  (p) => p.insuranceId === field.value
                                );
                                return patient
                                  ? `${patient.person.firstName} ${patient.person.lastName} ${patient.insuranceId}`
                                  : "Select patient";
                              })()
                            : "Select patient"}
                          <ChevronsUpDown className="ml-2 h-4 w-4 shrink-0 opacity-50" />
                        </Button>
                      </FormControl>
                    </PopoverTrigger>
                    <PopoverContent
                      className="min-w-[300px] w-auto p-0"
                      align="start"
                    >
                      <Command
                        filter={(value: string, search: string): number => {
                          const patient = patients.find(
                            (p) => p.insuranceId === value
                          );
                          if (
                            [
                              patient?.person.firstName,
                              patient?.person.lastName,
                              patient?.insuranceId,
                            ]
                              .join(" ")
                              .toLowerCase()
                              .includes(search.toLowerCase())
                          )
                            return 1;
                          else return 0;
                        }}
                      >
                        <CommandInput placeholder="Search patient..." />
                        <CommandList>
                          <CommandEmpty>No patient found.</CommandEmpty>
                          <CommandGroup>
                            {patients.map((patient) => (
                              <CommandItem
                                key={patient.insuranceId}
                                value={patient.insuranceId}
                                onSelect={() => {
                                  form.setValue(
                                    "patientInsuranceId",
                                    patient.insuranceId
                                  );
                                }}
                              >
                                <Check
                                  className={cn(
                                    "mr-2 h-4 w-4",
                                    field.value === patient.insuranceId
                                      ? "opacity-100"
                                      : "opacity-0"
                                  )}
                                />
                                <div className="mr-2">
                                  {patient.person.firstName}{" "}
                                  {patient.person.lastName}
                                </div>
                                <div className="ml-auto">
                                  ({patient.insuranceId})
                                </div>
                              </CommandItem>
                            ))}
                          </CommandGroup>
                        </CommandList>
                      </Command>
                    </PopoverContent>
                  </Popover>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="doctorNpwzId"
              render={({ field }) => (
                <FormItem className="flex flex-col">
                  <FormLabel>Doctor</FormLabel>
                  <Popover>
                    <PopoverTrigger asChild>
                      <FormControl>
                        <Button
                          variant="outline"
                          role="combobox"
                          className={cn(
                            "justify-between font-normal",
                            !field.value && "text-muted-foreground"
                          )}
                        >
                          {field.value
                            ? (() => {
                                const doctor = doctors.find(
                                  (d) => d.npwzId === field.value
                                );
                                return doctor
                                  ? `${doctor.clinicStaff.person.firstName} ${doctor.clinicStaff.person.lastName} ${doctor.npwzId}`
                                  : "Select doctor";
                              })()
                            : "Select doctor"}
                          <ChevronsUpDown className="ml-2 h-4 w-4 shrink-0 opacity-50" />
                        </Button>
                      </FormControl>
                    </PopoverTrigger>
                    <PopoverContent
                      className="min-w-[300px] w-auto p-0"
                      align="start"
                    >
                      <Command
                        filter={(value: string, search: string): number => {
                          const doctor = doctors.find(
                            (d) => d.npwzId === value
                          );
                          if (
                            [
                              doctor?.clinicStaff.person.firstName,
                              doctor?.clinicStaff.person.lastName,
                              doctor?.npwzId,
                            ]
                              .join(" ")
                              .toLowerCase()
                              .includes(search.toLowerCase())
                          )
                            return 1;
                          else return 0;
                        }}
                      >
                        <CommandInput placeholder="Search doctor..." />
                        <CommandList>
                          <CommandEmpty>No doctor found.</CommandEmpty>
                          <CommandGroup>
                            {doctors.map((doctor) => (
                              <CommandItem
                                key={doctor.npwzId}
                                value={doctor.npwzId}
                                onSelect={() => {
                                  form.setValue("doctorNpwzId", doctor.npwzId);
                                }}
                              >
                                <Check
                                  className={cn(
                                    "mr-2 h-4 w-4",
                                    field.value === doctor.npwzId
                                      ? "opacity-100"
                                      : "opacity-0"
                                  )}
                                />
                                <div className="mr-2">
                                  {doctor.clinicStaff.person.firstName}{" "}
                                  {doctor.clinicStaff.person.lastName}
                                </div>
                                <div className="ml-auto">({doctor.npwzId})</div>
                              </CommandItem>
                            ))}
                          </CommandGroup>
                        </CommandList>
                      </Command>
                    </PopoverContent>
                  </Popover>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="visitDate"
              render={({ field }) => (
                <FormItem className="flex flex-col">
                  <FormLabel>Date and time of visit</FormLabel>
                  <Popover>
                    <PopoverTrigger asChild>
                      <FormControl>
                        <Button
                          variant="outline"
                          className={cn(
                            "pl-3 text-left font-normal",
                            !field.value && "text-muted-foreground"
                          )}
                        >
                          {field.value ? (
                            format(field.value, "PPP HH:mm")
                          ) : (
                            <span>Pick a date</span>
                          )}
                          <CalendarIcon className="ml-auto h-4 w-4 opacity-50" />
                        </Button>
                      </FormControl>
                    </PopoverTrigger>
                    <PopoverContent className="w-auto p-0" align="start">
                      <Calendar
                        mode="single"
                        selected={field.value}
                        onSelect={field.onChange}
                        disabled={(date) => date < startOfDay(new Date())}
                        initialFocus
                      />
                      <div className="p-3 border-t border-border">
                        <TimePicker
                          setDate={field.onChange}
                          date={field.value}
                        />
                      </div>
                    </PopoverContent>
                  </Popover>
                  <FormMessage />
                </FormItem>
              )}
            />
          </CardContent>
          <CardFooter className="flex gap-4 justify-end">
            <Button variant="secondary" type="button" asChild>
              <Link to={"/receptionist"}>Cancel</Link>
            </Button>
            <Button type="submit">Create Visit</Button>
          </CardFooter>
        </Card>
      </form>
    </Form>
  );
};

export { CreateVisit };
