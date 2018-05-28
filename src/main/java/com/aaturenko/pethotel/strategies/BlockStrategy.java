package com.aaturenko.pethotel.strategies;

import com.aaturenko.pethotel.entities.User;

import java.util.List;

public interface BlockStrategy {
    List<User> showUsersToBlock();
}