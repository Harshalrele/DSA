/*
 * Bober Clinic note: Contains code or settings for AuthResult.ts.
 * File: frontend-main/frontend-main/src/types/AuthResult.ts
 */
type AuthResult = {
  access_token: string;
  access_token_expiry: number;
  token_type: string;
  user_name: string; // email
};

export type { AuthResult };


