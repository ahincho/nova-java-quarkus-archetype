package ${package}.boot;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

/**
 * Smoke test: boots Quarkus on the test port and exercises the full
 * ProductResource HTTP surface. Verifies that the multi-module
 * archetype (shared + product + boot) wires correctly and that
 * CDI discovers {@code InMemoryProductRepository} as the
 * {@link ${package}.product.domain.ProductRepository} port.
 */
@QuarkusTest
class ApplicationTest {

    @Test
    void listIsInitiallyEmpty() {
        given()
                .when().get("/api/products")
                .then()
                .statusCode(200)
                .body("size()", is(0));
    }

    @Test
    void createThenListReturnsTheNewProduct() {
        // Create
        String newId = given()
                .contentType("application/json")
                .body("{\"name\":\"Nova Notebook\",\"price\":\"12.50\"}")
                .when().post("/api/products")
                .then()
                .statusCode(201)
                .body("name", is("Nova Notebook"))
                .body("price", is("12.50"))
                .extract().path("id");

        // List should now have at least one product
        given()
                .when().get("/api/products")
                .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(1));

        // Find by id
        given()
                .when().get("/api/products/" + newId)
                .then()
                .statusCode(200)
                .body("id", is(newId))
                .body("name", is("Nova Notebook"));
    }

    @Test
    void validationErrorReturns400() {
        given()
                .contentType("application/json")
                .body("{\"name\":\"\",\"price\":\"1.00\"}")
                .when().post("/api/products")
                .then()
                .statusCode(400)
                .body("title", is("VALIDATION"));
    }

    @Test
    void notFoundReturns404() {
        given()
                .when().get("/api/products/00000000-0000-0000-0000-000000000000")
                .then()
                .statusCode(404)
                .body("title", is("NOT_FOUND"));
    }
}