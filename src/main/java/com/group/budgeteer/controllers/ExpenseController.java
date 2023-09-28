package com.group.budgeteer.controllers;

import com.group.budgeteer.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * A controller class responsible for handling HTTP requests related to Expenses.
 * It provides endpoints to perform various CRUD operations.
 */
@RestController
@RequestMapping(path = "/api/v1")
public class ExpenseController {
    private final ExpenseService expenseService;

    /**
     * Injects the ExpenseService
     * @param expenseService Is used by the ExpenseController
     */
    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }
}
