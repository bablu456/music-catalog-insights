import { BarChart3 } from "lucide-react";
import { Card, CardContent, CardTitle, CardDescription } from "@/components/ui/card";

export const metadata = { title: "Analytics | Music Catalog AI" };

export default function AnalyticsPage() {
  return (
    <div className="max-w-5xl space-y-6">
      <div className="space-y-1">
        <h2 className="text-2xl font-bold tracking-tight">Analytics</h2>
        <p className="text-muted-foreground">
          Visualize your listening patterns and music preferences.
        </p>
      </div>
      <Card>
        <CardContent className="flex flex-col items-center justify-center py-16 text-center">
          <div className="h-12 w-12 rounded-full bg-amber-500/10 flex items-center justify-center mb-4">
            <BarChart3 className="h-6 w-6 text-amber-500" />
          </div>
          <CardTitle className="text-base mb-1">Coming Soon</CardTitle>
          <CardDescription className="max-w-sm">
            Analytics will show genre distribution, top artists, listening trends, and more from your library data.
          </CardDescription>
        </CardContent>
      </Card>
    </div>
  );
}
