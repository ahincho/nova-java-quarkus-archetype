package ${package}.product.domain;

import java.util.List;
import java.util.Optional;

/**
 * Repository port for {@link Product}. Implemented by an adapter in the
 * {@code boot} module (or by an in-memory adapter in tests).
 *
 * <p>Defined in the {@code product} module (which depends on
 * {@code shared}, not on Quarkus). The interface uses only
 * {@link Optional} and {@link List} from the JDK so it stays portable
 * across frameworks.
 */
public interface ProductRepository {

    Optional<Product> findById(ProductId id);

    List<Product> findAll();

    void save(Product product);

    void delete(ProductId id);
}