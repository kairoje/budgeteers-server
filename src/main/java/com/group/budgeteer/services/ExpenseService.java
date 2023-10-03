package com.group.budgeteer.services;

import com.group.budgeteer.exceptions.DoesNotExistException;
import com.group.budgeteer.models.Budget;
import com.group.budgeteer.models.Expense;
import com.group.budgeteer.repositories.BudgetRepository;
import com.group.budgeteer.repositories.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * ExpenseService class hosts the business logic for ExpenseController class.
 */
@Service
public class ExpenseService extends ApplicationService {
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

    /**
     * Retrieves a list of expenses associated with the specified budget UUID.
     *
     * @param budgetId The unique identifier (UUID) of the budget for which expenses are to be retrieved.
     * @return A list of expenses associated with the specified budget.
     */
    public List<Expense> getExpenses(UUID budgetId) throws DoesNotExistException {
        return expenseRepository.findByBudget_Id(budgetId).orElseThrow(
                () -> new DoesNotExistException(Budget.class, budgetId)
        );
    }

    /**
     * Creates an expense associated with the specified budget UUID.
     * @param budgetId The unique identifier (UUID) of the budget for which expenses are to be retrieved.
     * @param expenseObject The expense object containing details of the new expense.
     * @return The expense object that was created.
     */
    public Expense createExpense(UUID budgetId, Expense expenseObject) throws DoesNotExistException {
        Budget budget = budgetRepository.findById(budgetId).orElseThrow(
                () -> new DoesNotExistException(Budget.class, budgetId)
        );
        budget.setBalance(budget.getBalance() - expenseObject.getPrice());
        expenseObject.setBudget(budget);
        budgetRepository.save(budget);
        return expenseRepository.save(expenseObject);
    }

    /**
     * Updates the specified expense object.
     * @param expenseObject The expense object containing details of the expense to be updated.
     * @return The updated expense object.
     */
    public Expense updateExpense(Expense expenseObject) {
        Expense existingExpense = expenseRepository.findById(expenseObject.getId()).orElseThrow();
        Budget budget = existingExpense.getBudget();
        if (expenseObject.getPrice() < existingExpense.getPrice()) {
            budget.setBalance(budget.getBalance() + (existingExpense.getPrice() - expenseObject.getPrice()));
        } else if (existingExpense.getPrice() < expenseObject.getPrice()) {
            budget.setBalance(budget.getBalance() - (expenseObject.getPrice() - existingExpense.getPrice()));
        }
        budgetRepository.save(budget);
        existingExpense.update(expenseObject);
        return expenseRepository.save(existingExpense);
    }

    /**
     * Deletes an expense associated with the specified expense UUID.
     * @param expenseId The unique identifier (UUID) of the expense to be deleted.
     */
    public void deleteExpense(UUID expenseId){
        Expense expense = expenseRepository.findById(expenseId).orElseThrow(
                () -> new DoesNotExistException(Expense.class, expenseId)
        );
        Budget budget = expense.getBudget();
        budget.setBalance(budget.getBalance() + expense.getPrice());
        budgetRepository.save(budget);
        expenseRepository.delete(expense);
    }
}
