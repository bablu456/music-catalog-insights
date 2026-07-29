# Backend Foundation

## Purpose

The backend foundation provides reusable infrastructure used by every feature.

No business logic belongs here.

---

# Build Order

1. Common Module
2. Global API Response
3. Global Exception Handler
4. Validation
5. Logging
6. Base Entity
7. OpenAPI
8. Health Endpoint
9. Security Foundation
10. JWT
11. Database
12. External API Integration

---

# Rule

Every future feature must use these shared modules.

No feature should implement its own response wrapper.

No feature should implement its own exception handler.

No feature should implement its own validation mechanism.

---

# Foundation Modules

common/

exception/

config/

validation/

security/

---

# Success Criteria

Every endpoint returns a unified response.

Errors are consistent.

Validation is automatic.

Logging is centralized.

Configuration is reusable.