package com.group.budgeteer.repositories;

import com.group.budgeteer.models.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository interface for managing budget data in the database.
 *
 * This interface extends JpaRepository, which provides CRUD (Create, Read, Update, Delete) operations
 * for the Budget entity. Spring Data JPA will automatically generate the implementation for these methods.
 *
 * @see Budget
 * @author Julian Smith
 * @date 09/28/2023
 */
@Repository
public interface BudgetRepository extends JpaRepository<Budget, UUID> {
    Budget findByIdAndUserId(UUID budgetIt, UUID userId);
}
