"use client";

import { useState } from "react";
import { useQuery, useMutation } from "@tanstack/react-query";
import { searchService, SearchResult } from "@/services/search.service";
import { libraryService, SaveAlbumRequest } from "@/services/library.service";
import { SearchInput } from "@/features/search/components/SearchInput";
import { AlbumCard } from "@/features/search/components/AlbumCard";
import { toast } from "sonner";
import { Music2, Loader2, AlertCircle } from "lucide-react";
import { motion } from "framer-motion";

export default function SearchPage() {
  const [query, setQuery] = useState("");

  const { data: results, isLoading, isError, error } = useQuery({
    queryKey: ['search', query],
    queryFn: () => searchService.search(query),
    enabled: query.length > 0,
    staleTime: 5 * 60 * 1000,
  });

  const saveMutation = useMutation({
    mutationFn: (data: SaveAlbumRequest) => libraryService.saveAlbum(data),
    onSuccess: () => {
      toast.success("Album saved to your library!");
    },
    onError: (error: any) => {
      toast.error(error.response?.data?.message || "Failed to save album");
    }
  });

  const handleSave = (album: SearchResult) => {
    saveMutation.mutate({
      appleCatalogId: album.id,
      title: album.title,
      artistName: album.artist,
      genre: album.genre,
      releaseDate: album.releaseDate,
      trackCount: album.trackCount || 1,
      artworkUrl: album.coverUrl,
    });
  };

  return (
    <div className="container max-w-7xl mx-auto py-8 px-4 sm:px-6 space-y-12">
      <div className="text-center space-y-4 max-w-2xl mx-auto">
        <h1 className="text-4xl font-extrabold tracking-tight">Find New Music</h1>
        <p className="text-muted-foreground text-lg">Search the entire iTunes catalog for your favorite albums.</p>
        
        <div className="pt-4">
          <SearchInput onSearch={setQuery} isLoading={isLoading} />
        </div>
      </div>

      <div className="min-h-[400px]">
        {!query && (
          <motion.div 
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            className="flex flex-col items-center justify-center h-full text-center space-y-4 mt-20 opacity-50"
          >
            <Music2 className="w-16 h-16 text-muted-foreground/30" />
            <p className="text-xl text-muted-foreground font-medium">Start typing to search for albums</p>
          </motion.div>
        )}

        {isLoading && query && (
          <div className="flex flex-col items-center justify-center mt-20 space-y-4">
            <Loader2 className="w-12 h-12 text-primary animate-spin" />
            <p className="text-muted-foreground">Searching catalog...</p>
          </div>
        )}

        {isError && (
          <div className="flex flex-col items-center justify-center mt-20 space-y-4 text-destructive">
            <AlertCircle className="w-12 h-12" />
            <p className="font-medium text-lg">Failed to fetch results. Please try again.</p>
            <p className="text-sm opacity-80">{(error as any)?.message}</p>
          </div>
        )}

        {results && results.length === 0 && !isLoading && (
          <div className="flex flex-col items-center justify-center mt-20 space-y-4">
            <p className="text-xl text-muted-foreground font-medium">No albums found for "{query}"</p>
            <p className="text-sm text-muted-foreground/70">Try a different search term.</p>
          </div>
        )}

        {results && results.length > 0 && (
          <motion.div 
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-5 gap-6"
          >
            {results.map((album) => (
              <AlbumCard 
                key={album.id} 
                album={album} 
                onSave={handleSave} 
                isSaving={saveMutation.isPending && saveMutation.variables?.appleCatalogId === album.id} 
              />
            ))}
          </motion.div>
        )}
      </div>
    </div>
  );
}
