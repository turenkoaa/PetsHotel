package com.aaturenko.pethotel.services.impl;

import com.aaturenko.pethotel.exceptions.EntityNotFoundException;
import com.aaturenko.pethotel.exceptions.UniqueNameException;
import com.aaturenko.pethotel.models.User;
import com.aaturenko.pethotel.repositories.UserRepository;
import com.aaturenko.pethotel.services.ManageStatusesService;
import com.aaturenko.pethotel.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ManageStatusesService manageStatusesService;

    @Override
    public List<User> findAll(int page, int size) {
        return userRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    @Override
    public User findUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(EntityNotFoundException.Entity.User, id));
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("User with email = %s does not exists.", email))
                );
    }

    @Override
    public User saveOrUpdateUser(User user) {
        try {
            return userRepository.save(validateUserBeforeSaving(user));
        } catch (DataIntegrityViolationException | ConstraintViolationException e) {
            String email = user.getEmail();
            if (userRepository.findByEmail(email).isPresent())
                throw new UniqueNameException(
                        EntityNotFoundException.Entity.User,
                        UniqueNameException.UniqueName.Email,
                        email);
            throw e;
        }
    }

    private User validateUserBeforeSaving(User user) {
        userRepository.findById(user.getId()).ifPresent(
                oldUser -> {
                    if (!user.getActive().equals(oldUser.getActive()))
                        throw new UnsupportedOperationException(
                                "There is no available for users except admins to modify their active status.");
                    if (!user.getUserType().equals(oldUser.getUserType()))
                        throw new UnsupportedOperationException("There is no available to modify user type.");
                }
        );

        return user;
    }

    @Override
    public void deleteUserById(long userId) {
        manageStatusesService.manageStatusesForUserRemoving(userId);
        userRepository.deleteById(userId);
    }
}
