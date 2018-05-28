package com.aaturenko.pethotel.repositories;

import com.aaturenko.pethotel.entities.User;

import java.util.List;

public interface UserRepository extends Repository {
    List<User> findAll();
}
