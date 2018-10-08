package com.aaturenko.pethotel.model;



import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Entity
public class User {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    protected long id;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected Boolean active;
    protected String address;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private Set<Response> responses = new HashSet<>();
}
