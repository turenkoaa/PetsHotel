package com.aaturenko.pethotel.old.strategies;

import com.aaturenko.pethotel.old.entities.User;

import java.util.List;

public interface BlockStrategy {
    List<User> showUsersToBlock();
}