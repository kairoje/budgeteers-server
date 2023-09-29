package com.group.budgeteer.controllers;

import com.group.budgeteer.models.Expense;
import com.group.budgeteer.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * A controller class responsible for handling HTTP requests related to Expenses.
 * It provides endpoints to perform various CRUD operations.
 */
@RestController
@RequestMapping(path = "/api/v1")
public class ExpenseController {
    Logger logger = Logger.getLogger(ExpenseController.class.getName());

    private final ExpenseService expenseService;

    /**
     * Injects the ExpenseService
     * @param expenseService Is used by the ExpenseController
     */
    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    //GET ALL
    @GetMapping(path = "/api/v1/budgets/{budgetId}/expenses") //http://localhost:4000/api/v1/budgets/1/expenses
    public List<Expense> getExpenses(@PathVariable UUID budgetId){
        return expenseService.getExpenses(budgetId);
    }

    //GET ONE
    @GetMapping(path = "/api/v1/budgets/{budgetId}/expenses/{expenseId}") //http://localhost:4000/api/v1/budgets/1/expenses/1
    public Expense getExpense(@PathVariable(value = "budgetId") UUID budgetId, @PathVariable(value = "expenseId") UUID expenseId){
        return expenseService.getExpense(budgetId, expenseId);
    }

    //POST/CREATE
    @PostMapping(path = "/api/v1/budgets/{budgetId}/expenses") //http://localhost:4000/api/v1/budgets/{budgetId}/expenses
    public Expense createExpense(@PathVariable(value = "budgetId") UUID budgetId, @RequestBody Expense expenseObject) {
        return expenseService.createExpense(budgetId, expenseObject);
    }
}

//TODO add docstring
