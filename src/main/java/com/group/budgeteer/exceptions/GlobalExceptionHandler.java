package com.group.budgeteer.exceptions;

import com.group.budgeteer.classes.APIResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.MethodNotAllowedException;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * GlobalExceptionHandler is a class that handles exceptions for the entire application.
 * It provides exception handling for various types of exceptions and maps them to appropriate HTTP responses.
 */
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {


    /**
     * Exception handler for UserAlreadyExistsException.
     * @param ex The UserAlreadyExistsException that occurred.
     * @return ResponseEntity containing APIResponse with a conflict status and an error message.
     */
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<APIResponse<String>> handleUserAlreadyExistExceptions(UserAlreadyExistsException ex){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new APIResponse<>(ex.getMessage(), "error"));
    }

    /**
     * Exception handler for DoesNotExistException.
     * @param ex The DoesNotExistException that occurred.
     * @return ResponseEntity containing APIResponse with a not found status and an error message.
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DoesNotExistException.class)
    public ResponseEntity<APIResponse<String>> handleDoesNotExistExceptions(DoesNotExistException ex){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new APIResponse<>(ex.getMessage(), "error"));
    }

    /**
     * Exception handler for NoSuchElementException.
     * @param ex The NoSuchElementException that occurred.
     * @return ResponseEntity containing APIResponse with a not found status and an error message.
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<APIResponse<String>> handleNoSuchElementExceptions(NoSuchElementException ex){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new APIResponse<>(ex.getMessage(), "error"));
    }

    /**
     * Exception handler for HttpMessageNotReadableException.
     * @param ex The HttpMessageNotReadableException that occurred.
     * @return ResponseEntity containing APIResponse with a bad request status and an error message.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<APIResponse<String>> handleHttpMessageNotReadableExceptions(HttpMessageNotReadableException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new APIResponse<>(ex.getMessage(), "error"));
    }

    /**
     * Exception handler for MethodArgumentNotValidException.
     * @param ex The MethodArgumentNotValidException that occurred.
     * @return ResponseEntity containing error messages for validation errors.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex){
        Map<String, Object> errors = new HashMap<>();
        errors.put("message", "error");
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
       return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    /**
     * Exception handler for HttpRequestMethodNotSupportedException.
     * @param ex The HttpRequestMethodNotSupportedException that occurred.
     * @return ResponseEntity containing error information for unsupported HTTP methods.
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, Object>> handleMethodNotAllowedExceptions(HttpRequestMethodNotSupportedException ex){
        HttpHeaders headers = new HttpHeaders();
        Map<String, Object> errors = new HashMap<>();
        errors.put("message", ex.getMessage());
        errors.put("allow", ex.getSupportedHttpMethods());
        headers.setAllow(Objects.requireNonNull(ex.getSupportedHttpMethods()));
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .headers(headers)
                .body(errors);
    }
}