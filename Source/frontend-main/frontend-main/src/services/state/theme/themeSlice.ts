import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { RootState } from "../store";
import { Theme } from "@/types";

type ThemeState = {
  theme: Theme;
};

const initialState: ThemeState = {
  theme: "system",
};

const themeSlice = createSlice({
  name: "theme",
  initialState: initialState,
  reducers: {
    setTheme: (state, action: PayloadAction<{ theme: Theme }>): void => {
      const theme = action.payload.theme;
      state.theme = theme;
      localStorage.setItem("theme", theme);

      const root = window.document.documentElement;

      root.classList.remove("light", "dark");

      if (theme === "system") {
        const systemTheme: Theme = window.matchMedia(
          "(prefers-color-scheme: dark)"
        ).matches
          ? "dark"
          : "light";
        root.classList.add(systemTheme);
        return;
      }

      root.classList.add(theme);
    },
  },
});

export const { setTheme } = themeSlice.actions;

export const selectCurrentTheme = (state: RootState): Theme =>
  state.theme.theme;

export default themeSlice.reducer;
