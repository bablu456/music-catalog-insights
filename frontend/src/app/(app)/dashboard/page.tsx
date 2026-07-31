"use client";

import { useAuth } from "@/hooks/useAuth";
import { Card, CardContent, CardHeader, CardTitle, CardDescription } from "@/components/ui/card";
import { Music2, Library, BarChart3, Sparkles, Search, ArrowRight, Loader2 } from "lucide-react";
import Link from "next/link";
import { useQuery } from "@tanstack/react-query";
import { analyticsService } from "@/services/analytics.service";

const quickActions = [
  {
    href: "/search",
    icon: Search,
    title: "Search Music",
    description: "Find albums and tracks from the iTunes catalog",
    color: "text-blue-500",
    bg: "bg-blue-500/10",
  },
  {
    href: "/library",
    icon: Library,
    title: "My Library",
    description: "View and manage your saved albums",
    color: "text-emerald-500",
    bg: "bg-emerald-500/10",
  },
  {
    href: "/analytics",
    icon: BarChart3,
    title: "Analytics",
    description: "Visualize your listening patterns",
    color: "text-amber-500",
    bg: "bg-amber-500/10",
  },
  {
    href: "/ai",
    icon: Sparkles,
    title: "AI Insights",
    description: "Get personalized recommendations",
    color: "text-violet-500",
    bg: "bg-violet-500/10",
  },
];

export default function DashboardPage() {
  const { user } = useAuth();

  const { data, isLoading, isError } = useQuery({
    queryKey: ['analytics'],
    queryFn: analyticsService.getAnalytics,
  });

  const overview = data?.data?.overview;
  const isLibraryEmpty = overview?.totalAlbums === 0;

  return (
    <div className="space-y-6 max-w-5xl">
      {/* Welcome Section */}
      <div className="space-y-1">
        <h2 className="text-2xl font-bold tracking-tight">
          Welcome back{user?.name ? `, ${user.name}` : ""}
        </h2>
        <p className="text-muted-foreground">
          Here&apos;s an overview of your music catalog.
        </p>
      </div>

      {/* Stats Row */}
      {isLoading ? (
        <div className="grid grid-cols-2 md:grid-cols-4 gap-3 md:gap-4">
          {[1, 2, 3, 4].map((i) => (
            <Card key={i} className="animate-pulse">
              <CardContent className="flex items-center gap-3 p-4">
                <div className="h-9 w-9 rounded-lg bg-muted shrink-0" />
                <div className="space-y-2">
                  <div className="h-4 w-12 bg-muted rounded" />
                  <div className="h-3 w-16 bg-muted rounded" />
                </div>
              </CardContent>
            </Card>
          ))}
        </div>
      ) : isError ? (
        <Card className="bg-destructive/10 border-destructive/20 text-destructive">
          <CardContent className="p-4 flex items-center justify-center text-sm font-medium">
            Failed to load dashboard metrics.
          </CardContent>
        </Card>
      ) : (
        <div className="grid grid-cols-2 md:grid-cols-4 gap-3 md:gap-4">
          <StatCard icon={Library} label="Saved Albums" value={overview?.totalAlbums?.toString() || "0"} />
          <StatCard icon={Music2} label="Artists" value={overview?.totalArtists?.toString() || "0"} />
          <StatCard icon={BarChart3} label="Genres" value={overview?.totalGenres?.toString() || "0"} />
          <StatCard icon={Sparkles} label="Avg Rating" value={overview?.averageRating?.toFixed(1) || "0.0"} />
        </div>
      )}

      {/* Quick Actions */}
      <div className="space-y-3">
        <h3 className="text-lg font-semibold tracking-tight">Quick Actions</h3>
        <div className="grid grid-cols-1 sm:grid-cols-2 gap-3 md:gap-4">
          {quickActions.map((action) => (
            <Link key={action.href} href={action.href} className="group">
              <Card className="h-full transition-all hover:ring-2 hover:ring-primary/20 hover:shadow-md">
                <CardContent className="flex items-start gap-4 p-4">
                  <div className={`${action.bg} rounded-lg p-2.5 shrink-0`}>
                    <action.icon className={`h-5 w-5 ${action.color}`} />
                  </div>
                  <div className="flex-1 min-w-0">
                    <div className="flex items-center gap-1">
                      <span className="font-medium text-sm">{action.title}</span>
                      <ArrowRight className="h-3.5 w-3.5 text-muted-foreground opacity-0 -translate-x-1 group-hover:opacity-100 group-hover:translate-x-0 transition-all" />
                    </div>
                    <p className="text-xs text-muted-foreground mt-0.5">
                      {action.description}
                    </p>
                  </div>
                </CardContent>
              </Card>
            </Link>
          ))}
        </div>
      </div>

      {/* Empty State / Call to Action */}
      {!isLoading && isLibraryEmpty && (
        <div className="space-y-3">
          <h3 className="text-lg font-semibold tracking-tight">Recent Activity</h3>
          <Card>
            <CardContent className="flex flex-col items-center justify-center py-12 text-center">
              <div className="h-12 w-12 rounded-full bg-muted flex items-center justify-center mb-4">
                <Music2 className="h-6 w-6 text-muted-foreground" />
              </div>
              <CardTitle className="text-base mb-1">No activity yet</CardTitle>
              <CardDescription className="max-w-xs">
                Start by searching for music and saving albums to your library.
              </CardDescription>
              <Link
                href="/search"
                className="mt-4 inline-flex items-center gap-1.5 px-4 py-2 rounded-lg bg-primary text-primary-foreground text-sm font-medium hover:bg-primary/90 transition-colors"
              >
                <Search className="h-4 w-4" />
                Search Music
              </Link>
            </CardContent>
          </Card>
        </div>
      )}
    </div>
  );
}

function StatCard({
  icon: Icon,
  label,
  value,
}: {
  icon: React.ElementType;
  label: string;
  value: string;
}) {
  return (
    <Card>
      <CardContent className="flex items-center gap-3 p-4">
        <div className="h-9 w-9 rounded-lg bg-primary/10 flex items-center justify-center shrink-0">
          <Icon className="h-4 w-4 text-primary" />
        </div>
        <div>
          <p className="text-xl font-bold tracking-tight">{value}</p>
          <p className="text-xs text-muted-foreground">{label}</p>
        </div>
      </CardContent>
    </Card>
  );
}
