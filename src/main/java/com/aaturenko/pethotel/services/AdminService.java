package com.aaturenko.pethotel.services;

public interface AdminService {
    void blockUser(long userId);
    void activateUser(long userId);
}
