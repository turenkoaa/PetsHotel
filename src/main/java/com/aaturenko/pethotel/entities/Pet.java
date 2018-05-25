package com.aaturenko.pethotel.entities;

import com.aaturenko.pethotel.enums.PetType;

public class Pet extends Entity {

    private PetType petType;
    private String name;
    private Integer age;
    private String passport;

    public Pet() {}

    public Pet(Long id, PetType petType, String name, Integer age, String passport) {
        this.id = id;
        this.petType = petType;
        this.name = name;
        this.age = age;
        this.passport = passport;
    }

    public static PetBuilder builder() {
        return new PetBuilder();
    }

    public static class PetBuilder {
        private Long id;
        private PetType petType;
        private String name;
        private Integer age;
        private String passport;

        PetBuilder() {
        }

        public PetBuilder id(Long id) {
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

        public Pet build() {
            return new Pet(id, petType, name, age, passport);
        }
    }
}
