type AuthResult = {
  access_token: string;
  access_token_expiry: number;
  token_type: string;
  user_name: string; // email
};

export type { AuthResult };
