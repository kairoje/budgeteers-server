package com.group.budgeteer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception class for indicating that a user already exists in the system.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class UserAlreadyExistsException extends RuntimeException {

    /**
     * Constructor for UserAlreadyExistsException.
     * @param message A custom error message describing the reason for the exception.
     */
    public UserAlreadyExistsException(String message){
        super(message);
    }
}
