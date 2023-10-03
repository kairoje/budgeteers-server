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
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
//TODO add docstrings
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

    public String create(User userObject) throws UserAlreadyExistsException {
        boolean exists = userRepository.existsByEmail(userObject.getEmail());
        if (exists) throw new UserAlreadyExistsException("User already exist with id: " + userObject.getId());
        userObject.setPassword(passwordEncoder.encode(userObject.getPassword()));
        String jwt = jwtUtils.generateJwtToken(new AuthUserDetails(userObject));
        userRepository.save(userObject);
        return jwt;
    }

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

    public List<User> test(){
        return userRepository.findAll();
    }
}
