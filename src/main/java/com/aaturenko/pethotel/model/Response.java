package com.aaturenko.pethotel.model;

import com.aaturenko.pethotel.enums.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Response {
    @Id
    @GeneratedValue
    @Column(name = "response_id")
    protected long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id")
    private Request request;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ResponseStatus status;
    private String details;
    private int cost;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Response response = (Response) o;
        return id == response.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
