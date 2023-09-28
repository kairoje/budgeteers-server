package com.group.budgeteer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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
    /**
     * The email for user
     */
    @Column(unique = true, updatable = false)
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    /**
     * The first name for user
     */
    @Column
    private String firstName;

    /**
     * The last name for user
     */
    @Column
    private String lastName;

    /**
     * The password for user
     */
    @Column(nullable = false)
    @JsonProperty (access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "Password cannot be blank")
    private String password;
}
