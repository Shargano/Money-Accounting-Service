package com.money.accounting.backend.repository;

import com.money.accounting.backend.model.Profit;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface ProfitRepository extends MoneyRepository<Profit> {
    Optional<Profit> findByIdAndWalletUserId(Integer profitId, Integer userId);

    List<Profit> findAllByWalletUserIdAndDateTimeBetween(Integer userId, ZonedDateTime from, ZonedDateTime to);
}