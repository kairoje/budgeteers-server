package com.group.budgeteer.controllers;

import com.group.budgeteer.classes.APIResponse;
import com.group.budgeteer.models.User;
import com.group.budgeteer.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller class for handling user authentication operations.
 * This class defines REST endpoints for user signup and login.
 */
@RestController
@RequestMapping("/api/v1/auth")
public class UserController {
    private final UserService userService;

    /**
     * Constructor to initialize UserController with a UserService.
     * @param userService The UserService used for user-related operations.
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Endpoint for user signup.
     * @param userObject The User object containing signup information.
     * @return ResponseEntity containing APIResponse with a success message.
     */
    @PostMapping("/signup")
    public ResponseEntity<APIResponse<String>> create(@Valid @RequestBody User userObject){
        return ResponseEntity
                .ok(new APIResponse<>(userService.create(userObject), "success"));
    }

    /**
     * Endpoint for user login.
     * @param userObject The User object containing login information.
     * @return ResponseEntity containing APIResponse with a success message.
     */
    @PostMapping("/login")
    public ResponseEntity<APIResponse<User>> login(@RequestBody User userObject){
        return ResponseEntity
                .ok(new APIResponse<>(userService.login(userObject), "success"));
    }
}
