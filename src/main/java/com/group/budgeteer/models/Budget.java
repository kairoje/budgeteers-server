package com.group.budgeteer.models;


import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;


/**
 * Represents a budget entity in the application.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "budgets")
public class Budget extends ApplicationEntity<Budget> {

    @Column(nullable = false)
    @NotNull(message = "Balance can not be empty")
    @Positive(message = "Balance must be greater than 0")
    private Double balance;

    @Column(nullable = false)
    @NotBlank(message = "Date can not be empty")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * The list of expenses associated with this budget.
     * Expenses are removed when the budget is removed (orphanRemoval).
     */
    @OneToMany(mappedBy = "expense", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Expense> expenses;
}

