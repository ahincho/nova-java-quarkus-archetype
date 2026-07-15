package ${package}.product.application;

import ${package}.product.domain.Product;
import ${package}.product.domain.ProductId;
import ${package}.product.domain.ProductName;
import ${package}.product.domain.ProductPrice;
import ${package}.product.domain.ProductRepository;
import ${package}.shared.domain.ConflictException;
import ${package}.shared.domain.NotFoundException;
import ${package}.shared.domain.ValidationException;

import java.math.BigDecimal;

/**
 * Use case: create a new product.
 *
 * <p>Domain invariants:
 * <ul>
 *   <li>The product's id must not already exist (ConflictException).</li>
 *   <li>The price must be non-negative (ValidationException via ProductPrice).</li>
 *   <li>The name must be non-blank and at most {@value ProductName#MAX_LENGTH}
 *       characters (ValidationException via ProductName).</li>
 * </ul>
 *
 * <p>Pure Java: zero Quarkus/CDI imports. The {@code boot} module wires
 * the concrete {@link ProductRepository} via CDI.
 */
public final class CreateProductUseCase {

    private final ProductRepository repository;

    public CreateProductUseCase(ProductRepository repository) {
        this.repository = repository;
    }

    public Product execute(CreateProductCommand command) {
        if (command == null) {
            throw new ValidationException("command must not be null");
        }
        if (repository.findById(command.id()).isPresent()) {
            throw new ConflictException(
                    "Product with id " + command.id() + " already exists");
        }
        Product product = Product.create(
                command.id(),
                ProductName.of(command.name()),
                ProductPrice.of(command.price()));
        repository.save(product);
        return product;
    }

    /**
     * Input data for {@link CreateProductUseCase}. A plain record;
     * intentionally not a Java record because use-case commands
     * typically evolve to carry richer metadata (audit, idempotency
     * keys, correlation ids) that don't fit a record signature.
     */
    public static final class CreateProductCommand {
        private final ProductId id;
        private final String name;
        private final BigDecimal price;

        public CreateProductCommand(ProductId id, String name, BigDecimal price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }

        public ProductId id() { return id; }
        public String name() { return name; }
        public BigDecimal price() { return price; }
    }

    /**
     * Helper for the REST layer: looks up a product by id or throws
     * {@link NotFoundException} if it doesn't exist.
     */
    public static Product requireById(ProductRepository repository, ProductId id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        "Product with id " + id + " not found"));
    }
}