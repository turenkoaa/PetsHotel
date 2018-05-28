package com.aaturenko.pethotel.strategies;

import com.aaturenko.pethotel.entities.Request;

public interface UpdateRequestStatusStrategy {
    void acceptResponse(Long responseId);
    void rejectResponses();
    void anulledRequest();

    void setNewStatus();
}
