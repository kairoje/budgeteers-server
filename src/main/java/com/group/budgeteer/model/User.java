package com.group.budgeteer.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The user class represents a user entity in the application.
 * It extends the application entity class to inherit common fields and functionality.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User extends ApplicationEntity<User> {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
}
