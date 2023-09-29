package com.group.budgeteer.services;

import com.group.budgeteer.models.ApplicationEntity;
import com.group.budgeteer.models.Budget;
import com.group.budgeteer.models.User;
import com.group.budgeteer.repositories.BudgetRepository;
import com.group.budgeteer.repositories.ExpenseRepository;
import com.group.budgeteer.security.AuthUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class BudgetService extends ApplicationService {

    private BudgetRepository budgetRepository;

    private ExpenseRepository expenseRepository;

    Logger logger = Logger.getLogger(BudgetService.class.getName()); //log messages related to the class

    @Autowired
    public BudgetService(BudgetRepository budgetRepository, ExpenseRepository expenseRepository) {
        this.budgetRepository = budgetRepository;
        this.expenseRepository = expenseRepository;
    }


    public List<Budget> getBudgets() { return (List<Budget>) currentUser(); }

    public Budget getBudget(UUID budgetId) { return budgetRepository.findById(budgetId).orElseThrow(() -> new RuntimeException("Budget with " + budgetId + " not found")); }

    public Budget createBudget(Budget budgetObject) { return budgetRepository.save(budgetObject); }


}
