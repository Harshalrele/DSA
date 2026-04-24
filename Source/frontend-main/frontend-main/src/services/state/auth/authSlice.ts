import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { RootState } from "../store";
import { User } from "@/types";
import { jwtDecode } from "jwt-decode";
import { convertStringToRole } from "@/utils";

type DecodedToken = {
  exp: number; // access token expiry
  firstName: string;
  iat: number; // sth
  iss: string; // token type
  lastName: string;
  scope: string; // role
  sub: string; //email
};

type AuthState = {
  user: User | null;
  token: string | null;
};

const initialState: AuthState = {
  user: null,
  token: null,
};

const authSlice = createSlice({
  name: "auth",
  initialState: initialState,
  reducers: {
    setCredentials: (state, action: PayloadAction<{ token: string }>) => {
      const token = action.payload.token;
      const decodedToken: DecodedToken = jwtDecode(token);
      const user: User = {
        email: decodedToken.sub,
        firstName: decodedToken.firstName,
        lastName: decodedToken.lastName,
        role: convertStringToRole(decodedToken.scope),
      };

      state.token = token;
      state.user = user;
      localStorage.setItem("token", token);
    },
    logOut: (state) => {
      state.user = null;
      state.token = null;
      localStorage.removeItem("token");
    },
  },
});

export const { setCredentials, logOut } = authSlice.actions;

export const selectCurrentUser = (state: RootState): User | null =>
  state.auth.user;
export const selectCurrentToken = (state: RootState): string | null =>
  state.auth.token;
export const selectIsAuthenticated = (state: RootState): boolean =>
  !!state.auth.token && !!state.auth.user;

export default authSlice.reducer;
