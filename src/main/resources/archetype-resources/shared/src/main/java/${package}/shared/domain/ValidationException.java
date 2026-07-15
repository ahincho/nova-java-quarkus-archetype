package ${package}.shared.domain;

import java.io.Serial;

/**
 * Thrown when an invariant of a value object or use case is violated.
 * Maps to HTTP 400 via the REST exception mapper.
 */
public class ValidationException extends DomainException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ValidationException(String message) {
        super(ErrorCode.VALIDATION, message);
    }
}