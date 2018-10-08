package com.aaturenko.pethotel.service;

import com.aaturenko.pethotel.model.Request;
import com.aaturenko.pethotel.model.Response;

public interface UpdateRequestAnsResponseStatusStrategy {
    void acceptResponse(Request request, Long responseId);
    void rejectResponses(Request request);
    void anulledRequest(Request request);
    void setNewStatus(Request request);
    void rejectResponse(Response response);
}
