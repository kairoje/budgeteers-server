package com.group.budgeteer.controllers;

import com.group.budgeteer.classes.APIResponse;
import com.group.budgeteer.models.Expense;
import com.group.budgeteer.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * A controller class responsible for handling HTTP requests related to Expenses.
 * It provides endpoints that perform various CRUD operations.
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

    /**
     * Retrieves a list of expenses associated with the specified budget UUID.
     *
     * @param budgetId The budget ID for which expenses are to be retrieved.
     * @return A message and a list of expenses if found, or a message with a NOT_FOUND status if no expenses are found.
     */
    @GetMapping(path = "/{budgetId}/expenses")
    public ResponseEntity<APIResponse<List<Expense>>> getExpenses(@PathVariable(value = "budgetId") UUID budgetId) {
        return ResponseEntity
                .ok(new APIResponse<>(expenseService.getExpenses(budgetId), "success"));
    }

    /**
     * Creates a new expense associated with the specified budget ID.
     *
     * @param budgetId The budget ID to which the expense will be associated.
     * @param expenseObject The Expense object containing details of the new expense.
     * @return A message and the newly created expense if successful, or an error message with a status code.
     */
    @PostMapping(path = "/{budgetId}/expenses")
    public ResponseEntity<APIResponse<Expense>> createExpense(@PathVariable(value = "budgetId") UUID budgetId,@Valid @RequestBody Expense expenseObject) {
        return  ResponseEntity
                .created(URI.create("/api/v1/budgets/" + budgetId + "/expenses"))
                .body(new APIResponse<>(expenseService.createExpense(budgetId, expenseObject), "success"));
    }

    /**
     * Updates an existing expense record with the provided Expense object.
     *
     * @param expenseObject The Expense object containing updated expense data.
     * @return A ResponseEntity with an APIResponse object indicating the status of the update operation
     *         and the updated expense if successful.
     */
    @PutMapping(path = "/expenses")
    public ResponseEntity<APIResponse<Expense>> updateExpense(@Valid @RequestBody Expense expenseObject) {
        return ResponseEntity.ok(new APIResponse<>(expenseService.updateExpense(expenseObject), "success"));
    }

    /**
     * Deletes an existing expense record with the provided Expense ID.
     * @param expenseId The expense Id to be deleted.
     * @return A ResponseEntity with a status of HTTP 204 No Content upon successful deletion.
     */
    @DeleteMapping(path="/expenses/{expenseId}")
    public ResponseEntity<APIResponse<Void>> deleteExpense(@PathVariable(value = "expenseId") UUID expenseId ){
        expenseService.deleteExpense(expenseId);
        return ResponseEntity
                .noContent().build();
    }
}
