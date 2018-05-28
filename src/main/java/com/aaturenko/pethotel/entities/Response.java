package com.aaturenko.pethotel.entities;

import com.aaturenko.pethotel.repositories.Registry;
import com.aaturenko.pethotel.dto.ResponseDto;
import com.aaturenko.pethotel.enums.ResponseStatus;
import com.aaturenko.pethotel.strategies.UpdateResponseStatusStrategy;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import static com.aaturenko.pethotel.enums.ResponseStatus.PROPOSED;
import static com.aaturenko.pethotel.enums.ResponseStatus.REJECTED;

@Data
@EqualsAndHashCode(callSuper = true)
public class Response extends Entity {

    private User user;
    private Request request;
    private ResponseStatus status;
    private String details;
    private int cost;

    private UpdateResponseStatusStrategy statusStrategy = new ValidUpdateResponseStatusStrategy();

    public Response(long id, User user, Request request, ResponseStatus status, String details, int cost) {
        super(id);
        this.user = user;
        this.request = request;
        this.status = status;
        this.details = details;
        this.cost = cost;
    }

    public void changeStatus(ResponseStatus status) {
        if (status.equals(this.status)) {
            setStatus(status);
            update();
        }
    }

    public void reject() {
        statusStrategy.reject();
    }

    public Request getRequest() {
        if (request == null)
            request = Request.findByResponse(this);
        return request;
    }

    public static List<Response> findAllByRequest(Request request) {
        return Registry.responseRepository.findByRequest(request);
    }

    public static Response newResponse(ResponseDto responseDto, User user) {
        Response response = Response.builder()
                .id(0)
                .request(Request.find(responseDto.getRequestId()))
                .details(responseDto.getDetails())
                .status(PROPOSED)
                .cost(responseDto.getCost())
                .build();
        return Registry.responseRepository.save(response);
    }

    public static List<Response> findAllByUser(User user) {
        return Registry.responseRepository.findAllByUser(user);
    }

    public static List<Response> findByRequest(Request request) {
        return Registry.responseRepository.findByRequest(request);
    }

    public static ResponseBuilder builder() {
        return new ResponseBuilder();
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
            getRequest().setNewStatus();
            changeStatus(REJECTED);
        }
    }

}
