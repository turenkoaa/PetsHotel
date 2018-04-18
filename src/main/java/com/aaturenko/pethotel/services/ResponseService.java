package com.aaturenko.pethotel.services;

import com.aaturenko.pethotel.dto.ResponseDto;
import com.aaturenko.pethotel.models.Response;

import java.util.List;

public interface ResponseService {
    Response findResponseById(long id);
    List<Response> findAllByUserId(long userId, int page, int size);
    List<Response> findAllResponsesByRequestId(long requestId, int page, int size);
    Response acceptResponseById(long responseId);
    Response rejectResponseById(long responseId);
    Response createResponse(ResponseDto responseDto);
    void rejectAllResponsesForRequestId(long requestId);
    void deleteResponseById(long responseId);
}
