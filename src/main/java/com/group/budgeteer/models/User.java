package com.group.budgeteer.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

/**
 * The User class represents a User Entity in the application.
 * It extends the ApplicationEntity class to inherit common fields and functionality.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
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


    @OneToMany(mappedBy = "user", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Budget> budgets;

    public User(UUID id, String email, String firstName, String lastName, String password) {
        super(id);
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public User(UUID id) {
        super(id);
    }

    @Override
   public User update(User payload) {
        setEmail(payload.getEmail());
        setFirstName(payload.getFirstName());
        setLastName(payload.getLastName());
        return this;
    }
}
