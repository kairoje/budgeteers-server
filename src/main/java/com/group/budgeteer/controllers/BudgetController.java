package com.group.budgeteer.controllers;

import com.group.budgeteer.classes.APIResponse;
import com.group.budgeteer.models.Budget;
import com.group.budgeteer.services.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/budget")
public class BudgetController {

    private BudgetService budgetService;

    @Autowired
    public void setBudgetService(BudgetService budgetService) { this.budgetService = budgetService; }

    @GetMapping("/budgets")
    public ResponseEntity<APIResponse<List<Budget>>> getBudgets(){
        return ResponseEntity
                .ok(new APIResponse<>(budgetService.getBudgets(), "success"));
    }

    @GetMapping("/budgets/{budgetId}")
    public ResponseEntity<APIResponse<Budget>> getBudget(@Valid @RequestBody Budget budgetObject){
        return ResponseEntity
                .ok(new APIResponse<>(budgetService.getBudget(budgetObject.getId()), "success"));
    }

    @PostMapping("/budgets")
    public ResponseEntity<APIResponse<Budget>> createBudget(@Valid @RequestBody Budget budgetObject) {
        return ResponseEntity
                .ok(new APIResponse<>(budgetService.createBudget(budgetObject), "success"));
    }
}
