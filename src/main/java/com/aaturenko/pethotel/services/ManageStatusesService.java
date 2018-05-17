package com.aaturenko.pethotel.services;

import com.aaturenko.pethotel.models.Request;
import com.aaturenko.pethotel.models.Response;
import com.aaturenko.pethotel.models.User;
import org.springframework.transaction.annotation.Transactional;

public interface ManageStatusesService {
    void manageStatusesForResponseRemoving(Response response);
    void manageStatusesForRequestRemoving(Request request);
    void manageStatusesForUserRemoving(long userId);
    void manageStatusesForBlockedUserResponses(long userId);
    void manageStatusesForBlockedUserRequests(long userId);
    void acceptResponse(Response response);
    void rejectAllResponsesForRequest(Request request);
    void anulledRequestAndRejectItsResponses(Request request);
}

