# Repository Guidelines

## Project Structure & Module Organization
`src/main/java/com/restful/dslearn` contains the Spring Boot application entry point and the current domain model in `entity/`. `src/main/resources` holds shared configuration in `application.properties`, profile-specific overrides in `application-*.properties`, and seed scripts such as `import.sql` and `import-mysql.sql`. Tests live under `src/test/java`. UML and reverse-engineering artifacts are kept in `uml/` and should be treated as reference material, not runtime assets.

## Build, Test, and Development Commands
Use the Maven wrapper so everyone builds with the same toolchain.

- `./mvnw spring-boot:run` starts the app with the default `h2` profile.
- `./mvnw spring-boot:run -Dspring-boot.run.profiles=desktop` runs against the desktop database profile.
- `./mvnw spring-boot:run -Dspring-boot.run.profiles=laptop` runs against the laptop database profile.
- `./mvnw test` runs the JUnit test suite.
- `./mvnw clean package` builds the jar and triggers Asciidoctor during `prepare-package`.
- `./mvnw clean package -DskipTests` is acceptable for quick packaging checks only.

## Coding Style & Naming Conventions
Follow the existing Java style: 4-space indentation, one top-level class per file, and `PascalCase` for classes, enums, and entity names. Keep packages under `com.restful.dslearn`. Use `camelCase` for fields and methods, and `UPPER_SNAKE_CASE` for enums such as `DeliverStatus`. JPA tables use the `tb_` prefix, for example `tb_course`. Prefer Lombok annotations already used in the project (`@Getter`, `@Setter`, `@NoArgsConstructor`) instead of handwritten boilerplate when extending entities.

## Testing Guidelines
Tests use Spring Boot Test with JUnit 5. Name test classes `*Tests` to match the current convention, for example `DslearnApplicationTests`. Add or update tests whenever you change entity mappings, profile configuration, or seed data. There is no enforced coverage threshold yet, but schema and mapping changes should not land without at least one automated verification path.

## Commit & Pull Request Guidelines
Recent history follows short conventional-style subjects such as `refactor: update Spring Boot version` and `docs: add CLAUDE.md`. Keep commit messages imperative and scoped. Pull requests should describe the impacted module or profile, summarize any schema or seed-script changes, list the commands you ran, and include screenshots only when documentation artifacts or diagrams change.

## Configuration Notes
The default active profile is `h2` (`spring.profiles.active=h2`). When changing datasource settings, keep `application.properties` and the matching `application-<profile>.properties` files aligned with the correct import script.
