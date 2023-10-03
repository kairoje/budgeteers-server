package com.group.budgeteer.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;
//TODO add docstrings

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DoesNotExistException extends RuntimeException {
    public <T> DoesNotExistException(Class<T> type, UUID id){
        super(type.getSimpleName() + " does not exist with id: " + id);
    }
}
