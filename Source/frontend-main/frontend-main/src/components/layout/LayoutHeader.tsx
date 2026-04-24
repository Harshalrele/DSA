import { logOut, selectCurrentUser } from "@/services/state/auth/authSlice";
import { ClinicLogo, ThemeToggle } from "..";
import { Button } from "@/components/ui";
import { useAppDispatch, useAppSelector } from "@/hooks";
import { Link, useNavigate } from "react-router-dom";
import { toast } from "sonner";
import { convertRoleToString } from "@/utils";
import { format } from "date-fns";
import { CalendarIcon } from "lucide-react";

const LayoutHeader: React.FC = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const date = new Date();
  const user = useAppSelector(selectCurrentUser);
  const role = user ? convertRoleToString(user.role) : null;

  const handleLogout = (): void => {
    dispatch(logOut());
    navigate("/login"); // TODO replace false trzeba dac ale ten button nie moze byc na page'ach ktore są publiczne
    toast.success(`Logged out successfully.`);
  };

  return (
    <div className="flex justify-between items-center p-4">
      <Link to={"/dashboard"}>
        <ClinicLogo className="h-10" />
      </Link>
      <div className="flex items-center gap-8">
        <div className="flex flex-row text-sm font-semibold">
          <CalendarIcon className="h-4 w-4 shrink-0" />
          <span className="ml-2">{format(date, "PPPP")}</span>
        </div>
        {user && (
          <>
            <div className="flex flex-col items-end">
              <div className="text-sm font-bold">
                {user?.firstName} {user?.lastName}
              </div>
              <div className="text-xs font-semibold text-primary">{role}</div>
            </div>
            <Button onClick={handleLogout}>logout</Button>
          </>
        )}
        <ThemeToggle />
      </div>
    </div>
  );
};
export { LayoutHeader };
