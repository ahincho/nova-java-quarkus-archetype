package ${package}.product.domain;

import ${package}.shared.domain.NotFoundException;

import java.util.Objects;

/**
 * Example aggregate root. Created via the static factory
 * {@link #create(ProductId, ProductName, ProductPrice)}; mutated via
 * {@link #rename(ProductName)} and {@link #reprice(ProductPrice)}.
 *
 * <p>Pure Java: zero framework imports. Persisted via
 * {@link ProductRepository} (port, not adapter).
 */
public final class Product {

    private final ProductId id;
    private ProductName name;
    private ProductPrice price;

    private Product(ProductId id, ProductName name, ProductPrice price) {
        this.id = Objects.requireNonNull(id, "Product.id");
        this.name = Objects.requireNonNull(name, "Product.name");
        this.price = Objects.requireNonNull(price, "Product.price");
    }

    /**
     * Factory: creates a new product with the given identity.
     * Throws {@link NotFoundException} if any required field is null
     * (the boundary check is delegated to the value objects).
     */
    public static Product create(ProductId id, ProductName name, ProductPrice price) {
        return new Product(id, name, price);
    }

    /**
     * Reconstructs an existing product from persistence. Same
     * invariants as {@link #create}.
     */
    public static Product rehydrate(ProductId id, ProductName name, ProductPrice price) {
        return new Product(id, name, price);
    }

    public void rename(ProductName newName) {
        this.name = Objects.requireNonNull(newName, "Product.rename.newName");
    }

    public void reprice(ProductPrice newPrice) {
        this.price = Objects.requireNonNull(newPrice, "Product.reprice.newPrice");
    }

    public ProductId id() {
        return id;
    }

    public ProductName name() {
        return name;
    }

    public ProductPrice price() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product other)) return false;
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Product[id=" + id + ", name=" + name + ", price=" + price + "]";
    }
}