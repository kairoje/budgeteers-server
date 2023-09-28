package com.group.budgeteer.repositories;

import com.group.budgeteer.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * The User Repository interface is responsible for defining database operations
 * for the User Entity, such as CRUD (CREATE, READ, UPDATE, DELETE) operations.
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);
}
