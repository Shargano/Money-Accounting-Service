package com.money.accounting.backend.service;

import com.money.accounting.backend.converter.ProfitConverter;
import com.money.accounting.backend.dto.request.EditOperationRequest;
import com.money.accounting.backend.dto.request.ProfitRequest;
import com.money.accounting.backend.exception.MoneyFinanceErrorCode;
import com.money.accounting.backend.exception.MoneyFinanceException;
import com.money.accounting.backend.model.Profit;
import com.money.accounting.backend.model.User;
import com.money.accounting.backend.model.Wallet;
import com.money.accounting.backend.model.enums.State;
import com.money.accounting.backend.repository.ProfitRepository;
import com.money.accounting.backend.validator.ProfitValidator;
import com.money.accounting.backend.validator.WalletValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfitService extends EntityService<Profit, ProfitRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProfitService.class);

    private final WalletValidator walletValidator;
    private final WalletService walletService;
    private final ProfitRepository profitRepository;
    private final ProfitValidator profitValidator;
    private final PaymentService paymentService;
    private final AuditService auditService;
    private final ProfitConverter converter;

    public ProfitService(WalletValidator walletValidator,
                         WalletService walletService,
                         ProfitRepository profitRepository,
                         ProfitValidator profitValidator,
                         PaymentService paymentService,
                         AuditService auditService,
                         ProfitConverter converter) {
        this.walletValidator = walletValidator;
        this.walletService = walletService;
        this.profitRepository = profitRepository;
        this.profitValidator = profitValidator;
        this.paymentService = paymentService;
        this.auditService = auditService;
        this.converter = converter;
    }

    @Transactional
    public Profit create(ProfitRequest request, User user) throws MoneyFinanceException {
        Wallet wallet = walletService.findByUserIdAndId(user.getId(), request.getWalletId());
        walletValidator.checkWalletIsInactive(wallet);
        Profit profit = inflateEntity(request, new Profit());
        profit.setDateTime(ZonedDateTime.now(ZoneId.of(user.getTimeZone())));
        profit.setWallet(wallet);
        this.save(profit);
        wallet.setMoneyCount(wallet.getMoneyCount().add(profit.getMoneyCount()));
        walletService.save(wallet);
        paymentService.save(profit, user);
        return profit;
    }

    @Transactional
    public Profit update(int profitId, EditOperationRequest request, User user) throws MoneyFinanceException {
        Profit profit = findByUserIdAndId(user.getId(), profitId);
        Wallet wallet = profit.getWallet();
        walletValidator.checkWalletIsInactive(wallet);
        BigDecimal delta = profit.getMoneyCount().subtract(request.getMoneyCount()).abs();
        if (profit.getMoneyCount().compareTo(request.getMoneyCount()) > 0) { // too much in profit
            wallet.setMoneyCount(wallet.getMoneyCount().subtract(delta));
        } else if (profit.getMoneyCount().compareTo(request.getMoneyCount()) < 0) { // too little in profit
            wallet.setMoneyCount(wallet.getMoneyCount().add(delta));
        }
        walletService.save(wallet);
        profit.setName(request.getName());
        profit.setMoneyCount(request.getMoneyCount());
        this.save(profit);
        paymentService.edit(user, profit);
        return profit;
    }

    public Profit archive(int profitId, User user) throws MoneyFinanceException {
        Profit profit = findByUserIdAndId(user.getId(), profitId);
        walletValidator.checkWalletIsInactive(profit.getWallet());
        profit.setState(State.REMOVED);
        this.save(profit);
        return profit;
    }

    public List<Profit> getAllBetweenDates(User user, ZonedDateTime from, ZonedDateTime to) {
        return profitRepository
                .findAllByWalletUserIdAndDateTimeBetween(user.getId(), from, to)
                .stream()
                .filter(profit -> profit.getWallet().getState().equals(State.ACTIVE))
                .filter(profit -> profit.getState().equals(State.ACTIVE))
                .collect(Collectors.toList());
    }

    @Override
    public Profit inflateEntity(ProfitRequest request, Profit profit) {
        profit.setState(State.ACTIVE);
        profit.setMoneyCount(request.getMoneyCount());
        profit.setName(request.getName());
        return profit;
    }

    @Override
    public Profit findByUserIdAndId(Integer userId, Integer entityId) throws MoneyFinanceException {
        Profit profit = profitRepository.findByIdAndWalletUserId(entityId, userId)
                .orElseThrow(() -> new MoneyFinanceException(MoneyFinanceErrorCode.PROFIT_NOT_EXIST));
        profitValidator.checkProfitIsInactive(profit);
        return profit;
    }

    @Override
    public void save(Profit profit) {
        profitRepository.save(profit);
        LOGGER.info("Save profit {}", profit);
        auditService.save(profit, converter);
    }
}
