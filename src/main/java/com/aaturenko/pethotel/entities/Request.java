package com.aaturenko.pethotel.entities;

import com.aaturenko.pethotel.repositories.Registry;
import com.aaturenko.pethotel.dto.RequestDto;
import com.aaturenko.pethotel.enums.RequestStatus;
import com.aaturenko.pethotel.enums.ResponseStatus;
import com.aaturenko.pethotel.repositories.ResponseRepository;
import com.aaturenko.pethotel.strategies.UpdateRequestStatusStrategy;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

@Setter
@Getter
public class Request extends Entity {

    private LocalDate startDate;
    private LocalDate endDate;
    private RequestStatus status;
    private Pet pet;
    private int cost;
    private User user;
    private List<Response> responses;

    private UpdateRequestStatusStrategy statusStrategy = new ValidResponseUpdateRequestStatusStrategy();

    public Request(long id, LocalDate startDate, LocalDate endDate, RequestStatus status, Pet pet, int cost) {
        super(id);
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.pet = pet;
        this.cost = cost;
    }

    private void changeStatus(RequestStatus status) {
        if (status.equals(this.status)) {
            setStatus(status);
            update();
        }
    }

    public Request annuled() {
        statusStrategy.anulledRequest();
        return this;
    }

    public Request rejectResponses() {
        statusStrategy.rejectResponses();
        return this;
    }

    public Request acceptResponse(Long responseId) {
        statusStrategy.acceptResponse(responseId);
        return this;
    }

    public Request setNewStatus() {
        statusStrategy.setNewStatus();
        return this;
    }

    public List<Response> getResponses() {
        if (responses == null)
            responses = Response.findByRequest(this);
        return responses;
    }

    public static Request newRequest(RequestDto requestDto, User user) {
        if (requestDto.getStartDate().compareTo(requestDto.getEndDate()) > 0)
            throw new IllegalArgumentException("Start date must be less then end date.");

        Request request = mapFromDto.apply(requestDto);
        request.setUser(user);
        return Registry.requestRepository.save(request);
    }

    public static Request findByResponse(Response response) {
        return Registry.requestRepository.findByResponse(response);
    }

    public static List<Request> findAllByUser(User user) {
        return Registry.requestRepository.findAllByUser(user);
    }

    public static List<Request> findNewRequests() {
        return Registry.requestRepository.findNewRequests();
    }

    public static RequestBuilder builder() {
        return new RequestBuilder();
    }

    public static Request find(long requestId) {
        return (Request) Registry.requestRepository.findById(requestId);
    }

    public static class RequestBuilder {
        private long id;
        private LocalDate startDate;
        private LocalDate endDate;
        private RequestStatus status;
        private Pet pet;
        private int cost;

        RequestBuilder() {
        }

        public RequestBuilder id(long id) {
            this.id = id;
            return this;
        }

        public RequestBuilder startDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public RequestBuilder endDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public RequestBuilder status(RequestStatus status) {
            this.status = status;
            return this;
        }

        public RequestBuilder pet(Pet pet) {
            this.pet = pet;
            return this;
        }

        public RequestBuilder cost(int cost) {
            this.cost = cost;
            return this;
        }

        public Request build() {
            return new Request(id, startDate, endDate, status, pet, cost);
        }
    }

    public class ValidResponseUpdateRequestStatusStrategy implements UpdateRequestStatusStrategy {

        @Override
        public void acceptResponse(Long responseId) {
            changeStatus(RequestStatus.SOLVED);
            getResponses().forEach(r -> {
                        if (responseId.equals(r.getId())) r.changeStatus(ResponseStatus.ACCEPTED);
                        else r.changeStatus(ResponseStatus.REJECTED);
                    });
        }

        @Override
        public void rejectResponses() {
            changeStatus(RequestStatus.NEW);
            getResponses().forEach(r -> r.changeStatus(ResponseStatus.REJECTED));
        }

        @Override
        public void anulledRequest() {
            getResponses().forEach(r -> r.changeStatus(ResponseStatus.REJECTED));
            changeStatus(RequestStatus.ANULLED);
        }

        @Override
        public void setNewStatus() {
            changeStatus(RequestStatus.NEW);
            getResponses().forEach(r -> r.changeStatus(ResponseStatus.PROPOSED));
        }
    }

    private static Function<RequestDto, Request> mapFromDto =
            dto -> Request.builder()
                    .startDate(dto.getStartDate())
                    .endDate(dto.getEndDate())
                    .status(RequestStatus.NEW)
                    .cost(dto.getCost())
            .build();
}
