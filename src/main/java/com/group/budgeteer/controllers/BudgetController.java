package com.group.budgeteer.controllers;

import com.group.budgeteer.services.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/budget")
public class BudgetController {

    private BudgetService budgetService;

    @Autowired
    public void setBudgetService(BudgetService budgetService) { this.budgetService = budgetService; }

    @GetMapping("/budgets")
    public List<Budget> getBudgets() { return budgetService.getBudgets(); }

    @GetMapping("/budgets/{budgetId}") {
        public Optional<Budget> getBudget(@PathVariable(value = "budgetId") budgetId) {
            return budgetService.getBudget(budgetId);
        }
    }
}
