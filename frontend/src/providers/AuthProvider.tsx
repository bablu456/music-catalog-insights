"use client";

import React, { createContext, useState, useEffect, useCallback, ReactNode } from 'react';
import Cookies from 'js-cookie';
import { useRouter } from 'next/navigation';
import { authService, LoginRequest, RegisterRequest } from '@/services/auth.service';
import type { User } from '@/types/api';

// ── Context Type ────────────────────────────────────────────

interface AuthContextType {
  user: User | null;
  isAuthenticated: boolean;
  isLoading: boolean;
  login: (data: LoginRequest) => Promise<void>;
  register: (data: RegisterRequest) => Promise<void>;
  logout: () => void;
}

export const AuthContext = createContext<AuthContextType | undefined>(undefined);

// ── Provider ────────────────────────────────────────────────

export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [user, setUser] = useState<User | null>(null);
  const [isAuthenticated, setIsAuthenticated] = useState<boolean>(false);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const router = useRouter();

  // Check token on mount only
  useEffect(() => {
    const token = Cookies.get('token');
    const storedUser = Cookies.get('user');
    if (token && storedUser) {
      try {
        setUser(JSON.parse(storedUser));
        setIsAuthenticated(true);
      } catch {
        Cookies.remove('token');
        Cookies.remove('user');
      }
    }
    setIsLoading(false);
  }, []);

  const login = useCallback(async (data: LoginRequest) => {
    const response = await authService.login(data);
    const token = response.data.token;
    const userDto = response.data.user;

    if (!token || typeof token !== 'string' || token.trim() === '' || token === 'undefined' || token === 'null') {
      throw new Error('Invalid token received from server');
    }

    const userData: User = { name: userDto.name, email: userDto.email };
    
    Cookies.set('token', token, { expires: 7 });
    Cookies.set('user', JSON.stringify(userData), { expires: 7 });
    
    setUser(userData);
    setIsAuthenticated(true);
    
    console.log('[Diagnostics] Stored Token:', token.substring(0, 10) + '...');
    console.log('[Diagnostics] Authentication State: true');
    console.log('[Diagnostics] Current User:', userData);
    
    router.push('/dashboard');
  }, [router]);

  const register = useCallback(async (data: RegisterRequest) => {
    const response = await authService.register(data);
    const token = response.data.token;
    const userDto = response.data.user;

    if (!token || typeof token !== 'string' || token.trim() === '' || token === 'undefined' || token === 'null') {
      throw new Error('Invalid token received from server');
    }

    const userData: User = { name: userDto.name, email: userDto.email };
    
    Cookies.set('token', token, { expires: 7 });
    Cookies.set('user', JSON.stringify(userData), { expires: 7 });
    
    setUser(userData);
    setIsAuthenticated(true);

    console.log('[Diagnostics] Stored Token:', token.substring(0, 10) + '...');
    console.log('[Diagnostics] Authentication State: true');
    console.log('[Diagnostics] Current User:', userData);

    router.push('/dashboard');
  }, [router]);

  const logout = useCallback(() => {
    Cookies.remove('token');
    Cookies.remove('user');
    setUser(null);
    setIsAuthenticated(false);
    router.push('/auth/login');
  }, [router]);

  return (
    <AuthContext.Provider value={{ user, isAuthenticated, isLoading, login, register, logout }}>
      {children}
    </AuthContext.Provider>
  );
};
