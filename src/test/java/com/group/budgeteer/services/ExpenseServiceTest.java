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



    @Test
    void testGetExpenses() {
        // Prepare test data
        UUID budgetId = budget.getId();
        List<Expense> expectedExpenses = List.of(
                new Expense(UUID.randomUUID(), "Expense1", "Expense1 description", 100.0, budget),
                new Expense(UUID.randomUUID(), "Expense2", "Expense2 description", 200.0,budget)
        );

        // Stub the behavior of expenseRepository to return the expected expenses
        when(expenseRepository.findByBudget_Id(budgetId)).thenReturn(Optional.of(expectedExpenses));

        // Call the method to be tested
        List<Expense> retrievedExpenses = expenseService.getExpenses(budgetId);

        // Verify that the returned expenses match the expected expenses
        assertEquals(expectedExpenses, retrievedExpenses);

        // Verify that no more interactions with the repository occurred
        verifyNoMoreInteractions(expenseRepository);
    }

    @Test
    void testCreateExpense() {
        // Prepare test data
        UUID budgetId = UUID.randomUUID();
        Budget budget = new Budget(budgetId, 1000.0, LocalDate.now(), authUserDetails.getUser());
        Expense expenseObject = new Expense(UUID.randomUUID(), "ExpenseName", "Expense description",  500.0,budget);

        // Stub the behavior of budgetRepository to return the budget when findById is called
        when(budgetRepository.findById(budgetId)).thenReturn(Optional.of(budget));

        // Call the method to be tested
        expenseService.createExpense(budgetId, expenseObject);

        // Verify that budget balance is updated correctly
        assertEquals(500.0, budget.getBalance());

        // Verify that budgetRepository.save is called to save the updated budget
        verify(budgetRepository).save(budget);

        // Verify that expenseRepository.save is called to save the expense
        verify(expenseRepository).save(expenseObject);

        // Verify that no more interactions with the repositories occurred
        verifyNoMoreInteractions(budgetRepository);
        verifyNoMoreInteractions(expenseRepository);
    }

    @Test
    void testDeleteExpense() {
        // Prepare test data
        UUID expenseId = UUID.randomUUID();
        Expense expense = new Expense(expenseId, "ExpenseName", "Expense description", 100.00, budget);

        // Stub the behavior of expenseRepository to return the expense when findById is called
        when(expenseRepository.findById(expenseId)).thenReturn(Optional.of(expense));

        // Call the method to be tested
        expenseService.deleteExpense(expenseId);

        // Verify that budget balance is updated correctly
        assertEquals(4100.0, expense.getBudget().getBalance());

        // Verify that budgetRepository.save is called to save the updated budget
        verify(budgetRepository).save(expense.getBudget());

        // Verify that expenseRepository.delete is called to delete the expense
        verify(expenseRepository).delete(expense);

        // Verify that no more interactions with the repositories occurred
        verifyNoMoreInteractions(budgetRepository);
        verifyNoMoreInteractions(expenseRepository);
    }

    @Test
    void testUpdateExpense() {
        // Prepare test data
        UUID expenseId = UUID.randomUUID();
        Expense existingExpense = new Expense(expenseId, "ExpenseName", "Expense description", 500.0, budget );
        Expense updatedExpense = new Expense(expenseId, "UpdatedExpenseName", "Update description", 600.0, existingExpense.getBudget());

        // Stub the behavior of expenseRepository to return the existing expense when findById is called
        when(expenseRepository.findById(expenseId)).thenReturn(Optional.of(existingExpense));

        // Call the method to be tested
        expenseService.updateExpense(updatedExpense);

        // Verify that budget balance is updated correctly
        assertEquals(3900.0, existingExpense.getBudget().getBalance());
        assertEquals("UpdatedExpenseName", existingExpense.getName());
        assertEquals("Update description", existingExpense.getDescription());
        // Verify that budgetRepository.save is called to save the updated budget
        verify(budgetRepository).save(existingExpense.getBudget());

        // Verify that expenseRepository.save is called to save the updated expense
        verify(expenseRepository).save(existingExpense);

        // Verify that no more interactions with the repositories occurred
        verifyNoMoreInteractions(budgetRepository);
        verifyNoMoreInteractions(expenseRepository);
    }
}