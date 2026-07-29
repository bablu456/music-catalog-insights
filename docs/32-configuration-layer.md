# Backend Configuration Layer

## Purpose

The configuration layer centralizes all framework and infrastructure configuration.

No business logic should exist here.

---

# Responsibilities

The configuration package manages:

- CORS
- Jackson
- OpenAPI
- Time Zone
- Bean Configuration
- Environment Configuration
- Async Configuration
- Cache Configuration (future)

---

# Configuration Classes

config/

ApplicationConfig

CorsConfig

JacksonConfig

OpenApiConfig

ClockConfig

---

# Rules

Controllers must never configure CORS.

Controllers must never configure ObjectMapper.

Configuration should be reusable.

Configuration should be environment-aware.

---

# Future Configuration

Redis

Caching

Rate Limiting

Flyway

Observability

Metrics

Tracing

Prometheus

Grafana
