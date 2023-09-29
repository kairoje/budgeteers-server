package com.group.budgeteer.services;

import com.group.budgeteer.models.Budget;
import com.group.budgeteer.models.Expense;
import com.group.budgeteer.repositories.BudgetRepository;
import com.group.budgeteer.repositories.ExpenseRepository;
import jdk.jfr.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;
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

    //GET ALL
    //GET ONE
    //POST/CREATE
    public Expense createExpense(UUID budgetId, Expense expenseObject) throws Exception {
        Budget budget = budgetRepository.findById(budgetId).orElseThrow();
        expenseObject.setBudget(budget);
        expenseObject.setUser(currentUser());
        budget.setBalance(budget.getBalance() - expenseObject.getPrice()); //TODO add validation for if balance is under $0
        budgetRepository.save(budget);
        return expenseRepository.save(expenseObject);
    }
    //PUT/UPDATE
    //DELETE

}
