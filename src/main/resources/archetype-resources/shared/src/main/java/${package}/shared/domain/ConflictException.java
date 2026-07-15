package ${package}.shared.domain;

import java.io.Serial;

/**
 * Thrown when an operation would violate a uniqueness or version
 * constraint (e.g. duplicate aggregate id, optimistic-lock collision).
 * Maps to HTTP 409 via the REST exception mapper.
 */
public class ConflictException extends DomainException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ConflictException(String message) {
        super(ErrorCode.CONFLICT, message);
    }
}