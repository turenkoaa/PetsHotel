package com.aaturenko.pethotel.entities;

import com.aaturenko.pethotel.dto.RequestDto;
import com.aaturenko.pethotel.enums.RequestStatus;
import com.aaturenko.pethotel.enums.ResponseStatus;
import com.aaturenko.pethotel.strategies.UpdateRequestStatusStrategy;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

@Data
@Builder
public class Request extends Entity {

    private long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private RequestStatus status;
    private int cost;
    private List<Response> responses;

    private UpdateRequestStatusStrategy statusStrategy = new ValidResponseUpdateRequestStatusStrategy();

    public static Request newRequest(RequestDto requestDto, User user) {
        return null;
    }

    public void changeStatus(RequestStatus status){
        if (status.equals(this.status)) {
            setStatus(status);
            update();
        }
    }

    public void annuled() {
        statusStrategy.anulledRequest();
    }

    public void rejectResponses() {
        statusStrategy.rejectResponses();
    }

    public void acceptResponse(Long responseId) {
        statusStrategy.acceptResponse(responseId);
    }

    public class ValidResponseUpdateRequestStatusStrategy implements UpdateRequestStatusStrategy {

        @Override
        public void acceptResponse(Long responseId) {
            changeStatus(RequestStatus.SOLVED);
            responses.forEach(r -> {
                        if (responseId.equals(r.getId())) r.changeStatus(ResponseStatus.ACCEPTED);
                        else r.changeStatus(ResponseStatus.REJECTED);
                    });
        }

        @Override
        public void rejectResponses() {
            changeStatus(RequestStatus.NEW);
            responses.forEach(r -> r.changeStatus(ResponseStatus.REJECTED));
        }

        @Override
        public void anulledRequest() {
            responses.forEach(r -> r.changeStatus(ResponseStatus.REJECTED));
            changeStatus(RequestStatus.ANULLED);
        }
    }

    private Function<RequestDto, Request> mapFromDto =
            dto -> Request.builder()
                    .startDate(dto.getStartDate())
                    .endDate(dto.getEndDate())
                    .status(RequestStatus.NEW)
                    .cost(dto.getCost())
                    .build();
}
