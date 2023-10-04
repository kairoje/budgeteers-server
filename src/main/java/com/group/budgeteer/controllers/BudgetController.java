package com.group.budgeteer.controllers;

import com.group.budgeteer.classes.APIResponse;
import com.group.budgeteer.models.Budget;
import com.group.budgeteer.services.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;

/**
 * Controller class for managing budget-related operations.
 * This class defines REST endpoints for creating, retrieving, updating, and deleting budgets.
 */
@RestController
@RequestMapping("/api/v1/budgets")
public class BudgetController {

    private BudgetService budgetService;

    /**
     * Setter method to inject the BudgetService instance.
     * @param budgetService The BudgetService used for budget operations.
     */
    @Autowired
    public void setBudgetService(BudgetService budgetService) { this.budgetService = budgetService; }

    /**
     * Endpoint to retrieve a list of all budgets.
     * @return ResponseEntity containing APIResponse with a list of budgets and a success message.
     */
    @GetMapping
    public ResponseEntity<APIResponse<List<Budget>>> getBudgets(){
        return ResponseEntity
                .ok(new APIResponse<>(budgetService.getBudgets(), "success"));
    }

    /**
     * Endpoint to retrieve a budget by the given ID.
     * @param budgetId The ID of the budget to retrieve.
     * @return ResponseEntity containing APIResponse with the requested budget and a success message.
     */
    @GetMapping("/{budgetId}")
    public ResponseEntity<APIResponse<Budget>> getBudget(@PathVariable( value = "budgetId") UUID budgetId){
        return ResponseEntity
                .ok(new APIResponse<>(budgetService.getBudget(budgetId), "success"));
    }

    /**
     * Endpoint to create a new budget.
     * @param budgetObject The Budget object containing budget information to create.
     * @return ResponseEntity containing APIResponse with the created budget and a success message.
     */
    @PostMapping
    public ResponseEntity<APIResponse<Budget>> createBudget(@Valid @RequestBody Budget budgetObject) {
        return ResponseEntity
                .created(URI.create("/api/v1/budgets"))
                .body(new APIResponse<>(budgetService.createBudget(budgetObject), "success"));
    }

    /**
     * Endpoint to update an existing budget.
     * @param budgetObject The Budget object containing the budget information to update.
     * @return ResponseEntity containing APIResponse with the updated budget and a success message.
     */
    @PutMapping()
    public ResponseEntity<APIResponse<Budget>> updateBudget(@Valid @RequestBody Budget budgetObject) {
        return ResponseEntity
                .ok(new APIResponse<>(budgetService.updateBudget(budgetObject), "success"));
    }

    /**
     * Endpoint to delete a budget by its ID.
     * @param id The ID of the budget to delete.
     * @return ResponseEntity with a status indicating successful deletion and a success message.
     */
    @DeleteMapping("/{budgetId}")
    public ResponseEntity<APIResponse<APIResponse<Void>>> deleteBudget(@PathVariable(value = "budgetId") UUID id) {
        budgetService.deleteBudget(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(new APIResponse<>(null, "success"));
    }
}