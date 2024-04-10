package io.github.z0r3f.pet.controller;

import io.github.z0r3f.pet.domain.Pet;
import io.github.z0r3f.pet.service.PetService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

@AllArgsConstructor
@RequestMapping("/api/v1/pet")
@RestController
@Slf4j
public class PetController {

    private final PetService petService;

    @PostMapping
    public void create(
            @RequestBody Pet pet
    ) {
        petService.create(pet);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mono<Pet>> findById(
            @PathVariable String id
    ) {
        log.info("Request to find pet by id: {}", id);
        var pet = petService.findById(id);
        HttpStatus status = pet != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(pet, status);
    }

    @PutMapping
    public Mono<Pet> update(
            @RequestBody Pet pet
    ) {
        return petService.update(pet);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(
            @PathVariable String id
    ) {
        return petService.delete(id);
    }

    @GetMapping(produces = TEXT_EVENT_STREAM_VALUE)
    public Flux<Pet> findAll() {
        return petService.findAll();
    }

    @GetMapping(value = "/name/{name}", produces = TEXT_EVENT_STREAM_VALUE)
    public Flux<Pet> findByName(
            @PathVariable String name
    ) {
        return petService.findByName(name);
    }
}
