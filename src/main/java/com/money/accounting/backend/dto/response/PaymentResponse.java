package com.money.accounting.backend.dto.response;

import com.money.accounting.backend.model.enums.PaymentType;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

public class PaymentResponse {
    private Integer id;
    private PaymentType type;
    private Integer goalId; // id of profit/loss
    private ZonedDateTime dateTime;
    private BigDecimal moneyCount;
    private List<PaymentResponse> correctingPayments;

    public PaymentResponse() {
    }

    public Integer getId() {
        return id;
    }

    public PaymentResponse setId(Integer id) {
        this.id = id;
        return this;
    }

    public PaymentType getType() {
        return type;
    }

    public PaymentResponse setType(PaymentType type) {
        this.type = type;
        return this;
    }

    public Integer getGoalId() {
        return goalId;
    }

    public PaymentResponse setGoalId(Integer goalId) {
        this.goalId = goalId;
        return this;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public PaymentResponse setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public BigDecimal getMoneyCount() {
        return moneyCount;
    }

    public PaymentResponse setMoneyCount(BigDecimal moneyCount) {
        this.moneyCount = moneyCount;
        return this;
    }

    public List<PaymentResponse> getCorrectingPayments() {
        return correctingPayments;
    }

    public PaymentResponse setCorrectingPayments(List<PaymentResponse> correctingPayments) {
        this.correctingPayments = correctingPayments;
        return this;
    }

    @Override
    public String toString() {
        return "PaymentResponse{" +
                "id=" + id +
                ", type=" + type +
                ", goalId=" + goalId +
                ", dateTime=" + dateTime +
                ", moneyCount=" + moneyCount +
                ", correctingPayments=" + correctingPayments +
                '}';
    }
}
