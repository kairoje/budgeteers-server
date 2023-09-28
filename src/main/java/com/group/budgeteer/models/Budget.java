package com.group.budgeteer.models;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "budgets")
public class Budget extends ApplicationEntity<Budget>{

    @Column(nullable = false)
    @NotNull(message = "Balance can not empty")
    @Positive(message = "Balance must be greater than 0")
    private Double balance;

    @Column(nullable = false)
    @NotBlank(message = "Date can not empty")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;
}
