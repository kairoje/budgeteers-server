package com.group.budgeteer.model;

import javax.persistence.MappedSuperclass;
import java.util.UUID;

/**
 * The application entity class serves as a base entity for all entities in the application,
 * providing common fields in functionality such as unique identifiers and timestamps
 * @param <T> The type of payload that can be used to update this entity.
 */
@MappedSuperclass
public class ApplicationEntity <T> {
    private UUID id;

    public ApplicationEntity() {
    }

    public ApplicationEntity(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
