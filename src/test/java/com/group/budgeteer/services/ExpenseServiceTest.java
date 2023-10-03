package com.group.budgeteer.services;

import com.group.budgeteer.models.Budget;
import com.group.budgeteer.models.Expense;
import com.group.budgeteer.models.User;
import com.group.budgeteer.repositories.BudgetRepository;
import com.group.budgeteer.repositories.ExpenseRepository;
import com.group.budgeteer.security.AuthUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private BudgetRepository budgetRepository;

    @InjectMocks
    private ExpenseService expenseService;

    private AuthUserDetails authUserDetails;
    private Budget budget;


    @BeforeEach
    void setup() {
        authUserDetails = new AuthUserDetails(
                new User(UUID.randomUUID(), "julian@gmail.com", "Julian", "Smith", "password")
        );
        budget = new Budget(UUID.randomUUID(),4000.00, LocalDate.now(), authUserDetails.getUser());
        // Create a mock Authentication object and set it in the SecurityContext
        Authentication authentication = new UsernamePasswordAuthenticationToken(authUserDetails, null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}