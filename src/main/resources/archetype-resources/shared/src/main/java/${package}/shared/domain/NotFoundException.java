package ${package}.shared.domain;

import java.io.Serial;

/**
 * Thrown when an aggregate is not found in its repository.
 * Maps to HTTP 404 via the REST exception mapper.
 */
public class NotFoundException extends DomainException {

    @Serial
    private static final long serialVersionUID = 1L;

    public NotFoundException(String message) {
        super(ErrorCode.NOT_FOUND, message);
    }
}