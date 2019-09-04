package com.money.accounting.backend.repository;

import com.money.accounting.backend.model.Loss;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface LossRepository extends MoneyRepository<Loss> {
    Optional<Loss> findByIdAndWalletUserId(Integer lossId, Integer userId);

    List<Loss> findAllByWalletUserIdAndDateTimeBetween(Integer userId, ZonedDateTime from, ZonedDateTime to);
}
