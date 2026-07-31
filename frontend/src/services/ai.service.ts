import { api } from './api';
import type { ApiResponse } from '../types/api';

export interface RecommendationResponse {
  genreSummary: string;
  favouriteArtist: string;
  listeningTrends: string;
  interestingObservations: string;
  albumRecommendations: string[];
}

export const aiService = {
  getRecommendations: async (): Promise<ApiResponse<RecommendationResponse>> => {
    const response = await api.get('/ai/recommendations');
    return response.data;
  },
};
