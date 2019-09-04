package com.money.accounting.backend.repository;

import com.money.accounting.backend.model.Payment;
import com.money.accounting.backend.model.enums.PaymentType;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends MoneyRepository<Payment> {
    Optional<Payment> findByWalletUserIdAndGoalIdAndTypeAndParentPaymentIsNull(int userId, int goalId, PaymentType type);

    List<Payment> findAllByWalletIdAndParentPaymentIsNull(Integer walletId);
}
