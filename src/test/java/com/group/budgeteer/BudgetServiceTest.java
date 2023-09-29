package com.group.budgeteer;

import com.group.budgeteer.models.Budget;
import com.group.budgeteer.models.Expense;
import com.group.budgeteer.models.User;
import com.group.budgeteer.services.ApplicationService;
import com.group.budgeteer.services.BudgetService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BudgetServiceTest {

    @Test
    public void testGetBudgets(){
        BudgetService budgetService = Mockito.mock(BudgetService.class);

        Mockito.when(ApplicationService.currentUser()).thenReturn();

        List<Budget> budgets = budgetService.getBudgets();

        assertEquals(mockBudgets, budgets);
    }

}
