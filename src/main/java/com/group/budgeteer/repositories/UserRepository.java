package com.group.budgeteer.repositories;

import com.group.budgeteer.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * The User Repository interface is responsible for defining database operations
 * for the User Entity, such as CRUD (CREATE, READ, UPDATE, DELETE) operations.
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Checks if a user with a given email exists.
     * @param email The user email address to check.
     * @return true, if a user with the specified email exists, otherwise false.
     */
    boolean existsByEmail(String email);

    /**
     * Finds a user by their email, if it exists.
     * @param email The user email address to check.
     * @return An optional user if found.
     */
    Optional<User> findByEmail(String email);
}
