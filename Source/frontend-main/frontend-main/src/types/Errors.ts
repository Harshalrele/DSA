/*
 * Bober Clinic note: Contains code or settings for Errors.ts.
 * File: frontend-main/frontend-main/src/types/Errors.ts
 */
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


