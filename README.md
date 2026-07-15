# nova-java-quarkus-archetype

Nova Platform Quarkus Maven archetype. Generates a multi-module
microservice instance (Java 25 + Quarkus 3.33.2.1 LTS) with the
[`nova-notifications-quarkus-extension`](https://github.com/ahincho/nova-java-notifications-quarkus-extension)
adapter pre-wired.

This is the Quarkus counterpart of
[`ahincho/nova-java-spring-boot-archetype`](https://github.com/ahincho/nova-java-spring-boot-archetype):
same role, same consumption pattern (`mvn archetype:generate`), same
3-artefact ecosystem (archetype + parent POM + instance).

## The 3-artefact pattern

| Artefact | Purpose |
|---|---|
| `pe.edu.nova.java:nova-quarkus-archetype` | The Maven archetype (this repo). `<packaging>maven-archetype</packaging>`. Consumed via `mvn archetype:generate`. |
| `pe.edu.nova.java:nova-quarkus-parent` | The parent POM. Centralizes Java 25, Quarkus 3.33.2.1 LTS, Maven plugins, BOM import, and the notifications extension. |
| The generated instance | A multi-module Maven project that declares `<parent>nova-quarkus-parent</parent>` and contains 3 modules: `shared`, `product`, `boot`. |

## Generated project structure

```
<artifactId>/
├── pom.xml                        (packaging=pom, modules=shared,product,boot)
├── shared/
│   ├── pom.xml                    (pure Java, no Quarkus)
│   └── src/main/java/<package>/shared/domain/
│       ├── AggregateId.java
│       ├── DomainException.java
│       ├── ErrorCode.java
│       ├── NotFoundException.java
│       ├── ValidationException.java
│       └── ConflictException.java
├── product/
│   ├── pom.xml                    (depends on shared)
│   └── src/main/java/<package>/product/
│       ├── domain/
│       │   ├── Product.java
│       │   ├── ProductId.java
│       │   ├── ProductName.java
│       │   ├── ProductPrice.java
│       │   └── ProductRepository.java
│       └── application/
│           └── CreateProductUseCase.java
└── boot/
    ├── pom.xml                    (depends on shared, product; uses Quarkus BOM)
    ├── Dockerfile.jvm             (multi-stage, non-root UID 1001, tini, OCI labels)
    ├── Dockerfile.native          (GraalVM multi-stage)
    └── src/
        ├── main/java/<package>/boot/
        │   ├── Application.java
        │   ├── infrastructure/InMemoryProductRepository.java
        │   └── resource/
        │       ├── ProductResource.java
        │       └── DomainExceptionMapper.java
        └── main/resources/application.properties
                            (nova.notifications.* + quarkus.http.* + openapi + logging)
```

## How to generate a new instance

```bash
mvn -B archetype:generate \
    -DarchetypeGroupId=pe.edu.nova.java \
    -DarchetypeArtifactId=nova-quarkus-archetype \
    -DarchetypeVersion=1.0.0 \
    -DgroupId=com.example \
    -DartifactId=ms-catalog \
    -Dversion=1.0.0-SNAPSHOT \
    -Dpackage=com.example.catalog \
    -Dnova-parent-version=1.0.0
```

| Parameter | Default | Description |
|---|---|---|
| `groupId` | `com.example` | Maven groupId of the generated project |
| `artifactId` | `nova-microservice` | Maven artifactId |
| `version` | `1.0.0-SNAPSHOT` | Version |
| `package` | `${groupId}` | Java root package |
| `nova-parent-version` | `1.0.0` | Pin to a specific `nova-quarkus-parent` version |

The generated project is ready to build and run with no further edits:

```bash
cd ms-catalog
./mvnw clean verify
./mvnw -pl boot quarkus:dev
```

## Versioning

- `1.0.0` — initial release aligned with `nova-java-notifications-quarkus-extension:1.1.2` and `nova-java-quarkus-parent:1.0.0`.
- Java 25 toolchain.
- Quarkus 3.33.2.1 LTS (the Nova canonical LTS branch; the rest of Nova still tracks 3.37.x for some non-notifications extensions, and a global migration is in the meta-framework backlog).

## Distribution

Published to GitHub Packages as
`pe.edu.nova.java:nova-quarkus-archetype:1.0.0`. Consumed via
`mvn archetype:generate` (see above).

## Related

- [`nova-java-quarkus-parent`](https://github.com/ahincho/nova-java-quarkus-parent) — the parent POM that the generated instance inherits from.
- [`nova-java-spring-boot-archetype`](https://github.com/ahincho/nova-java-spring-boot-archetype) — the Spring Boot counterpart.
- [`nova-java-notifications-quarkus-extension`](https://github.com/ahincho/nova-java-notifications-quarkus-extension) — the notifications adapter pre-included in the parent.

---

## AI Assistance Attribution

This work was created through human-AI collaboration. The human author
(Angel Eduardo Hincho Jove, `ahincho@unsa.edu.pe`, UNSA) retains full
responsibility for the final artifact.

The canonical Nova Platform AI disclosure (covering every repository in
the workspace) lives in
[`AI-ATTRIBUTION.md`](https://github.com/ahincho/nova-platform-meta/blob/main/AI-ATTRIBUTION.md)
and includes references to:

- The R-AI requirement of `Reto-Tecnico-Backend.pdf` (challenge §2.5)
- **Regulation (EU) 2024/1689** ("EU AI Act"), Article 3(1) and Article 50
- **UNESCO Recommendation on the Ethics of Artificial Intelligence**
  (2021), Principle 6: Transparency and explainability

The artifacts shipped here are **not "AI systems"** under EU AI Act
Article 3(1) (a deterministic Java library does not "infer" outputs).
The disclosure is made voluntarily, aligned with the spirit of the EU
AI Act and UNESCO Principle 6.