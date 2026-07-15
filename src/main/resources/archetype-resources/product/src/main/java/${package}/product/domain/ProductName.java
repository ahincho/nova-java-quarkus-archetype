package ${package}.product.domain;

import ${package}.shared.domain.ValidationException;

import java.util.Objects;

/**
 * Example value object: a non-empty product name with a maximum length.
 */
public final class ProductName {

    public static final int MAX_LENGTH = 120;

    private final String value;

    private ProductName(String value) {
        this.value = value;
    }

    public static ProductName of(String value) {
        if (value == null || value.isBlank()) {
            throw new ValidationException("ProductName must not be blank");
        }
        if (value.length() > MAX_LENGTH) {
            throw new ValidationException(
                    "ProductName must be at most " + MAX_LENGTH + " characters");
        }
        return new ProductName(value.trim());
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductName other)) return false;
        return value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}