package com.group.budgeteer.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * The Expense class represents an expense by User in the application.
 * It extends the ApplicationEntity class to inherit common fields and functionality.
 */
@Entity
@Table(name = "expenses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Expense extends ApplicationEntity<Expense> {

    /**
     * The name of the expense.
     */
    @Column(nullable = false)
    @NotBlank(message = "Expense name cannot be blank")
    private String name;

    /**
     * A description of the expense.
     */
    @Column
    private String description;

    /**
     * The price of the expense.
     */
    @Column
    @NotNull(message = "Expense price cannot be blank")
    @Min(0)
    private double price;
}
