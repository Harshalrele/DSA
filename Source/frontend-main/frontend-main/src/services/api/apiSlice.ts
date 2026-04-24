import {
  BaseQueryFn,
  FetchArgs,
  FetchBaseQueryError,
  createApi,
  fetchBaseQuery,
} from "@reduxjs/toolkit/query/react";
import { RootState } from "@/services/state/store";
import { logOut, setCredentials } from "@/services/state/auth/authSlice";
import { AuthResult, BackendError } from "@/types";

const baseQuery = fetchBaseQuery({
  baseUrl: "http://localhost:8080",
  credentials: "include",
  prepareHeaders: (headers, { getState }) => {
    const token = (getState() as RootState).auth.token;
    if (token) {
      headers.set("authorization", `Bearer ${token}`);
    }
    headers.set("Content-Type", "application/json");
    return headers;
  },
});

const baseQueryWithReauth: BaseQueryFn<
  string | FetchArgs,
  unknown,
  FetchBaseQueryError | BackendError
> = async (args, api, extraOptions) => {
  let result = await baseQuery(args, api, extraOptions);

  if (result.error) {
    const error = result.error as BackendError;

    if (error.status === 406 && error.data.code === "EXPIRED_JWT") {
      // Token has expired, try to refresh it
      const refreshResult = (await fetchBaseQuery({
        baseUrl: "http://localhost:8080",
        credentials: "include",
      })(
        {
          url: "/refresh-token",
          method: "POST",
        },
        api,
        extraOptions
      )) as { data?: AuthResult };

      if (refreshResult.data) {
        api.dispatch(
          setCredentials({ token: refreshResult.data.access_token })
        );

        // Retry the original query with the new token
        result = await baseQuery(args, api, extraOptions);
      } else {
        // Refresh token failed, logout the user
        console.log("refresh token failed, logging out");
        api.dispatch(logOut());
      }
    }
  }
  return result;
};

export const apiSlice = createApi({
  baseQuery: baseQueryWithReauth,
  endpoints: () => ({}),
});
