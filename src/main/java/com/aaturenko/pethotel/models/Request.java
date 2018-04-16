package com.aaturenko.pethotel.models;

import com.aaturenko.pethotel.enums.RequestStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
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
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Instant startDate;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Instant endDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    @JsonIgnore
    @OneToMany
    private Set<Pet> pets = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "request", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<Response> responses = new HashSet<>();

}
