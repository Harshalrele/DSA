import { z } from "zod";
import {
  Button,
  Calendar,
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
  Input,
  Popover,
  PopoverContent,
  PopoverTrigger,
  ScrollArea,
  ScrollBar,
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "./ui";
import { cn } from "@/lib/utils";
import { format } from "date-fns";
import { CalendarIcon } from "lucide-react";
import { useNavigate } from "react-router-dom";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { VisitSearchRequest, VisitStatus } from "@/types";

const searchVisitsSchema = z.object({
  patientFirstName: z
    .string()
    .max(20, {
      message: "First name must not be longer than 20 characters.",
    })
    .optional(),
  patientLastName: z
    .string()
    .max(20, {
      message: "Last name must not be longer than 20 characters.",
    })
    .optional(),
  patientInsuranceId: z
    .string()
    .max(20, {
      message: "Insurance ID must not be longer than 20 characters.",
    })
    .optional(),
  doctorFirstName: z
    .string()
    .max(20, {
      message: "First name must not be longer than 20 characters.",
    })
    .optional(),
  doctorLastName: z
    .string()
    .max(20, {
      message: "Last name must not be longer than 20 characters.",
    })
    .optional(),
  doctorNpwzId: z
    .string()
    .max(20, {
      message: "NPWZ ID must not be longer than 20 characters.",
    })
    .optional(),
  visitStatus: z.nativeEnum(VisitStatus).optional(),
  visitDate: z.date().optional(),
});

type SearchVisitsValues = z.infer<typeof searchVisitsSchema>;

type AllVisitsSearchProps = {
  setSearchParams: (searchParams: Partial<VisitSearchRequest>) => void;
  isGetVisitsLoading: boolean;
};

const AllVisitsSearch: React.FC<AllVisitsSearchProps> = ({
  setSearchParams,
  isGetVisitsLoading,
}) => {
  const navigate = useNavigate();

  const defaultValues: Partial<SearchVisitsValues> = {
    patientFirstName: "",
    patientLastName: "",
    patientInsuranceId: "",
    doctorFirstName: "",
    doctorLastName: "",
    doctorNpwzId: "",
    visitStatus: undefined,
    visitDate: undefined,
  };

  const form = useForm<SearchVisitsValues>({
    resolver: zodResolver(searchVisitsSchema),
    defaultValues,
    mode: "onChange",
  });

  const onSearchHandler = (searchVisitValues: SearchVisitsValues) => {
    setSearchParams({
      patientFirstName: searchVisitValues.patientFirstName || undefined,
      patientLastName: searchVisitValues.patientLastName || undefined,
      patientInsuranceId: searchVisitValues.patientInsuranceId || undefined,
      doctorFirstName: searchVisitValues.doctorFirstName || undefined,
      doctorLastName: searchVisitValues.doctorLastName || undefined,
      doctorNpwzId: searchVisitValues.doctorNpwzId || undefined,
      status: searchVisitValues.visitStatus || undefined,
      scheduledDate: searchVisitValues.visitDate
        ? format(searchVisitValues.visitDate, "HH:mm dd.MM.yyyy")
        : undefined,
    });
  };

  return (
    <div className="h-full flex flex-col justify-between gap-4">
      <ScrollArea className="h-full min-w-[286px]">
        <Form {...form}>
          <form onSubmit={form.handleSubmit(onSearchHandler)}>
            <Card>
              <CardHeader>
                <CardTitle className="font-bold">Search</CardTitle>
                <CardDescription>
                  Enter the details of the visit below
                </CardDescription>
              </CardHeader>
              <CardContent className="grid gap-4">
                <FormField
                  control={form.control}
                  name="patientFirstName"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Patient First Name</FormLabel>
                      <FormControl>
                        <Input
                          placeholder="John"
                          autoComplete="off"
                          {...field}
                        />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  control={form.control}
                  name="patientLastName"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Patient Last Name</FormLabel>
                      <FormControl>
                        <Input
                          placeholder="Doe"
                          autoComplete="off"
                          {...field}
                        />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  control={form.control}
                  name="patientInsuranceId"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Patient Insurance ID</FormLabel>
                      <FormControl>
                        <Input
                          placeholder="123456"
                          autoComplete="off"
                          {...field}
                        />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  control={form.control}
                  name="doctorFirstName"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Doctor First Name</FormLabel>
                      <FormControl>
                        <Input
                          placeholder="Jane"
                          autoComplete="off"
                          {...field}
                        />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  control={form.control}
                  name="doctorLastName"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Doctor Last Name</FormLabel>
                      <FormControl>
                        <Input
                          placeholder="Smith"
                          autoComplete="off"
                          {...field}
                        />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  control={form.control}
                  name="doctorNpwzId"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Doctor NPWZ ID</FormLabel>
                      <FormControl>
                        <Input
                          placeholder="NPWZ123456"
                          autoComplete="off"
                          {...field}
                        />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  control={form.control}
                  name="visitStatus"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Visit Status</FormLabel>
                      <Select
                        onValueChange={field.onChange}
                        value={field.value || ""}
                      >
                        <FormControl>
                          <SelectTrigger
                            className={cn(
                              !field.value && "text-muted-foreground"
                            )}
                          >
                            <SelectValue placeholder="Select status" />
                          </SelectTrigger>
                        </FormControl>
                        <SelectContent>
                          <SelectItem value="REGISTERED">Registered</SelectItem>
                          <SelectItem value="IN_PROGRESS">
                            In progress
                          </SelectItem>
                          <SelectItem value="COMPLETED">Completed</SelectItem>
                          <SelectItem value="CANCELLED">Cancelled</SelectItem>
                        </SelectContent>
                      </Select>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  control={form.control}
                  name="visitDate"
                  render={({ field }) => (
                    <FormItem className="flex flex-col">
                      <FormLabel>Date of visit</FormLabel>
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
                                format(field.value, "PPP")
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
                            disabled={(date) => date < new Date("1900-01-01")}
                            initialFocus
                          />
                        </PopoverContent>
                      </Popover>
                      <FormMessage />
                    </FormItem>
                  )}
                />
              </CardContent>
              <CardFooter>
                <Button type="submit" className="ml-auto">
                  {isGetVisitsLoading ? "Loading..." : "Search"}
                </Button>
              </CardFooter>
            </Card>
          </form>
        </Form>
        <ScrollBar orientation="vertical" />
      </ScrollArea>
      <Button variant="outline" onClick={() => navigate(-1)}>
        Back
      </Button>
    </div>
  );
};

export { AllVisitsSearch };
