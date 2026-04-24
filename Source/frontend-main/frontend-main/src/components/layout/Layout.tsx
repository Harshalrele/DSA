/*
 * Bober Clinic note: Builds the shared page layout used around app pages.
 * File: frontend-main/frontend-main/src/components/layout/Layout.tsx
 */
import { Outlet } from "react-router-dom";
import { LayoutHeader } from ".";
import { Separator } from "@/components/ui";

const Layout: React.FC = () => {
  return (
    <div className="flex flex-col h-screen">
      <LayoutHeader />
      <Separator />
      <div className="flex-grow p-4 overflow-y-auto">
        <Outlet /> {/* Nested routes are rendered here */}
      </div>
      {/* <Separator /> */}
      {/* <LayoutFooter /> */}
    </div>
  );
};

export { Layout };


