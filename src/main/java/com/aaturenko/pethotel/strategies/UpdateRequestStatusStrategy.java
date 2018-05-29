package com.aaturenko.pethotel.strategies;

public interface UpdateRequestStatusStrategy {
    void acceptResponse(Long responseId);
    void rejectResponses();
    void anulledRequest();
    void setNewStatus();
}
