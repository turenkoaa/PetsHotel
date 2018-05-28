package com.aaturenko.pethotel.repositories.impl;

import com.aaturenko.pethotel.dao.mapper.DataMapper;
import com.aaturenko.pethotel.dao.mapper.RequestMapper;
import com.aaturenko.pethotel.entities.Request;
import com.aaturenko.pethotel.entities.Response;
import com.aaturenko.pethotel.entities.User;
import com.aaturenko.pethotel.enums.RequestStatus;
import com.aaturenko.pethotel.repositories.RequestRepository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

public class MysqlRequestRepository implements RequestRepository {

    private RequestMapper requestMapper;

    @Override
    public Request save(Request request) {
        return (Request) requestMapper.save(request);
    }

    @Override
    public List<Request> findAllByUser(User user) {
        return requestMapper.findAllByUser(user);
    }

    @Override
    public List<Request> findNewRequests() {
        return requestMapper.findAllByStatus(RequestStatus.NEW);
    }

    @Override
    public Request findByResponse(Response response) {
        return findByResponse(response);
    }

    @Override
    public DataMapper getDataMapper() {
        return requestMapper;
    }
}
