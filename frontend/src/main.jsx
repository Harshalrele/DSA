// React builds the frontend screen.
import React from "react";

// ReactDOM connects React to the HTML page.
import ReactDOM from "react-dom/client";

// Main Bober Clinic component.
import App from "./App.jsx";

// Page styling.
import "./styles.css";

// Render the app inside the root div.
ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
