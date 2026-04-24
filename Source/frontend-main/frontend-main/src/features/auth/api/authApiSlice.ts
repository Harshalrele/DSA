/*
 * Bober Clinic note: Defines API calls from the frontend to the backend.
 * File: frontend-main/frontend-main/src/features/auth/api/authApiSlice.ts
 */
import { apiSlice } from "@/services/api/apiSlice";
import { AuthResult } from "@/types";
import { LoginValues } from "../types";

// Shape of the data sent from the Register form to the backend /register endpoint.
export type RegisterValues = {
  email: string;
  password: string;
  nationalIdNumber: string;
  firstName: string;
  lastName: string;
  sex: "MALE" | "FEMALE" | "OTHER";
  role: "PATIENT" | "DOCTOR";
  insuranceId?: string;
  npwzId?: string;
};

// Adds auth-specific API calls to the shared RTK Query apiSlice.
const authApiSlice = apiSlice.injectEndpoints({
  endpoints: (builder) => ({
    // Sends Basic Auth credentials to the backend and receives JWT tokens.
    login: builder.mutation<AuthResult, LoginValues>({
      query: ({ email, password }) => ({
        url: "/sign-in",
        method: "POST",
        headers: {
          Authorization: `Basic ${btoa(`${email}:${password}`)}`,
        },
      }),
    }),
    // Creates either a patient or doctor account in the local H2 database.
    register: builder.mutation<{ message: string; email: string }, RegisterValues>({
      query: (body) => ({
        url: "/register",
        method: "POST",
        body,
      }),
    }),
  }),
});

export const { useLoginMutation, useRegisterMutation } = authApiSlice;




