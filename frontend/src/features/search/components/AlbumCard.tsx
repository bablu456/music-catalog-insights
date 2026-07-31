"use client";

import { SearchResult } from "@/services/search.service";
import { Card, CardContent } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Plus, Disc3 } from "lucide-react";
import { motion } from "framer-motion";
import { useState } from "react";

interface AlbumCardProps {
  album: SearchResult;
  onSave: (album: SearchResult) => void;
  isSaving?: boolean;
}

export function AlbumCard({ album, onSave, isSaving }: AlbumCardProps) {
  const [isHovered, setIsHovered] = useState(false);

  return (
    <motion.div
      initial={{ opacity: 0, y: 20 }}
      animate={{ opacity: 1, y: 0 }}
      whileHover={{ y: -5 }}
      transition={{ duration: 0.3 }}
      onHoverStart={() => setIsHovered(true)}
      onHoverEnd={() => setIsHovered(false)}
      className="h-full"
    >
      <Card className="overflow-hidden border-border/50 bg-card/50 backdrop-blur-sm h-full flex flex-col hover:shadow-xl hover:shadow-primary/5 transition-all">
        <div className="relative aspect-square overflow-hidden bg-muted">
          {album.coverUrl ? (
            <img
              src={album.coverUrl.replace('100x100bb', '600x600bb')} // Get higher res image
              alt={album.title}
              className="object-cover w-full h-full transition-transform duration-700 ease-out group-hover:scale-105"
              loading="lazy"
            />
          ) : (
            <div className="w-full h-full flex items-center justify-center bg-secondary/50">
              <Disc3 className="w-16 h-16 text-muted-foreground/30" />
            </div>
          )}
          
          <div className={`absolute inset-0 bg-black/40 backdrop-blur-[2px] transition-opacity duration-300 flex items-center justify-center ${isHovered ? 'opacity-100' : 'opacity-0'}`}>
            <Button 
              size="lg" 
              className="rounded-full shadow-2xl scale-90 hover:scale-100 transition-transform"
              onClick={() => onSave(album)}
              disabled={isSaving}
            >
              {isSaving ? (
                <span className="flex items-center gap-2">Saving...</span>
              ) : (
                <span className="flex items-center gap-2"><Plus className="w-5 h-5"/> Save Album</span>
              )}
            </Button>
          </div>
        </div>
        
        <CardContent className="p-4 flex flex-col flex-grow">
          <h3 className="font-semibold text-lg leading-tight line-clamp-1 mb-1">{album.title}</h3>
          <p className="text-muted-foreground text-sm line-clamp-1 mb-3">{album.artist}</p>
          
          <div className="mt-auto flex items-center justify-between text-xs text-muted-foreground/80">
            <span>{album.genre}</span>
            <span>{album.releaseDate ? new Date(album.releaseDate).getFullYear() : ''}</span>
          </div>
        </CardContent>
      </Card>
    </motion.div>
  );
}
