# Backend Common Module

## Purpose

The Common Module contains reusable infrastructure shared across the entire backend.

Business logic must never be placed here.

---

# Responsibilities

The common package provides:

- Standard API responses
- API constants
- Shared utilities
- Base models
- Common enums

---

# Initial Components

ApiResponse

ApiError

ApiConstants

---

# Rules

Controllers never build responses manually.

Every endpoint returns ApiResponse<T>.

The frontend must always receive the same response structure.

No feature may create its own response wrapper.

---

# Future Components

BaseEntity

PaginationResponse

AuditFields

CommonUtils

DateUtils

ResponseFactory

Enums

Constants