package com.group.budgeteer.services;

import com.group.budgeteer.exceptions.UserAlreadyExistsException;
import com.group.budgeteer.models.User;
import com.group.budgeteer.repositories.UserRepository;
import com.group.budgeteer.security.AuthUserDetails;
import com.group.budgeteer.security.JWTUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JWTUtils jwtUtils;

    @Mock
    private AuthenticationManager authenticationManager;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, passwordEncoder, jwtUtils, authenticationManager);
    }

    @Test
    public void testCreateUser_Success() throws UserAlreadyExistsException {
        // Create a sample user object
        User user = new User(null, "jason@gmail.com", "Tom", "Doe", "password");

        // Mock the behavior of userRepository and passwordEncoder
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(jwtUtils.generateJwtToken(any(AuthUserDetails.class))).thenReturn("sampleJWTToken");

        // Call the create method
        String jwtToken = userService.create(user);

        // Verify that the user object was saved to the repository
        Mockito.verify(userRepository).save(user);

        // Verify that the returned JWT token is not null
        assertNotNull(jwtToken);
    }

    @Test
    public void testCreateUser_UserAlreadyExistsException() {
        // Create a sample user object
        User user = new User(null, "existingUser@gmail.com", "John", "Doe", "password");

        // Mock the behavior of userRepository to return true (user already exists)
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        // Call the create method and expect a UserAlreadyExistsException
        assertThrows(UserAlreadyExistsException.class, () -> userService.create(user));
    }

    @Test
    public void testLoginUser_Success() {
        // Create a sample user object
        User user = new User(null, "testUser@gmail.com", "Tom", "Doe", "password");

        // Mock the behavior of authenticationManager and jwtUtils
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(jwtUtils.generateJwtToken(any(AuthUserDetails.class))).thenReturn("sampleJWTToken");

        // Call the login method
        User authenticatedUser = userService.login(user);

        // Verify that the returned user is not null
        assertNotNull(authenticatedUser);

        // Verify that the user details were correctly set in the security context
        AuthUserDetails authUserDetails = (AuthUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        assertEquals(user.getEmail(), authUserDetails.getUsername());
    }

    @Test
    public void testLoginUser_InvalidCredentials() {
        // Create a sample user object
        User user = new User(null, "sam@gmail.com", "sam", "smith", "invalidPassword");

        // Mock the behavior of authenticationManager to throw an exception (invalid credentials)
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new RuntimeException());

        // Call the login method and expect a RuntimeException
        assertThrows(RuntimeException.class, () -> userService.login(user));
    }
}
