package com.aaturenko.pethotel.entities;

import lombok.Data;

@Data
public abstract class User extends Entity {

    protected long id;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected Boolean active;
    protected String address;

    public abstract static class UserBuilder {
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

    protected User(long id, String firstName, String lastName, String email, Boolean active, String address) {
        this.id = id;
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
}
