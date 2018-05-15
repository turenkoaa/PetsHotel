package com.aaturenko.pethotel.services;

import com.aaturenko.pethotel.models.User;

import java.util.List;

public interface UserService {
    List<User> findAll(int page, int size);
    User findUserById(long id);
    User findUserByEmail(String email);
    User saveOrUpdateUser(User user);
    void deleteUserById(long id);
}
