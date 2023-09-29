package com.group.budgeteer.services;

import com.group.budgeteer.repositories.BudgetRepository;
import com.group.budgeteer.repositories.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

/**
 * ExpenseService class hosts the business logic for ExpenseController class.
 */
@Service
public class ExpenseService extends ApplicationService{
    Logger logger = Logger.getLogger(ExpenseService.class.getName());

    private final ExpenseRepository expenseRepository;
    private final BudgetRepository budgetRepository;

    /**
     * Injects the ExpenseRepository and BudgetRepository
     * for managing and accessing expense and budget related data
     *
     * @param expenseRepository Is used by the ExpenseService
     * @param budgetRepository Is used by the ExpenseService
     */
    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository, BudgetRepository budgetRepository) {
        this.expenseRepository = expenseRepository;
        this.budgetRepository = budgetRepository;
    }


}
