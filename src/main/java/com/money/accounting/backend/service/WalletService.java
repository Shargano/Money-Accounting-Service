package com.money.accounting.backend.service;

import com.money.accounting.backend.converter.WalletConverter;
import com.money.accounting.backend.dto.request.TransferRequest;
import com.money.accounting.backend.dto.request.WalletRequest;
import com.money.accounting.backend.exception.MoneyFinanceErrorCode;
import com.money.accounting.backend.exception.MoneyFinanceException;
import com.money.accounting.backend.model.Payment;
import com.money.accounting.backend.model.Transfer;
import com.money.accounting.backend.model.User;
import com.money.accounting.backend.model.Wallet;
import com.money.accounting.backend.model.enums.PaymentType;
import com.money.accounting.backend.model.enums.State;
import com.money.accounting.backend.repository.LossRepository;
import com.money.accounting.backend.repository.PaymentRepository;
import com.money.accounting.backend.repository.ProfitRepository;
import com.money.accounting.backend.repository.WalletRepository;
import com.money.accounting.backend.validator.WalletValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WalletService extends EntityService<Wallet, WalletRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(WalletService.class);

    private final WalletRepository walletRepository;
    private final PaymentRepository paymentRepository;
    private final ProfitRepository profitRepository;
    private final LossRepository lossRepository;
    private final PaymentService paymentService;
    private final WalletValidator walletValidator;
    private final AuditService auditService;
    private final WalletConverter converter;

    public WalletService(WalletRepository walletRepository,
                         PaymentRepository paymentRepository,
                         ProfitRepository profitRepository,
                         LossRepository lossRepository,
                         PaymentService paymentService,
                         WalletValidator walletValidator,
                         AuditService auditService,
                         WalletConverter converter) {
        this.walletRepository = walletRepository;
        this.paymentRepository = paymentRepository;
        this.profitRepository = profitRepository;
        this.lossRepository = lossRepository;
        this.paymentService = paymentService;
        this.walletValidator = walletValidator;
        this.auditService = auditService;
        this.converter = converter;
    }

    public Wallet create(WalletRequest request, User currentUser) throws MoneyFinanceException {
        Wallet wallet = inflateEntity(request, new Wallet());
        walletValidator.checkUserAlreadyHaveWallet(currentUser, wallet);
        wallet.setUser(currentUser);
        wallet.setLimits(new ArrayList<>());
        this.save(wallet);
        return wallet;
    }

    public Wallet get(User user, int walletId) throws MoneyFinanceException {
        return walletRepository.findByUserIdAndId(user.getId(), walletId)
                .filter(w -> w.getState().equals(State.ACTIVE))
                .orElseThrow(() -> new MoneyFinanceException(MoneyFinanceErrorCode.WALLET_NOT_EXIST));
    }

    public List<Wallet> getAllWallets(User user) {
        return walletRepository.findAllByUserId(user.getId())
                .stream().filter(w -> w.getState().equals(State.ACTIVE))
                .collect(Collectors.toList());
    }

    public List<Payment> getPayments(Wallet wallet) {
        return paymentRepository.findAllByWalletIdAndParentPaymentIsNull(wallet.getId())
                .stream().filter(payment -> { // TODO refactor it
                    if (payment.getType().equals(PaymentType.CREATE_PROFIT) || payment.getType().equals(PaymentType.EDIT_PROFIT)) {
                        return profitRepository.findById(payment.getGoalId()).get().getState().equals(State.ACTIVE);
                    } else if (payment.getType().equals(PaymentType.CREATE_LOSS) || payment.getType().equals(PaymentType.EDIT_LOSS)) {
                        return lossRepository.findById(payment.getGoalId()).get().getState().equals(State.ACTIVE);
                    } else { // transfer
                        return true;
                    }
                })
                .map(payment -> {
                    if (payment.getCorrectingPayments() != null && !payment.getCorrectingPayments().isEmpty())
                        return payment.getCorrectingPayments().get(payment.getCorrectingPayments().size() - 1);
                    return payment;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public Transfer makeTransfer(TransferRequest request, User user) throws MoneyFinanceException {
        Wallet walletFrom = findByUserIdAndId(user.getId(), request.getWalletFrom());
        Wallet walletTo = findByUserIdAndId(user.getId(), request.getWalletTo());
        walletValidator.checkWalletIsEnoughMoney(walletFrom, request.getMoneyCount());
        walletFrom.setMoneyCount(walletFrom.getMoneyCount().subtract(request.getMoneyCount()));
        walletTo.setMoneyCount(walletTo.getMoneyCount().add(request.getMoneyCount()));
        this.save(walletFrom);
        this.save(walletTo);
        Transfer transfer = new Transfer(request.getMoneyCount(), walletFrom, walletTo);
        paymentService.save(transfer, user);
        return transfer;
    }

    public Wallet update(int walletId, WalletRequest request, User user) throws MoneyFinanceException {
        Wallet wallet = findByUserIdAndId(user.getId(), walletId);
        walletValidator.checkWalletIsInactive(wallet);
        wallet.setName(request.getName());
        wallet.setMoneyCount(request.getMoneyCount());
        this.save(wallet);
        return wallet;
    }

    public Wallet archive(int walletId, User user) throws MoneyFinanceException {
        Wallet wallet = findByUserIdAndId(user.getId(), walletId);
        walletValidator.checkWalletIsInactive(wallet);
        wallet.setState(State.REMOVED);
        this.save(wallet);
        return wallet;
    }

    @Override
    public Wallet inflateEntity(WalletRequest request, Wallet wallet) {
        wallet.setMoneyCount(request.getMoneyCount());
        wallet.setName(request.getName());
        wallet.setState(State.ACTIVE);
        return wallet;
    }

    @Override
    public Wallet findByUserIdAndId(Integer userId, Integer entityId) throws MoneyFinanceException {
        Wallet wallet = walletRepository.findByUserIdAndId(userId, entityId)
                .orElseThrow(() -> new MoneyFinanceException(MoneyFinanceErrorCode.WALLET_NOT_EXIST));
        walletValidator.checkWalletIsInactive(wallet);
        return wallet;
    }

    @Override
    public void save(Wallet wallet) {
        walletRepository.save(wallet);
        LOGGER.info("Save wallet {}", wallet);
        auditService.save(wallet, converter);
    }

}