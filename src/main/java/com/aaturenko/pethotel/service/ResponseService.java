package com.aaturenko.pethotel.service;

import com.aaturenko.pethotel.dto.ResponseDto;
import com.aaturenko.pethotel.model.Response;

import java.util.List;

public interface ResponseService {
    List<Response> findForRequest(long requestId);
    List<Response> findForUser(long authorId);
    Response findById(long id);
    Response save(ResponseDto responseDto);
    void deleteById(long id);
}
