package ${package}.product.domain;

import ${package}.shared.domain.AggregateId;

import java.util.Objects;
import java.util.UUID;

/**
 * Typed identifier of a {@link Product} aggregate.
 *
 * <p>Wraps a {@link UUID}. Two ProductIds are equal iff their wrapped
 * UUIDs are equal.
 */
public final class ProductId implements AggregateId {

    private final UUID value;

    private ProductId(UUID value) {
        this.value = Objects.requireNonNull(value, "ProductId.value");
    }

    public static ProductId of(UUID value) {
        return new ProductId(value);
    }

    public static ProductId of(String value) {
        return new ProductId(UUID.fromString(value));
    }

    public static ProductId generate() {
        return new ProductId(UUID.randomUUID());
    }

    @Override
    public UUID value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductId other)) return false;
        return value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}