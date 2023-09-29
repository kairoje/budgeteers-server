package com.group.budgeteer.exceptions;

import com.group.budgeteer.classes.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.MethodNotAllowedException;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<APIResponse<String>> handleNoSuchElementExceptions(NoSuchElementException ex){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new APIResponse<>(ex.getMessage(), "error"));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse<String>> handleMethodArgumentNotValidException(MethodNotAllowedException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new APIResponse<>(ex.getMessage(), "error"));
    }
}
