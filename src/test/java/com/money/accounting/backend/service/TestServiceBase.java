package com.money.accounting.backend.service;

import com.money.accounting.backend.model.Loss;
import com.money.accounting.backend.model.Payment;
import com.money.accounting.backend.model.Profit;
import com.money.accounting.backend.model.Wallet;
import com.money.accounting.backend.model.enums.PaymentType;
import com.money.accounting.backend.model.enums.State;
import com.money.accounting.backend.repository.LossRepository;
import com.money.accounting.backend.repository.PaymentRepository;
import com.money.accounting.backend.repository.ProfitRepository;
import com.money.accounting.backend.repository.WalletRepository;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class TestServiceBase {
    @MockBean
    protected LossRepository lossRepository;

    @MockBean
    protected ProfitRepository profitRepository;

    @MockBean
    protected WalletRepository walletRepository;

    @MockBean
    protected PaymentRepository paymentRepository;

    protected Wallet getActiveWallet(int userId, BigDecimal moneyCount) {
        Wallet wallet = new Wallet("some", moneyCount);
        wallet.setState(State.ACTIVE);
        wallet.setId(99);
        when(walletRepository.findByUserIdAndId(userId, wallet.getId())).thenReturn(Optional.of(wallet));
        return wallet;
    }

    protected Wallet getInactiveWallet(int userId, BigDecimal moneyCount) {
        Wallet wallet = new Wallet("some", moneyCount);
        wallet.setState(State.REMOVED);
        wallet.setId(99);
        when(walletRepository.findByUserIdAndId(userId, wallet.getId())).thenReturn(Optional.of(wallet));
        return wallet;
    }

    protected Loss getActiveLoss(Integer userId) {
        Wallet wallet = getActiveWallet(userId, new BigDecimal(90.5));
        Loss loss = new Loss("myLoss", new BigDecimal(10), ZonedDateTime.now(ZoneId.of("UTC+6")), State.ACTIVE, wallet);
        loss.setId(33);
        Payment payment = new Payment(PaymentType.CREATE_LOSS, loss.getId(), ZonedDateTime.now(ZoneId.of("UTC+6")), new BigDecimal(10), wallet);
        when(paymentRepository.findByWalletUserIdAndGoalIdAndTypeAndParentPaymentIsNull(userId, loss.getId(), PaymentType.CREATE_LOSS)).thenReturn(Optional.of(payment));
        when(lossRepository.findByIdAndWalletUserId(loss.getId(), userId)).thenReturn(Optional.of(loss));
        return loss;
    }

    protected Loss getInactiveLoss(Integer userId) {
        Wallet wallet = getActiveWallet(userId, new BigDecimal(90.5));
        Loss loss = new Loss("myLoss", new BigDecimal(10), ZonedDateTime.now(ZoneId.of("UTC+6")), State.REMOVED, wallet);
        loss.setId(33);
        Payment payment = new Payment(PaymentType.CREATE_LOSS, loss.getId(), ZonedDateTime.now(ZoneId.of("UTC+6")), new BigDecimal(10), wallet);
        when(paymentRepository.findByWalletUserIdAndGoalIdAndTypeAndParentPaymentIsNull(userId, loss.getId(), PaymentType.CREATE_LOSS)).thenReturn(Optional.of(payment));
        when(lossRepository.findByIdAndWalletUserId(loss.getId(), userId)).thenReturn(Optional.of(loss));
        return loss;
    }

    protected Profit getActiveProfit(Integer userId) {
        Wallet wallet = getActiveWallet(userId, new BigDecimal(110.5));
        Profit profit = new Profit("myLoss", new BigDecimal(10), ZonedDateTime.now(ZoneId.of("UTC+6")), State.ACTIVE, wallet);
        profit.setId(44);
        Payment payment = new Payment(PaymentType.CREATE_PROFIT, profit.getId(), ZonedDateTime.now(ZoneId.of("UTC+6")), new BigDecimal(10), wallet);
        when(paymentRepository.findByWalletUserIdAndGoalIdAndTypeAndParentPaymentIsNull(userId, profit.getId(), PaymentType.CREATE_PROFIT)).thenReturn(Optional.of(payment));
        when(profitRepository.findByIdAndWalletUserId(profit.getId(), userId)).thenReturn(Optional.of(profit));
        return profit;
    }

    protected Profit getInactiveProfit(Integer userId) {
        Wallet wallet = getActiveWallet(userId, new BigDecimal(110.5));
        Profit profit = new Profit("myLoss", new BigDecimal(10), ZonedDateTime.now(ZoneId.of("UTC+6")), State.REMOVED, wallet);
        profit.setId(44);
        Payment payment = new Payment(PaymentType.CREATE_PROFIT, profit.getId(), ZonedDateTime.now(ZoneId.of("UTC+6")), new BigDecimal(10), wallet);
        when(paymentRepository.findByWalletUserIdAndGoalIdAndTypeAndParentPaymentIsNull(userId, profit.getId(), PaymentType.CREATE_PROFIT)).thenReturn(Optional.of(payment));
        when(profitRepository.findByIdAndWalletUserId(profit.getId(), userId)).thenReturn(Optional.of(profit));
        return profit;
    }

    protected void createWalletsForTransfer(int userId, BigDecimal moneyCount) {
        Wallet walletFrom = new Wallet("some", moneyCount);
        walletFrom.setState(State.ACTIVE);
        walletFrom.setId(2);
        when(walletRepository.findByUserIdAndId(userId, walletFrom.getId())).thenReturn(Optional.of(walletFrom));
        Wallet walletTo = new Wallet("some", new BigDecimal(0));
        walletTo.setState(State.ACTIVE);
        walletTo.setId(3);
        when(walletRepository.findByUserIdAndId(userId, walletTo.getId())).thenReturn(Optional.of(walletTo));
    }

}
