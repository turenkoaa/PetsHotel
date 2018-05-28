package com.aaturenko.pethotel.repositories;

import com.aaturenko.pethotel.dto.ResponseDto;
import com.aaturenko.pethotel.entities.Request;
import com.aaturenko.pethotel.entities.Response;
import com.aaturenko.pethotel.entities.Sitter;
import com.aaturenko.pethotel.entities.User;

import java.util.List;

public interface ResponseRepository extends Repository{
    List<Response> findByRequest(Request request);
    List<Response> findAllByUser(User user);
    Response save(Response response);
}
