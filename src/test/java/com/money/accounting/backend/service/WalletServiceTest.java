package com.money.accounting.backend.service;

import com.money.accounting.backend.dto.request.TransferRequest;
import com.money.accounting.backend.dto.request.WalletRequest;
import com.money.accounting.backend.exception.MoneyFinanceErrorCode;
import com.money.accounting.backend.exception.MoneyFinanceException;
import com.money.accounting.backend.model.Transfer;
import com.money.accounting.backend.model.User;
import com.money.accounting.backend.model.Wallet;
import com.money.accounting.backend.model.enums.State;
import com.money.accounting.backend.repository.UserRepository;
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
public class WalletServiceTest extends TestServiceBase {
    private int walletId = 99;
    private int walletFromId = 2;
    private int walletToId = 3;
    @Autowired
    private WalletService walletService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void addWallet() throws MoneyFinanceException {
        WalletRequest request = new WalletRequest("myWalletName", new BigDecimal(123.99));
        User currentUser = userRepository.getByLogin("somebody");
        Wallet response = walletService.create(request, currentUser);
        assertEquals(request.getName(), response.getName());
        assertEquals(request.getMoneyCount(), response.getMoneyCount());
    }

    @Test
    public void addAlreadyExistingWallet() {
        WalletRequest request = new WalletRequest("myWalletName", new BigDecimal(123.99));
        User currentUser = userRepository.getByLogin("somebody");
        Wallet wallet = getActiveWallet(currentUser.getId(), new BigDecimal(100.5));
        try {
            walletService.create(request, currentUser);
        } catch (MoneyFinanceException exception) {
            assertEquals(MoneyFinanceErrorCode.WALLET_ALREADY_EXISTS, exception.getErrorCode());
        }
    }

    @Test
    @Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/create-wallet-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/clear.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void makeTransfer() throws MoneyFinanceException {
        TransferRequest request = new TransferRequest(walletFromId, walletToId, new BigDecimal(20));
        User currentUser = userRepository.getByLogin("somebody");
        createWalletsForTransfer(currentUser.getId(), new BigDecimal(170));
        Transfer response = walletService.makeTransfer(request, currentUser);
        assertEquals(request.getMoneyCount(), response.getMoneyCount());
        assertEquals(0, response.getFrom().getMoneyCount().compareTo(new BigDecimal(150)));
        assertEquals(0, response.getTo().getMoneyCount().compareTo(new BigDecimal(20)));
    }

    @Test
    public void makeTransferNotExistingWallets() {
        TransferRequest request = new TransferRequest(walletFromId, walletToId, new BigDecimal(20));
        User currentUser = userRepository.getByLogin("somebody");
        try {
            walletService.makeTransfer(request, currentUser);
        } catch (MoneyFinanceException exception) {
            assertEquals(MoneyFinanceErrorCode.WALLET_NOT_EXIST, exception.getErrorCode());
        }
    }

    @Test
    @Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/create-wallet-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/clear.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void makeTransferNotEnoughMoney() {
        TransferRequest request = new TransferRequest(walletFromId, walletToId, new BigDecimal(200));
        User currentUser = userRepository.getByLogin("somebody");
        createWalletsForTransfer(currentUser.getId(), new BigDecimal(170));
        try {
            walletService.makeTransfer(request, currentUser);
        } catch (MoneyFinanceException exception) {
            assertEquals(MoneyFinanceErrorCode.NOT_ENOUGH_MONEY, exception.getErrorCode());
        }
    }

    @Test
    public void updateWallet() throws MoneyFinanceException {
        User currentUser = userRepository.getByLogin("somebody");
        Wallet wallet = getActiveWallet(currentUser.getId(), new BigDecimal(100.5));
        WalletRequest request = new WalletRequest("newNameForWallet", new BigDecimal(11.95));
        Wallet response = walletService.update(walletId, request, currentUser);
        assertEquals(request.getName(), response.getName());
        assertEquals(request.getMoneyCount(), response.getMoneyCount());
    }

    @Test
    public void updateInactiveWallet() {
        User currentUser = userRepository.getByLogin("somebody");
        Wallet wallet = getInactiveWallet(currentUser.getId(), new BigDecimal(100.5));
        WalletRequest request = new WalletRequest("newNameForWallet", new BigDecimal(11.95));
        try {
            walletService.update(walletId, request, currentUser);
        } catch (MoneyFinanceException exception) {
            assertEquals(MoneyFinanceErrorCode.WALLET_NOT_ACTIVE, exception.getErrorCode());
        }
    }

    @Test(expected = MoneyFinanceException.class)
    public void updateNotExistingWallet() throws MoneyFinanceException {
        User currentUser = userRepository.getByLogin("somebody");
        when(walletRepository.findByUserIdAndId(currentUser.getId(), 3)).thenReturn(Optional.empty()); // imitation of not existing wallet
        WalletRequest request = new WalletRequest("newNameForWallet", new BigDecimal(11.95));
        walletService.update(walletId, request, currentUser);
    }

    @Test
    public void archiveWallet() throws MoneyFinanceException {
        User currentUser = userRepository.getByLogin("somebody");
        Wallet wallet = getActiveWallet(currentUser.getId(), new BigDecimal(100.5));
        Wallet response = walletService.archive(walletId, currentUser);
        assertEquals(State.REMOVED, response.getState());
    }

    @Test
    public void archiveNotExistingWallet() {
        User currentUser = userRepository.getByLogin("somebody");
        try {
            walletService.archive(walletId, currentUser);
        } catch (MoneyFinanceException exception) {
            assertEquals(MoneyFinanceErrorCode.WALLET_NOT_EXIST, exception.getErrorCode());
        }
    }

    @Test
    public void archiveInactiveWallet() {
        User currentUser = userRepository.getByLogin("somebody");
        Wallet wallet = getInactiveWallet(currentUser.getId(), new BigDecimal(100.5));
        try {
            walletService.archive(walletId, currentUser);
        } catch (MoneyFinanceException exception) {
            assertEquals(MoneyFinanceErrorCode.WALLET_NOT_ACTIVE, exception.getErrorCode());
        }
    }

}
