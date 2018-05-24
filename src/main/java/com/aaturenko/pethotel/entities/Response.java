package com.aaturenko.pethotel.entities;

import com.aaturenko.pethotel.enums.RequestStatus;
import com.aaturenko.pethotel.enums.ResponseStatus;
import com.aaturenko.pethotel.strategies.UpdateResponseStatusStrategy;
import lombok.Builder;
import lombok.Data;

import static com.aaturenko.pethotel.enums.ResponseStatus.REJECTED;

@Builder
@Data
public class Response extends Entity {

    private long id;
    private User user;
    private Request request;
    private ResponseStatus status;
    private String details;
    private int cost;

    private UpdateResponseStatusStrategy statusStrategy = new ValidUpdateResponseStatusStrategy();

    public void changeStatus(ResponseStatus status) {
        if (status.equals(this.status)) {
            setStatus(status);
            update();
        }
    }

    public void reject() {
        statusStrategy.reject();
    }

    public class ValidUpdateResponseStatusStrategy implements UpdateResponseStatusStrategy {

        @Override
        public void reject() {
            if (ResponseStatus.ACCEPTED.equals(status)) {
                request.changeStatus(RequestStatus.NEW);
                request.getResponses().forEach(r -> r.changeStatus(ResponseStatus.PROPOSED));
            }
            changeStatus(REJECTED);
        }
    }

}
