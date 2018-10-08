package com.aaturenko.pethotel.service.impl;

import com.aaturenko.pethotel.model.Review;
import com.aaturenko.pethotel.model.User;
import com.aaturenko.pethotel.service.AdminService;
import com.aaturenko.pethotel.service.UserService;
import com.aaturenko.pethotel.service.UpdateRequestAnsResponseStatusStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserService userService;
    @Autowired
    private UpdateRequestAnsResponseStatusStrategy statusStrategy;

    @Override
    public void blockUser(long userId) {
        User user = userService.findById(userId);
        user.setActive(false);
//        user.getResponses().forEach(statusStrategy::rejectResponse);
        userService.save(user);
    }

    @Override
    public List<User> showUsersToBlock() {
        return userService.showDislikedReviews()
                .stream()
                .map(Review::getUser)
                .filter(User::getActive)
                .distinct()
                .collect(toList());
    }

}
