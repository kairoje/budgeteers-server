package com.group.budgeteer.repositories;

import com.group.budgeteer.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    public boolean existsByEmail(String email);
}
