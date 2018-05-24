package com.aaturenko.pethotel.repositories;

import com.aaturenko.pethotel.dto.RequestDto;
import com.aaturenko.pethotel.entities.Request;
import com.aaturenko.pethotel.entities.User;

public interface RequestRepository extends Repository<Request> {
    long save(RequestDto requestDto, User user);
}
