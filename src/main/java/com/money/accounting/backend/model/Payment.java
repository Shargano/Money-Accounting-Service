package com.money.accounting.backend.model;

import com.money.accounting.backend.model.enums.PaymentType;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Immutable
public class Payment extends MoneyEntity {
    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private PaymentType type;
    private Integer goalId;// id of profit/loss
    private ZonedDateTime dateTime;
    private BigDecimal moneyCount;

    @OneToMany(mappedBy = "parentPayment")
    private List<Payment> correctingPayments;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    private Payment parentPayment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    public Payment() {
    }

    public Payment(PaymentType type, Integer goalId, ZonedDateTime dateTime, BigDecimal moneyCount, Wallet wallet) {
        this.type = type;
        this.goalId = goalId;
        this.dateTime = dateTime;
        this.moneyCount = moneyCount;
        this.wallet = wallet;
    }

    public PaymentType getType() {
        return type;
    }

    public void setType(PaymentType type) {
        this.type = type;
    }

    public Integer getGoalId() {
        return goalId;
    }

    public void setGoalId(Integer goalId) {
        this.goalId = goalId;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public BigDecimal getMoneyCount() {
        return moneyCount;
    }

    public void setMoneyCount(BigDecimal moneyCount) {
        this.moneyCount = moneyCount;
    }

    public List<Payment> getCorrectingPayments() {
        return correctingPayments;
    }

    public void setCorrectingPayments(List<Payment> correctingPayments) {
        this.correctingPayments = correctingPayments;
    }

    public Payment getParentPayment() {
        return parentPayment;
    }

    public void setParentPayment(Payment parentPayment) {
        this.parentPayment = parentPayment;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }
}
