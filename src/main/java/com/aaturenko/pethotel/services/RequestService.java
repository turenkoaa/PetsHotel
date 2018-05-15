package com.aaturenko.pethotel.services;

import com.aaturenko.pethotel.dto.RequestDto;
import com.aaturenko.pethotel.models.Request;

import java.util.List;

public interface RequestService {
    Request createRequest(RequestDto request);
    Request findRequestById(long id);
    void deleteRequestById(long id);
    List<Request> findAllNewRequests(int page, int size);
    List<Request> findAllRequestsByAuthor(long userId, int page, int size);
}
