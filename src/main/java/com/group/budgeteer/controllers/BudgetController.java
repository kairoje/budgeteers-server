package com.group.budgeteer.controllers;

import com.group.budgeteer.classes.APIResponse;
import com.group.budgeteer.models.Budget;
import com.group.budgeteer.services.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/budgets")
public class BudgetController {

    private BudgetService budgetService;

    @Autowired
    public void setBudgetService(BudgetService budgetService) { this.budgetService = budgetService; }

    @GetMapping
    public ResponseEntity<APIResponse<List<Budget>>> getBudgets(){
        return ResponseEntity
                .ok(new APIResponse<>(budgetService.getBudgets(), "success"));
    }

  
    @GetMapping("/{budgetId}")
    public ResponseEntity<APIResponse<Budget>> getBudget(@PathVariable( value = "budgetId") UUID budgetId){
        return ResponseEntity
                .ok(new APIResponse<>(budgetService.getBudget(budgetId), "success"));
    }

    @PostMapping
    public ResponseEntity<APIResponse<Budget>> createBudget(@Valid @RequestBody Budget budgetObject) {
        return ResponseEntity
                .ok(new APIResponse<>(budgetService.createBudget(budgetObject), "success"));
    }

    @PutMapping("/budgets")
    public ResponseEntity<APIResponse<Budget>> updateBudget(@Valid @RequestBody Budget budgetObject) {
        return ResponseEntity
                .ok(new APIResponse<>(budgetService.updateBudget(budgetObject), "success"));
    }

    @DeleteMapping("/{budgetId}")
    public ResponseEntity<APIResponse<APIResponse<Void>>> deleteBudget(@PathVariable(value = "budgetId") UUID id) {
        budgetService.deleteBudget(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(new APIResponse<>(null, "success"));
    }
}
