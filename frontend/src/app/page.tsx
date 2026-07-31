import { Sparkles, Music2, TrendingUp } from "lucide-react";
import Link from "next/link";

export default function Home() {
  return (
    <main className="flex min-h-screen flex-col items-center justify-center p-6 md:p-24 relative overflow-hidden">
      
      {/* Background Decorative Blur */}
      <div className="absolute top-1/3 left-1/2 -translate-x-1/2 -translate-y-1/2 w-[600px] h-[600px] bg-primary/20 rounded-full blur-[120px] pointer-events-none" />

      <div className="z-10 flex flex-col items-center text-center space-y-8 max-w-3xl">
        <div className="inline-flex items-center rounded-full border border-border bg-background/50 backdrop-blur-sm px-3 py-1 text-sm font-medium text-muted-foreground shadow-sm">
          <Sparkles className="mr-2 h-4 w-4 text-primary" />
          <span>Music Catalog AI Platform</span>
        </div>
        
        <h1 className="text-4xl md:text-6xl font-bold tracking-tight text-foreground">
          Discover, Save, and{" "}
          <span className="text-transparent bg-clip-text bg-gradient-to-r from-primary to-primary/50">
            Analyze Music
          </span>
        </h1>
        
        <p className="text-lg md:text-xl text-muted-foreground max-w-2xl leading-relaxed">
          Search the iTunes catalog, build your personal library, visualize listening patterns, and get AI-powered recommendations — all in one platform.
        </p>
        
        <div className="flex flex-col sm:flex-row gap-4 pt-4">
          <Link
            href="/dashboard"
            className="inline-flex h-11 items-center justify-center rounded-md bg-primary px-8 text-sm font-medium text-primary-foreground shadow transition-colors hover:bg-primary/90 focus-visible:outline-none focus-visible:ring-1 focus-visible:ring-ring"
          >
            View Dashboard
          </Link>
          <Link
            href="/auth/register"
            className="inline-flex h-11 items-center justify-center rounded-md border border-input bg-background/50 backdrop-blur-sm px-8 text-sm font-medium shadow-sm transition-colors hover:bg-accent hover:text-accent-foreground focus-visible:outline-none focus-visible:ring-1 focus-visible:ring-ring"
          >
            Get Started
          </Link>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-6 pt-16 w-full text-left">
          <FeatureCard 
            icon={<Music2 className="h-5 w-5 text-primary" />}
            title="Catalog Management"
            description="Search, save, and organize your favorite albums with personal ratings and notes."
          />
          <FeatureCard 
            icon={<TrendingUp className="h-5 w-5 text-primary" />}
            title="Real-time Analytics"
            description="Visualize genre distribution, top artists, and listening trends from your library."
          />
          <FeatureCard 
            icon={<Sparkles className="h-5 w-5 text-primary" />}
            title="AI Insights"
            description="Get personalized album recommendations and music analysis powered by AI."
          />
        </div>
      </div>
    </main>
  );
}

function FeatureCard({ icon, title, description }: { icon: React.ReactNode, title: string, description: string }) {
  return (
    <div className="group rounded-xl border border-border/50 bg-card/40 backdrop-blur-sm p-6 shadow-sm transition-all hover:shadow-md hover:border-border">
      <div className="mb-4 inline-flex h-10 w-10 items-center justify-center rounded-lg bg-primary/10">
        {icon}
      </div>
      <h3 className="mb-2 font-semibold tracking-tight">{title}</h3>
      <p className="text-sm text-muted-foreground leading-relaxed">{description}</p>
    </div>
  );
}
