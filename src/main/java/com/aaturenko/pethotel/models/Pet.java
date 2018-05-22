package com.aaturenko.pethotel.models;


import com.aaturenko.pethotel.enums.PetType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pet{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pet_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PetType petType;

    @NotNull
    private String name;

    @NotNull
    private Integer age;

    @NotNull
    private String passport;

    public static PetBuilder builder() {
        return new PetBuilder();
    }

    public static class PetBuilder {
        private Long id;
        private User owner;
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

        public PetBuilder owner(User owner) {
            this.owner = owner;
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
            return new Pet(id, owner, petType, name, age, passport);
        }
    }
}
