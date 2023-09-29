package com.group.budgeteer.seed;

import com.group.budgeteer.models.Budget;
import com.group.budgeteer.models.Expense;
import com.group.budgeteer.models.User;
import com.group.budgeteer.repositories.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class BudgetData implements CommandLineRunner {

    LocalDate localDate = LocalDate.ofEpochDay(2023-9-29);


    User mockUser = new User("email@email.com", "First", "Last", "password");

    List<Expense> mockExpense = new ArrayList<>();
    Budget budget = new Budget(5000.0, localDate, mockUser, mockExpense);
    Expense expense1 = new Expense("Rent", "Payment for rent", 1000.00, mockUser, budget);

    @Autowired
    BudgetRepository budgetRepository;
    @Override
    public void run(String... args) throws Exception {
        loadUserData();
    }

    public void loadUserData() {
        if (budgetRepository.count() == 0) {
            budgetRepository.save(budget);
        }
    }
}
