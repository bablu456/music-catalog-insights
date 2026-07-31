"use client";

import { useQuery } from "@tanstack/react-query";
import { aiService } from "@/services/ai.service";
import { Card, CardContent, CardHeader, CardTitle, CardDescription } from "@/components/ui/card";
import { Sparkles, Loader2, Music2, TrendingUp, Search, Info } from "lucide-react";
import Link from "next/link";
import { Button } from "@/components/ui/button";

export default function AIPage() {
  const { data, isLoading, isError, refetch, isFetching } = useQuery({
    queryKey: ['ai-recommendations'],
    queryFn: aiService.getRecommendations,
    refetchOnWindowFocus: false, // Don't refetch on focus to save API calls
  });

  const recommendation = data?.data;
  const hasError = isError || recommendation?.favouriteArtist === "Unavailable";
  const isEmpty = !isLoading && !hasError && !recommendation;

  if (isLoading) {
    return (
      <div className="max-w-4xl space-y-6">
        <div className="space-y-1">
          <h2 className="text-2xl font-bold tracking-tight">AI Insights</h2>
          <p className="text-muted-foreground">Analyzing your music library...</p>
        </div>
        <Card className="border-primary/20 bg-primary/5">
          <CardContent className="flex flex-col items-center justify-center py-20 text-center space-y-4">
            <Sparkles className="h-10 w-10 text-primary animate-pulse" />
            <div className="space-y-2">
              <h3 className="text-xl font-medium">Generating Personalized Insights</h3>
              <p className="text-muted-foreground text-sm max-w-sm mx-auto">
                Gemini is looking for patterns in your library to recommend your next favorite albums.
              </p>
            </div>
            <Loader2 className="h-6 w-6 animate-spin text-primary/50 mt-4" />
          </CardContent>
        </Card>
      </div>
    );
  }

  if (hasError) {
    return (
      <div className="max-w-4xl space-y-6">
        <div className="space-y-1">
          <h2 className="text-2xl font-bold tracking-tight">AI Insights</h2>
        </div>
        <Card className="bg-destructive/10 border-destructive/20 text-destructive">
          <CardContent className="p-8 flex flex-col items-center justify-center text-center space-y-4">
            <div className="h-12 w-12 rounded-full bg-destructive/20 flex items-center justify-center">
              <Info className="h-6 w-6" />
            </div>
            <div>
              <p className="font-medium text-lg">Unable to generate insights</p>
              <p className="text-sm mt-2 opacity-90 max-w-md mx-auto">
                {recommendation?.interestingObservations || "We couldn't connect to the AI service. Please try again later."}
              </p>
            </div>
            <Button onClick={() => refetch()} disabled={isFetching} variant="outline" className="mt-4 border-destructive/30 hover:bg-destructive/20">
              {isFetching ? <Loader2 className="h-4 w-4 animate-spin mr-2" /> : null}
              Try Again
            </Button>
          </CardContent>
        </Card>
      </div>
    );
  }

  if (isEmpty) {
    return (
      <div className="max-w-4xl space-y-6">
        <div className="space-y-1">
          <h2 className="text-2xl font-bold tracking-tight">AI Insights</h2>
          <p className="text-muted-foreground">Get personalized recommendations.</p>
        </div>
        <Card>
          <CardContent className="flex flex-col items-center justify-center py-16 text-center">
            <div className="h-12 w-12 rounded-full bg-muted flex items-center justify-center mb-4">
              <Music2 className="h-6 w-6 text-muted-foreground" />
            </div>
            <CardTitle className="text-base mb-1">Library too small</CardTitle>
            <CardDescription className="max-w-sm mb-4">
              Save some albums to your library first so the AI can learn your taste and provide accurate recommendations.
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
    <div className="max-w-4xl space-y-6 pb-12">
      <div className="flex items-center justify-between">
        <div className="space-y-1">
          <h2 className="text-2xl font-bold tracking-tight">AI Insights</h2>
          <p className="text-muted-foreground">
            Personalized analysis powered by Gemini.
          </p>
        </div>
        <Button onClick={() => refetch()} disabled={isFetching} variant="outline" size="sm" className="hidden sm:flex">
          {isFetching ? <Loader2 className="h-4 w-4 animate-spin mr-2" /> : <Sparkles className="h-4 w-4 mr-2" />}
          Refresh Insights
        </Button>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        {/* Genre & Artist Summaries */}
        <div className="space-y-6">
          <Card className="border-t-4 border-t-blue-500 shadow-md">
            <CardHeader className="pb-3">
              <CardTitle className="text-lg flex items-center gap-2">
                <Music2 className="h-5 w-5 text-blue-500" />
                Favorite Genres
              </CardTitle>
            </CardHeader>
            <CardContent>
              <p className="text-sm leading-relaxed">{recommendation.genreSummary}</p>
            </CardContent>
          </Card>

          <Card className="border-t-4 border-t-emerald-500 shadow-md">
            <CardHeader className="pb-3">
              <CardTitle className="text-lg flex items-center gap-2">
                <Sparkles className="h-5 w-5 text-emerald-500" />
                Favorite Artists
              </CardTitle>
            </CardHeader>
            <CardContent>
              <p className="text-sm leading-relaxed">{recommendation.favouriteArtist}</p>
            </CardContent>
          </Card>
        </div>

        {/* Trends & Observations */}
        <div className="space-y-6">
          <Card className="border-t-4 border-t-amber-500 shadow-md h-full flex flex-col">
            <CardHeader className="pb-3">
              <CardTitle className="text-lg flex items-center gap-2">
                <TrendingUp className="h-5 w-5 text-amber-500" />
                Listening Trends & Personality
              </CardTitle>
            </CardHeader>
            <CardContent className="space-y-6 flex-1">
              <p className="text-sm leading-relaxed">{recommendation.listeningTrends}</p>
              
              {recommendation.listeningPersonality && (
                <div className="bg-violet-500/10 p-4 rounded-lg border border-violet-500/20">
                  <h4 className="text-sm font-semibold text-violet-600 dark:text-violet-400 mb-2">Listener Profile</h4>
                  <p className="text-sm">{recommendation.listeningPersonality}</p>
                </div>
              )}
              
              {recommendation.interestingObservations && (
                <div className="bg-amber-500/10 p-4 rounded-lg border border-amber-500/20 mt-auto">
                  <h4 className="text-sm font-semibold text-amber-600 dark:text-amber-400 mb-2">Interesting Observation</h4>
                  <p className="text-sm">{recommendation.interestingObservations}</p>
                </div>
              )}
            </CardContent>
          </Card>
        </div>
      </div>

      {/* Album Recommendations */}
      <Card className="border-t-4 border-t-violet-500 shadow-md">
        <CardHeader>
          <CardTitle className="flex items-center gap-2">
            <Search className="h-5 w-5 text-violet-500" />
            Top Recommendations
          </CardTitle>
          <CardDescription>Based on your unique taste profile</CardDescription>
        </CardHeader>
        <CardContent>
          <ul className="grid gap-3 sm:grid-cols-1 md:grid-cols-2 lg:grid-cols-3">
            {recommendation.albumRecommendations.map((album, idx) => (
              <li key={idx} className="bg-muted/50 border rounded-lg p-4 text-sm flex items-start gap-3 hover:bg-muted/80 transition-colors">
                <span className="font-bold text-violet-500 text-lg leading-none shrink-0">{idx + 1}</span>
                <span className="leading-tight">{album}</span>
              </li>
            ))}
          </ul>
        </CardContent>
      </Card>
    </div>
  );
}
