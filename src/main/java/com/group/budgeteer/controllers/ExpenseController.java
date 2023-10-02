package com.group.budgeteer.controllers;

import com.group.budgeteer.classes.APIResponse;
import com.group.budgeteer.models.Expense;
import com.group.budgeteer.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * A controller class responsible for handling HTTP requests related to Expenses.
 * It provides endpoints to perform various CRUD operations.
 */
@RestController
@RequestMapping(path = "/api/v1/budgets")
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

    static HashMap<String, Object> result = new HashMap<>();
    static HashMap<String, Object> message = new HashMap<>();

    //HELLO WORLD
    @GetMapping(path = "/hello-world")
    public String helloWorld() {
        return "hello World";
    }

    //GET ALL
    @GetMapping(path = "/{budgetId}/expenses") //http://localhost:4000/api/v1/budgets/1/expenses
    public ResponseEntity<?> getExpenses(@PathVariable(value = "budgetId") UUID budgetId) {
        List<Expense> expenses = expenseService.getExpenses(budgetId);
        if (expenses.isEmpty()) {
            message.put("message", "cannot find any expenses");
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            message.put("message", "success");
            message.put("data", expenses);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    //GET ONE
    @GetMapping(path = "/{budgetId}/expenses/{expenseId}") //http://localhost:4000/api/v1/budgets/1/expenses/1
    public ResponseEntity<?> getExpense(@PathVariable(value = "budgetId") UUID budgetId, @PathVariable(value = "expenseId") UUID expenseId) {
        Expense expense = expenseService.getExpense(budgetId, expenseId);
        if (expense != null) {
            message.put("message", "success");
            message.put("data", expense);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            message.put("message", "cannot find expense with id " + expenseId);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }

    //POST/CREATE
    @PostMapping(path = "/{budgetId}/expenses") //http://localhost:4000/api/v1/budgets/{budgetId}/expenses
    public ResponseEntity<?> createExpense(@PathVariable(value = "budgetId") UUID budgetId, @RequestBody Expense expenseObject) {
        Expense newExpense = expenseService.createExpense(budgetId, expenseObject);
        if (newExpense != null) {
            message.put("message", "success");
            message.put("data", newExpense);
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } else {
            message.put("message", "unable to create an expense at this time");
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    @PutMapping(path = "/{budgetId}/expenses/{expenseId}")
    public ResponseEntity<?> updateExpense(@PathVariable(value = "budgetId") UUID budgetId, @PathVariable(value = "expenseId") UUID expenseId, @RequestBody Expense expenseObject) {
        Expense expenseToUpdate = expenseService.updateExpense(budgetId, expenseId, expenseObject);
        if (expenseToUpdate == null) {
            message.put("message", "cannot find expense with id " + expenseId);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            message.put("message", "success");
            message.put("data", expenseToUpdate.getUpdatedAt());
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    @DeleteMapping(path="/expenses/{expenseId}")
    public ResponseEntity<APIResponse<Void>> deleteExpense(@PathVariable(value = "expenseId") UUID id ){
        expenseService.deleteExpense(id);
        return ResponseEntity
                .ok(new APIResponse<>(null, "success"));
    }
}

//TODO add docstring
