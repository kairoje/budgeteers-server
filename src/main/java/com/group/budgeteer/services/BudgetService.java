package com.group.budgeteer.services;

import com.group.budgeteer.models.User;
import com.group.budgeteer.security.AuthUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class BudgetService {

    private BudgetRepository budgetRepository;

    private ExpenseRepository expenseRepository;

    Logger logger = Logger.getLogger(BudgetService.class.getName()); //log messages related to the class

    @Autowired
    public void setBudgetRepository(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    @Autowired
    public void setExpenseRepository(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    //Retrieves the currently logged-in user's data
    public static User getCurrentLoggedInUser(){
        AuthUserDetails authUserDetails = (AuthUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return authUserDetails.getUser();
    }

}
