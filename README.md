# Music Catalog AI

## Project Overview
This project is a production-grade full-stack application foundation for a Music Catalog SaaS product. It leverages a modern stack designed for scalability, maintainability, and future AI integration.

## Architecture
The application uses a modular monolithic backend architecture paired with a feature-driven frontend design. It relies on PostgreSQL for persistent data storage and is fully containerized.

## Tech Stack
**Frontend:**
- Next.js 15 (App Router)
- TypeScript
- Tailwind CSS
- shadcn/ui
- Framer Motion, TanStack Query, Zod, React Hook Form

**Backend:**
- Java 21
- Spring Boot 3.x
- Spring Security, Data JPA, Validation
- MapStruct, Lombok
- JWT

**DevOps & Database:**
- PostgreSQL (Docker/Neon)
- Docker & Docker Compose
- pgAdmin

## Folder Structure
- rontend/: Next.js frontend application.
- ackend/: Spring Boot Java application.
- docker/: Docker configurations and definitions.
- docs/: Comprehensive project documentation.
- .github/: CI/CD workflows and GitHub templates (Future).

## Setup & Running Locally
1. Clone the repository.
2. Copy .env.example to .env and fill in the values.
3. Run docker compose up -d to start the database and pgAdmin.
4. Open ackend/ and run ./mvnw spring-boot:run.
5. Open rontend/ and run 
pm run dev.

## Future Roadmap
- Complete authentication flows (JWT integration).
- Implement catalog CRUD operations.
- Integrate AI features.
- Setup GitHub Actions CI/CD.

## Coding Standards
- Follow SOLID principles.
- Use feature-first organization.
- Strict TypeScript & Java typing.

## Contributing
Please see docs/09-coding-standards.md for guidelines.

## License
MIT License
