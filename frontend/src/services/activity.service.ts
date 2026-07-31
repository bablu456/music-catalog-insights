import { api } from './api';
import type { ApiResponse } from '../types/api';

export interface TimelineEvent {
  id: string;
  type: string;
  title: string;
  description: string;
  metadata: string;
  timestamp: string;
}

export const activityService = {
  getRecentActivity: async (): Promise<ApiResponse<TimelineEvent[]>> => {
    const response = await api.get('/activity/recent');
    return response.data;
  },
};
