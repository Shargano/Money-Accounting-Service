package com.money.accounting.backend.dto.response;

import com.money.accounting.backend.model.enums.State;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class ProfitResponse {
    private Integer id;
    private String name;
    private BigDecimal moneyCount;
    private State state;
    private ZonedDateTime dateTime;

    public ProfitResponse() {
    }

    public Integer getId() {
        return id;
    }

    public ProfitResponse setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProfitResponse setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getMoneyCount() {
        return moneyCount;
    }

    public ProfitResponse setMoneyCount(BigDecimal moneyCount) {
        this.moneyCount = moneyCount;
        return this;
    }

    public State getState() {
        return state;
    }

    public ProfitResponse setState(State state) {
        this.state = state;
        return this;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public ProfitResponse setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    @Override
    public String toString() {
        return "ProfitResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", moneyCount=" + moneyCount +
                ", state=" + state +
                ", dateTime=" + dateTime +
                '}';
    }
}