type ApiError = {
  data: {
    error: string;
    message: string;
    path: string;
    status: number;
    timestamp: string;
    trace: string;
  };
  status: number;
};

type BackendError = {
  data: {
    code: string;
    description: string;
    value: string;
  };
  status: number;
};

export type { ApiError, BackendError };
