package com.aaturenko.pethotel.old.entities;

import com.aaturenko.pethotel.old.dao.DataMapper;
import com.aaturenko.pethotel.old.dao.DataMapperRegistry;
import com.aaturenko.pethotel.old.dao.mapper.RequestMapper;
import com.aaturenko.pethotel.dto.RequestDto;
import com.aaturenko.pethotel.enums.RequestStatus;
import com.aaturenko.pethotel.enums.ResponseStatus;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

@EqualsAndHashCode(callSuper = true)
public class Request extends Entity {

    private LocalDate startDate;
    private LocalDate endDate;
    private RequestStatus status;
    private Pet pet;
    private int cost;
    private User user;
    private boolean paid;
    private List<Response> responses;
    private ValidResponseUpdateRequestStatusStrategy statusStrategy = new ValidResponseUpdateRequestStatusStrategy();
    private static RequestMapper requestMapper = (RequestMapper) DataMapperRegistry.getMapper(Request.class);

    public Request(long id, LocalDate startDate, LocalDate endDate, RequestStatus status, boolean paid, Pet pet, int cost) {
        super(id);
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.pet = pet;
        this.paid = paid;
        this.cost = cost;
    }

    @Override
    public DataMapper dataMapper() {
        return requestMapper;
    }

    private void changeStatus(RequestStatus status) {
        if (!status.equals(this.status)) {
            setStatus(status);
            save();
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

    public List<Response> responses() {
        if (responses == null)
            responses = Response.findByRequest(this);
        return responses;
    }

    public User getUser() {
        if (user == null)
            user = Owner.findByRequest(this);
        return user;
    }

    public static Request newRequest(RequestDto requestDto, User user) {
        if (requestDto.getStartDate().compareTo(requestDto.getEndDate()) > 0)
            throw new IllegalArgumentException("Start date must be less then end date.");

        Request request = mapFromDto.apply(requestDto);
        request.setUser(user);
        request.setPet(Pet.find(requestDto.getPetId()));
        return (Request) requestMapper.save(request);
    }

    public void paidConfirmed() {
        this.setPaid(true);
        save();
    }

    public static Request findByResponse(Response response) {
        return requestMapper.findByResponse(response);
    }

    public static List<Request> findAllByUser(User user) {
        return requestMapper.findAllByUser(user);
    }

    public static List<Request> findNewRequests(long userId) {
        return requestMapper.findAllByStatus(RequestStatus.NEW)
                .stream()
                .filter(r -> userId != r.getUser().getId())
                .distinct()
                .collect(toList());
    }

    public static Request find(long requestId) {
        return (Request) requestMapper.findById(requestId);
    }

    private static Function<RequestDto, Request> mapFromDto =
            dto -> Request.builder()
                    .startDate(dto.getStartDate())
                    .endDate(dto.getEndDate())
                    .status(RequestStatus.NEW)
                    .cost(dto.getCost())
                    .paid(false)
                    .build();

    public static RequestBuilder builder() {
        return new RequestBuilder();
    }

    public static class RequestBuilder {
        private long id;
        private LocalDate startDate;
        private LocalDate endDate;
        private RequestStatus status;
        private Pet pet;
        private int cost;
        private boolean paid;

        RequestBuilder() {
        }

        public RequestBuilder id(long id) {
            this.id = id;
            return this;
        }

        public RequestBuilder paid(boolean paid) {
            this.paid = paid;
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
            return new Request(id, startDate, endDate, status, paid, pet, cost);
        }

    }
    public class ValidResponseUpdateRequestStatusStrategy  {
        public void acceptResponse(Long responseId) {
            changeStatus(RequestStatus.SOLVED);
            responses().forEach(r -> {
                        if (responseId.equals(r.getId())) r.changeStatus(ResponseStatus.ACCEPTED);
                        else r.changeStatus(ResponseStatus.REJECTED);
                    });
        }
        public void rejectResponses() {
            changeStatus(RequestStatus.NEW);
            responses().forEach(r -> r.changeStatus(ResponseStatus.REJECTED));
        }

        public void anulledRequest() {
            responses().forEach(r -> r.changeStatus(ResponseStatus.REJECTED));
            changeStatus(RequestStatus.ANULLED);
        }
        public void setNewStatus() {
            changeStatus(RequestStatus.NEW);
            responses().forEach(r -> r.changeStatus(ResponseStatus.PROPOSED));
        }

    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
