package com.group.budgeteer.classes;

/**
 * The APIResponse class represents the response body structure for API responses.
 * It encapsulates the actual response data.
 *
 * @param data The data or object to be included in the API response.
 * @param message The status message of the response
 */
public record APIResponse<T>(T data, String message) { }
