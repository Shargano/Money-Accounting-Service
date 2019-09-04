package com.money.accounting.backend.dto.response;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class LimitResponse {
    private Integer id;
    private BigDecimal moneyCount;
    private ZonedDateTime dateFrom;
    private ZonedDateTime dateTo;

    public LimitResponse() {
    }

    public Integer getId() {
        return id;
    }

    public LimitResponse setId(Integer id) {
        this.id = id;
        return this;
    }

    public BigDecimal getMoneyCount() {
        return moneyCount;
    }

    public LimitResponse setMoneyCount(BigDecimal moneyCount) {
        this.moneyCount = moneyCount;
        return this;
    }

    public ZonedDateTime getDateFrom() {
        return dateFrom;
    }

    public LimitResponse setDateFrom(ZonedDateTime dateFrom) {
        this.dateFrom = dateFrom;
        return this;
    }

    public ZonedDateTime getDateTo() {
        return dateTo;
    }

    public LimitResponse setDateTo(ZonedDateTime dateTo) {
        this.dateTo = dateTo;
        return this;
    }
}
