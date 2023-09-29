package com.group.budgeteer.services;

import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class BudgetService {

    private BudgetRepository budgetRepository;

    private ExpenseRepository expenseRepository;

    Logger logger = Logger.getLogger(BudgetService.class.getName()); //log messages related to the class
    


}
