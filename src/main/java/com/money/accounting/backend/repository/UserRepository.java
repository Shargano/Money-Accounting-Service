package com.money.accounting.backend.repository;

import com.money.accounting.backend.model.User;

import java.util.Optional;

public interface UserRepository extends MoneyRepository<User> {
    User getByLogin(String login);

    Optional<User> findByEmail(String email);
}
