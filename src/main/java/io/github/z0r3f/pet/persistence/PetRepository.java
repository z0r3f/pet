package io.github.z0r3f.pet.persistence;

import io.github.z0r3f.pet.domain.Pet;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface PetRepository extends ReactiveMongoRepository<Pet, String> {

    Flux<Pet> findByName(final String name);

//    default Flux<Pet> findAllWithDelay() {
//        return findAll().delayElements(ofSeconds(1));
//    }

    default Flux<Pet> findAllWithDelay() {
        return findAll();
    }

}
