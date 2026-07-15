package ${package}.boot.resource;

import ${package}.shared.domain.DomainException;
import ${package}.shared.domain.ErrorCode;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Map;

/**
 * Maps every {@link DomainException} (and its subclasses) to an RFC
 * 7807 problem-details JSON body with the appropriate HTTP status.
 *
 * <p>Mapping:
 * <ul>
 *   <li>{@link ErrorCode#VALIDATION} -> 400 Bad Request</li>
 *   <li>{@link ErrorCode#NOT_FOUND} -> 404 Not Found</li>
 *   <li>{@link ErrorCode#CONFLICT} -> 409 Conflict</li>
 * </ul>
 *
 * <p>Logging is intentionally avoided here: the exception carries its
 * own {@link ErrorCode} and message, and Quarkus's access log will
 * record the HTTP status. Production deployments can swap this for a
 * mapper that also logs at WARN/ERROR with a correlation id.
 */
@Provider
public class DomainExceptionMapper implements ExceptionMapper<DomainException> {

    @Override
    public Response toResponse(DomainException exception) {
        Response.Status status = switch (exception.code()) {
            case VALIDATION -> Response.Status.BAD_REQUEST;
            case NOT_FOUND  -> Response.Status.NOT_FOUND;
            case CONFLICT   -> Response.Status.CONFLICT;
        };

        Map<String, Object> body = Map.of(
                "type", "about:blank",
                "title", exception.code().name(),
                "status", status.getStatusCode(),
                "detail", exception.getMessage());

        return Response.status(status)
                .type(MediaType.APPLICATION_JSON)
                .entity(body)
                .build();
    }
}