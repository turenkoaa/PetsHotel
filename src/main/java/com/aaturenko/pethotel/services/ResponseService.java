package com.aaturenko.pethotel.services;

import com.aaturenko.pethotel.dto.ResponseDto;
import com.aaturenko.pethotel.models.Response;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ResponseService {
    Response createResponse(ResponseDto responseDto);
    Response findResponseById(long id);
    List<Response> findAllResponsesByUserId(long userId, int page, int size);
    List<Response> findAllResponsesByRequestId(long requestId, int page, int size);
    void deleteResponseById(long responseId);
}
