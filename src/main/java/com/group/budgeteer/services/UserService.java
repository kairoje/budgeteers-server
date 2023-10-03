package com.group.budgeteer.services;

import com.group.budgeteer.exceptions.UserAlreadyExistsException;
import com.group.budgeteer.models.User;
import com.group.budgeteer.repositories.UserRepository;
import com.group.budgeteer.security.AuthUserDetails;
import com.group.budgeteer.security.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

/**
 * Service class for managing user CRUD operations.
 */
@Service
public class UserService extends ApplicationService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    /**
     * Constructs a new UserService with the specified dependencies.
     * @param userRepository The repository responsible for user data storage and retrieval.
     * @param passwordEncoder The password encoder used for securing user passwords (lazy-initialized).
     * @param jwtUtils The utility class for handling JSON Web Tokens (JWT).
     * @param authenticationManager The authentication manager for user authentication (lazy-initialized).
     */
    @Autowired
    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder, JWTUtils jwtUtils, @Lazy AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Creates a new user with the provided user data and returns a JSON Web Token (JWT) after successful creation.
     * If a user with the same email already exists, an UserAlreadyExistsException is thrown.
     *
     * @param userObject The user object containing user data to be created.
     * @return A JWT token representing the newly created user's authentication.
     * @throws UserAlreadyExistsException If a user with the same email already exists in the system.
     */
    public String create(User userObject) throws UserAlreadyExistsException {
        boolean exists = userRepository.existsByEmail(userObject.getEmail());
        if (exists) throw new UserAlreadyExistsException("User already exist with id: " + userObject.getId());
        userObject.setPassword(passwordEncoder.encode(userObject.getPassword()));
        String jwt = jwtUtils.generateJwtToken(new AuthUserDetails(userObject));
        userRepository.save(userObject);
        return jwt;
    }

    /**
     * Attempts to authenticate a user with the provided email and password, and upon successful authentication,
     * returns the user's details along with a JSON Web Token (JWT).
     *
     * @param payload The user object containing email and password for authentication.
     * @return The authenticated user's details and a JWT token.
     * @throws RuntimeException If the authentication fails due to invalid email or password.
     */
    public User login(User payload)  {
        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(payload.getEmail(), payload.getPassword());
        try {
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            AuthUserDetails authUserDetails = (AuthUserDetails) authentication.getPrincipal();
            String jwt = jwtUtils.generateJwtToken(authUserDetails);
            return authUserDetails.getUser();
        } catch (Exception e) {
            throw new RuntimeException("Invalid email/password");
        }
    }
}
