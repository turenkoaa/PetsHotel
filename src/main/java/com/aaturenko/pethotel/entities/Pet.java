package com.aaturenko.pethotel.entities;

import com.aaturenko.pethotel.dto.PetDto;
import com.aaturenko.pethotel.enums.PetType;
import com.aaturenko.pethotel.repositories.Registry;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Data
@EqualsAndHashCode(callSuper = true)
public class Pet extends Entity {

    private PetType petType;
    private String name;
    private Integer age;
    private String passport;
    private User user;

    public Pet(Long id, PetType petType, String name, Integer age, String passport, User user) {
        super(id);
        this.petType = petType;
        this.name = name;
        this.age = age;
        this.passport = passport;
        this.user = user;
    }

    public static Pet newPet(PetDto petDto, User user) {
        Pet pet = Pet.builder()
                .id(0)
                .petType(PetType.valueOf(petDto.getPetType()))
                .name(petDto.getName())
                .age(petDto.getAge())
                .passport(petDto.getPassport())
                .user(user)
                .build();
        return Registry.petRepository.save(pet);
    }

    public static List<Pet> findAllByUser(User user) {
        return Registry.petRepository.findAllByUser(user);
    }

    public static PetBuilder builder() {
        return new PetBuilder();
    }

    public static class PetBuilder {
        private long id;
        private PetType petType;
        private String name;
        private Integer age;
        private String passport;
        private User user;

        PetBuilder() {
        }

        public PetBuilder id(long id) {
            this.id = id;
            return this;
        }

        public PetBuilder petType(PetType petType) {
            this.petType = petType;
            return this;
        }

        public PetBuilder name(String name) {
            this.name = name;
            return this;
        }

        public PetBuilder age(Integer age) {
            this.age = age;
            return this;
        }

        public PetBuilder passport(String passport) {
            this.passport = passport;
            return this;
        }

        public PetBuilder user(User user) {
            this.user = user;
            return this;
        }

        public Pet build() {
            return new Pet(id, petType, name, age, passport, user);
        }
    }
}
