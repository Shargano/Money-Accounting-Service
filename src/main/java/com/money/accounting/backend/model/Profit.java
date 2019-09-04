package com.money.accounting.backend.model;

import com.money.accounting.backend.model.enums.State;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
public class Profit extends MoneyEntity {

    private String name;
    private BigDecimal moneyCount;
    private ZonedDateTime dateTime;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private State state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    @ManyToMany(mappedBy = "profits")
    private List<Report> reports;

    public Profit() {
    }

    public Profit(String name, BigDecimal moneyCount, ZonedDateTime dateTime, State state, Wallet wallet) {
        this.name = name;
        this.moneyCount = moneyCount;
        this.dateTime = dateTime;
        this.state = state;
        this.wallet = wallet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getMoneyCount() {
        return moneyCount;
    }

    public void setMoneyCount(BigDecimal moneyCount) {
        this.moneyCount = moneyCount;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    @Override
    public String toString() {
        return "Profit{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", moneyCount=" + moneyCount +
                ", dateTime=" + dateTime +
                ", state=" + state +
                ", wallet=" + wallet +
                '}';
    }
}