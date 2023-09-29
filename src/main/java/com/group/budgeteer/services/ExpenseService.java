package com.group.budgeteer.services;

import com.group.budgeteer.models.Budget;
import com.group.budgeteer.models.Expense;
import com.group.budgeteer.repositories.BudgetRepository;
import com.group.budgeteer.repositories.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.NoSuchElementException;
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
    public List<Expense> getExpenses(UUID budgetId){
        return expenseRepository.findAllById(budgetId);
    }

    //GET ONE
    public Expense getExpense(UUID budgetId, UUID expenseId){
        Budget budget = budgetRepository.findById(budgetId).orElseThrow();

        return  expenseRepository.findById(expenseId).orElseThrow();
    }

    //POST/CREATE
    public Expense createExpense(UUID budgetId, Expense expenseObject) {
        Budget budget = budgetRepository.findById(budgetId).orElseThrow();
        expenseObject.setBudget(budget);
        expenseObject.setUser(currentUser());
        budget.setBalance(budget.getBalance() - expenseObject.getPrice()); //TODO add validation for if balance is under $0
        budgetRepository.save(budget);
        return expenseRepository.save(expenseObject);
    }

    //PUT/UPDATE
    public Expense updateExpense(@PathVariable String budgetId, @PathVariable String expenseId, Expense expenseObject){


    }

//TODO DELETE
//TODO add docstrings
