package com.aaturenko.pethotel.old.entities;

import com.aaturenko.pethotel.old.dao.DataMapper;
import com.aaturenko.pethotel.old.dao.DataMapperRegistry;
import com.aaturenko.pethotel.old.dao.mapper.ReviewMapper;
import com.aaturenko.pethotel.dto.ReviewDto;
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
        return (Review) reviewMapper.save(review);
    }

    public static Review find(long id) {
        return (Review) reviewMapper.findById(id);
    }

    public static List<Review> showDislikedReviews() {
        return reviewMapper.findAllByLike(false);
    }

    public static List<Review> findAllByUser(User user) {
        return reviewMapper.findAllByUser(user);
    }

    public static ReviewBuilder builder() {
        return new ReviewBuilder();
    }

    @Override
    public DataMapper dataMapper() {
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
