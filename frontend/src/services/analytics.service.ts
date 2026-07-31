import { api } from './api';
import type { ApiResponse } from '../types/api';

export interface ChartData {
  name: string;
  value: number;
}

export interface DashboardOverview {
  totalAlbums: number;
  albumsPercentageChange: number;
  favouriteArtist: string;
  favouriteArtistCount: number;
  favouriteGenre: string;
  favouriteGenrePercentage: number;
  averageRating: number;
}

export const analyticsService = {
  getOverview: async (): Promise<ApiResponse<DashboardOverview>> => {
    const response = await api.get('/analytics/overview');
    return response.data;
  },
  getGenres: async (): Promise<ApiResponse<ChartData[]>> => {
    const response = await api.get('/analytics/genres');
    return response.data;
  },
  getArtists: async (): Promise<ApiResponse<ChartData[]>> => {
    const response = await api.get('/analytics/artists');
    return response.data;
  },
  getReleases: async (): Promise<ApiResponse<ChartData[]>> => {
    const response = await api.get('/analytics/releases');
    return response.data;
  },
  getRatings: async (): Promise<ApiResponse<ChartData[]>> => {
    const response = await api.get('/analytics/ratings');
    return response.data;
  }
};
