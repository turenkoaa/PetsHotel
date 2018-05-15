package com.aaturenko.pethotel.services.impl;

import com.aaturenko.pethotel.exceptions.EntityNotFoundException;
import com.aaturenko.pethotel.exceptions.UniqueNameException;
import com.aaturenko.pethotel.models.User;
import com.aaturenko.pethotel.repositories.UserRepository;
import com.aaturenko.pethotel.services.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

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
            return userRepository.save(user);
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

    @Override
    public void deleteUserById(long id) {
        //todo set reject to new responses for user requests
        userRepository.deleteById(id);
    }
}
