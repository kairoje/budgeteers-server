package com.group.budgeteer.services;

import com.group.budgeteer.models.Budget;
import com.group.budgeteer.models.User;
import com.group.budgeteer.repositories.BudgetRepository;
import com.group.budgeteer.security.AuthUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    @Test
    void testGetAllBudgets() {
        // Create some sample budgets
        Budget budget1 = new Budget(UUID.randomUUID(), 1000.0, LocalDate.now(), authUserDetails.getUser());
        Budget budget2 = new Budget(UUID.randomUUID(), 2000.0, LocalDate.now(),authUserDetails.getUser());
        List<Budget> expectedBudgets = List.of(budget1, budget2);

        // Stub the behavior of budgetRepository to return the expected budgets

        when(budgetRepository.findByUser_Id(authUserDetails.getUser().getId())).thenReturn(Optional.of(expectedBudgets));

        // Call the method to be tested
        List<Budget> retrievedBudgets = budgetService.getBudgets();

        // Verify that the repository method was called with the correct user ID
        verify(budgetRepository).findByUser_Id(authUserDetails.getUser().getId());

        // Verify that the returned budgets match the expected budgets
        assertEquals(expectedBudgets, retrievedBudgets);

        // Verify that no more interactions with the repository occurred
        verifyNoMoreInteractions(budgetRepository);
    }

    @Test
    void testCreateBudget() {
        //given
        Budget budget = new Budget(
                4000.00,
                LocalDate.of(2023, 3, 1),
                authUserDetails.getUser(),
                null
        );
        // when
        budgetService.createBudget(budget);

        // then
        ArgumentCaptor<Budget> budgetArgument = ArgumentCaptor.forClass(Budget.class);
        verify(budgetRepository).save(budgetArgument.capture());
        Budget capturedBudget = budgetArgument.getValue();
        assertEquals(capturedBudget, budget);
        verifyNoMoreInteractions(budgetRepository);
    }

    @Test
    void testGetBudget() {
        //Given
        Budget budget = new Budget(
                UUID.randomUUID(),
                4000.00,
                LocalDate.of(2023, 3, 1),
                authUserDetails.getUser()
        );
        // Mock the behavior of budgetRepository.findById
        when(budgetRepository.findById(budget.getId())).thenReturn(Optional.of(budget));

        // When
        Budget result = budgetService.getBudget(budget.getId());

        // Then
        assertNotNull(result);
        assertEquals(budget, result);

        // Then
        ArgumentCaptor<UUID> budgetArgument = ArgumentCaptor.forClass(UUID.class);
        verify(budgetRepository).findById(budgetArgument.capture());
        UUID capturedId = budgetArgument.getValue();
        assertEquals(capturedId, budget.getId());
        verifyNoMoreInteractions(budgetRepository);
    }

    @Test
    void testUpdateBudget() {
        // Given
        UUID budgetId = UUID.randomUUID();

        Budget existingBudget = new Budget(
                budgetId,
                4000.00,
                LocalDate.of(2023, 3, 1),
                authUserDetails.getUser()
        );

        Budget updatedBudget = new Budget(
                budgetId,
                5000.00,
                LocalDate.of(2023, 3, 1),
                authUserDetails.getUser()
        );

        // Mock the behavior of budgetRepository.findById to return the existingBudget
        when(budgetRepository.findById(budgetId)).thenReturn(Optional.of(existingBudget));

        // Use doReturn(...) for stubbing budgetRepository.save
        doReturn(updatedBudget).when(budgetRepository).save(any()); // Use any() to match any argument

        // When
        Budget result = budgetService.updateBudget(updatedBudget);

        // Then
        assertNotNull(result);
        assertEquals(updatedBudget, result);

        // Verify that budgetRepository.findById was called with the correct budgetId
        verify(budgetRepository).findById(budgetId);

        // Verify that budgetRepository.save was called with any argument
        verify(budgetRepository).save(any());

        // Verify that there are no more interactions with budgetRepository
        verifyNoMoreInteractions(budgetRepository);
    }


    @Test
    void testDeleteBudget() {
        // Given
        UUID budgetId = UUID.randomUUID();

        // When
        budgetService.deleteBudget(budgetId);

        // Then
        verify(budgetRepository, times(1)).deleteById(budgetId);
        verifyNoMoreInteractions(budgetRepository);
    }

}