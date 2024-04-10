package io.github.z0r3f.pet.service;

import io.github.z0r3f.pet.domain.Pet;
import io.github.z0r3f.pet.persistence.PetRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class PetService {

    private final PetRepository petRepository;

    public void create(Pet p) {
        petRepository.save(p).subscribe();
    }

    public Mono<Pet> findById(String id) {
        return petRepository.findById(id);
    }

    public Mono<Pet> update(Pet p) {
        return petRepository.save(p);
    }

    public Mono<Void> delete(String id) {
        return petRepository.deleteById(id);
    }

    public Flux<Pet> findAll() {
        return petRepository.findAllWithDelay();
    }

    public Flux<Pet> findByName(String name) {
        return petRepository.findByName(name);
    }
}
