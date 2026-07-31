import { AppSidebar } from "./AppSidebar";
import { AppNavbar } from "./AppNavbar";

/**
 * AppShell — the root layout for all authenticated pages.
 * Composes the sidebar (desktop) and navbar (with mobile drawer) around the main content area.
 */
export function AppShell({ children }: { children: React.ReactNode }) {
  return (
    <div className="flex h-screen overflow-hidden">
      <AppSidebar />
      <div className="flex flex-1 flex-col overflow-hidden">
        <AppNavbar />
        <main className="flex-1 overflow-y-auto p-4 md:p-6">
          {children}
        </main>
      </div>
    </div>
  );
}
