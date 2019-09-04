package com.money.accounting.backend.repository;

import com.money.accounting.backend.model.MoneyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoneyRepository<Entity extends MoneyEntity> extends JpaRepository<Entity, Integer> {
}