package com.group.budgeteer.services;

import org.springframework.beans.factory.annotation.Autowired;
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

}
