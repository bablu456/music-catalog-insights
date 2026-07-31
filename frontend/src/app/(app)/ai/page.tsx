import { Sparkles } from "lucide-react";
import { Card, CardContent, CardTitle, CardDescription } from "@/components/ui/card";

export const metadata = { title: "AI Insights | Music Catalog AI" };

export default function AIPage() {
  return (
    <div className="max-w-5xl space-y-6">
      <div className="space-y-1">
        <h2 className="text-2xl font-bold tracking-tight">AI Insights</h2>
        <p className="text-muted-foreground">
          AI-powered recommendations and music analysis.
        </p>
      </div>
      <Card>
        <CardContent className="flex flex-col items-center justify-center py-16 text-center">
          <div className="h-12 w-12 rounded-full bg-violet-500/10 flex items-center justify-center mb-4">
            <Sparkles className="h-6 w-6 text-violet-500" />
          </div>
          <CardTitle className="text-base mb-1">Coming Soon</CardTitle>
          <CardDescription className="max-w-sm">
            AI insights will analyze your library and provide personalized album recommendations, genre summaries, and listening trends.
          </CardDescription>
        </CardContent>
      </Card>
    </div>
  );
}
