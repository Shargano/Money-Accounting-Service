package com.money.accounting.backend.service;

import com.money.accounting.backend.dto.request.EditOperationRequest;
import com.money.accounting.backend.dto.request.LossRequest;
import com.money.accounting.backend.exception.MoneyFinanceErrorCode;
import com.money.accounting.backend.exception.MoneyFinanceException;
import com.money.accounting.backend.model.Loss;
import com.money.accounting.backend.model.User;
import com.money.accounting.backend.model.Wallet;
import com.money.accounting.backend.model.enums.State;
import com.money.accounting.backend.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/clear.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class LossServiceTest extends TestServiceBase {
    private int lossId = 33;
    private int walletId = 99;

    @Autowired
    private LossService lossService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void addLoss() throws MoneyFinanceException {
        LossRequest request = new LossRequest(walletId, "lossName", new BigDecimal(10));
        User currentUser = userRepository.getByLogin("somebody");
        Wallet wallet = getActiveWallet(currentUser.getId(), new BigDecimal(100.5));
        Loss response = lossService.create(request, currentUser);
        assertEquals(request.getName(), response.getName());
        assertEquals(request.getMoneyCount(), response.getMoneyCount());
        Assert.assertEquals(State.ACTIVE, response.getState());
        assertEquals(request.getWalletId(), (int) response.getWallet().getId());
        assertEquals(new BigDecimal(90.5), response.getWallet().getMoneyCount());
    }

    @Test
    public void addLossToNotExistingWallet() {
        LossRequest request = new LossRequest(walletId, "lossName", new BigDecimal(10));
        User currentUser = userRepository.getByLogin("somebody");
        try {
            lossService.create(request, currentUser);
        } catch (MoneyFinanceException exception) {
            Assert.assertEquals(MoneyFinanceErrorCode.WALLET_NOT_EXIST, exception.getErrorCode());
        }
    }

    @Test
    public void addLossToInactiveWallet() {
        LossRequest request = new LossRequest(walletId, "lossName", new BigDecimal(10));
        User currentUser = userRepository.getByLogin("somebody");
        Wallet wallet = getInactiveWallet(currentUser.getId(), new BigDecimal(100.5));
        try {
            lossService.create(request, currentUser);
        } catch (MoneyFinanceException exception) {
            assertEquals(MoneyFinanceErrorCode.WALLET_NOT_ACTIVE, exception.getErrorCode());
        }
    }

    @Test
    public void addLossTooMuchMoney() {
        LossRequest request = new LossRequest(walletId, "lossName", new BigDecimal(1000));
        User currentUser = userRepository.getByLogin("somebody");
        Wallet wallet = getActiveWallet(currentUser.getId(), new BigDecimal(100.5));
        try {
            lossService.create(request, currentUser);
        } catch (MoneyFinanceException exception) {
            assertEquals(MoneyFinanceErrorCode.NOT_ENOUGH_MONEY, exception.getErrorCode());
        }
    }

    @Test
    public void updateLoss() throws MoneyFinanceException {
        User currentUser = userRepository.getByLogin("somebody");
        EditOperationRequest request = new EditOperationRequest("newNameFOrLosS", new BigDecimal(5.5));
        Loss loss = getActiveLoss(currentUser.getId());
        Loss response = lossService.update(lossId, request, currentUser);
        assertEquals(request.getName(), response.getName());
        assertEquals(request.getMoneyCount(), response.getMoneyCount());
        assertTrue(response.getWallet().getMoneyCount().compareTo(new BigDecimal(95)) == 0);
    }

    @Test
    public void updateInactiveLoss() {
        User currentUser = userRepository.getByLogin("somebody");
        EditOperationRequest request = new EditOperationRequest("newNameFOrLosS", new BigDecimal(5.5));
        Loss loss = getInactiveLoss(currentUser.getId());
        try {
            lossService.update(lossId, request, currentUser);
        } catch (MoneyFinanceException exception) {
            assertEquals(MoneyFinanceErrorCode.LOSS_NOT_ACTIVE, exception.getErrorCode());
        }
    }

    @Test
    public void updateNotExistingLoss() {
        User currentUser = userRepository.getByLogin("somebody");
        when(lossRepository.findByIdAndWalletUserId(lossId, currentUser.getId())).thenReturn(Optional.empty()); // imitation of not existing wallet
        EditOperationRequest request = new EditOperationRequest("newNameFOrLosS", new BigDecimal(5.5));
        try {
            lossService.update(lossId, request, currentUser);
        } catch (MoneyFinanceException exception) {
            assertEquals(MoneyFinanceErrorCode.LOSS_NOT_EXIST, exception.getErrorCode());
        }
    }

    @Test
    public void updateLossSetTooMuchMoney() {
        User currentUser = userRepository.getByLogin("somebody");
        EditOperationRequest request = new EditOperationRequest("newNameFOrLosS", new BigDecimal(555));
        Loss loss = getActiveLoss(currentUser.getId());
        try {
            lossService.update(lossId, request, currentUser);
        } catch (MoneyFinanceException exception) {
            assertEquals(MoneyFinanceErrorCode.NOT_ENOUGH_MONEY, exception.getErrorCode());
        }
    }


}