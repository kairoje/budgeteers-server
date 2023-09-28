package com.group.budgeteer.classes;

/**
 * The APIResponse class represents the response body structure for API responses.
 * It encapsulates the actual response data.
 *
 * @param data The data or object to be included in the API response.
 * @param message The status message of the response
 */
public record APIResponse<T>(T data, String message) { }

//record is the same as this
//public class APIResponse<T> {
//    private final T data;
//    private final String message;
//
//    public APIResponse(T data, String message){
//        this.data = data;
//        this.message = message;
//    }
//
//    public T getData() {
//        return data;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//}
