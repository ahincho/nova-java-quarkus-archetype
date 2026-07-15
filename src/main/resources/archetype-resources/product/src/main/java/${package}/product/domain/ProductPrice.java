package ${package}.product.domain;

import java.math.BigDecimal;
import java.util.Objects;
import ${package}.shared.domain.ValidationException;

/**
 * Example value object: a non-negative price with two decimal places.
 */
public final class ProductPrice {

    public static final ProductPrice ZERO = new ProductPrice(BigDecimal.ZERO);

    private final BigDecimal value;

    private ProductPrice(BigDecimal value) {
        this.value = value;
    }

    public static ProductPrice of(BigDecimal value) {
        Objects.requireNonNull(value, "ProductPrice.value");
        if (value.signum() < 0) {
            throw new ValidationException("ProductPrice must be >= 0");
        }
        if (value.scale() > 2) {
            throw new ValidationException("ProductPrice must have at most 2 decimal places");
        }
        return new ProductPrice(value);
    }

    public BigDecimal value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductPrice other)) return false;
        return value.compareTo(other.value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value.toPlainString();
    }
}