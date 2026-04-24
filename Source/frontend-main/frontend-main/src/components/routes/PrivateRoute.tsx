import { Navigate, Outlet, useLocation } from "react-router-dom";
import {
  selectCurrentToken,
  selectCurrentUser,
} from "@/services/state/auth/authSlice";
import { toast } from "sonner";
import { useAppSelector } from "@/hooks";
import { Role } from "@/types";

type PrivateRouteProps = {
  allowedRoles: Role[];
};

const PrivateRoute = ({ allowedRoles }: PrivateRouteProps) => {
  const user = useAppSelector(selectCurrentUser);
  const token = useAppSelector(selectCurrentToken);
  const location = useLocation();

  return !!user && !!token && allowedRoles.includes(user?.role) ? (
    <Outlet />
  ) : !!user && !!token ? (
    <>
      {toast.error(`Error Not Authorized`, {
        description: `You are not authorized to go there`,
      })}
      <Navigate to={"/unauthorized"} state={{ from: location }} replace />
    </>
  ) : (
    <>
      {toast.error(`Error Not Authenticated`, {
        description: `You are not logged in`,
      })}
      <Navigate to={"/login"} state={{ from: location }} replace />
    </>
  );
};

export { PrivateRoute };
