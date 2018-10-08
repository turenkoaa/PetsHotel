package com.aaturenko.pethotel.service;

import com.aaturenko.pethotel.model.User;

import java.util.List;

public interface AdminService {
    void blockUser(long userId);
    List<User> showUsersToBlock();
}
