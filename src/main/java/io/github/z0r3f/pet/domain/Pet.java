package io.github.z0r3f.pet.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Pet {
    @Id
    private String id;
    private String name;
    private String species;
    private String breed;
    private String color;
}
