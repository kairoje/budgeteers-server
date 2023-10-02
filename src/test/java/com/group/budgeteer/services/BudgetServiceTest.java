package com.group.budgeteer.services;

import com.group.budgeteer.models.Budget;
import com.group.budgeteer.models.User;
import com.group.budgeteer.repositories.BudgetRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class BudgetServiceTest {

    @Mock
    private  BudgetRepository budgetRepository;

    @Mock
    private BudgetService budgetService;

    static User user = User.builder()
            .firstName("John")
            .lastName("Doe")
            .email("tester@test.com")
            .password("password")
            .build();

    public static List<Budget> getMockBudgets() {
        List<Budget> budgets = new ArrayList<>();

        Budget budget1 = new Budget();
        budget1.setId(UUID.randomUUID());
        budget1.setBalance(5000.00);
        budget1.setUser(user);
        budget1.setDate(LocalDate.of(2023, Month.OCTOBER, 2));

        Budget budget2 = new Budget();
        budget2.setId(UUID.randomUUID());
        budget2.setBalance(15000.00);
        budget2.setUser(user);
        budget2.setDate(LocalDate.of(2023, Month.NOVEMBER, 2));

        budgets.add(budget1);
        budgets.add(budget2);

        return budgets;
    }

    @Test
    void getAllBudgets() {
        Mockito.when(budgetService.getBudgets()).thenReturn(getMockBudgets());
        List<Budget> budgets = this.budgetService.getBudgets();
        assertEquals(2, budgets.size());
    }

    @Test
    void getOneBudget() {
        Mockito.when(budgetService.getBudget(UUID.randomUUID())).thenReturn(getMockBudgets().get(0));
    }

    @Test
    void createABudget() {
        Mockito.when(budgetService.createBudget(any(Budget.class))).thenReturn(getMockBudgets().get(0));
    }

    @Test
    void updateBudget() {
        Budget originalBudget = getMockBudgets().get(0);
        Budget updatedBudget = new Budget();
        updatedBudget.setId(originalBudget.getId());
        updatedBudget.setBalance(7500.00);
        Mockito.when(budgetRepository.save(updatedBudget)).thenReturn(updatedBudget);
    }

    @Test
    void deleteABudget() {
        Budget budgetToDelete = getMockBudgets().get(0);
        budgetService.deleteBudget(budgetToDelete.getId());
    }

}