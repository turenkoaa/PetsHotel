package com.aaturenko.pethotel.services.impl;

import com.aaturenko.pethotel.repositories.UserRepository;
import com.aaturenko.pethotel.services.AdminService;
import com.aaturenko.pethotel.services.ManageStatusesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

    private final ManageStatusesService manageStatusesService;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void blockUser(long userId) {
        manageStatusesService.manageStatusesForBlockedUserResponses(userId);
        manageStatusesService.manageStatusesForBlockedUserRequests(userId);
        userRepository.updateUserActiveStatus(userId, false);
    }

    @Override
    @Transactional
    public void activateUser(long userId) {
        userRepository.updateUserActiveStatus(userId, true);
    }

}
