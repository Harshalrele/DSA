// This file configures Vite, the frontend development server.
import react from "@vitejs/plugin-react";
import { defineConfig } from "vite";

// Vite runs the React app on localhost:3000.
export default defineConfig({
  plugins: [react()],
  server: {
    port: 3000
  }
});
