package com.aaturenko.pethotel.models;

import com.aaturenko.pethotel.enums.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Response {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "response_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "request_request_id", nullable = false)
    private Request request;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ResponseStatus status;

    private String details;


}
