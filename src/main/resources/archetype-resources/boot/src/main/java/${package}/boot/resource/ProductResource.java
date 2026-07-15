package ${package}.boot.resource;

import ${package}.product.application.CreateProductUseCase;
import ${package}.product.application.CreateProductUseCase.CreateProductCommand;
import ${package}.product.domain.ProductId;
import ${package}.product.domain.ProductRepository;
import ${package}.shared.domain.DomainException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * REST surface for the {@code product} bounded context. Thin
 * controller: parses the HTTP request, delegates to a use case, and
 * returns the result as JSON.
 *
 * <p>The class deliberately does NOT catch {@link DomainException} —
 * the exception mapper in {@link DomainExceptionMapper} converts every
 * domain error into the corresponding HTTP status with an RFC 7807
 * problem-details body.
 */
@ApplicationScoped
@Path("/api/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    private final ProductRepository repository;
    private final CreateProductUseCase createProduct;

    @Inject
    public ProductResource(ProductRepository repository) {
        this.repository = repository;
        this.createProduct = new CreateProductUseCase(repository);
    }

    @GET
    public List<Map<String, Object>> list() {
        return repository.findAll().stream()
                .map(ProductResource::toJson)
                .toList();
    }

    @GET
    @Path("/{id}")
    public Map<String, Object> findOne(@PathParam("id") String id) {
        Product product = CreateProductUseCase.requireById(
                repository, ProductId.of(id));
        return toJson(product);
    }

    @POST
    public Response create(CreateProductRequest request) {
        Product product = createProduct.execute(
                new CreateProductCommand(
                        ProductId.generate(),
                        request.name(),
                        request.price() == null ? BigDecimal.ZERO : request.price()));
        return Response.status(Response.Status.CREATED)
                .entity(toJson(product))
                .build();
    }

    /**
     * Request body for {@link #create(CreateProductRequest)}. Plain
     * record kept here (not in {@code shared} or {@code product}) so
     * the domain stays free of HTTP concerns.
     */
    public record CreateProductRequest(String name, BigDecimal price) { }

    private static Map<String, Object> toJson(Product product) {
        return Map.of(
                "id", product.id().toString(),
                "name", product.name().value(),
                "price", product.price().value().toPlainString());
    }
}