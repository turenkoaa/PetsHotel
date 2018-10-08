package com.aaturenko.pethotel.service.impl;

import com.aaturenko.pethotel.dto.ReviewDto;
import com.aaturenko.pethotel.exceptions.EntityNotFoundException;
import com.aaturenko.pethotel.exceptions.EntityNotFoundException.Entity;
import com.aaturenko.pethotel.model.Review;
import com.aaturenko.pethotel.model.User;
import com.aaturenko.pethotel.repo.ReviewRepository;
import com.aaturenko.pethotel.repo.UserRepository;
import com.aaturenko.pethotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(Entity.User, userId));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User with email: " + email + " does not exest."));
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Review addReview(ReviewDto reviewDto) {
        User user = findById(reviewDto.getUserId());
        Review review = Review.builder()
                .like(reviewDto.isLike())
                .comment(reviewDto.getComment())
                .user(user)
                .build();
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> showDislikedReviews() {
        return reviewRepository.findAllByLike(false);
    }
}
