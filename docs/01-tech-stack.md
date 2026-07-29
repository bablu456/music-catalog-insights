# 🛠️ Technology Stack

> This document defines the official technology stack for the Music Catalog AI Platform.
> Every engineering decision should align with this stack unless there is a documented reason to change it.

---

# Project Philosophy

This project is designed to demonstrate production-grade software engineering practices.

Goals:

- Clean Architecture
- Scalable Design
- Maintainability
- Performance
- Security
- Developer Experience
- Beautiful User Experience
- AI Integration
- Production Deployment

---

# Frontend

## Framework

Next.js 15

Reason

- App Router
- Server Components
- Excellent Developer Experience
- Production Ready
- Vercel Native

---

## Language

TypeScript

Reason

- Type Safety
- Better Refactoring
- Better Developer Experience

---

## Styling

Tailwind CSS

Reason

- Utility First
- Fast Development
- Responsive Design
- Industry Standard

---

## UI Components

shadcn/ui

Reason

- Accessible
- Reusable
- Beautiful
- Fully Customizable

---

## Icons

Lucide React

Reason

- Lightweight
- Consistent
- Tree Shakeable

---

## Animations

Framer Motion

Reason

- Smooth Animations
- Production Ready

---

## Forms

React Hook Form

Reason

- Performance
- Simplicity

---

## Validation

Zod

Reason

- Runtime Validation
- TypeScript Friendly

---

## API State

TanStack Query

Reason

- Caching
- Retry
- Background Refresh

---

## HTTP Client

Axios

Reason

- Interceptors
- Cleaner API Calls

---

## Charts

Recharts

Reason

- Responsive
- Easy Integration
- Good Documentation

---

# Backend

## Language

Java 21 LTS

Reason

- Stable
- Modern
- Long Term Support

---

## Framework

Spring Boot 3.x

Reason

- Enterprise Standard
- Large Ecosystem
- Fast Development

---

## Build Tool

Maven

Reason

- Widely Used
- Stable

---

## Security

Spring Security

JWT Authentication

---

## Database

PostgreSQL 16

Reason

- Reliable
- ACID Compliant
- Excellent Performance

---

## ORM

Spring Data JPA

Hibernate

---

## Validation

Jakarta Validation

---

## Mapping

MapStruct

---

## Boilerplate Reduction

Lombok

---

## Documentation

Springdoc OpenAPI

Swagger UI

---

# Infrastructure

Docker

Docker Compose

PostgreSQL

pgAdmin

---

# Version Control

Git

GitHub

---

# Deployment

Frontend

Vercel

Backend

Render

Database

Neon PostgreSQL

---

# AI

Provider Agnostic

The AI layer will communicate through an abstraction interface so the underlying LLM provider can be replaced without changing business logic.

---

# Future Integrations

Redis

Kafka

Prometheus

Grafana

GitHub Actions

Cloudflare

Object Storage

---

# Engineering Principles

- Feature First Architecture
- SOLID
- Clean Code
- REST API
- DTO Pattern
- Repository Pattern
- Service Layer
- Global Exception Handling
- Validation First
- Reusable Components
- Responsive Design
- Accessibility
- Performance Optimization
- Testability