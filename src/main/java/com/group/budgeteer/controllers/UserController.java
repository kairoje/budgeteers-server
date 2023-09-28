package com.group.budgeteer.controllers;

import com.group.budgeteer.classes.APIResponse;
import com.group.budgeteer.models.User;
import com.group.budgeteer.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
//TODO add docstrings

@RestController
@RequestMapping("/api/v1/auth")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    //@Valid performs validation
    public ResponseEntity<APIResponse<String>> create(@Valid @RequestBody User userObject){
        return ResponseEntity
                .ok(new APIResponse<>(userService.create(userObject), "success"));
    }

    @PostMapping("/login")
    public ResponseEntity<APIResponse<User>> login(@RequestBody User userObject){
        return ResponseEntity
                .ok(new APIResponse<>(userService.login(userObject), "success"));
    }
}
