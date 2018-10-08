package com.aaturenko.pethotel.model;

import com.aaturenko.pethotel.enums.PetType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Pet {
    @Id
    @GeneratedValue
    @Column(name = "pet_id")
    protected long id;
    @NotNull
    @Enumerated(EnumType.STRING)
    private PetType petType;
    private String name;
    private Integer age;
    private String passport;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToMany(
            mappedBy = "pet",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private Set<Request> requests = new HashSet<>();

}
