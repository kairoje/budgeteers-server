package com.group.budgeteer.services;

import com.group.budgeteer.exceptions.DoesNotExistException;
import com.group.budgeteer.models.Budget;
import com.group.budgeteer.models.Expense;
import com.group.budgeteer.repositories.BudgetRepository;
import com.group.budgeteer.repositories.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * ExpenseService class hosts the business logic for ExpenseController class.
 */
@Service
public class ExpenseService extends ApplicationService {
    Logger logger = Logger.getLogger(ExpenseService.class.getName());

    private final ExpenseRepository expenseRepository;
    private final BudgetRepository budgetRepository;

    /**
     * Injects the ExpenseRepository and BudgetRepository
     * for managing and accessing expense and budget related data
     *
     * @param expenseRepository Is used by the ExpenseService
     * @param budgetRepository  Is used by the ExpenseService
     */
    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository, BudgetRepository budgetRepository) {
        this.expenseRepository = expenseRepository;
        this.budgetRepository = budgetRepository;
    }


    //GET ALL
    public List<Expense> getExpenses(UUID budgetId) throws DoesNotExistException {
        return expenseRepository.findByBudget_Id(budgetId).orElseThrow(
                () -> new DoesNotExistException(Budget.class, budgetId)
        );
    }


    @Deprecated
    //GET ONE
    public Expense getExpense(UUID budgetId, UUID expenseId) {
        Budget budget = budgetRepository.findById(budgetId).orElseThrow();

        return expenseRepository.findById(expenseId).orElseThrow();
    }

    //POST/CREATE
    //TODO Refactor as necessary
    public Expense createExpense(UUID budgetId, Expense expenseObject) throws DoesNotExistException {
        Budget budget = budgetRepository.findById(budgetId).orElseThrow(
                () -> new DoesNotExistException(Budget.class, budgetId)
        );
        budget.setBalance(budget.getBalance() - expenseObject.getPrice()); //TODO add validation for if balance is under $0
        expenseObject.setBudget(budget);
        budgetRepository.save(budget);
        return expenseRepository.save(expenseObject);

    }
    public Expense deleteExpense(UUID expenseId) throws DoesNotExistException {
        Expense expense = expenseRepository.findById(expenseId).orElseThrow(
                () -> new DoesNotExistException(Expense.class, expenseId)
        );
        Budget budget = expense.getBudget();
        budget.setBalance(budget.getBalance() + expense.getPrice());
        budgetRepository.save(budget);
        expenseRepository.delete(expense);

        return expense;
    }

    //PUT/UPDATE
    public Expense updateExpense(Expense expenseObject) throws DoesNotExistException {
        Expense existingExpense = expenseRepository.findById(expenseObject.getId()).orElseThrow(
                () -> new DoesNotExistException(Expense.class, expenseObject.getId())
        );
        Budget existingBudget = existingExpense.getBudget();
        if (existingExpense.getPrice() > expenseObject.getPrice()) {
            existingBudget.setBalance(existingBudget.getBalance() + (existingExpense.getPrice() - expenseObject.getPrice()));
        } else if (existingExpense.getPrice() < expenseObject.getPrice()) {
            existingBudget.setBalance(existingBudget.getBalance() - (expenseObject.getPrice() - existingExpense.getPrice()) );
        }
        budgetRepository.save(existingBudget);
        return expenseRepository.save(expenseObject);
    }
}

//TODO add docstrings
