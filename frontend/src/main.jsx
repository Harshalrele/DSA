// React is the library used to build the frontend screen.
import React from "react";

// ReactDOM connects React to the real HTML page.
import ReactDOM from "react-dom/client";

// App is the main component for Bober Clinic.
import App from "./App.jsx";

// This imports the page styling.
import "./styles.css";

// This tells React to render the app inside the div with id root.
ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
