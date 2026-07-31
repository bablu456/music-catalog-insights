import { Settings as SettingsIcon } from "lucide-react";
import { Card, CardContent, CardTitle, CardDescription } from "@/components/ui/card";

export const metadata = { title: "Settings | Music Catalog AI" };

export default function SettingsPage() {
  return (
    <div className="max-w-5xl space-y-6">
      <div className="space-y-1">
        <h2 className="text-2xl font-bold tracking-tight">Settings</h2>
        <p className="text-muted-foreground">
          Manage your account and application preferences.
        </p>
      </div>
      <Card>
        <CardContent className="flex flex-col items-center justify-center py-16 text-center">
          <div className="h-12 w-12 rounded-full bg-muted flex items-center justify-center mb-4">
            <SettingsIcon className="h-6 w-6 text-muted-foreground" />
          </div>
          <CardTitle className="text-base mb-1">Coming Soon</CardTitle>
          <CardDescription className="max-w-sm">
            Settings will let you update your profile, change passwords, and customize application preferences.
          </CardDescription>
        </CardContent>
      </Card>
    </div>
  );
}
