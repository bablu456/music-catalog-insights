import { api } from './api';
import type { ApiResponse } from '@/types/api';

export interface SavedAlbum {
  id: string;
  appleCatalogId: string;
  title: string;
  artistName: string;
  genre: string;
  releaseDate: string;
  trackCount: number;
  artworkUrl: string;
  userRating: number | null;
  userNotes: string;
  createdAt: string;
  updatedAt: string;
}

export interface SaveAlbumRequest {
  appleCatalogId: string;
  title: string;
  artistName: string;
  genre?: string;
  releaseDate?: string;
  trackCount?: number;
  artworkUrl?: string;
  userRating?: number;
  userNotes?: string;
}

export interface UpdateAlbumRequest {
  appleCatalogId: string;
  title: string;
  artistName: string;
  genre?: string;
  releaseDate?: string;
  trackCount?: number;
  artworkUrl?: string;
  userRating?: number;
  userNotes?: string;
}

export const libraryService = {
  getAllSavedAlbums: async (): Promise<SavedAlbum[]> => {
    const response = await api.get<ApiResponse<SavedAlbum[]>>('/library');
    return response.data.data;
  },

  saveAlbum: async (data: SaveAlbumRequest): Promise<SavedAlbum> => {
    const response = await api.post<ApiResponse<SavedAlbum>>('/library', data);
    return response.data.data;
  },

  updateAlbum: async (id: string, data: UpdateAlbumRequest): Promise<SavedAlbum> => {
    const response = await api.put<ApiResponse<SavedAlbum>>(`/library/${id}`, data);
    return response.data.data;
  },

  deleteAlbum: async (id: string): Promise<void> => {
    await api.delete(`/library/${id}`);
  },
};
