import { ApiError, BackendError } from "@/types";
import { toast } from "sonner";

const handleError = (error: unknown): void => {
  if ((error as BackendError)?.data?.code) {
    const backendError = error as BackendError;
    toast.error(`Error ${backendError.data.description}`, {
      description: `${backendError.data.value}`,
    });
  } else if ((error as ApiError)?.data?.status) {
    const apiError = error as ApiError;
    toast.error(`Error ${apiError.data.error}`, {
      description: `${apiError.data.message}`,
    });
  } else if ((error as { error: string; status: string })?.status) {
    const err = error as { error: string; status: string };
    toast.error(`Error ${err.status}`, {
      description: `${err.error}`,
    });
  } else {
    toast.error(`Unknown error`);
    console.error(error);
  }
};

export { handleError };
