package com.aaturenko.pethotel.services;

import com.aaturenko.pethotel.models.User;

public interface UserService {
    User findUserById(long id);
    User findUserByEmail(String email);
    User saveOrUpdateUser(User user);
    void deleteUserById(long id);
}
