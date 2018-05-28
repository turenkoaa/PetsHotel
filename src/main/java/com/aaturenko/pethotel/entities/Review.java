package com.aaturenko.pethotel.entities;

import com.aaturenko.pethotel.dao.DataMapperRegistry;
import com.aaturenko.pethotel.dao.mapper.DataMapper;
import com.aaturenko.pethotel.dao.mapper.ReviewMapper;
import com.aaturenko.pethotel.dto.ReviewDto;
import com.aaturenko.pethotel.repositories.Registry;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class Review extends Entity {

    private String comment;
    private boolean like;
    private User user;

    private static ReviewMapper reviewMapper = (ReviewMapper) DataMapperRegistry.getMapper(Review.class);

    private Review(long id, boolean like, String comment, User user) {
        super(id);
        this.comment = comment;
        this.like = like;
        this.user = user;
    }

    public static Review newReview(ReviewDto reviewDto, User user) {
        Review review = Review.builder()
                .id(0)
                .like(reviewDto.isLike())
                .comment(reviewDto.getComment())
                .user(user)
                .build();
        return Registry.reviewRepository.save(review);
    }

    public static List<Review> showDislikedReviews() {
        return Registry.reviewRepository.findAllByLike(false);
    }

    public static List<Review> findAllByUser(User user) {
        return Registry.reviewRepository.findAllByUser(user);
    }

    public static ReviewBuilder builder() {
        return new ReviewBuilder();
    }

    @Override
    public DataMapper getMapper() {
        return reviewMapper;
    }

    public static class ReviewBuilder {
        private long id;
        private String comment;
        private boolean like;
        private User user;

        ReviewBuilder() {
        }

        public ReviewBuilder comment(String comment) {
            this.comment = comment;
            return this;
        }

        public ReviewBuilder like(boolean like) {
            this.like = like;
            return this;
        }

        public ReviewBuilder user(User user) {
            this.user = user;
            return this;
        }

        public ReviewBuilder id(long id) {
            this.id = id;
            return this;
        }

        public Review build() {
            return new Review(id, like, comment, user);
        }
    }
}
