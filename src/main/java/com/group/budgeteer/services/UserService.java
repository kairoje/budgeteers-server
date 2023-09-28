package com.group.budgeteer.services;

import com.group.budgeteer.exceptions.UserAlreadyExistsException;
import com.group.budgeteer.models.User;
import com.group.budgeteer.repositories.UserRepository;
import com.group.budgeteer.security.AuthUserDetails;
import com.group.budgeteer.security.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserService extends ApplicationService{
    Logger logger = Logger.getLogger(UserService.class.getName());

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder, JWTUtils jwtUtils, @Lazy AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    public String create(User userObject){
        User user = userRepository.findByEmail(userObject.getEmail()).orElseThrow();

        if (user != null){
            throw new UserAlreadyExistsException("User with email " + userObject.getEmail() + " already exists.");
        }

        userObject.setPassword(passwordEncoder.encode(userObject.getPassword()));
        String jwt = jwtUtils.generateJwtToken(new AuthUserDetails(userObject));
        userRepository.save(userObject);

        return jwt;
    }
}
