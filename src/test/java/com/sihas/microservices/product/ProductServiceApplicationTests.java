package com.sihas.microservices.product;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MongoDBContainer;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {
    @ServiceConnection
    static MongoDBContainer mongoDbContainer = new MongoDBContainer("mongo:7.0.5");
    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    static {
        mongoDbContainer.start();
    }

    @Test
    void shouldCreateProduct() {
        String requestBody = """
                		{
                		    "id": "679f99259affe55ef005a68e",
                		    "name": "Smartwatch",
                		    "description": "Feature-packed smartwatch with fitness tracking and heart rate monitoring.",
                		    "price": 199.99
                		}
                """;

        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/product")
                .then()
                .statusCode(201)
                .body("id", Matchers.notNullValue())
                .body("name", Matchers.equalTo("Smartwatch"))
                .body("description", Matchers.equalTo("Feature-packed smartwatch with fitness tracking and heart rate monitoring.")).body("price", Matchers.equalTo(199.99));
    }

}
