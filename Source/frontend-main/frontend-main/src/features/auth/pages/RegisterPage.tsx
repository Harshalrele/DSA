/*
 * Bober Clinic note: Shows the register screen and creates patient or doctor accounts.
 * File: frontend-main/frontend-main/src/features/auth/pages/RegisterPage.tsx
 */
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
import { zodResolver } from "@hookform/resolvers/zod";
import { ArrowLeft, BadgePlus, CircleUserRound, Stethoscope } from "lucide-react";
import { useEffect } from "react";
import { useForm } from "react-hook-form";
import { Link, useNavigate } from "react-router-dom";
import { toast } from "sonner";
import { z } from "zod";
import { useRegisterMutation } from "../api";
import type { RegisterValues } from "../api";

// Validation rules for creating either a patient or doctor account.
const registerSchema = z
  .object({
    email: z.string().email("Enter a valid email address."),
    password: z.string().min(3, "Password must be at least 3 characters."),
    nationalIdNumber: z.string().min(3, "National ID is required."),
    firstName: z.string().min(1, "First name is required."),
    lastName: z.string().min(1, "Last name is required."),
    sex: z.enum(["MALE", "FEMALE", "OTHER"]),
    role: z.enum(["PATIENT", "DOCTOR"]),
    insuranceId: z.string().optional(),
    npwzId: z.string().optional(),
  })
  // Cross-field validation: patient needs insuranceId, doctor needs npwzId.
  .superRefine((values, ctx) => {
    if (values.role === "PATIENT" && !values.insuranceId?.trim()) {
      ctx.addIssue({
        code: z.ZodIssueCode.custom,
        path: ["insuranceId"],
        message: "Insurance ID is required for patients.",
      });
    }

    if (values.role === "DOCTOR" && !values.npwzId?.trim()) {
      ctx.addIssue({
        code: z.ZodIssueCode.custom,
        path: ["npwzId"],
        message: "NPWZ ID is required for doctors.",
      });
    }
  });

// Register screen shown at /register.
const RegisterPage: React.FC = () => {
  const navigate = useNavigate();
  // RTK Query mutation that calls POST /register on the backend.
  const [register, { isLoading, isSuccess }] = useRegisterMutation();

  // Form state and starting values for the registration form.
  const form = useForm<RegisterValues>({
    resolver: zodResolver(registerSchema),
    defaultValues: {
      email: "",
      password: "",
      nationalIdNumber: "",
      firstName: "",
      lastName: "",
      sex: "FEMALE",
      role: "PATIENT",
      insuranceId: "",
      npwzId: "",
    },
    mode: "onChange",
  });

  // Watches account type so the page can show Insurance ID or NPWZ ID dynamically.
  const role = form.watch("role");

  // Sends the cleaned form data to the backend.
  const onSubmit = async (values: RegisterValues) => {
    const payload = {
      ...values,
      insuranceId: values.role === "PATIENT" ? values.insuranceId : undefined,
      npwzId: values.role === "DOCTOR" ? values.npwzId : undefined,
    };

    try {
      await register(payload).unwrap();
    } catch {
      toast.error("Could not create account. Check if the email or ID is already used.");
    }
  };

  // After account creation, return the user to login.
  useEffect(() => {
    if (isSuccess) {
      toast.success("Account created. You can log in now.");
      navigate("/login");
    }
  }, [isSuccess, navigate]);

  return (
    <main className="min-h-screen bg-[radial-gradient(circle_at_top_left,_rgba(20,184,166,0.20),_transparent_34%),linear-gradient(135deg,_#f8fafc_0%,_#eef7f4_45%,_#f6f3ee_100%)] px-4 py-8 text-slate-950">
      <div className="mx-auto grid min-h-[calc(100vh-4rem)] w-full max-w-6xl items-center gap-8 lg:grid-cols-[0.85fr_1.15fr]">
        {/* Left side: explanation of the two account types. */}
        <section className="space-y-6">
          <Link
            to="/login"
            className="inline-flex items-center gap-2 text-sm font-medium text-teal-800 hover:text-teal-950"
          >
            <ArrowLeft className="h-4 w-4" />
            Back to sign in
          </Link>
          <div className="space-y-4">
            <div className="flex h-14 w-14 items-center justify-center rounded-lg bg-teal-700 text-white shadow-sm">
              <BadgePlus className="h-7 w-7" />
            </div>
            <div>
              <h1 className="text-4xl font-semibold tracking-normal text-slate-950">
                Create a clinic account
              </h1>
              <p className="mt-3 max-w-md text-base leading-7 text-slate-600">
                Register as a patient or doctor. The backend stores these
                credentials in the local H2 database for this project.
              </p>
            </div>
          </div>
          <div className="grid gap-3 sm:grid-cols-2">
            <div className="rounded-lg border border-teal-200 bg-white/80 p-4 shadow-sm">
              <CircleUserRound className="mb-3 h-5 w-5 text-teal-700" />
              <p className="font-medium">Patient access</p>
              <p className="mt-1 text-sm text-slate-600">
                Create a patient profile with insurance ID.
              </p>
            </div>
            <div className="rounded-lg border border-amber-200 bg-white/80 p-4 shadow-sm">
              <Stethoscope className="mb-3 h-5 w-5 text-amber-700" />
              <p className="font-medium">Doctor access</p>
              <p className="mt-1 text-sm text-slate-600">
                Create a doctor profile with NPWZ ID.
              </p>
            </div>
          </div>
        </section>

        {/* Right side: registration form connected to backend /register. */}
        <Form {...form}>
          <form onSubmit={form.handleSubmit(onSubmit)}>
            <Card className="border-slate-200 bg-white/95 shadow-xl">
              <CardHeader>
                <CardTitle className="text-2xl font-semibold">
                  New account
                </CardTitle>
                <CardDescription>
                  Use this form for the working 50% local demo.
                </CardDescription>
              </CardHeader>
              <CardContent className="grid gap-4">
                <div className="grid gap-4 md:grid-cols-2">
                  <FormField
                    control={form.control}
                    name="firstName"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>First name</FormLabel>
                        <FormControl>
                          <Input placeholder="Olivia" {...field} />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  <FormField
                    control={form.control}
                    name="lastName"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>Last name</FormLabel>
                        <FormControl>
                          <Input placeholder="Davis" {...field} />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                </div>
                <div className="grid gap-4 md:grid-cols-2">
                  <FormField
                    control={form.control}
                    name="email"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>Email</FormLabel>
                        <FormControl>
                          <Input placeholder="name@email.com" {...field} />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  <FormField
                    control={form.control}
                    name="password"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>Password</FormLabel>
                        <FormControl>
                          <Input type="password" {...field} />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                </div>
                <div className="grid gap-4 md:grid-cols-3">
                  <FormField
                    control={form.control}
                    name="role"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>Account type</FormLabel>
                        <Select onValueChange={field.onChange} value={field.value}>
                          <FormControl>
                            <SelectTrigger>
                              <SelectValue />
                            </SelectTrigger>
                          </FormControl>
                          <SelectContent>
                            <SelectItem value="PATIENT">Patient</SelectItem>
                            <SelectItem value="DOCTOR">Doctor</SelectItem>
                          </SelectContent>
                        </Select>
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
                        <Select onValueChange={field.onChange} value={field.value}>
                          <FormControl>
                            <SelectTrigger>
                              <SelectValue />
                            </SelectTrigger>
                          </FormControl>
                          <SelectContent>
                            <SelectItem value="FEMALE">Female</SelectItem>
                            <SelectItem value="MALE">Male</SelectItem>
                            <SelectItem value="OTHER">Other</SelectItem>
                          </SelectContent>
                        </Select>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  <FormField
                    control={form.control}
                    name="nationalIdNumber"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>National ID</FormLabel>
                        <FormControl>
                          <Input placeholder="1234567" {...field} />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                </div>
                {/* Show the role-specific ID field based on the selected account type. */}
                {role === "PATIENT" ? (
                  <FormField
                    control={form.control}
                    name="insuranceId"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>Insurance ID</FormLabel>
                        <FormControl>
                          <Input placeholder="insurance-new-1" {...field} />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                ) : (
                  <FormField
                    control={form.control}
                    name="npwzId"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>NPWZ ID</FormLabel>
                        <FormControl>
                          <Input placeholder="npwz-new-1" {...field} />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                )}
              </CardContent>
              <CardFooter>
                <Button
                  type="submit"
                  disabled={isLoading}
                  className="w-full bg-teal-700 hover:bg-teal-800"
                >
                  {isLoading ? "Creating..." : "Create account"}
                </Button>
              </CardFooter>
            </Card>
          </form>
        </Form>
      </div>
    </main>
  );
};

export { RegisterPage };




