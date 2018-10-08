package com.aaturenko.pethotel.service.impl;

import com.aaturenko.pethotel.enums.RequestStatus;
import com.aaturenko.pethotel.enums.ResponseStatus;
import com.aaturenko.pethotel.model.Request;
import com.aaturenko.pethotel.model.Response;
import com.aaturenko.pethotel.service.UpdateRequestAnsResponseStatusStrategy;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ValidResponseUpdateRequestAnsResponseStatusStrategy implements UpdateRequestAnsResponseStatusStrategy {

    private void changeStatus(Request request, RequestStatus status) {
        if (status != request.getStatus()) {
            request.setStatus(status);
        }
    }

    private void changeStatus(Response response, ResponseStatus status) {
        if (status != response.getStatus()) {
            response.setStatus(status);
        }
    }

    @Override
    @Transactional
    public void acceptResponse(Request request, Long responseId) {
        changeStatus(request, RequestStatus.SOLVED);
        request.getResponses().forEach(r -> {
            if (responseId.equals(r.getId())) {
                changeStatus(r, ResponseStatus.ACCEPTED);
            } else {
                changeStatus(r, ResponseStatus.REJECTED);
            }
        });
    }

    @Override
    public void rejectResponses(Request request) {
        changeStatus(request, RequestStatus.NEW);
        request.getResponses().forEach(r -> changeStatus(r, ResponseStatus.REJECTED));
    }

    @Override
    public void anulledRequest(Request request) {
        request.getResponses().forEach(r -> changeStatus(r, ResponseStatus.REJECTED));
        changeStatus(request, RequestStatus.ANULLED);
    }

    @Override
    public void setNewStatus(Request request) {
        changeStatus(request, RequestStatus.NEW);
        request.getResponses().forEach(r -> changeStatus(r, ResponseStatus.PROPOSED));
    }

    @Override
    public void rejectResponse(Response response) {
//        anulledRequest(response.getRequest());
        changeStatus(response, ResponseStatus.REJECTED);
    }

}
