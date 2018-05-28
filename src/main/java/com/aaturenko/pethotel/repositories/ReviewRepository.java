package com.aaturenko.pethotel.repositories;

import com.aaturenko.pethotel.dto.ReviewDto;
import com.aaturenko.pethotel.entities.Review;
import com.aaturenko.pethotel.entities.User;

import java.util.List;

public interface ReviewRepository extends Repository{
    Review save(Review review);
    List<Review> findAllByLike(boolean like);
    List<Review> findAllByUser(User user);
}
