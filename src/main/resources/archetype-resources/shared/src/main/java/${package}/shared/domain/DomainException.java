package ${package}.shared.domain;

import java.io.Serial;

/**
 * Base class for all domain-level exceptions. Bounded contexts extend
 * this class with their own typed subclasses (e.g. {@code ProductNotFoundException}
 * in the {@code product} module).
 *
 * <p>Carries an {@link ErrorCode} so the REST layer can map domain
 * errors to HTTP responses (RFC 7807 problem details) without coupling
 * the domain to HTTP. The Quarkus {@code boot} module supplies an
 * exception mapper that consumes this base class.
 */
public abstract class DomainException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final ErrorCode code;

    protected DomainException(ErrorCode code, String message) {
        super(message);
        this.code = code;
    }

    protected DomainException(ErrorCode code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public ErrorCode code() {
        return code;
    }
}