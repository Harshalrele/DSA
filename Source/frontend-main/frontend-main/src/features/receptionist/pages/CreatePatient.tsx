import {
  Button,
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
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui";
import { useCreatePatientMutation } from "@/features/receptionist/api";
import { Sex } from "@/types";
import { handleError } from "@/utils";
import { zodResolver } from "@hookform/resolvers/zod";
import { useEffect } from "react";
import { useForm } from "react-hook-form";
import { Link, useNavigate } from "react-router-dom";
import { toast } from "sonner";
import { z } from "zod";
import { PatientCreateRequest } from "../types";
import { cn } from "@/lib/utils";

const createPatientSchema = z.object({
  firstName: z.string().min(1, { message: "First name is required." }),
  lastName: z.string().min(1, { message: "Last name is required." }),
  email: z.string().email().min(1, { message: "Email is required." }),
  nationalIdNumber: z.string().min(1, {
    message: "National ID number is required.",
  }),
  sex: z.nativeEnum(Sex, {
    errorMap: () => ({ message: "Sex is required." }),
  }),
  insuranceId: z.string().min(1, { message: "Insurance ID is required." }),
});

type CreatePatientValues = z.infer<typeof createPatientSchema>;

const CreatePatient: React.FC = () => {
  const navigate = useNavigate();

  const [createPatient, { isSuccess: isCreatePatientSuccess }] =
    useCreatePatientMutation();

  const defaultValues: Partial<CreatePatientValues> = {
    firstName: "",
    lastName: "",
    email: "",
    nationalIdNumber: "",
    sex: undefined,
    insuranceId: "",
  };

  const form = useForm<CreatePatientValues>({
    resolver: zodResolver(createPatientSchema),
    defaultValues,
    mode: "onChange",
  });

  const onCreatePatientHandler = async (patientValues: CreatePatientValues) => {
    const patientCreateRequest: PatientCreateRequest = {
      firstName: patientValues.firstName,
      lastName: patientValues.lastName,
      email: patientValues.email,
      nationalIdNumber: patientValues.nationalIdNumber,
      insuranceId: patientValues.insuranceId,
      sex: patientValues.sex,
    };

    try {
      await createPatient(patientCreateRequest).unwrap();
    } catch (error) {
      handleError(error);
    }
  };

  useEffect(() => {
    if (isCreatePatientSuccess) {
      toast.success(`Patient registered successfully`);
      navigate("/receptionist");
    }
  }, [isCreatePatientSuccess, navigate]);

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onCreatePatientHandler)}>
        <Card className="max-w-[700px] mx-auto">
          <CardHeader>
            <CardTitle className="font-bold">Register New Patient</CardTitle>
            <CardDescription>
              Enter the details of the patient below
            </CardDescription>
          </CardHeader>
          <CardContent className="grid gap-4">
            <div className="flex flex-row gap-4 justify-between">
              <FormField
                control={form.control}
                name="firstName"
                render={({ field }) => (
                  <FormItem className="w-full">
                    <FormLabel>First Name</FormLabel>
                    <FormControl>
                      <Input placeholder="First Name" {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="lastName"
                render={({ field }) => (
                  <FormItem className="w-full">
                    <FormLabel>Last Name</FormLabel>
                    <FormControl>
                      <Input placeholder="Last Name" {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
            </div>
            <FormField
              control={form.control}
              name="email"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Email</FormLabel>
                  <FormControl>
                    <Input
                      placeholder="m@example.com"
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
              name="nationalIdNumber"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>National ID Number</FormLabel>
                  <FormControl>
                    <Input placeholder="National ID Number" {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="sex"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Sex</FormLabel>
                  <Select
                    onValueChange={field.onChange}
                    value={field.value || ""}
                  >
                    <FormControl>
                      <SelectTrigger
                        className={cn(!field.value && "text-muted-foreground")}
                      >
                        <SelectValue placeholder="Select sex" />
                      </SelectTrigger>
                    </FormControl>
                    <SelectContent>
                      <SelectItem value="FEMALE">Female</SelectItem>
                      <SelectItem value="MALE">Male</SelectItem>
                    </SelectContent>
                  </Select>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="insuranceId"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Insurance ID</FormLabel>
                  <FormControl>
                    <Input placeholder="Insurance ID" {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
          </CardContent>
          <CardFooter className="flex gap-4 justify-end">
            <Button variant="secondary" type="button" asChild>
              <Link to={"/receptionist"}>Cancel</Link>
            </Button>
            <Button type="submit">Create Patient</Button>
          </CardFooter>
        </Card>
      </form>
    </Form>
  );
};

export { CreatePatient };
