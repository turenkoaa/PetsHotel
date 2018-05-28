package com.aaturenko.pethotel.entities;

import com.aaturenko.pethotel.repositories.Registry;
import com.aaturenko.pethotel.dto.ReviewDto;
import com.aaturenko.pethotel.repositories.ReviewRepository;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class User extends Entity {

    protected String firstName;
    protected String lastName;
    protected String email;
    protected Boolean active;
    protected String address;
    protected List<Review> reviewsAboutMe;

    protected User(long id, String firstName, String lastName, String email, Boolean active, String address) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.active = active;
        this.address = address;
    }

    public void changeStatus(boolean active) {
        this.setActive(active);
        update();
    }

    public abstract void block();

    public void activate(){
        changeStatus(true);
    }

    public Review addReview(ReviewDto reviewDto) {
        Review review = Review.newReview(reviewDto, this);
        reviewsAboutMe.add(review);
        return review;
    }

    public List<Review> getReviewsAboutMe() {
        if (reviewsAboutMe == null)
            reviewsAboutMe = Review.findAllByUser(this);
        return reviewsAboutMe;
    }

    public static List<User> findAll(){
        return Registry.userRepository.findAll();
    }

    public static abstract class UserBuilder {
        protected long id;
        protected String firstName;
        protected String lastName;
        protected String email;
        protected Boolean active;
        protected String address;

        UserBuilder() {}

        public UserBuilder id(long id) {
            this.id = id;
            return this;
        }

        public UserBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder active(Boolean active) {
            this.active = active;
            return this;
        }

        public UserBuilder address( String address) {
            this.address = address;
            return this;
        }

        public abstract User build();

    }
}
