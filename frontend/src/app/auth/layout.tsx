import { ReactNode } from "react";
import { Music2 } from "lucide-react";
import Link from "next/link";

export default function AuthLayout({ children }: { children: ReactNode }) {
  return (
    <div className="min-h-screen flex flex-col md:flex-row">
      {/* Left side - Branding/Hero (visible on md+) */}
      <div className="hidden md:flex md:w-1/2 bg-zinc-950 p-12 flex-col justify-between border-r border-border">
        <Link href="/" className="flex items-center gap-2 text-xl font-semibold text-white">
          <Music2 className="h-6 w-6 text-primary" />
          <span>Music Catalog AI</span>
        </Link>
        <div className="space-y-6">
          <h1 className="text-4xl font-bold tracking-tight text-white lg:text-5xl">
            Discover, Save, and Analyze Your Music.
          </h1>
          <p className="text-zinc-400 text-lg max-w-md">
            Join the premium platform for music enthusiasts. Get AI-driven insights into your listening habits.
          </p>
        </div>
        <div className="text-sm text-zinc-500">
          © {new Date().getFullYear()} Music Catalog Insights. All rights reserved.
        </div>
      </div>

      {/* Right side - Forms */}
      <div className="flex-1 flex items-center justify-center p-8 sm:p-12 bg-background">
        <div className="w-full max-w-md space-y-8">
          <div className="md:hidden flex justify-center mb-8">
            <Link href="/" className="flex items-center gap-2 text-xl font-semibold text-foreground">
              <Music2 className="h-6 w-6 text-primary" />
              <span>Music Catalog AI</span>
            </Link>
          </div>
          {children}
        </div>
      </div>
    </div>
  );
}
