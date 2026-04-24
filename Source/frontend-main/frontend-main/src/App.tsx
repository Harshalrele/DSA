import {
  Navigate,
  RouterProvider,
  createBrowserRouter,
} from "react-router-dom";
import { DashboardPage, NotFoundPage, UnauthorizedPage } from "./pages";
import { Layout } from "./components/layout";
import { GuestOnlyRoute, PrivateRoute } from "./components/routes";
import { Role, Theme } from "./types";
import { setTheme } from "./services/state/theme/themeSlice";
import { useAppDispatch } from "./hooks";
import { setCredentials } from "./services/state/auth/authSlice";
import { AdminDashboard } from "./features/admin/pages";
import {
  DoctorAllVisits,
  DoctorDashboard,
  FullMedicalHistory,
  VisitDetails,
} from "./features/doctor/pages";
import {
  LabAssistantDashboard,
  LabAssistantAllTests,
  LabAssistantProcessExamination,
} from "./features/labAssistant/pages";
import {
  LabSupervisorAllTests,
  LabSupervisorDashboard,
  LabSupervisorVerifyExamination,
} from "./features/labSupervisor/pages";
import { PatientDashboard } from "./features/patient/pages";
import {
  CreatePatient,
  CreateVisit,
  ReceptionistAllVisits,
  ReceptionistDashboard,
} from "./features/receptionist/pages";
import { UserDashboard } from "./features/user/pages";
import { LoginPage, RegisterPage } from "./features/auth/pages";

const router = createBrowserRouter([
  // error page
  {
    path: "*",
    element: <NotFoundPage />,
  },
  // pages with layout
  {
    path: "/",
    element: <Layout />,
    children: [
      // public pages - unrestricted access
      {
        index: true,
        element: <Navigate to="/login" replace />,
      },
      {
        path: "dashboard",
        element: <DashboardPage />, // TODO przeniesc do restricted
      },
      {
        path: "unauthorized",
        element: <UnauthorizedPage />,
      },
      // private pages - restricted access
      {
        element: <PrivateRoute allowedRoles={[Role.Admin]} />,
        children: [
          // only admin
          {
            path: "admin",
            element: <AdminDashboard />,
          },
        ],
      },
      {
        element: <PrivateRoute allowedRoles={[Role.Admin, Role.Doctor]} />,
        children: [
          // only admin and doctor
          {
            path: "doctor",
            element: <DoctorDashboard />,
          },
          {
            path: "doctor/all-visits",
            element: <DoctorAllVisits />,
          },
          {
            path: "doctor/process-visit/:visitId",
            element: <VisitDetails />,
          },
          {
            path: "doctor/view-visit/:visitId",
            element: <VisitDetails shouldBeDisabled />,
          },
          {
            path: "doctor/medical-history/:insuranceId",
            element: <FullMedicalHistory />,
          },
        ],
      },
      {
        element: (
          <PrivateRoute allowedRoles={[Role.Admin, Role.LabAssistant]} />
        ),
        children: [
          // only admin and labAssistant
          {
            path: "lab-assistant",
            element: <LabAssistantDashboard />,
          },
          {
            path: "lab-assistant/all-tests",
            element: <LabAssistantAllTests />,
          },
          {
            path: "lab-assistant/process-examination/:examinationId",
            element: <LabAssistantProcessExamination />,
          },
        ],
      },
      {
        element: (
          <PrivateRoute allowedRoles={[Role.Admin, Role.LabSupervisor]} />
        ),
        children: [
          // only admin and labSupervisor
          {
            path: "lab-supervisor",
            element: <LabSupervisorDashboard />,
          },
          {
            path: "lab-supervisor/all-tests",
            element: <LabSupervisorAllTests />,
          },
          {
            path: "lab-supervisor/verify-examination/:examinationId",
            element: <LabSupervisorVerifyExamination />,
          },
        ],
      },
      {
        element: <PrivateRoute allowedRoles={[Role.Admin, Role.Patient]} />,
        children: [
          // only admin and patient - TODO chyba niepotrzebne
          {
            path: "patient",
            element: <PatientDashboard />,
          },
        ],
      },
      {
        element: (
          <PrivateRoute allowedRoles={[Role.Admin, Role.Receptionist]} />
        ),
        children: [
          // only admin and receptionist
          {
            path: "receptionist",
            element: <ReceptionistDashboard />,
          },
          {
            path: "receptionist/all-visits",
            element: <ReceptionistAllVisits />,
          },
          {
            path: "receptionist/create-patient",
            element: <CreatePatient />,
          },
          {
            path: "receptionist/create-visit",
            element: <CreateVisit />,
          },
        ],
      },
      {
        element: <PrivateRoute allowedRoles={[Role.Admin, Role.User]} />,
        children: [
          // only admin and user
          {
            path: "user",
            element: <UserDashboard />,
          },
        ],
      },
    ],
  },
  // pages without layout
  {
    element: <GuestOnlyRoute />,
    children: [
      // only not-logged-in users
      {
        path: "/login",
        element: <LoginPage />,
      },
      {
        path: "/register",
        element: <RegisterPage />,
      },
    ],
  },
]);

const App: React.FC = () => {
  const dispatch = useAppDispatch();
  const theme = (localStorage.getItem("theme") as Theme) || "system";
  const token = localStorage.getItem("token") || null;

  dispatch(setTheme({ theme: theme }));
  if (token) {
    dispatch(setCredentials({ token: token }));
  }

  return <RouterProvider router={router} />;
};

export { App };
