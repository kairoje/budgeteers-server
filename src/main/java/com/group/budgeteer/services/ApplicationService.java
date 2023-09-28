package com.group.budgeteer.services;

import com.group.budgeteer.models.User;
import com.group.budgeteer.security.AuthUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * The ApplicationService class provides common utility methods for accessing information about the current user
 * within the library application.
 */
@Service
public class ApplicationService {

    public static User currentUser(){
        AuthUserDetails authUserDetails = (AuthUserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
                return authUserDetails.getUser();
    }
}
