package com.aaturenko.pethotel.entities;

import com.aaturenko.pethotel.dto.ResponseDto;
import com.aaturenko.pethotel.repositories.RequestRepository;
import com.aaturenko.pethotel.repositories.ResponseRepository;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class Sitter extends User {

    private List<Response> responses;

    public Sitter(long id, String firstName, String lastName, String email, Boolean active, String address, List<Response> responses) {
        super(id, firstName, lastName, email, active, address);
        this.responses = responses;
    }

    public static SitterBuilder builder() {
        return new SitterBuilder();
    }

    public static class SitterBuilder extends UserBuilder{

        private List<Response> responses;

        @Override
        public Sitter build() {
            return new Sitter(id, firstName, lastName, email, active, address, responses);
        }

        public void setResponses(List<Response> responses) {
            this.responses = responses;
        }
    }

    @Override
    public void block() {
        changeStatus(false);
        responses.forEach(Response::reject);
    }

    public List<Response> getResponses() {
        if (responses == null)
            responses = Response.findAllByUser(this);
        return responses;
    }

    public void addResponse(ResponseDto responseDto){
        responses.add(Response.newResponse(responseDto, this));
    }

    public List<Request> findNewRequests() {
        return Request.findNewRequests();
    }
}
