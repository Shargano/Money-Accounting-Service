package com.money.accounting.backend.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public class LimitRequest {
    @NotNull(message = "MoneyCount can't be null")
    @PositiveOrZero(message = "MoneyCount can't be negative")
    private BigDecimal moneyCount;

    @NotBlank(message = "DateFrom can't be empty")
    private String dateFrom;

    @NotBlank(message = "DateTo can't be empty")
    private String dateTo;

    public LimitRequest() {
    }

    public BigDecimal getMoneyCount() {
        return moneyCount;
    }

    public void setMoneyCount(BigDecimal moneyCount) {
        this.moneyCount = moneyCount;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }
}
