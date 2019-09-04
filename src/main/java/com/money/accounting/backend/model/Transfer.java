package com.money.accounting.backend.model;

import java.math.BigDecimal;

public class Transfer extends MoneyEntity {
    private BigDecimal moneyCount;
    private Wallet from;
    private Wallet to;

    public Transfer() {
    }

    public Transfer(BigDecimal moneyCount, Wallet from, Wallet to) {
        this.moneyCount = moneyCount;
        this.from = from;
        this.to = to;
    }

    public BigDecimal getMoneyCount() {
        return moneyCount;
    }

    public void setMoneyCount(BigDecimal moneyCount) {
        this.moneyCount = moneyCount;
    }

    public Wallet getFrom() {
        return from;
    }

    public void setFrom(Wallet from) {
        this.from = from;
    }

    public Wallet getTo() {
        return to;
    }

    public void setTo(Wallet to) {
        this.to = to;
    }
}
