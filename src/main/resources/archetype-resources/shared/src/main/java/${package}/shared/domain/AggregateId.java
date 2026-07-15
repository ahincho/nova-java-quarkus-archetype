package ${package}.shared.domain;

import java.util.Objects;

/**
 * Marker interface for the typed identifier of an aggregate root.
 * Implementations wrap a primitive (UUID, Long, String) so the domain
 * never exposes raw primitives in its public API.
 *
 * <p>This type lives in the {@code shared} module on purpose: every
 * bounded context (e.g. {@code product}) will define its own concrete
 * subclass (e.g. {@code ProductId}) that implements this interface.
 *
 * <p>Pure Java: zero framework imports, zero CDI annotations. The
 * {@code shared} module is intentionally framework-agnostic so it can
 * be reused by any framework starter (Quarkus, Spring Boot,
 * Micronaut).
 */
public interface AggregateId {

    /**
     * Returns the underlying primitive value of the identifier.
     *
     * @return the identifier's wrapped value, never {@code null}.
     */
    Object value();

    /**
     * Default implementation of structural equality based on
     * {@link #value()}. Two aggregate ids are equal iff their wrapped
     * values are equal.
     */
    static boolean equals(AggregateId a, AggregateId b) {
        return Objects.equals(a, b);
    }
}