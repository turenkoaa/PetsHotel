package com.aaturenko.pethotel.entities;

import com.aaturenko.pethotel.dao.DataMapperRegistry;
import com.aaturenko.pethotel.dao.mapper.SitterMapper;
import com.aaturenko.pethotel.dto.ResponseDto;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
public class Sitter extends User {

    private List<Response> responses;

    private static SitterMapper userMapper = (SitterMapper) DataMapperRegistry.getMapper(Sitter.class);

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
        responses().forEach(Response::reject);
    }

    public List<Response> responses() {
        if (responses == null)
            responses = Response.findAllByUser(this);
        return responses;
    }

    public static Sitter find(long id) {
        return (Sitter) userMapper.findById(id);
    }

    public Response addResponse(ResponseDto responseDto){
        Response response = Response.newResponse(responseDto, this);
        responses().add(response);
        return response;
    }

    public List<Request> findNewRequests() {
        return Request.findNewRequests(this.id);
    }
}
