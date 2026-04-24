import { Navigate, Outlet, useLocation } from "react-router-dom";
import {
  selectCurrentToken,
  selectCurrentUser,
} from "@/services/state/auth/authSlice";
import { useAppSelector } from "@/hooks";

const GuestOnlyRoute = () => {
  const user = useAppSelector(selectCurrentUser);
  const token = useAppSelector(selectCurrentToken);
  const location = useLocation();

  return !(!!user && !!token) ? (
    <Outlet />
  ) : (
    <Navigate to={"/dashboard"} state={{ from: location }} replace />
  );
};

export { GuestOnlyRoute };
