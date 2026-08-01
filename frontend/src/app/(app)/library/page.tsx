"use client";

import { useState } from "react";
import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { libraryService, SavedAlbum } from "@/services/library.service";
import { LibraryCard } from "@/features/library/components/LibraryCard";
import { EditAlbumDialog } from "@/features/library/components/EditAlbumDialog";
import { toast } from "sonner";
import { Library, Loader2, AlertCircle, ChevronLeft, ChevronRight } from "lucide-react";
import { Button } from "@/components/ui/button";
import { motion } from "framer-motion";

export default function LibraryPage() {
  const queryClient = useQueryClient();
  const [selectedAlbum, setSelectedAlbum] = useState<SavedAlbum | null>(null);
  const [isEditDialogOpen, setIsEditDialogOpen] = useState(false);
  const [deletingId, setDeletingId] = useState<string | null>(null);

  const [page, setPage] = useState(0);
  const size = 12;

  const { data: pagedData, isLoading, isError, error } = useQuery({
    queryKey: ['library', page],
    queryFn: () => libraryService.getAllSavedAlbums(page, size),
  });

  const albums = pagedData?.content;

  const deleteMutation = useMutation({
    mutationFn: (id: string) => libraryService.deleteAlbum(id),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['library'] });
      queryClient.invalidateQueries({ queryKey: ['analytics-overview'] });
      queryClient.invalidateQueries({ queryKey: ['analytics-genres'] });
      queryClient.invalidateQueries({ queryKey: ['analytics-artists'] });
      queryClient.invalidateQueries({ queryKey: ['analytics-releases'] });
      queryClient.invalidateQueries({ queryKey: ['analytics-ratings'] });
      queryClient.invalidateQueries({ queryKey: ['recent-activity'] });
      queryClient.invalidateQueries({ queryKey: ['ai-recommendations'] });
      toast.success("Album removed from library");
    },
    onError: () => {
      toast.error("Failed to remove album");
    },
    onSettled: () => {
      setDeletingId(null);
    }
  });

  const handleEdit = (album: SavedAlbum) => {
    setSelectedAlbum(album);
    setIsEditDialogOpen(true);
  };

  const handleDelete = (album: SavedAlbum) => {
    setDeletingId(album.id);
    deleteMutation.mutate(album.id);
  };

  return (
    <div className="container max-w-7xl mx-auto py-8 px-4 sm:px-6 space-y-12">
      <div className="flex flex-col md:flex-row items-center justify-between gap-4">
        <div>
          <h1 className="text-4xl font-extrabold tracking-tight">Your Library</h1>
          <p className="text-muted-foreground text-lg mt-2">Manage your saved albums, ratings, and notes.</p>
        </div>
      </div>

      <div className="min-h-[400px]">
        {isLoading && (
          <div className="flex flex-col items-center justify-center mt-20 space-y-4">
            <Loader2 className="w-12 h-12 text-primary animate-spin" />
            <p className="text-muted-foreground">Loading your library...</p>
          </div>
        )}

        {isError && (
          <div className="flex flex-col items-center justify-center mt-20 space-y-4 text-destructive">
            <AlertCircle className="w-12 h-12" />
            <p className="font-medium text-lg">Failed to fetch library. Please try again.</p>
            <p className="text-sm opacity-80">{error instanceof Error ? error.message : "Unknown error"}</p>
          </div>
        )}

        {albums && albums.length === 0 && !isLoading && (
          <motion.div 
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            className="flex flex-col items-center justify-center h-full text-center space-y-4 mt-20 opacity-60"
          >
            <Library className="w-16 h-16 text-muted-foreground/30" />
            <p className="text-xl text-muted-foreground font-medium">Your library is empty</p>
            <p className="text-sm text-muted-foreground/70">Go to Search to find and save albums you love.</p>
          </motion.div>
        )}

        {albums && albums.length > 0 && (
          <motion.div 
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            className="space-y-8"
          >
            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
              {albums.map((album) => (
                <LibraryCard 
                  key={album.id} 
                  album={album} 
                  onEdit={handleEdit}
                  onDelete={handleDelete}
                  isDeleting={deletingId === album.id}
                />
              ))}
            </div>

            {pagedData && pagedData.totalPages > 1 && (
              <div className="flex items-center justify-center gap-4 mt-8">
                <Button
                  variant="outline"
                  onClick={() => setPage((p) => Math.max(0, p - 1))}
                  disabled={pagedData.isFirst}
                >
                  <ChevronLeft className="w-4 h-4 mr-2" />
                  Previous
                </Button>
                <span className="text-sm text-muted-foreground">
                  Page {pagedData.pageNumber + 1} of {pagedData.totalPages}
                </span>
                <Button
                  variant="outline"
                  onClick={() => setPage((p) => Math.min(pagedData.totalPages - 1, p + 1))}
                  disabled={pagedData.isLast}
                >
                  Next
                  <ChevronRight className="w-4 h-4 ml-2" />
                </Button>
              </div>
            )}
          </motion.div>
        )}
      </div>

      <EditAlbumDialog 
        album={selectedAlbum} 
        isOpen={isEditDialogOpen} 
        onClose={() => setIsEditDialogOpen(false)} 
      />
    </div>
  );
}
