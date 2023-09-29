package com.group.budgeteer.controllers;

import com.group.budgeteer.services.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/budget")
public class BudgetController {

    private BudgetService budgetService;

    @Autowired
    public void setBudgetService(BudgetService budgetService) { this.budgetService = budgetService; }
}
