import { api } from './api';
import type { ApiResponse } from '../types/api';

export interface ChartData {
  name: string;
  value: number;
}

export interface DashboardOverview {
  totalAlbums: number;
  totalArtists: number;
  totalGenres: number;
  averageRating: number;
}

export interface AnalyticsResponse {
  overview: DashboardOverview;
  topGenres: ChartData[];
  topArtists: ChartData[];
  releaseYears: ChartData[];
  ratingDistribution: ChartData[];
}

export const analyticsService = {
  getAnalytics: async (): Promise<ApiResponse<AnalyticsResponse>> => {
    const response = await api.get('/analytics');
    return response.data;
  },
};
