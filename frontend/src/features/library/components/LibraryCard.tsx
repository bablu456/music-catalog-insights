"use client";

import { SavedAlbum } from "@/services/library.service";
import { Card, CardContent } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Disc3, Edit2, Trash2, Star, Calendar, Music } from "lucide-react";
import { motion } from "framer-motion";

interface LibraryCardProps {
  album: SavedAlbum;
  onEdit: (album: SavedAlbum) => void;
  onDelete: (album: SavedAlbum) => void;
  isDeleting?: boolean;
}

export function LibraryCard({ album, onEdit, onDelete, isDeleting }: LibraryCardProps) {
  return (
    <motion.div
      initial={{ opacity: 0, y: 20 }}
      animate={{ opacity: 1, y: 0 }}
      whileHover={{ y: -5 }}
      transition={{ duration: 0.3 }}
      className="h-full"
    >
      <Card className="overflow-hidden border-border/50 bg-card/50 backdrop-blur-sm h-full flex flex-col group hover:shadow-xl hover:shadow-primary/5 transition-all">
        <div className="relative aspect-square overflow-hidden bg-muted">
          {album.artworkUrl ? (
            <img
              src={album.artworkUrl.replace('100x100bb', '600x600bb')}
              alt={album.title}
              className="object-cover w-full h-full transition-transform duration-700 ease-out group-hover:scale-105"
              loading="lazy"
            />
          ) : (
            <div className="w-full h-full flex items-center justify-center bg-secondary/50">
              <Disc3 className="w-16 h-16 text-muted-foreground/30" />
            </div>
          )}
          
          <div className="absolute inset-0 bg-gradient-to-t from-black/80 via-black/20 to-transparent opacity-0 group-hover:opacity-100 transition-opacity duration-300 flex flex-col justify-end p-4">
            <div className="flex gap-2 justify-end">
              <Button 
                size="icon" 
                variant="secondary" 
                className="rounded-full shadow-lg h-10 w-10"
                onClick={() => onEdit(album)}
              >
                <Edit2 className="w-4 h-4" />
              </Button>
              <Button 
                size="icon" 
                variant="destructive" 
                className="rounded-full shadow-lg h-10 w-10"
                onClick={() => onDelete(album)}
                disabled={isDeleting}
              >
                <Trash2 className="w-4 h-4" />
              </Button>
            </div>
          </div>
        </div>
        
        <CardContent className="p-4 flex flex-col flex-grow">
          <h3 className="font-semibold text-lg leading-tight line-clamp-1 mb-1">{album.title}</h3>
          <p className="text-muted-foreground text-sm line-clamp-1 mb-4">{album.artistName}</p>
          
          <div className="grid grid-cols-2 gap-y-2 gap-x-4 text-xs text-muted-foreground/80 mb-4">
            <div className="flex items-center gap-1.5">
              <Music className="w-3.5 h-3.5" />
              <span className="truncate">{album.genre}</span>
            </div>
            <div className="flex items-center gap-1.5">
              <Calendar className="w-3.5 h-3.5" />
              <span>{album.releaseDate ? new Date(album.releaseDate).getFullYear() : 'Unknown'}</span>
            </div>
            <div className="flex items-center gap-1.5">
              <Disc3 className="w-3.5 h-3.5" />
              <span>{album.trackCount} Tracks</span>
            </div>
            <div className="flex items-center gap-1.5 text-yellow-500">
              <Star className="w-3.5 h-3.5 fill-current" />
              <span>{album.userRating || 0}/5</span>
            </div>
          </div>

          {album.userNotes && (
            <div className="mt-auto pt-3 border-t border-border/50">
              <p className="text-xs text-muted-foreground italic line-clamp-2">
                &quot;{album.userNotes}&quot;
              </p>
            </div>
          )}
        </CardContent>
      </Card>
    </motion.div>
  );
}
