package com.aaturenko.pethotel.entities;

import com.aaturenko.pethotel.dto.ResponseDto;
import lombok.Data;

import java.util.List;

@Data
public class Sitter extends User {

    private List<Response> responses;

    public Sitter(long id, String firstName, String lastName, String email, Boolean active, String address, List<Response> responses) {
        super(id, firstName, lastName, email, active, address);
        this.responses = responses;
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

    public void addResponse(ResponseDto responseDto){
        responses.add(Response.newResponse(responseDto));
    }


}
