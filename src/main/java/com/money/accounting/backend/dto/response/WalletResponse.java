package com.money.accounting.backend.dto.response;

import com.money.accounting.backend.model.enums.State;

import java.math.BigDecimal;
import java.util.List;

public class WalletResponse {
    private Integer id;
    private String name;
    private BigDecimal moneyCount;
    private State state;
    private List<LimitResponse> limits;

    public WalletResponse() {
    }

    public Integer getId() {
        return id;
    }

    public WalletResponse setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public WalletResponse setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getMoneyCount() {
        return moneyCount;
    }

    public WalletResponse setMoneyCount(BigDecimal moneyCount) {
        this.moneyCount = moneyCount;
        return this;
    }

    public State getState() {
        return state;
    }

    public WalletResponse setState(State state) {
        this.state = state;
        return this;
    }

    public List<LimitResponse> getLimits() {
        return limits;
    }

    public WalletResponse setLimits(List<LimitResponse> limits) {
        this.limits = limits;
        return this;
    }

    @Override
    public String toString() {
        return "WalletResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", moneyCount=" + moneyCount +
                ", state=" + state +
                '}';
    }
}
