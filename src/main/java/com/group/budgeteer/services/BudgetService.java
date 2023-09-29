package com.group.budgeteer.services;

import com.group.budgeteer.models.ApplicationEntity;
import com.group.budgeteer.models.User;
import com.group.budgeteer.security.AuthUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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

    //Retrieves the currently logged-in user's data
    public static User getCurrentLoggedInUser(){
        AuthUserDetails authUserDetails = (AuthUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return authUserDetails.getUser();
    }

    public List<Budget> getBudgets() {
        List<Budget> budgets = budgetRepository.findByUserId(BudgetService.getCurrentLoggedInUser().getId());
        if (budgets.isEmpty()) {
            throw new RuntimeException("No budgets available to display");
        } else {
            return budgets;
        }
    }

    public Optional<Budget> getBudget(budgetId) { //id being generated from uuid
        Budget budget = budgetRepository.findById(budgetId, BudgetService.getCurrentLoggedInUser().getId());
        if (budget == null) {
            throw new RuntimeException("Budget with " + budgetId + " not found");
        } else {
            return Optional.of(budget)
        }
    }

    public Budget createBudget(Budget budgetObject) {
        Budget budget = budgetRepository.findById(budgetObject.getId());
        if (budget != null) {
            throw new RuntimeException("Budget with " + budgetObject.getId + " already exists");
        } else {
            return budgetRepository.save(budgetObject);
        }
    }


}
