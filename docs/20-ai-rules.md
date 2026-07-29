# AI Engineering Rules

> This document defines the operating rules for every AI assistant working on this repository.

---

# Mission

The objective is NOT to complete the assignment quickly.

The objective is to build a production-grade software product.

Every implementation must improve the maintainability, readability, scalability and quality of the codebase.

---

# Before Writing Code

The AI must always read:

- docs/00-project-overview.md
- docs/01-tech-stack.md
- docs/02-folder-structure.md
- docs/21-coding-standards.md
- docs/22-development-workflow.md

If those documents conflict with the prompt, follow the documentation.

---

# Golden Rules

Never implement multiple features in one response.

Never rewrite unrelated files.

Never delete working code.

Never introduce unnecessary dependencies.

Never duplicate business logic.

Never ignore existing architecture.

Always prefer reusable components.

Always write production-quality code.

Always explain architectural decisions.

Always keep the project buildable.

---

# Scope Rules

Implement only the requested feature.

If a task affects another feature, explain it instead of changing it automatically.

Do not modify files outside the requested scope.

---

# Backend Rules

Controllers only handle HTTP.

Business logic belongs in services.

Repositories only access data.

DTOs define API contracts.

Entities are persistence models only.

Validation occurs before business logic.

Global exception handling is mandatory.

Never expose entities directly to the frontend.

---

# Frontend Rules

Pages compose features.

Features own business logic.

Components are reusable.

Never fetch APIs directly inside UI components.

Use service and query layers.

Keep presentation separate from business logic.

Every page must include:

- Loading state
- Empty state
- Error state

---

# Security Rules

Never hardcode secrets.

Never commit API keys.

Never expose sensitive information.

Validate all external input.

Use environment variables.

---

# Documentation Rules

Every architectural decision must be documented.

If a folder changes, update the relevant document.

README must stay current.

---

# Testing Rules

Every completed feature must be manually tested.

New code must not break existing features.

Document edge cases.

---

# Output Format

After each implementation provide:

1. Summary
2. Files Created
3. Files Modified
4. Architectural Decisions
5. Testing Steps
6. Future Improvements

Then stop.