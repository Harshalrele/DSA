/*
 * Bober Clinic note: Contains code or settings for User.ts.
 * File: frontend-main/frontend-main/src/types/User.ts
 */
import { Role } from ".";

type User = {
  email: string;
  firstName: string;
  lastName: string;
  role: Role;
};
export type { User };


