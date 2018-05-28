package com.aaturenko.pethotel.repositories.impl;

import com.aaturenko.pethotel.dao.mapper.DataMapper;
import com.aaturenko.pethotel.dao.mapper.UserMapper;
import com.aaturenko.pethotel.entities.User;
import com.aaturenko.pethotel.repositories.UserRepository;

import java.util.List;

public class MysqlUserRepository implements UserRepository {

    private UserMapper userMapper;

    @Override
    public List<User> findAll() {
        return findAll();
    }

    @Override
    public DataMapper getDataMapper() {
        return userMapper;
    }
}
