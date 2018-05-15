package com.aaturenko.pethotel.services;

import com.aaturenko.pethotel.models.Request;
import com.aaturenko.pethotel.models.Response;

public interface ManageStatusesService {
    void manageStatusesForResponseRemoving(Response response);
    void manageStatusesForRequestRemoving(Request request);
    void acceptResponseById(long responseId);
    void rejectAllResponsesForRequestId(long requestId);
    void anulledRequestByIdAndRejectItsResponses(long requestId);
}
