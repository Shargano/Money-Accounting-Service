package com.money.accounting.backend.repository;

import com.money.accounting.backend.model.Limit;

import java.time.ZonedDateTime;
import java.util.List;

public interface LimitRepository extends MoneyRepository<Limit> {
    List<Limit> findAllByWalletUserIdAndDateFromBetweenOrDateToBetween(Integer userId,
                                                                       ZonedDateTime fromFrom,
                                                                       ZonedDateTime fromTo,
                                                                       ZonedDateTime toFrom,
                                                                       ZonedDateTime toTo);
}
