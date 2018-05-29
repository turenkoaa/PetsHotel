package com.aaturenko.pethotel.entities;

import com.aaturenko.pethotel.dao.DataMapper;
import com.aaturenko.pethotel.dao.DataMapperRegistry;
import com.aaturenko.pethotel.dao.mapper.ResponseMapper;
import com.aaturenko.pethotel.dto.ResponseDto;
import com.aaturenko.pethotel.enums.ResponseStatus;
import com.aaturenko.pethotel.strategies.UpdateResponseStatusStrategy;
import lombok.EqualsAndHashCode;

import java.util.List;

import static com.aaturenko.pethotel.enums.ResponseStatus.PROPOSED;
import static com.aaturenko.pethotel.enums.ResponseStatus.REJECTED;


@EqualsAndHashCode(callSuper = true)
public class Response extends Entity {

    private User user;
    private Request request;
    private ResponseStatus status;
    private String details;
    private int cost;

    private UpdateResponseStatusStrategy statusStrategy = new ValidUpdateResponseStatusStrategy();
    private static ResponseMapper responseMapper = (ResponseMapper) DataMapperRegistry.getMapper(Response.class);

    public Response(long id, User user, Request request, ResponseStatus status, String details, int cost) {
        super(id);
        this.user = user;
        this.request = request;
        this.status = status;
        this.details = details;
        this.cost = cost;
    }

    public void changeStatus(ResponseStatus status) {
        if (!status.equals(this.status)) {
            setStatus(status);
            save();
        }
    }

    public void reject() {
        statusStrategy.reject();
    }

    public Request getRequest() {
        return request;
    }

    public Request request() {
        if (request == null)
            request = Request.findByResponse(this);
        return request;
    }

    public static Response newResponse(ResponseDto responseDto, User user) {
        Response response = Response.builder()
                .id(0)
                .request(Request.find(responseDto.getRequestId()))
                .details(responseDto.getDetails())
                .status(PROPOSED)
                .user(user)
                .cost(responseDto.getCost())
                .build();
        return (Response) responseMapper.save(response);
    }

    public static List<Response> findAllByUser(User user) {
        List<Response> responses = responseMapper.findAllByUser(user);
        responses.forEach(r -> r.request());
        return responses;
    }

    public static List<Response> findByRequest(Request request) {
        List<Response> responses = responseMapper.findAllByRequest(request);
        return responses;
    }

    public static Response find(long id) {
        return (Response) responseMapper.findById(id);
    }

    public static ResponseBuilder builder() {
        return new ResponseBuilder();
    }

    @Override
    public DataMapper dataMapper() {
        return responseMapper;
    }

    public static class ResponseBuilder {
        private long id;
        private User user;
        private Request request;
        private ResponseStatus status;
        private String details;
        private int cost;

        ResponseBuilder() {
        }

        public ResponseBuilder user(User user) {
            this.user = user;
            return this;
        }

        public ResponseBuilder request(Request request) {
            this.request = request;
            return this;
        }

        public ResponseBuilder status(ResponseStatus status) {
            this.status = status;
            return this;
        }

        public ResponseBuilder details(String details) {
            this.details = details;
            return this;
        }

        public ResponseBuilder cost(int cost) {
            this.cost = cost;
            return this;
        }

        public ResponseBuilder id(long id) {
            this.id = id;
            return this;
        }

        public Response build() {
            return new Response(id, user, request, status, details, cost);
        }
    }

    public class ValidUpdateResponseStatusStrategy implements UpdateResponseStatusStrategy {

        @Override
        public void reject() {
            request().setNewStatus();
            changeStatus(REJECTED);
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
