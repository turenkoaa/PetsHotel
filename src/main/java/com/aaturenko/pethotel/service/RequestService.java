package com.aaturenko.pethotel.service;

import com.aaturenko.pethotel.dto.RequestDto;
import com.aaturenko.pethotel.model.Request;

import java.util.List;

public interface RequestService {
    List<Request> findNewRequestsForUser(long userId);
    List<Request> findAllRequestsOfUser(long userId);
    Request findById(long id);
    Request saveRequest(RequestDto requestDto);
    void deleteById(long id);
    void annuledById(long id);
    void rejectAllResponsesById(long id);
    void acceptResponse(long id, long responseId);
    void confirmPayment(long id);
}
