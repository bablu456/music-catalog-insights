# Validation Foundation

## Objective

Create a reusable validation layer for the entire backend.

This module validates every incoming request before it reaches the service layer.

---

## Principles

- Validate at the API boundary.
- Keep controllers thin.
- Keep business validation inside services.
- Never expose entities as request models.
- Always validate DTOs.

---

## Standard Constraints

- @NotBlank
- @NotNull
- @Email
- @Size
- @Pattern
- @Positive
- @PositiveOrZero
- @Min
- @Max

---

## Folder Structure

validation/

├── annotations/
├── validators/
├── constants/
└── messages/

---

## Future Extensions

- Password strength validator
- Rating range validator
- Duplicate request validator
- Custom annotation validators
- Internationalized validation messages

---

## Definition of Done

- Validation dependency configured
- Validation package created
- Sample Request DTO created
- Global validation errors handled
- Documentation updated