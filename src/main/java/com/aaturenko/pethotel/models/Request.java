package com.aaturenko.pethotel.models;

import com.aaturenko.pethotel.enums.RequestStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "request_id")
    private long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    @OneToMany(cascade = CascadeType.MERGE, orphanRemoval = true)
    @JoinTable(
            name="pet_request",
            joinColumns=@JoinColumn(name="request_id", referencedColumnName="request_id"),
            inverseJoinColumns=@JoinColumn(name="pet_id", referencedColumnName="pet_id", unique=true))
    private Set<Pet> pets = new HashSet<>();

    private int cost;

//    @JsonIgnore
//    @OneToMany(mappedBy = "request", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
//    private Set<Response> responses = new HashSet<>();

    public boolean addPet(Pet pet) {
        return pets.add(pet);
    }
}
