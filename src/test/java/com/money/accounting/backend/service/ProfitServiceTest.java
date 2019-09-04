package com.money.accounting.backend.service;

import com.money.accounting.backend.dto.request.EditOperationRequest;
import com.money.accounting.backend.dto.request.ProfitRequest;
import com.money.accounting.backend.exception.MoneyFinanceErrorCode;
import com.money.accounting.backend.exception.MoneyFinanceException;
import com.money.accounting.backend.model.Profit;
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
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/clear.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ProfitServiceTest extends TestServiceBase {
    private int profitId = 44;
    @Autowired
    private ProfitService profitService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void addProfit() throws MoneyFinanceException {
        ProfitRequest request = new ProfitRequest(99, "someGoodProfit", new BigDecimal(123.5));
        User currentUser = userRepository.getByLogin("somebody");
        Wallet wallet = new Wallet("some", new BigDecimal(10.9));
        wallet.setState(State.ACTIVE);
        wallet.setId(99);
        when(walletRepository.findByUserIdAndId(currentUser.getId(), 99)).thenReturn(Optional.of(wallet));
        Profit response = profitService.create(request, currentUser);
        assertEquals(request.getName(), response.getName());
        assertEquals(request.getMoneyCount(), response.getMoneyCount());
        assertEquals(State.ACTIVE, response.getState());
        assertEquals(request.getWalletId(), (int) response.getWallet().getId());
    }

    @Test
    public void addProfitToNotExistingWallet() {
        ProfitRequest request = new ProfitRequest(99, "someGoodProfit", new BigDecimal(123.5));
        User currentUser = userRepository.getByLogin("somebody");
        try {
            profitService.create(request, currentUser);
        } catch (MoneyFinanceException exception) {
            Assert.assertEquals(MoneyFinanceErrorCode.WALLET_NOT_EXIST, exception.getErrorCode());
        }
    }

    @Test
    public void addProfitToInactiveWallet() {
        ProfitRequest request = new ProfitRequest(99, "someGoodProfit", new BigDecimal(123.5));
        User currentUser = userRepository.getByLogin("somebody");
        Wallet wallet = new Wallet("some", new BigDecimal(10.9));
        wallet.setState(State.REMOVED);
        when(walletRepository.findByUserIdAndId(currentUser.getId(), 99)).thenReturn(Optional.of(wallet));
        try {
            profitService.create(request, currentUser);
        } catch (MoneyFinanceException exception) {
            assertEquals(MoneyFinanceErrorCode.WALLET_NOT_ACTIVE, exception.getErrorCode());
        }
    }

    @Test
    public void updateProfit() throws MoneyFinanceException {
        User currentUser = userRepository.getByLogin("somebody");
        EditOperationRequest request = new EditOperationRequest("newNameFOrProfit", new BigDecimal(55));
        Profit profit = getActiveProfit(currentUser.getId());
        Profit response = profitService.update(profitId, request, currentUser);
        assertEquals(request.getName(), response.getName());
        assertEquals(request.getMoneyCount(), response.getMoneyCount());
        assertEquals(0, response.getWallet().getMoneyCount().compareTo(new BigDecimal(155.5)));
    }

    @Test
    public void updateInactiveProfit() {
        User currentUser = userRepository.getByLogin("somebody");
        EditOperationRequest request = new EditOperationRequest("newNameFOrProfit", new BigDecimal(55));
        Profit profit = getInactiveProfit(currentUser.getId());
        try {
            profitService.update(profitId, request, currentUser);
        } catch (MoneyFinanceException exception) {
            assertEquals(MoneyFinanceErrorCode.PROFIT_NOT_ACTIVE, exception.getErrorCode());
        }
    }

    @Test
    public void updateNotExistingProfit() {
        User currentUser = userRepository.getByLogin("somebody");
        when(profitRepository.findByIdAndWalletUserId(profitId, currentUser.getId())).thenReturn(Optional.empty()); // imitation of not existing wallet
        EditOperationRequest request = new EditOperationRequest("newNameFOrProfit", new BigDecimal(55));
        try {
            profitService.update(profitId, request, currentUser);
        } catch (MoneyFinanceException exception) {
            assertEquals(MoneyFinanceErrorCode.PROFIT_NOT_EXIST, exception.getErrorCode());
        }
    }
}
