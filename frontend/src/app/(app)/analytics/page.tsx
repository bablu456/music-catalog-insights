"use client";

import { useQuery } from "@tanstack/react-query";
import { analyticsService } from "@/services/analytics.service";
import { Card, CardContent, CardHeader, CardTitle, CardDescription } from "@/components/ui/card";
import { BarChart3, Loader2, Music2 } from "lucide-react";
import Link from "next/link";
import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  ResponsiveContainer,
  PieChart,
  Pie,
  Cell,
  Legend,
} from "recharts";

const COLORS = ['#3b82f6', '#10b981', '#f59e0b', '#8b5cf6', '#ec4899', '#6366f1'];

export default function AnalyticsPage() {
  const { data: genresData, isLoading: genresLoading } = useQuery({ queryKey: ['analytics-genres'], queryFn: analyticsService.getGenres });
  const { data: artistsData, isLoading: artistsLoading } = useQuery({ queryKey: ['analytics-artists'], queryFn: analyticsService.getArtists });
  const { data: releasesData, isLoading: releasesLoading } = useQuery({ queryKey: ['analytics-releases'], queryFn: analyticsService.getReleases });
  const { data: ratingsData, isLoading: ratingsLoading } = useQuery({ queryKey: ['analytics-ratings'], queryFn: analyticsService.getRatings });
  const { data: overviewData } = useQuery({ queryKey: ['analytics-overview'], queryFn: analyticsService.getOverview });

  const isLibraryEmpty = overviewData?.data?.totalAlbums === 0;

  if (isLibraryEmpty) {
    return (
      <div className="max-w-6xl space-y-6">
        <div className="space-y-1">
          <h2 className="text-2xl font-bold tracking-tight">Analytics</h2>
          <p className="text-muted-foreground">Visualize your listening patterns.</p>
        </div>
        <Card>
          <CardContent className="flex flex-col items-center justify-center py-16 text-center">
            <div className="h-12 w-12 rounded-full bg-muted flex items-center justify-center mb-4">
              <Music2 className="h-6 w-6 text-muted-foreground" />
            </div>
            <CardTitle className="text-base mb-1">Not enough data</CardTitle>
            <CardDescription className="max-w-sm mb-4">
              Save some albums to your library to see your listening analytics and charts.
            </CardDescription>
            <Link
              href="/search"
              className="inline-flex items-center justify-center rounded-md text-sm font-medium transition-colors focus-visible:outline-none focus-visible:ring-1 focus-visible:ring-ring bg-primary text-primary-foreground shadow hover:bg-primary/90 h-9 px-4 py-2"
            >
              Search Music
            </Link>
          </CardContent>
        </Card>
      </div>
    );
  }

  return (
    <div className="max-w-6xl space-y-6">
      <div className="space-y-1">
        <h2 className="text-2xl font-bold tracking-tight">Analytics</h2>
        <p className="text-muted-foreground">
          Visualize your listening patterns and music preferences.
        </p>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        <Card>
          <CardHeader>
            <CardTitle>Top Genres</CardTitle>
            <CardDescription>Distribution of genres in your library</CardDescription>
          </CardHeader>
          <CardContent>
            <div className="h-[300px] w-full">
              {genresLoading ? (
                <div className="h-full flex items-center justify-center"><Loader2 className="h-8 w-8 animate-spin text-muted-foreground/30" /></div>
              ) : (
                <ResponsiveContainer width="100%" height="100%">
                  <PieChart>
                    <Pie data={genresData?.data} cx="50%" cy="50%" innerRadius={60} outerRadius={100} paddingAngle={2} dataKey="value">
                      {genresData?.data?.map((entry, index) => <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />)}
                    </Pie>
                    <Tooltip contentStyle={{ backgroundColor: 'hsl(var(--card))', borderColor: 'hsl(var(--border))', color: 'hsl(var(--foreground))' }} itemStyle={{ color: 'hsl(var(--foreground))' }} />
                    <Legend />
                  </PieChart>
                </ResponsiveContainer>
              )}
            </div>
          </CardContent>
        </Card>

        <Card>
          <CardHeader>
            <CardTitle>Top Artists</CardTitle>
            <CardDescription>Most saved artists in your library</CardDescription>
          </CardHeader>
          <CardContent>
            <div className="h-[300px] w-full">
              {artistsLoading ? (
                <div className="h-full flex items-center justify-center"><Loader2 className="h-8 w-8 animate-spin text-muted-foreground/30" /></div>
              ) : (
                <ResponsiveContainer width="100%" height="100%">
                  <BarChart data={artistsData?.data} layout="vertical" margin={{ top: 5, right: 30, left: 40, bottom: 5 }}>
                    <CartesianGrid strokeDasharray="3 3" horizontal={false} stroke="hsl(var(--border))" />
                    <XAxis type="number" stroke="hsl(var(--foreground))" fontSize={12} tickLine={false} axisLine={false} />
                    <YAxis dataKey="name" type="category" width={80} stroke="hsl(var(--foreground))" fontSize={12} tickLine={false} axisLine={false} />
                    <Tooltip cursor={{ fill: 'hsl(var(--muted))' }} contentStyle={{ backgroundColor: 'hsl(var(--card))', borderColor: 'hsl(var(--border))', color: 'hsl(var(--foreground))' }} />
                    <Bar dataKey="value" fill="#3b82f6" radius={[0, 4, 4, 0]} />
                  </BarChart>
                </ResponsiveContainer>
              )}
            </div>
          </CardContent>
        </Card>

        <Card>
          <CardHeader>
            <CardTitle>Release Years</CardTitle>
            <CardDescription>When your favorite albums were released</CardDescription>
          </CardHeader>
          <CardContent>
            <div className="h-[300px] w-full">
              {releasesLoading ? (
                <div className="h-full flex items-center justify-center"><Loader2 className="h-8 w-8 animate-spin text-muted-foreground/30" /></div>
              ) : (
                <ResponsiveContainer width="100%" height="100%">
                  <BarChart data={releasesData?.data}>
                    <CartesianGrid strokeDasharray="3 3" vertical={false} stroke="hsl(var(--border))" />
                    <XAxis dataKey="name" stroke="hsl(var(--foreground))" fontSize={12} tickLine={false} axisLine={false} />
                    <YAxis stroke="hsl(var(--foreground))" fontSize={12} tickLine={false} axisLine={false} />
                    <Tooltip cursor={{ fill: 'hsl(var(--muted))' }} contentStyle={{ backgroundColor: 'hsl(var(--card))', borderColor: 'hsl(var(--border))', color: 'hsl(var(--foreground))' }} />
                    <Bar dataKey="value" fill="#10b981" radius={[4, 4, 0, 0]} />
                  </BarChart>
                </ResponsiveContainer>
              )}
            </div>
          </CardContent>
        </Card>

        <Card>
          <CardHeader>
            <CardTitle>Rating Distribution</CardTitle>
            <CardDescription>How you rate your albums</CardDescription>
          </CardHeader>
          <CardContent>
            <div className="h-[300px] w-full">
              {ratingsLoading ? (
                <div className="h-full flex items-center justify-center"><Loader2 className="h-8 w-8 animate-spin text-muted-foreground/30" /></div>
              ) : (
                <ResponsiveContainer width="100%" height="100%">
                  <BarChart data={ratingsData?.data}>
                    <CartesianGrid strokeDasharray="3 3" vertical={false} stroke="hsl(var(--border))" />
                    <XAxis dataKey="name" stroke="hsl(var(--foreground))" fontSize={12} tickLine={false} axisLine={false} />
                    <YAxis stroke="hsl(var(--foreground))" fontSize={12} tickLine={false} axisLine={false} />
                    <Tooltip cursor={{ fill: 'hsl(var(--muted))' }} contentStyle={{ backgroundColor: 'hsl(var(--card))', borderColor: 'hsl(var(--border))', color: 'hsl(var(--foreground))' }} />
                    <Bar dataKey="value" fill="#f59e0b" radius={[4, 4, 0, 0]} />
                  </BarChart>
                </ResponsiveContainer>
              )}
            </div>
          </CardContent>
        </Card>
      </div>
    </div>
  );
}
