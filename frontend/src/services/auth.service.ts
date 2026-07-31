import { api } from './api';
import { z } from 'zod';
import type { ApiResponse } from '@/types/api';

// ── Validation Schemas ─────────────────────────────────────

export const loginSchema = z.object({
  email: z.string().email('Please enter a valid email address.'),
  password: z.string().min(1, 'Password is required.'),
});

export const registerSchema = z.object({
  name: z.string().min(2, 'Name must be at least 2 characters.'),
  email: z.string().email('Please enter a valid email address.'),
  password: z.string().regex(
    /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/,
    'Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one number, and one special character (@$!%*?&)'
  ),
});

export const authResponseSchema = z.object({
  token: z.string(),
  user: z.object({
    id: z.string().uuid(),
    name: z.string(),
    email: z.string().email(),
    role: z.string(),
  }),
});

// ── Types ───────────────────────────────────────────────────

export type LoginRequest = z.infer<typeof loginSchema>;
export type RegisterRequest = z.infer<typeof registerSchema>;
export type AuthResponse = z.infer<typeof authResponseSchema>;

// ── Service ─────────────────────────────────────────────────

export const authService = {
  login: async (data: LoginRequest) => {
    const response = await api.post<ApiResponse<AuthResponse>>('/auth/login', data);
    console.log('[Diagnostics] Backend Auth Response (Login):', response.data);
    
    // Runtime validation
    const parsed = authResponseSchema.safeParse(response.data.data);
    if (!parsed.success) {
      console.error('[Diagnostics] Backend Auth Response validation failed:', parsed.error);
      throw new Error('Invalid authentication response from server');
    }
    
    return response.data;
  },

  register: async (data: RegisterRequest) => {
    const response = await api.post<ApiResponse<AuthResponse>>('/auth/register', data);
    console.log('[Diagnostics] Backend Auth Response (Register):', response.data);
    
    // Runtime validation
    const parsed = authResponseSchema.safeParse(response.data.data);
    if (!parsed.success) {
      console.error('[Diagnostics] Backend Auth Response validation failed:', parsed.error);
      throw new Error('Invalid authentication response from server');
    }
    
    return response.data;
  },
};
