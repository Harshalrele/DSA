// Vite setup for the React frontend.
import react from "@vitejs/plugin-react";
import { defineConfig } from "vite";

// The frontend runs on localhost:3000.
export default defineConfig({
  plugins: [react()],
  server: {
    port: 3000
  }
});
