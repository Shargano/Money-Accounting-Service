package com.money.accounting.backend.validator;

import com.money.accounting.backend.exception.MoneyFinanceErrorCode;
import com.money.accounting.backend.exception.MoneyFinanceException;
import com.money.accounting.backend.model.Loss;
import com.money.accounting.backend.model.User;
import com.money.accounting.backend.model.Wallet;
import com.money.accounting.backend.model.enums.State;
import com.money.accounting.backend.repository.WalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class WalletValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(WalletValidator.class);

    private final WalletRepository walletRepository;

    public WalletValidator(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public void checkWalletIsInactive(Wallet wallet) throws MoneyFinanceException {
        if (wallet.getState().equals(State.REMOVED)) {
            LOGGER.warn("Can't operate wallet - wallet have been archived");
            throw new MoneyFinanceException(MoneyFinanceErrorCode.WALLET_NOT_ACTIVE);
        }
    }

    public void checkWalletIsEnoughMoney(Wallet wallet, BigDecimal moneyCount) throws MoneyFinanceException {
        if (wallet.getMoneyCount().compareTo(moneyCount) < 0) {
            LOGGER.warn("Can't operate wallet - not enough money");
            throw new MoneyFinanceException(MoneyFinanceErrorCode.NOT_ENOUGH_MONEY);
        }
    }

    public void checkUserAlreadyHaveWallet(User user, Wallet wallet) throws MoneyFinanceException {
        Optional<Wallet> optional = walletRepository.findByUserIdAndName(user.getId(), wallet.getName());
        if (optional.isPresent()) {
            LOGGER.warn("Can't create wallet {} - already exists", wallet);
            throw new MoneyFinanceException(MoneyFinanceErrorCode.WALLET_ALREADY_EXISTS);
        }
    }

    public void checkWalletIsEnoughMoneyForEdit(Wallet wallet, Loss loss, BigDecimal moneyCount) throws MoneyFinanceException {
        if (moneyCount.compareTo(wallet.getMoneyCount().add(loss.getMoneyCount())) > 0) {
            LOGGER.warn("Can't create loss - not enough money in wallet");
            throw new MoneyFinanceException(MoneyFinanceErrorCode.NOT_ENOUGH_MONEY);
        }
    }
}
