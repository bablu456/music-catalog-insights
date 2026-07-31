"use client";

import { useAuth } from "@/hooks/useAuth";
import { Card, CardContent, CardHeader, CardTitle, CardDescription } from "@/components/ui/card";
import { Music2, Library, BarChart3, Sparkles, Search, ArrowRight, User, Disc, TrendingUp, Clock, FileText, CheckCircle2 } from "lucide-react";
import Link from "next/link";
import { useQuery } from "@tanstack/react-query";
import { analyticsService } from "@/services/analytics.service";
import { activityService } from "@/services/activity.service";
import { useRouter } from "next/navigation";

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
  const router = useRouter();

  const { data: analyticsData, isLoading: isAnalyticsLoading } = useQuery({
    queryKey: ['analytics-overview'],
    queryFn: analyticsService.getOverview,
  });

  const { data: activityData, isLoading: isActivityLoading } = useQuery({
    queryKey: ['recent-activity'],
    queryFn: activityService.getRecentActivity,
  });

  const overview = analyticsData?.data;
  const activities = activityData?.data || [];
  const isLibraryEmpty = overview?.totalAlbums === 0 && activities.length === 0;
  const isLoading = isAnalyticsLoading || isActivityLoading;

  const handleActivityClick = (type: string, metadata: string) => {
    if (type === "SEARCH" && metadata) {
      router.push(`/search?q=${encodeURIComponent(metadata)}`);
    } else if ((type === "SAVE" || type === "UPDATE") && metadata) {
      router.push(`/library`);
    }
  };

  const getTimelineIcon = (type: string) => {
    switch (type) {
      case "SEARCH": return <Search className="h-4 w-4 text-blue-500" />;
      case "SAVE": return <CheckCircle2 className="h-4 w-4 text-emerald-500" />;
      case "UPDATE": return <FileText className="h-4 w-4 text-amber-500" />;
      default: return <Clock className="h-4 w-4 text-muted-foreground" />;
    }
  };

  return (
    <div className="space-y-6 max-w-5xl">
      <div className="space-y-1">
        <h2 className="text-2xl font-bold tracking-tight">
          Welcome back{user?.name ? `, ${user.name}` : ""}
        </h2>
        <p className="text-muted-foreground">
          Here&apos;s an overview of your music catalog.
        </p>
      </div>

      {isLoading ? (
        <div className="grid grid-cols-2 md:grid-cols-4 gap-3 md:gap-4">
          {[1, 2, 3, 4].map((i) => (
            <Card key={i} className="animate-pulse">
              <CardContent className="flex flex-col gap-2 p-4">
                <div className="h-8 w-8 rounded-lg bg-muted shrink-0" />
                <div className="space-y-2 mt-2">
                  <div className="h-6 w-16 bg-muted rounded" />
                  <div className="h-3 w-24 bg-muted rounded" />
                </div>
              </CardContent>
            </Card>
          ))}
        </div>
      ) : (
        <div className="grid grid-cols-2 md:grid-cols-4 gap-3 md:gap-4">
          <StatCard 
            icon={Library} 
            label="Saved Albums" 
            value={overview?.totalAlbums?.toString() || "0"} 
            subtext={overview?.albumsPercentageChange ? `${overview.albumsPercentageChange > 0 ? '+' : ''}${overview.albumsPercentageChange}% vs last week` : undefined} 
          />
          <StatCard 
            icon={User} 
            label="Favourite Artist" 
            value={overview?.favouriteArtist || "N/A"} 
            subtext={overview?.favouriteArtistCount ? `${overview.favouriteArtistCount} Albums Saved` : undefined} 
          />
          <StatCard 
            icon={Disc} 
            label="Favourite Genre" 
            value={overview?.favouriteGenre || "N/A"} 
            subtext={overview?.favouriteGenrePercentage ? `${overview.favouriteGenrePercentage}% of library` : undefined} 
          />
          <StatCard 
            icon={Sparkles} 
            label="Average Rating" 
            value={overview?.averageRating ? `${overview.averageRating} / 5` : "0.0 / 5"} 
          />
        </div>
      )}

      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <div className="md:col-span-2 space-y-4">
          <h3 className="text-lg font-semibold tracking-tight">Recent Activity</h3>
          <Card>
            <CardContent className="p-0">
              {isLoading ? (
                <div className="p-6 space-y-4">
                  {[1, 2, 3].map(i => (
                    <div key={i} className="flex gap-4 animate-pulse">
                      <div className="w-8 h-8 rounded-full bg-muted shrink-0" />
                      <div className="space-y-2 flex-1">
                        <div className="h-4 w-1/3 bg-muted rounded" />
                        <div className="h-3 w-1/2 bg-muted rounded" />
                      </div>
                    </div>
                  ))}
                </div>
              ) : isLibraryEmpty ? (
                <div className="flex flex-col items-center justify-center py-12 text-center">
                  <div className="h-12 w-12 rounded-full bg-muted flex items-center justify-center mb-4">
                    <Music2 className="h-6 w-6 text-muted-foreground" />
                  </div>
                  <CardTitle className="text-base mb-1">Your library is empty</CardTitle>
                  <CardDescription className="max-w-xs mb-4">
                    Start searching and save albums to unlock analytics and AI insights.
                  </CardDescription>
                  <Link
                    href="/search"
                    className="inline-flex items-center gap-1.5 px-4 py-2 rounded-lg bg-primary text-primary-foreground text-sm font-medium hover:bg-primary/90 transition-colors"
                  >
                    <Search className="h-4 w-4" />
                    Search Music
                  </Link>
                </div>
              ) : activities.length > 0 ? (
                <div className="divide-y divide-border">
                  {activities.map((activity) => (
                    <div 
                      key={activity.id} 
                      onClick={() => handleActivityClick(activity.type, activity.metadata)}
                      className="p-4 flex gap-4 hover:bg-muted/50 transition-colors cursor-pointer"
                    >
                      <div className="mt-0.5 shrink-0 w-8 h-8 rounded-full bg-muted flex items-center justify-center">
                        {getTimelineIcon(activity.type)}
                      </div>
                      <div className="flex-1 min-w-0">
                        <p className="text-sm font-medium text-foreground">{activity.title}</p>
                        <p className="text-sm text-muted-foreground truncate">{activity.description}</p>
                      </div>
                      <div className="text-xs text-muted-foreground whitespace-nowrap">
                        {new Date(activity.timestamp).toLocaleDateString(undefined, { month: 'short', day: 'numeric' })}
                      </div>
                    </div>
                  ))}
                </div>
              ) : (
                <div className="p-6 text-center text-muted-foreground text-sm">
                  No recent activity to show.
                </div>
              )}
            </CardContent>
          </Card>
        </div>

        <div className="space-y-4">
          <h3 className="text-lg font-semibold tracking-tight">Quick Actions</h3>
          <div className="grid grid-cols-1 gap-3">
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
      </div>
    </div>
  );
}

function StatCard({
  icon: Icon,
  label,
  value,
  subtext
}: {
  icon: React.ElementType;
  label: string;
  value: string;
  subtext?: string;
}) {
  return (
    <Card>
      <CardContent className="flex flex-col gap-1 p-4">
        <div className="flex items-center gap-2 mb-2 text-muted-foreground">
          <Icon className="h-4 w-4" />
          <span className="text-xs font-medium uppercase tracking-wider">{label}</span>
        </div>
        <p className="text-2xl font-bold tracking-tight truncate" title={value}>{value}</p>
        {subtext && (
          <p className="text-xs text-muted-foreground truncate" title={subtext}>{subtext}</p>
        )}
      </CardContent>
    </Card>
  );
}
