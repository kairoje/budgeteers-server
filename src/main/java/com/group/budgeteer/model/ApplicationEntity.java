package com.group.budgeteer.model;

import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
public class ApplicationEntity <T> {
    private UUID id;
}
