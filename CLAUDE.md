# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run

```bash
# Build (skip tests)
./mvnw clean package -DskipTests

# Run (default profile: H2 in-memory)
./mvnw spring-boot:run

# Run with MySQL/PostgreSQL profile
./mvnw spring-boot:run -Dspring-boot.run.profiles=desktop
./mvnw spring-boot:run -Dspring-boot.run.profiles=laptop

# Run tests
./mvnw test

# Run a single test class
./mvnw test -Dtest=DslearnApplicationTests
```

H2 console available at `/h2-console` when using profile `h2` (default).

## Tech Stack

- Java 25, Spring Boot 3.5.6, Maven
- Spring Data JPA + Hibernate, Bean Validation
- Lombok (entities use @Getter/@Setter/@NoArgsConstructor)
- Spring REST Docs (mockmvc + asciidoctor)
- Databases: H2 (dev default), MySQL, PostgreSQL

## Database Profiles

| Profile   | DB         | Seed file              |
|-----------|------------|------------------------|
| `h2`      | H2 mem     | `import.sql`           |
| `desktop` | MySQL 3307 | `import-mysql.sql`     |
| `laptop`  | MySQL 3306 | `import-mysql.sql`     |

Desktop/laptop profiles have commented-out PostgreSQL config that can be toggled. Active profile is set in `application.properties` (`spring.profiles.active`).

## Domain Architecture

Plataforma de ensino (learning platform). Pacote base: `com.restful.dslearn`.

Currently the project has only the **entity layer** (`entity/` package) — no controllers, services, or repositories yet.

### Core Hierarchy: Course Content

```
Course 1──* Offer 1──* Resource 1──* Section 1──* Lesson
                                        │              │
                                        └─self-ref     ├── Content (text + videoUri)
                                        (prerequisite) └── Task (questions, weight, dueDate)
```

- **Lesson** is `abstract` with `@Inheritance(strategy = JOINED)` — subclasses `Content` and `Task` each map to their own table sharing the `tb_lesson` PK.
- **Section** has a self-referencing `@ManyToOne prerequisite` for ordering dependencies.
- **Resource** has an `@Enumerated(STRING) type` field: `LESSON_ONLY`, `LESSON_TASK`, `FORUM`, `EXTERNAL_LINK`.

### Enrollment (Composite Key)

`Enrollment` uses `@EmbeddedId` with `EnrollmentPK(User, Offer)`. Other entities reference this composite key:
- `Deliver` joins on both `user_id` + `offer_id` via `@JoinColumns`
- `tb_lessons_done` is a join table between `Lesson` and `Enrollment` (composite FK)

### Forum System

```
Topic *──1 Offer       Topic 1──* Reply
Topic *──1 Lesson      Topic 1──1 Reply (answer — best reply)
Topic *──* User (likes)  Reply *──* User (likes)
```

`Topic.answer` points to a single `Reply` (the accepted answer), while `Topic.replies` is the full list.

### Supporting Entities

- **Notification**: belongs to User, has `reading` (boolean) and `route` (frontend path)
- **Deliver**: task submission linked to a Lesson and an Enrollment, with `DeliverStatus` enum (`PENDING`, `ACCEPTED`, `REJECTED`)
- **Role**: authority string, many-to-many with User (`EAGER` fetch)

## Conventions

- Table names: `tb_` prefix (e.g., `tb_user`, `tb_course`)
- All entities implement HibernateProxy-aware `equals()`/`hashCode()` comparing by `id`
- Collection setters disabled via `@Setter(AccessLevel.NONE)` — modify collections through the getter
- Timestamps: `Instant` for date-only fields, `LocalDateTime` with `@CreationTimestamp`/`@UpdateTimestamp` for audit fields
- Jackson configured for `yyyy-MM-dd HH:mm:ss` format, timezone `America/Sao_Paulo`
