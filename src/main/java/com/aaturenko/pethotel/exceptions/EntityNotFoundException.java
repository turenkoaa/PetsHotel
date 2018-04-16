package com.aaturenko.pethotel.exceptions;

public class EntityNotFoundException extends RuntimeException {

    private static final String MESSAGE = "%s with id = %d was not found.";

    public enum Entity {
        User, Pet, Request, Response
    }

    public EntityNotFoundException(Entity entity, long id) {
        super(String.format(MESSAGE, entity, id));
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}
