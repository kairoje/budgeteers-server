package com.group.budgeteer.services;

import com.group.budgeteer.models.Budget;
import com.group.budgeteer.models.User;
import com.group.budgeteer.repositories.BudgetRepository;
import com.group.budgeteer.security.AuthUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class BudgetServiceTest {

    @InjectMocks
    private  BudgetRepository budgetRepository;

    @Mock
    private BudgetService budgetService;

    private static final UUID userId = UUID.randomUUID();
    private AuthUserDetails authUserDetails;

    @BeforeEach
    void setup() {
        authUserDetails = new AuthUserDetails(
                new User(userId, "tester@test.com", "John", "Doe", "password")
        );

        // Create a mock Authentication object and set it in the SecurityContext

        Authentication authentication = new UsernamePasswordAuthenticationToken(authUserDetails, null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }



}