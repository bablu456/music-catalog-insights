# Global Exception Handling

## Purpose

The Global Exception Handler centralizes error handling across the application.

Controllers must never contain try-catch blocks for business exceptions.

All exceptions are translated into a consistent API response.

---

# Goals

- Centralized error handling
- Consistent error responses
- Better debugging
- Better frontend integration
- Cleaner controllers

---

# Responsibilities

Handle

- ValidationException
- ResourceNotFoundException
- DuplicateResourceException
- IllegalArgumentException
- AuthenticationException
- AccessDeniedException
- MethodArgumentNotValidException
- Exception

---

# Response Format

{
    "success": false,
    "message": "...",
    "error": "...",
    "timestamp": "...",
    "path": "...",
    "status": 400
}

---

# Rules

Never expose stack traces.

Never expose database errors.

Never expose internal implementation details.

Always log unexpected exceptions.

Always return meaningful messages.

---

# Future

Support

- Localization
- Error Codes
- Correlation IDs
- Request IDs