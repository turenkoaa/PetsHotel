package com.aaturenko.pethotel.repositories.impl;

import com.aaturenko.pethotel.dao.mapper.DataMapper;
import com.aaturenko.pethotel.dao.mapper.RequestMapper;
import com.aaturenko.pethotel.dao.mapper.ReviewMapper;
import com.aaturenko.pethotel.entities.Review;
import com.aaturenko.pethotel.entities.User;
import com.aaturenko.pethotel.repositories.ReviewRepository;

import java.util.List;

public class MysqlReviewRepository implements ReviewRepository {

    private ReviewMapper reviewMapper;

    @Override
    public Review save(Review review) {
        return (Review) reviewMapper.save(review);
    }

    @Override
    public List<Review> findAllByLike(boolean like) {
        return reviewMapper.findAllByLike(like);
    }

    @Override
    public List<Review> findAllByUser(User user) {
        return findAllByUser(user);
    }

    @Override
    public DataMapper getDataMapper() {
        return reviewMapper;
    }
}
