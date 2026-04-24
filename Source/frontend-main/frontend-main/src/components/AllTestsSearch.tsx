import { z } from "zod";

import { cn } from "@/lib/utils";
import { format } from "date-fns";
import { CalendarIcon } from "lucide-react";
import { useNavigate } from "react-router-dom";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import {
  LabExaminationSearchRequest,
  LabExaminationStatus,
  RightsLevel,
} from "@/types";
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
} from "@/components/ui";

const searchLabExaminationsSchema = z.object({
  status: z.nativeEnum(LabExaminationStatus).optional(),
  examinationCode: z
    .string()
    .max(20, {
      message: "Examination code must not be longer than 20 characters.",
    })
    .optional(),
  rightsLevel: z.nativeEnum(RightsLevel).optional(),
  orderedDateTime: z.date().optional(),
});

type SearchLabExaminationsValues = z.infer<typeof searchLabExaminationsSchema>;

type AllTestsSearchProps = {
  setSearchParams: (searchParams: Partial<LabExaminationSearchRequest>) => void;
  isGetLabExaminationsLoading: boolean;
};

const AllTestsSearch: React.FC<AllTestsSearchProps> = ({
  setSearchParams,
  isGetLabExaminationsLoading,
}) => {
  const navigate = useNavigate();

  const defaultValues: Partial<SearchLabExaminationsValues> = {
    status: undefined,
    examinationCode: "",
    rightsLevel: undefined,
    orderedDateTime: undefined,
  };

  const form = useForm<SearchLabExaminationsValues>({
    resolver: zodResolver(searchLabExaminationsSchema),
    defaultValues,
    mode: "onChange",
  });

  const onSearchHandler = (searchExamValues: SearchLabExaminationsValues) => {
    setSearchParams({
      status: searchExamValues.status || undefined,
      examinationCode: searchExamValues.examinationCode || undefined,
      rightsLevel: searchExamValues.rightsLevel || undefined,
      orderedDateTime: searchExamValues.orderedDateTime
        ? format(searchExamValues.orderedDateTime, "HH:mm dd.MM.yyyy")
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
                  Enter the details of the examination below
                </CardDescription>
              </CardHeader>
              <CardContent className="grid gap-4">
                <FormField
                  control={form.control}
                  name="status"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Examination Status</FormLabel>
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
                          <SelectItem value="ORDERED">Ordered</SelectItem>
                          <SelectItem value="COMPLETED">Completed</SelectItem>
                          <SelectItem value="CANCELLED">Cancelled</SelectItem>
                          <SelectItem value="APPROVED">Approved</SelectItem>
                          <SelectItem value="REJECTED">Rejected</SelectItem>
                        </SelectContent>
                      </Select>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  control={form.control}
                  name="examinationCode"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Examination code</FormLabel>
                      <FormControl>
                        <Input
                          placeholder="EKG"
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
                  name="rightsLevel"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Examination rights level</FormLabel>
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
                            <SelectValue placeholder="Select rights level" />
                          </SelectTrigger>
                        </FormControl>
                        <SelectContent>
                          <SelectItem value="NONE">None</SelectItem>
                          <SelectItem value="LOW">Low</SelectItem>
                          <SelectItem value="MEDIUM">Medium</SelectItem>
                          <SelectItem value="HIGH">High</SelectItem>
                        </SelectContent>
                      </Select>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  control={form.control}
                  name="orderedDateTime"
                  render={({ field }) => (
                    <FormItem className="flex flex-col">
                      <FormLabel>Date of examination</FormLabel>
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
                  {isGetLabExaminationsLoading ? "Loading..." : "Search"}
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

export { AllTestsSearch };
