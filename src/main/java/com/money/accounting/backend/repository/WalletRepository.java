package com.money.accounting.backend.repository;

import com.money.accounting.backend.model.Wallet;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WalletRepository extends MoneyRepository<Wallet> {
    Optional<Wallet> findByUserIdAndId(Integer id, Integer walletId);

    Optional<Wallet> findByUserIdAndName(Integer id, String name);

    List<Wallet> findAllByUserId(Integer userId);
}
