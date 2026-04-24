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
} from "@/components/ui";
import { useAppDispatch } from "@/hooks";
import { setCredentials } from "@/services/state/auth/authSlice";
import { handleError } from "@/utils";
import { zodResolver } from "@hookform/resolvers/zod";
import { ArrowRight, ShieldCheck, Stethoscope } from "lucide-react";
import { useEffect } from "react";
import { useForm } from "react-hook-form";
import { Link, useNavigate } from "react-router-dom";
import { toast } from "sonner";
import { z } from "zod";
import { useLoginMutation } from "@/features/auth/api";
import type { LoginValues } from "../types";

// Validation rules for the login form. React Hook Form uses this before submit.
const loginSchema = z.object({
  email: z.string().email("Enter a valid email address."),
  password: z.string().min(2, "Password is required."),
});

// Login screen shown at /login.
const LoginPage: React.FC = () => {
  const navigate = useNavigate();
  const dispatch = useAppDispatch();
  const [
    login,
    { data: loginData, isLoading: isLoginLoading, isSuccess: isLoginSuccess },
  ] = useLoginMutation();

  // Form state, default values, and validation resolver.
  const form = useForm<LoginValues>({
    resolver: zodResolver(loginSchema),
    defaultValues: {
      email: "",
      password: "",
    },
    mode: "onChange",
  });

  // Runs when the user presses Sign in.
  const onSubmitHandler = async (loginValues: LoginValues) => {
    try {
      await login(loginValues).unwrap();
    } catch (error) {
      handleError(error);
    }
  };

  // After a successful login, save the JWT token and move to the dashboard.
  useEffect(() => {
    if (isLoginSuccess && loginData) {
      toast.success("Logged in successfully");
      dispatch(setCredentials({ token: loginData.access_token }));
      navigate("/dashboard");
    }
  }, [dispatch, isLoginSuccess, loginData, navigate]);

  return (
    <main className="min-h-screen bg-[radial-gradient(circle_at_top_right,_rgba(245,158,11,0.18),_transparent_30%),linear-gradient(135deg,_#f8fafc_0%,_#e9f7f4_48%,_#f7f1e8_100%)] px-4 py-8 text-slate-950">
      <div className="mx-auto grid min-h-[calc(100vh-4rem)] w-full max-w-6xl items-center gap-8 lg:grid-cols-[1.1fr_0.9fr]">
        {/* Left side: product/project identity and demo status cards. */}
        <section className="space-y-8">
          <div className="inline-flex items-center gap-3 rounded-lg border border-teal-200 bg-white/80 px-4 py-3 shadow-sm">
            <div className="flex h-10 w-10 items-center justify-center rounded-md bg-teal-700 text-white">
              <Stethoscope className="h-5 w-5" />
            </div>
            <div>
              <p className="text-sm font-semibold text-slate-900">
                Bober Clinic
              </p>
              <p className="text-xs text-slate-500">Local working demo</p>
            </div>
          </div>

          <div className="max-w-2xl space-y-5">
            <h1 className="text-5xl font-semibold tracking-normal text-slate-950">
              Patient visits, doctors, and lab flow in one clean workspace.
            </h1>
            <p className="text-lg leading-8 text-slate-600">
              This version keeps the original backend structure but focuses the
              demo on sign in, account creation, and the core clinic dashboard.
            </p>
          </div>

          <div className="grid max-w-2xl gap-3 sm:grid-cols-3">
            {["Local H2 database", "Patient accounts", "Doctor accounts"].map(
              (item) => (
                <div
                  key={item}
                  className="rounded-lg border border-white/70 bg-white/75 p-4 text-sm font-medium text-slate-700 shadow-sm"
                >
                  <ShieldCheck className="mb-3 h-5 w-5 text-teal-700" />
                  {item}
                </div>
              )
            )}
          </div>
        </section>

        {/* Right side: actual login form. */}
        <Form {...form}>
          <form onSubmit={form.handleSubmit(onSubmitHandler)}>
            <Card className="border-slate-200 bg-white/95 shadow-xl">
              <CardHeader>
                <CardTitle className="text-2xl font-semibold">
                  Sign in
                </CardTitle>
                <CardDescription>
                  Use a preloaded user or an account you create.
                </CardDescription>
              </CardHeader>
              <CardContent className="grid gap-4">
                <FormField
                  control={form.control}
                  name="email"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Email</FormLabel>
                      <FormControl>
                        <Input
                          placeholder="doctor1@email.com"
                          autoComplete="email"
                          {...field}
                        />
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
                        <Input
                          type="password"
                          autoComplete="current-password"
                          placeholder="doctor1"
                          {...field}
                        />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <div className="rounded-lg border border-teal-100 bg-teal-50 px-4 py-3 text-sm text-teal-950">
                  Try `doctor1@email.com / doctor1` or create a new patient or
                  doctor account.
                </div>
              </CardContent>
              <CardFooter className="flex flex-col gap-3">
                <Button
                  disabled={isLoginLoading}
                  className="w-full bg-teal-700 hover:bg-teal-800"
                  type="submit"
                >
                  {isLoginLoading ? "Signing in..." : "Sign in"}
                  <ArrowRight className="ml-2 h-4 w-4" />
                </Button>
                <Button variant="outline" className="w-full" asChild>
                  <Link to="/register">Create patient or doctor account</Link>
                </Button>
              </CardFooter>
            </Card>
          </form>
        </Form>
      </div>
    </main>
  );
};

export { LoginPage };



