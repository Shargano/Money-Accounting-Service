package com.money.accounting.backend.model;

import com.money.accounting.backend.model.enums.State;
import org.apache.commons.lang3.builder.EqualsBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
public class Wallet extends MoneyEntity {
    private String name;
    private BigDecimal moneyCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "wallet", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Limit> limits;

    @OneToMany(mappedBy = "wallet", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Profit> profits;

    @OneToMany(mappedBy = "wallet", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Loss> losses;

    @OneToMany(mappedBy = "wallet", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Payment> payments;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private State state;

    public Wallet(String name, BigDecimal moneyCount) {
        this.name = name;
        this.moneyCount = moneyCount;
    }

    public Wallet() {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public List<Limit> getLimits() {
        return limits;
    }

    public void setLimits(List<Limit> limits) {
        this.limits = limits;
    }

    public List<Profit> getProfits() {
        return profits;
    }

    public void setProfits(List<Profit> profits) {
        this.profits = profits;
    }

    public List<Loss> getLosses() {
        return losses;
    }

    public void setLosses(List<Loss> losses) {
        this.losses = losses;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getMoneyCount());
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", moneyCount=" + moneyCount +
                ", user=" + user +
                '}';
    }
}
