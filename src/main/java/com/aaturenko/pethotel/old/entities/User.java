package com.aaturenko.pethotel.old.entities;

import com.aaturenko.pethotel.old.dao.DataMapper;
import com.aaturenko.pethotel.old.dao.DataMapperRegistry;
import com.aaturenko.pethotel.old.dao.mapper.UserMapper;
import com.aaturenko.pethotel.dto.ReviewDto;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
public abstract class User extends Entity {

    protected String firstName;
    protected String lastName;
    protected String email;
    protected Boolean active;
    protected String address;
    protected List<Review> reviewsAboutMe = new ArrayList<>();

    private static UserMapper userMapper = (UserMapper) DataMapperRegistry.getMapper(User.class);

    public DataMapper dataMapper() {
        return userMapper;
    }

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
        save();
    }

    public static User find(long id) {
        return (User) userMapper.findById(id);
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

    public List<Review> reviewsAboutMe() {
        if (reviewsAboutMe == null)
            reviewsAboutMe = Review.findAllByUser(this);
        return reviewsAboutMe;
    }

    public static List<User> findAll(){
        return userMapper.findAll();
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
