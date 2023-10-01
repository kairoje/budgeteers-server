package com.group.budgeteer;

import com.group.budgeteer.repositories.BudgetRepository;
import com.group.budgeteer.repositories.ExpenseRepository;
import com.group.budgeteer.services.BudgetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//import static org.junit.jupiter.api.Assertions.*;

class BudgetServiceTest {

    private ExpenseRepository expenseRepository;

    private BudgetRepository budgetRepository;

    private BudgetService underTest;

    @BeforeEach
    void setUp() {
        underTest = new BudgetService(budgetRepository, expenseRepository);
    }

    @Test
    void canGetBudgets() {

    }

    @Test
    void getBudget() {
    }

    @Test
    void createBudget() {
    }

    @Test
    void updateBudget() {
    }

    @Test
    void deleteBudget() {
    }
}