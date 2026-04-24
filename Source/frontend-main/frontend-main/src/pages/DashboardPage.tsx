import { useAppSelector } from "@/hooks";
import { selectCurrentUser } from "@/services/state/auth/authSlice";
import { Role } from "@/types";
import { Navigate, useLocation } from "react-router-dom";

const DashboardPage: React.FC = () => {
  const user = useAppSelector(selectCurrentUser);
  const location = useLocation();
  let route: string = "/login";

  switch (user?.role) {
    case Role.Admin:
      route = "/admin";
      break;
    case Role.Doctor:
      route = "/doctor";
      break;
    case Role.LabAssistant:
      route = "/lab-assistant";
      break;
    case Role.LabSupervisor:
      route = "/lab-supervisor";
      break;
    case Role.Patient:
      route = "/patient";
      break;
    case Role.Receptionist:
      route = "/receptionist";
      break;
    case Role.User:
      route = "/user";
      break;
    default:
      break;
  }
  return <Navigate to={route} state={{ from: location }} replace />;
};

export { DashboardPage };
