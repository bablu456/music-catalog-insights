/**
 * Standard API response envelope from the Spring Boot backend.
 * Every endpoint returns this shape.
 */
export interface ApiResponse<T> {
  success: boolean;
  message: string;
  data: T;
  timestamp: string;
  path: string;
}

/**
 * Authenticated user information stored client-side after login.
 */
export interface User {
  name: string;
  email: string;
}
