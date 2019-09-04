package com.money.accounting.backend.dto.response;

import com.money.accounting.backend.model.enums.State;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class LossResponse {
    private Integer id;
    private String name;
    private BigDecimal moneyCount;
    private State state;
    private ZonedDateTime dateTime;

    public LossResponse() {
    }

    public Integer getId() {
        return id;
    }

    public LossResponse setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public LossResponse setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getMoneyCount() {
        return moneyCount;
    }

    public LossResponse setMoneyCount(BigDecimal moneyCount) {
        this.moneyCount = moneyCount;
        return this;
    }

    public State getState() {
        return state;
    }

    public LossResponse setState(State state) {
        this.state = state;
        return this;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public LossResponse setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    @Override
    public String toString() {
        return "LossResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", moneyCount=" + moneyCount +
                ", state=" + state +
                ", dateTime=" + dateTime +
                '}';
    }
}
