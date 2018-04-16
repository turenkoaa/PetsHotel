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

}
