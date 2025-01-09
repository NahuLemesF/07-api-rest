package com.example.restaurant.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class SwaggerConfigTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testSwaggerApiDocsEndpoint() {
        webTestClient.get()
                .uri("/v3/api-docs")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType("application/json")
                .expectBody()
                .jsonPath("$.info.title").isEqualTo("Restaurant Management API")
                .jsonPath("$.info.version").isEqualTo("1.0")
                .jsonPath("$.info.description").isEqualTo("This API handles restaurant operations, including menu management, orders, and customer management.");
    }


    @Test
    void testSwaggerUiEndpoint() {
        webTestClient.get()
                .uri("/swagger-ui.html")
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueEquals("Location", "/api/v1/swagger-ui/index.html");
    }


    @Test
    void testSwaggerUiRedirect() {
        webTestClient.get()
                .uri("/swagger-ui/index.html")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType("text/html");
    }
}

