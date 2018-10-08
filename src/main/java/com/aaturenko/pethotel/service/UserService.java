package com.aaturenko.pethotel.service;

import com.aaturenko.pethotel.dto.ReviewDto;
import com.aaturenko.pethotel.model.Review;
import com.aaturenko.pethotel.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(long userId);
    User findByEmail(String email);
    User save(User user);
    void deleteById(long id);
    Review addReview(ReviewDto reviewDto);
    List<Review> showDislikedReviews();
}
