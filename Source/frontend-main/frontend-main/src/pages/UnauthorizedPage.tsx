import { Link } from "react-router-dom";
import { Button } from "@/components/ui";
import { useAppSelector } from "@/hooks";
import { selectCurrentUser } from "@/services/state/auth/authSlice";

const UnauthorizedPage: React.FC = () => {
  const user = useAppSelector(selectCurrentUser);

  return (
    <div className="h-full flex flex-col items-center justify-center text-center gap-4">
      <p className="text-4xl font-bold">Unauthorized</p>
      {user ? (
        <p>
          <strong>
            {user.firstName} {user.lastName}
          </strong>
          , you are not authorized to go there.
        </p>
      ) : (
        <p>You are not authorized to go there.</p>
      )}
      <Button asChild>
        <Link to="/dashboard">Go back to dashboard</Link>
      </Button>
    </div>
  );
};

export { UnauthorizedPage };
