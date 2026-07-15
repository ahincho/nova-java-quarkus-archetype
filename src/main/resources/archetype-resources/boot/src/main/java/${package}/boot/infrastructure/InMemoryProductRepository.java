package ${package}.boot.infrastructure;

import ${package}.product.domain.Product;
import ${package}.product.domain.ProductId;
import ${package}.product.domain.ProductRepository;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory adapter for {@link ProductRepository}. Used in the
 * archetype skeleton so the generated project boots and serves REST
 * traffic without any external dependency (database, message broker,
 * etc.). Production deployments replace this with a Panache /
 * Hibernate ORM / JDBC adapter in the same module.
 *
 * <p>The store is {@code @ApplicationScoped} (single instance per
 * Quarkus application) and backed by a {@link ConcurrentHashMap} for
 * thread-safe reads/writes.
 */
@ApplicationScoped
public class InMemoryProductRepository implements ProductRepository {

    private final ConcurrentHashMap<ProductId, Product> store = new ConcurrentHashMap<>();

    @Override
    public Optional<Product> findById(ProductId id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Product> findAll() {
        return Collections.unmodifiableList(new ArrayList<>(store.values()));
    }

    @Override
    public void save(Product product) {
        store.put(product.id(), product);
    }

    @Override
    public void delete(ProductId id) {
        store.remove(id);
    }
}