package com.group.budgeteer.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

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
    @Min(1)
    private double price;

    /**
     * Join the Expense model to the Budgets model.
     */
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "budget_id", nullable = false)
    private Budget budget;

    public Expense(UUID id, String name, String description, double price, Budget budget) {
        super(id);
        this.name = name;
        this.description = description;
        this.price = price;
        this.budget = budget;
    }

    @Override
    public Expense update(Expense payload) {
        setName(payload.getName());
        setPrice(payload.getPrice());
        setDescription(payload.getDescription());
        return this;
    }
}
