package com.money.accounting.backend.service;

import com.money.accounting.backend.converter.PaymentConverter;
import com.money.accounting.backend.exception.MoneyFinanceErrorCode;
import com.money.accounting.backend.exception.MoneyFinanceException;
import com.money.accounting.backend.model.Loss;
import com.money.accounting.backend.model.Payment;
import com.money.accounting.backend.model.Profit;
import com.money.accounting.backend.model.Transfer;
import com.money.accounting.backend.model.User;
import com.money.accounting.backend.model.enums.PaymentType;
import com.money.accounting.backend.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class PaymentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentService.class);

    private final PaymentRepository paymentRepository;
    private final AuditService auditService;
    private final PaymentConverter converter;

    public PaymentService(PaymentRepository paymentRepository, AuditService auditService, PaymentConverter converter) {
        this.paymentRepository = paymentRepository;
        this.auditService = auditService;
        this.converter = converter;
    }

    public void save(Profit profit, User user) {
        Payment payment = inflateEntity(profit, PaymentType.CREATE_PROFIT);
        payment.setDateTime(ZonedDateTime.now(ZoneId.of(user.getTimeZone())));
        save(payment);
    }

    public void save(Loss loss, User user) {
        Payment payment = inflateEntity(loss, PaymentType.CREATE_LOSS);
        payment.setDateTime(ZonedDateTime.now(ZoneId.of(user.getTimeZone())));
        save(payment);
    }

    public void save(Transfer transfer, User user) {
        Payment paymentFrom = inflateTransferFrom(transfer);
        paymentFrom.setDateTime(ZonedDateTime.now(ZoneId.of(user.getTimeZone())));
        Payment paymentTo = inflateTransferTo(transfer);
        paymentTo.setDateTime(ZonedDateTime.now(ZoneId.of(user.getTimeZone())));
        save(paymentFrom);
        save(paymentTo);
    }

    public void edit(User user, Profit profit) throws MoneyFinanceException {
        Payment parent = paymentRepository.findByWalletUserIdAndGoalIdAndTypeAndParentPaymentIsNull(user.getId(), profit.getId(), PaymentType.CREATE_PROFIT)
                .orElseThrow(() -> new MoneyFinanceException(MoneyFinanceErrorCode.PAYMENT_NOT_EXIST));
        Payment correctingPayment = inflateEntity(profit, PaymentType.EDIT_PROFIT);
        correctingPayment.setParentPayment(parent);
        correctingPayment.setDateTime(parent.getDateTime());
        save(correctingPayment);
    }

    public void edit(User user, Loss loss) throws MoneyFinanceException {
        Payment parent = paymentRepository.findByWalletUserIdAndGoalIdAndTypeAndParentPaymentIsNull(user.getId(), loss.getId(), PaymentType.CREATE_LOSS)
                .orElseThrow(() -> new MoneyFinanceException(MoneyFinanceErrorCode.PAYMENT_NOT_EXIST));
        Payment correctingPayment = inflateEntity(loss, PaymentType.EDIT_LOSS);
        correctingPayment.setParentPayment(parent);
        correctingPayment.setDateTime(parent.getDateTime());
        save(correctingPayment);
    }

    private Payment inflateEntity(Profit profit, PaymentType type) {
        Payment payment = new Payment();
        payment.setGoalId(profit.getId());
        payment.setMoneyCount(profit.getMoneyCount());
        payment.setWallet(profit.getWallet());
        payment.setType(type);
        return payment;
    }

    private Payment inflateEntity(Loss loss, PaymentType type) {
        Payment payment = new Payment();
        payment.setGoalId(loss.getId());
        payment.setMoneyCount(loss.getMoneyCount());
        payment.setWallet(loss.getWallet());
        payment.setType(type);
        return payment;
    }

    private Payment inflateTransferFrom(Transfer transfer) {
        Payment payment = new Payment();
        payment.setGoalId(transfer.getTo().getId());
        payment.setMoneyCount(transfer.getMoneyCount());
        payment.setWallet(transfer.getFrom());
        payment.setType(PaymentType.TRANSFER_FROM);
        return payment;
    }

    private Payment inflateTransferTo(Transfer transfer) {
        Payment payment = new Payment();
        payment.setGoalId(transfer.getTo().getId());
        payment.setMoneyCount(transfer.getMoneyCount());
        payment.setWallet(transfer.getTo());
        payment.setType(PaymentType.TRANSFER_TO);
        return payment;
    }

    public void save(Payment payment) {
        paymentRepository.save(payment);
        auditService.save(payment, converter);
        LOGGER.info("Save payment {}", payment);
    }
}
