import { cn } from "@/lib/utils";
import { Stethoscope } from "lucide-react";

interface ClinicLogoProps extends React.HTMLAttributes<HTMLDivElement> {}

const ClinicLogo: React.FC<ClinicLogoProps> = ({ className, ...props }) => {
  return (
    <div
      className={cn("relative flex items-center", className)}
      {...props}
    >
      <Stethoscope className="h-5 absolute left-1 top-1" />
      <div className="flex flex-col items-end h-full">
        <div className="font-bold text-primary h-1/2 mr-2">Clinic</div>
        <div className="text-sm font-semibold  h-1/2">management</div>
      </div>
    </div>
  );
};

export { ClinicLogo };
