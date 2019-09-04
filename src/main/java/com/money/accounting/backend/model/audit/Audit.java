package com.money.accounting.backend.model.audit;

import com.money.accounting.backend.model.MoneyEntity;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;

@Entity
@Immutable
public class Audit extends MoneyEntity {
    private String objectState;

    public Audit() {
    }

    public String getObjectState() {
        return objectState;
    }

    public void setObjectState(String objectState) {
        this.objectState = objectState;
    }
}
