"use client";

import { useState, useEffect } from "react";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import * as z from "zod";
import { SavedAlbum, libraryService } from "@/services/library.service";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogFooter,
} from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { toast } from "sonner";
import { Loader2 } from "lucide-react";

const editSchema = z.object({
  userRating: z.coerce.number().min(0).max(5).optional(),
  userNotes: z.string().max(2000).optional(),
});

type EditFormData = z.infer<typeof editSchema>;

interface EditAlbumDialogProps {
  album: SavedAlbum | null;
  isOpen: boolean;
  onClose: () => void;
}

export function EditAlbumDialog({ album, isOpen, onClose }: EditAlbumDialogProps) {
  const queryClient = useQueryClient();

  const { register, handleSubmit, reset, formState: { errors } } = useForm<EditFormData>({
    resolver: zodResolver(editSchema),
    defaultValues: {
      userRating: 0,
      userNotes: "",
    }
  });

  useEffect(() => {
    if (album) {
      reset({
        userRating: album.userRating || 0,
        userNotes: album.userNotes || "",
      });
    }
  }, [album, reset]);

  const mutation = useMutation({
    mutationFn: (data: EditFormData) => {
      if (!album) throw new Error("No album selected");
      return libraryService.updateAlbum(album.id, {
        ...album,
        userRating: data.userRating === 0 ? undefined : data.userRating,
        userNotes: data.userNotes,
      });
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['library'] });
      toast.success("Album updated successfully");
      onClose();
    },
    onError: () => {
      toast.error("Failed to update album");
    }
  });

  const onSubmit = (data: EditFormData) => {
    mutation.mutate(data);
  };

  return (
    <Dialog open={isOpen} onOpenChange={(open) => !open && onClose()}>
      <DialogContent className="sm:max-w-[425px]">
        <DialogHeader>
          <DialogTitle>Edit Album Details</DialogTitle>
        </DialogHeader>
        
        {album && (
          <form onSubmit={handleSubmit(onSubmit)} className="space-y-4 py-4">
            <div className="space-y-2">
              <Label htmlFor="userRating">Rating (1-5)</Label>
              <Input 
                id="userRating" 
                type="number" 
                min="0" 
                max="5" 
                {...register("userRating")} 
              />
              {errors.userRating && <p className="text-sm text-destructive">{errors.userRating.message}</p>}
            </div>
            
            <div className="space-y-2">
              <Label htmlFor="userNotes">Notes</Label>
              <textarea 
                id="userNotes" 
                className="flex min-h-[80px] w-full rounded-md border border-input bg-transparent px-3 py-2 text-sm ring-offset-background placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50"
                placeholder="Write your thoughts about this album..."
                {...register("userNotes")} 
              />
              {errors.userNotes && <p className="text-sm text-destructive">{errors.userNotes.message}</p>}
            </div>

            <DialogFooter>
              <Button type="button" variant="outline" onClick={onClose} disabled={mutation.isPending}>
                Cancel
              </Button>
              <Button type="submit" disabled={mutation.isPending}>
                {mutation.isPending && <Loader2 className="mr-2 h-4 w-4 animate-spin" />}
                Save changes
              </Button>
            </DialogFooter>
          </form>
        )}
      </DialogContent>
    </Dialog>
  );
}
