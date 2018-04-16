package com.aaturenko.pethotel.exceptions;

public class UniqueNameException extends RuntimeException {

    private static final String MESSAGE = "%s with %s = %s already exists.";

    public enum UniqueName {
        Email, UserName
    }

    public UniqueNameException (EntityNotFoundException.Entity entity, UniqueName uniqueName, String value) {
        super(String.format(MESSAGE, entity, uniqueName, value));
    }
}
