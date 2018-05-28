package com.aaturenko.pethotel.repositories;

import com.aaturenko.pethotel.dto.RequestDto;
import com.aaturenko.pethotel.entities.Owner;
import com.aaturenko.pethotel.entities.Request;
import com.aaturenko.pethotel.entities.Response;
import com.aaturenko.pethotel.entities.User;

import java.util.List;

public interface RequestRepository extends Repository {
    Request save(Request request);
    List<Request> findAllByUser(User user);
    List<Request> findNewRequests();
    Request findByResponse(Response response);

}
