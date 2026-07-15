package ${package}.boot;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

/**
 * Quarkus entry point. The {@code @QuarkusMain} annotation tells the
 * Quarkus Maven plugin to generate a small native launcher class
 * ({@code ${package}.boot.Application}) that delegates to
 * {@link Quarkus#run(String...)} at JVM startup.
 */
@QuarkusMain
public class Application {

    public static void main(String[] args) {
        Quarkus.run(args);
    }
}