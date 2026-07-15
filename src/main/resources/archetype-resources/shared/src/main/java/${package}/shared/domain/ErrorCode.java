package ${package}.shared.domain;

/**
 * Stable, well-known identifiers for every domain-level failure mode.
 * The Quarkus {@code boot} module maps each code to an HTTP status:
 * <ul>
 *   <li>{@link #VALIDATION} -> 400 Bad Request</li>
 *   <li>{@link #NOT_FOUND} -> 404 Not Found</li>
 *   <li>{@link #CONFLICT} -> 409 Conflict (e.g. duplicate aggregate)</li>
 * </ul>
 *
 * <p>Adding a new code requires adding a mapping in the REST
 * exception mapper of the {@code boot} module.
 */
public enum ErrorCode {
    VALIDATION,
    NOT_FOUND,
    CONFLICT
}