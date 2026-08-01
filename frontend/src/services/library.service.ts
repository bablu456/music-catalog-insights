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

export interface PagedResponse<T> {
  content: T[];
  pageNumber: number;
  pageSize: number;
  totalElements: number;
  totalPages: number;
  isLast: boolean;
  isFirst: boolean;
  hasNext: boolean;
  hasPrevious: boolean;
}

export const libraryService = {
  getAllSavedAlbums: async (page = 0, size = 20): Promise<PagedResponse<SavedAlbum>> => {
    const response = await api.get<ApiResponse<PagedResponse<SavedAlbum>>>(`/library?page=${page}&size=${size}`);
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
