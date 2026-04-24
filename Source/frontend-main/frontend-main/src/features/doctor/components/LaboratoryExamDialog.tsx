import { cn } from "@/lib/utils";
import { Check, ChevronsUpDown } from "lucide-react";
import {
  Button,
  Command,
  CommandEmpty,
  CommandGroup,
  CommandInput,
  CommandItem,
  CommandList,
  DialogClose,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
  Popover,
  PopoverContent,
  PopoverTrigger,
  Textarea,
} from "@/components/ui";
import {
  useAddLaboratoryExaminationMutation,
  useGetExaminationsQuery,
} from "@/features/doctor/api";
import { useForm } from "react-hook-form";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import { handleError } from "@/utils";
import { useEffect } from "react";
import { toast } from "sonner";
import { LaboratoryExaminationAddRequest } from "../types";

const laboratoryExaminationSchema = z.object({
  examinationDictCode: z.string({
    required_error: "Examination is required.",
  }),
  doctorNotices: z
    .string({
      required_error: "Notices are required.",
    })
    .max(255, {
      message: "Notices must not be longer than 255 characters.",
    }),
});
type LaboratoryExamValues = z.infer<typeof laboratoryExaminationSchema>;

type LaboratoryExamDialogProps = {
  setOpen: (open: boolean) => void;
  visitId: number;
  refetchVisitExaminations: () => void;
};

const LaboratoryExamDialog: React.FC<LaboratoryExamDialogProps> = ({
  setOpen,
  visitId,
  refetchVisitExaminations,
}) => {
  const [addExamination, { isSuccess: isAddExaminationSuccess }] =
    useAddLaboratoryExaminationMutation();

  const {
    data: examinations = [],
    isError: isExaminationsError,
    error: examinationsError,
  } = useGetExaminationsQuery({});

  const defaultValues: Partial<LaboratoryExamValues> = {
    examinationDictCode: undefined,
    doctorNotices: undefined,
  };

  const form = useForm<LaboratoryExamValues>({
    resolver: zodResolver(laboratoryExaminationSchema),
    defaultValues,
    mode: "onChange",
  });

  const onAddHandler = async (exam: LaboratoryExamValues) => {
    const examAddRequest: LaboratoryExaminationAddRequest = {
      examinationCode: exam.examinationDictCode,
      doctorNotices: exam.doctorNotices,
      visitId: visitId,
    };

    try {
      await addExamination(examAddRequest).unwrap();
    } catch (error) {
      handleError(error);
    }
  };

  useEffect(() => {
    if (isAddExaminationSuccess) {
      form.reset();
      setOpen(false);
      refetchVisitExaminations();
      toast.success(`Laboratory examination ordered successfully`);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [isAddExaminationSuccess]);

  useEffect(() => {
    if (isExaminationsError) {
      handleError(examinationsError);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [isExaminationsError]);

  const laboratoryExaminations = examinations.filter(
    (examination) => examination.type === "LABORATORY"
  );

  return (
    <DialogContent className="sm:max-w-md">
      <DialogHeader>
        <DialogTitle>Order laboratory examination</DialogTitle>
        <DialogDescription>
          Enter the details of the examination below.
        </DialogDescription>
      </DialogHeader>
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onAddHandler)}>
          <FormField
            control={form.control}
            name="examinationDictCode"
            render={({ field }) => (
              <FormItem className="flex flex-col">
                <FormLabel>Examination</FormLabel>
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
                          ? laboratoryExaminations.find(
                              (e) => e.code === field.value
                            )?.description
                          : "Select examination"}
                        <ChevronsUpDown className="ml-2 h-4 w-4 shrink-0 opacity-50" />
                      </Button>
                    </FormControl>
                  </PopoverTrigger>
                  <PopoverContent
                    className="min-w-[300px] w-auto p-0"
                    align="start"
                  >
                    <Command className="rounded-lg border shadow-md">
                      <CommandInput placeholder="Search examination..." />
                      <CommandList>
                        <CommandEmpty>No examinations found.</CommandEmpty>
                        <CommandGroup>
                          {laboratoryExaminations.map((examination) => (
                            <CommandItem
                              key={examination.code}
                              value={examination.description}
                              onSelect={() => {
                                form.setValue(
                                  "examinationDictCode",
                                  examination.code
                                );
                              }}
                            >
                              <Check
                                className={cn(
                                  "mr-2 h-4 w-4",
                                  field.value === examination.code
                                    ? "opacity-100"
                                    : "opacity-0"
                                )}
                              />
                              <span className="mr-2">
                                {examination.description}
                              </span>
                              <span className="ml-auto">
                                {examination.code}
                              </span>
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
            name="doctorNotices"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Notices</FormLabel>
                <FormControl>
                  <Textarea
                    placeholder="Enter any notices here..."
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
            <Button type="submit">Order examination</Button>
          </DialogFooter>
        </form>
      </Form>
    </DialogContent>
  );
};

export { LaboratoryExamDialog };
