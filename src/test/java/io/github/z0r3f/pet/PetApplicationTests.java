package io.github.z0r3f.pet;

import io.github.z0r3f.pet.domain.Pet;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureWebTestClient
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PetApplicationTests {

    @BeforeAll
    static void beforeAll() {
        MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest").withExposedPorts(27017);
        mongoDBContainer.start();
        System.setProperty("spring.data.mongodb.uri", mongoDBContainer.getReplicaSetUrl());
    }

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Order(1)
    void post_shouldCreteRex_whenItIsCalled() {
        var pet = """
                {
                  "name": "Rex",
                  "species": "Dog",
                  "breed": "Labrador",
                  "color": "Black"
                }
                """;

        webTestClient.post()
                .uri("/api/v1/pet")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(pet)
                .exchange()
                .expectStatus().isOk();

    }

    @Test
    @Order(2)
    void get_shouldReturnRex_whenItIsCalled() {
        webTestClient.get()
                .uri("/api/v1/pet/name/Rex")
                .exchange()
                .expectStatus().isOk()
                .returnResult(Pet.class)
                .getResponseBody()
                .next()
                .as(StepVerifier::create)
                .consumeNextWith(pet -> Assertions.assertEquals("Rex", pet.getName()))
                .verifyComplete();
    }

    @Test
    @Order(3)
    void post_shouldCreteMia_whenItIsCalled() {
        var pet = """
                {
                  "name": "Mia",
                  "species": "Cat",
                  "breed": "Siamese",
                  "color": "White"
                }
                """;

        webTestClient.post()
                .uri("/api/v1/pet")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(pet)
                .exchange()
                .expectStatus().isOk();

    }

    @Test
    @Order(4)
    void get_shouldReturnMia_whenItIsCalled() {
        webTestClient.get()
                .uri("/api/v1/pet/name/Mia")
                .exchange()
                .expectStatus().isOk()
                .returnResult(Pet.class)
                .getResponseBody()
                .next()
                .as(StepVerifier::create)
                .consumeNextWith(pet -> Assertions.assertEquals("Mia", pet.getName()))
                .verifyComplete();
    }

    @Test
    @Order(5)
    void get_shouldReturnRexAndMia_whenItIsCalled() {
        List<Pet> pets = webTestClient.get()
                .uri("/api/v1/pet")
                .exchange()
                .expectStatus().isOk()
                .returnResult(Pet.class)
                .getResponseBody()
                .collectList()
                .block();

        Assertions.assertNotNull(pets);
        Assertions.assertEquals(2, pets.size());

        assertTrue(allNamesContained(pets, List.of("Rex", "Mia")));
    }

    private boolean allNamesContained(List<Pet> pets, List<String> names) {
        return pets.stream()
                .map(Pet::getName)
                .collect(Collectors.toSet())
                .containsAll(names);
    }

}
