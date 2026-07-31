import { api } from './api';
import type { ApiResponse } from '@/types/api';

export interface SearchResult {
  id: string;
  type: string;
  title: string;
  artist: string;
  album: string;
  coverUrl: string;
  previewUrl: string;
  releaseDate: string;
  genre: string;
  trackCount?: number;
}

export const searchService = {
  search: async (query: string): Promise<SearchResult[]> => {
    if (!query.trim()) return [];
    const response = await api.get<ApiResponse<SearchResult[]>>('/search', {
      params: { query }
    });
    return response.data.data;
  },
};
