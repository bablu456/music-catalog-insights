import axios from 'axios';
import Cookies from 'js-cookie';

const API_URL = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080/api/v1';

export const api = axios.create({
  baseURL: API_URL,
  timeout: 30000,
  headers: {
    'Content-Type': 'application/json',
  },
});

api.interceptors.request.use(
  (config) => {
    const token = Cookies.get('token');
    if (token && token !== 'undefined' && token !== 'null' && token.trim() !== '') {
      config.headers.Authorization = `Bearer ${token}`;
      console.log(`[Diagnostics] Outgoing Authorization Header: Bearer ${token.substring(0, 10)}...`);
    } else {
      console.log('[Diagnostics] No valid token found in cookies, not attaching Authorization header');
    }
    return config;
  },
  (error) => Promise.reject(error)
);

api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      const originalRequest = error.config;
      
      // Do not redirect if the request was to login or register
      if (originalRequest.url?.includes('/auth/login') || originalRequest.url?.includes('/auth/register')) {
        return Promise.reject(error);
      }
      
      const token = Cookies.get('token');
      // Only logout if user was ostensibly authenticated and it's a protected endpoint
      if (token && token !== 'undefined' && token !== 'null') {
        console.log('[Diagnostics] 401 Unauthorized received on protected API. Logging out.');
        Cookies.remove('token');
        Cookies.remove('user');
        if (typeof window !== 'undefined' && !window.location.pathname.startsWith('/auth')) {
          window.location.href = '/auth/login';
        }
      }
    }
    return Promise.reject(error);
  }
);
