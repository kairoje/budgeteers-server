package com.group.budgeteer.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

/**
 * Custom exception class for indicating that a resource does not exist.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class DoesNotExistException extends RuntimeException {

    /**
     * Constructor for DoesNotExistException.
     * @param type The class type of the resource that does not exist.
     * @param id The unique ID of the resource.
     */
    public <T> DoesNotExistException(Class<T> type, UUID id){
        super(type.getSimpleName() + " does not exist with id: " + id);
    }
}
