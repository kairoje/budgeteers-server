package com.group.budgeteer.repository;

import com.group.budgeteer.models.Budget;
import com.group.budgeteer.models.User;
import com.group.budgeteer.repositories.BudgetRepository;
import com.group.budgeteer.repositories.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class BudgetRepositoryTest {

    @Autowired
    BudgetRepository budgetRepository;

    @Autowired
    UserRepository userRepository;

    User user = new User("sampleuser@example.com", "John", "Doe", "password123", null, null);

    LocalDate date = LocalDate.of(2023, Month.OCTOBER, 1);

    @Test
    public void budgetRepository_FindBudgetById(){

        Budget budget = Budget.builder()
                .balance(5000.00)
                .date(date)
                .expenses(null)
                .user(user)
                .build();

        user = userRepository.save(user);

        Budget savedBudget = budgetRepository.save(budget);

        assertNotNull(savedBudget.getId());

        Budget retrievedBudget = budgetRepository.findById(savedBudget.getId()).orElse(null);

        assertNotNull(retrievedBudget);
        assertEquals(5000.00, retrievedBudget.getBalance());
        assertEquals(LocalDate.of(2023, 10, 1), retrievedBudget.getDate());
    }
}
