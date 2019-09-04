package com.money.accounting.backend.service;

import com.money.accounting.backend.converter.LossConverter;
import com.money.accounting.backend.dto.request.EditOperationRequest;
import com.money.accounting.backend.dto.request.LossRequest;
import com.money.accounting.backend.exception.MoneyFinanceErrorCode;
import com.money.accounting.backend.exception.MoneyFinanceException;
import com.money.accounting.backend.model.Loss;
import com.money.accounting.backend.model.User;
import com.money.accounting.backend.model.Wallet;
import com.money.accounting.backend.model.enums.State;
import com.money.accounting.backend.repository.LossRepository;
import com.money.accounting.backend.validator.LossValidator;
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
public class LossService extends EntityService<Loss, LossRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(LossService.class);

    private final WalletValidator walletValidator;
    private final WalletService walletService;
    private final LossRepository lossRepository;
    private final LossValidator lossValidator;
    private final PaymentService paymentService;
    private final AuditService auditService;
    private final LossConverter converter;

    public LossService(WalletValidator walletValidator,
                       WalletService walletService,
                       LossRepository lossRepository,
                       LossValidator lossValidator,
                       PaymentService paymentService,
                       AuditService auditService,
                       LossConverter converter) {
        this.walletValidator = walletValidator;
        this.walletService = walletService;
        this.lossRepository = lossRepository;
        this.lossValidator = lossValidator;
        this.paymentService = paymentService;
        this.auditService = auditService;
        this.converter = converter;
    }

    @Transactional
    public Loss create(LossRequest request, User user) throws MoneyFinanceException {
        Wallet wallet = walletService.findByUserIdAndId(user.getId(), request.getWalletId());
        walletValidator.checkWalletIsInactive(wallet);
        walletValidator.checkWalletIsEnoughMoney(wallet, request.getMoneyCount());
        Loss loss = inflateEntity(request, new Loss());
        loss.setWallet(wallet);
        loss.setDateTime(ZonedDateTime.now(ZoneId.of(user.getTimeZone())));
        this.save(loss);
        wallet.setMoneyCount(wallet.getMoneyCount().subtract(loss.getMoneyCount()));
        walletService.save(wallet);
        paymentService.save(loss, user);
        return loss;
    }

    public Loss update(int lossId, EditOperationRequest request, User user) throws MoneyFinanceException {
        Loss loss = findByUserIdAndId(user.getId(), lossId);
        Wallet wallet = loss.getWallet();
        walletValidator.checkWalletIsInactive(wallet);
        walletValidator.checkWalletIsEnoughMoneyForEdit(wallet, loss, request.getMoneyCount());
        BigDecimal delta = loss.getMoneyCount().subtract(request.getMoneyCount()).abs();
        if (loss.getMoneyCount().compareTo(request.getMoneyCount()) < 0) { // too little in loss
            wallet.setMoneyCount(wallet.getMoneyCount().subtract(delta));
        } else if (loss.getMoneyCount().compareTo(request.getMoneyCount()) > 0) { // too much in loss
            wallet.setMoneyCount(wallet.getMoneyCount().add(delta));
        }
        walletService.save(wallet);
        loss.setName(request.getName());
        loss.setMoneyCount(request.getMoneyCount());
        this.save(loss);
        paymentService.edit(user, loss);
        return loss;
    }

    public Loss archive(int lossId, User user) throws MoneyFinanceException {
        Loss loss = findByUserIdAndId(user.getId(), lossId);
        walletValidator.checkWalletIsInactive(loss.getWallet());
        loss.setState(State.REMOVED);
        this.save(loss);
        return loss;
    }

    public List<Loss> getAllBetweenDates(User user, ZonedDateTime from, ZonedDateTime to) {
        return lossRepository
                .findAllByWalletUserIdAndDateTimeBetween(user.getId(), from, to)
                .stream()
                .filter(loss -> loss.getWallet().getState().equals(State.ACTIVE))
                .filter(loss -> loss.getState().equals(State.ACTIVE))
                .collect(Collectors.toList());
    }

    @Override
    public Loss inflateEntity(LossRequest request, Loss loss) {
        loss.setMoneyCount(request.getMoneyCount());
        loss.setName(request.getName());
        loss.setState(State.ACTIVE);
        return loss;
    }

    @Override
    public Loss findByUserIdAndId(Integer userId, Integer entityId) throws MoneyFinanceException {
        Loss loss = lossRepository.findByIdAndWalletUserId(entityId, userId)
                .orElseThrow(() -> new MoneyFinanceException(MoneyFinanceErrorCode.LOSS_NOT_EXIST));
        lossValidator.checkLossIsInactive(loss);
        return loss;
    }

    @Override
    public void save(Loss loss) {
        lossRepository.save(loss);
        LOGGER.info("Save loss {}", loss);
        auditService.save(loss, converter);
    }
}
