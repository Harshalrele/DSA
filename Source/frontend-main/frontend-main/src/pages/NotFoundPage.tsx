/*
 * Bober Clinic note: Builds one visible page in the frontend.
 * File: frontend-main/frontend-main/src/pages/NotFoundPage.tsx
 */
import { Button } from "@/components/ui";
import { Link } from "react-router-dom";

const NotFoundPage: React.FC = () => {
  return (
    <div className="min-h-screen flex flex-col items-center justify-center text-center gap-4">
      <p className="text-6xl font-bold">404</p>
      <p className="text-2xl font-semibold">Page Not Found</p>
      <p>Sorry, the page you're looking for doesn't exist.</p>
      <Button asChild>
        <Link to="/dashboard">Go back to dashboard</Link>
      </Button>
    </div>
  );
};

export { NotFoundPage };


