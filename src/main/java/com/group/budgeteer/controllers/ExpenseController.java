package com.group.budgeteer.controllers;

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

    static HashMap<String, Object> result = new HashMap<>();
    static HashMap<String, Object> message = new HashMap<>();

    //HELLO WORLD
    @GetMapping(path = "/hello-world")
    public String helloWorld() {
        return "hello World";
    }

    //GET ALL
    @GetMapping(path = "/api/v1/budgets/{budgetId}/expenses") //http://localhost:4000/api/v1/budgets/1/expenses
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
//    public List<Expense> getExpenses(@PathVariable UUID budgetId){
//        List<Expense> expenseList = expenseService.getExpenses(budgetId);
//        return expenseService.getExpenses(budgetId);
//    }

    //GET ONE
    @GetMapping(path = "/api/v1/budgets/{budgetId}/expenses/{expenseId}") //http://localhost:4000/api/v1/budgets/1/expenses/1
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
//    public Expense getExpense(@PathVariable(value = "budgetId") UUID budgetId, @PathVariable(value = "expenseId") UUID expenseId){
//        return expenseService.getExpense(budgetId, expenseId);
//    }

    //POST/CREATE
    @PostMapping(path = "/api/v1/budgets/{budgetId}/expenses") //http://localhost:4000/api/v1/budgets/{budgetId}/expenses
    public Expense createExpense(@PathVariable(value = "budgetId") UUID budgetId, @RequestBody Expense expenseObject) {
        return expenseService.createExpense(budgetId, expenseObject);
    }

    @PutMapping(path = "/api/v1/budgets/{budgetId}/expenses/{expenseId}")
    public Expense updateExpense(@PathVariable UUID budgetId, @PathVariable UUID expenseId, Expense expenseObject){
        return expenseService.updateExpense(budgetId, expenseId, expenseObject);
    }
}

//TODO add docstring
