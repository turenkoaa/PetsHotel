package com.aaturenko.pethotel.repositories.impl;

import com.aaturenko.pethotel.dao.mapper.DataMapper;
import com.aaturenko.pethotel.dao.mapper.ResponseMapper;
import com.aaturenko.pethotel.entities.Request;
import com.aaturenko.pethotel.entities.Response;
import com.aaturenko.pethotel.entities.User;
import com.aaturenko.pethotel.repositories.ResponseRepository;

import java.util.List;

public class MysqlResponseRepository implements ResponseRepository {

    private ResponseMapper responseMapper;

    @Override
    public List<Response> findByRequest(Request request) {
        return responseMapper.findAllByRequest(request);
    }

    @Override
    public List<Response> findAllByUser(User user) {
        return responseMapper.findAllByUser(user);
    }

    @Override
    public Response save(Response response) {
        return (Response) responseMapper.save(response);
    }

    @Override
    public DataMapper getDataMapper() {
        return responseMapper;
    }
}
